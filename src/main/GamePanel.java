package main;

import main.entity.*;
import main.graphics.Arrow;
import main.state.GameState;
import main.util.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private int width;
    private int height;
    private int towerWidth;
    private int towerHeight;

    private Tower playerTower;
    private Tower enemyTower;

    private final int characterStartX;
    private final int characterStartY;

    private ArrayList<Entity> playerEntities = new ArrayList<>();
    List<Arrow> projectiles = new ArrayList<>();
    private GameState gameState;
    private List<CharacterCard> availableCharacters;

    private InputHandler inputHandler;
    private BufferedImage staticLayer;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.towerWidth = width/4;
        this.towerHeight = towerWidth;
        this.characterStartX = towerWidth+5;
        this.characterStartY = height-100;

        this.playerTower = new Tower(0,characterStartY-towerHeight+100, towerWidth, towerHeight, 800, 1);
        this.enemyTower = new Tower(width-towerWidth-1,characterStartY-towerHeight+100, towerWidth, towerHeight, 800, 1);

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        gameState = new GameState(1, 3000, 100);

        availableCharacters = CharacterDefinitions.getLevel1Characters();

        inputHandler = new InputHandler(this);
        addMouseListener(inputHandler);
        setFocusable(true);

        createStaticLayer();
    }

    public void update(double delta) {
        for (Entity entity : playerEntities){
            Rectangle bounds = entity.getBounds();
            boolean canWalk = true;
            for (Entity other : playerEntities) {
                if (!entity.equals(other) && bounds.x < other.getBounds().x && Math.abs(bounds.x - other.getBounds().x) < bounds.width+other.getBounds().width)
                    canWalk = false;
            }
            if(bounds.x+bounds.width >= this.width-this.towerWidth-10)
                canWalk = false;

            long now = System.currentTimeMillis();

            if (canWalk) {
                entity.walk();
            }else if(bounds.x+bounds.width+entity.getRange() >= enemyTower.getX()){
                if(now - entity.getLastAttackTime() >= entity.getRecoilTime()) {
                    enemyTower.setHealth(enemyTower.getHealth() - entity.getDamage());
                    entity.setLastAttackTime(now);
                }
                entity.setAttacksTower(true);
                entity.attack();

            } else{
                entity.idle();
            }
            entity.update(delta);
        }

        for (int i = 0; i < projectiles.size(); i++) {
            Arrow a = projectiles.get(i);
            a.update();
            if (!a.isActive()) {
                projectiles.remove(i);
                i--;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // draw background and towers (once)
        if (staticLayer != null) {
            g2d.drawImage(staticLayer, 0, 0, null);
        }

        drawUI(g2d);

        // draw player entities
        for(Entity e : playerEntities){
            e.render(g2d);
        }
        // draw projectiles
        for (Arrow p : projectiles) {
            p.draw(g2d);
        }

        g2d.dispose();
    }

    public void drawUI(Graphics2D g2d) {
        int cardStartX = 30;
        int cardStartY = 30;
        int padding = 2;
        int btnPerRow = 6;

        // meta data
        g2d.setColor(Color.WHITE);
        g2d.drawString("Money: " + gameState.getMoney(), cardStartX, cardStartY-10);
        g2d.drawString("soldiers: " + playerEntities.size()+"/10", cardStartX+100,cardStartY-10);

        // tower health bars
        playerTower.drawHealthBar(g2d);
        enemyTower.drawHealthBar(g2d);

        for (int i = 0; i < availableCharacters.size(); i++) {
            int x = cardStartX + (i % btnPerRow) * (56 + padding);
            int y = cardStartY + (i / btnPerRow) * (108 + padding);

            availableCharacters.get(i).renderButton(g2d, x, y);
        }
    }

    public void checkCharacterClick(int mx, int my) {
        int startX = 30;
        int startY = 30;
        int padding = 2;
        int btnPerRow = 6;
        int btnWidth = 56;
        int btnHeight = 56;

        for (int i = 0; i < availableCharacters.size(); i++) {
            int x = startX + (i % btnPerRow) * (btnWidth + padding);
            int y = startY + (i / btnPerRow) * (btnHeight + padding + 56);

            if (mx >= x && mx <= x + btnWidth &&
                    my >= y && my <= y + btnHeight) {

                drawClickedCharacter(availableCharacters.get(i));
                break;
            }
        }
    }
    public void drawClickedCharacter(CharacterCard character){
        if(gameState.getMoney() < character.getPrice() || playerEntities.size() >= 10) return;

        gameState.setMoney(gameState.getMoney()-character.getPrice());
        Entity s = EntityFactory.createEntityOf(character, characterStartX, characterStartY, this);
        playerEntities.add(s);
    }

    public void addProjectile(Arrow proj){
        projectiles.add(proj);
    }

    public Tower getEnemyTower() {
        return enemyTower;
    }

    public void createStaticLayer() {
        staticLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = staticLayer.createGraphics();

        // draw background
        g2d.drawImage(gameState.getLvlBGImage(), 0, 0, width, height, null);

        // draw towers
        playerTower.draw(g2d, true);
        enemyTower.draw(g2d, false);

        g2d.dispose();
    }
}