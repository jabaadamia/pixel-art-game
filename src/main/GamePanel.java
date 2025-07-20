package main;

import main.characters.cards.CharacterCard;
import main.characters.cards.CharacterDefinitions;
import main.characters.entities.Entity;
import main.characters.entities.EntityFactory;
import main.enemies.EnemySpawner;
import main.projectiles.Arrow;
import main.state.GameState;
import main.state.LevelDefinitions;
import main.towers.Tower;
import main.util.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int TOWER_WIDTH;
    private final int TOWER_HEIGHT;

    private final float KILL_MONEY_PERCENT = 1.33f; // When killing entity get 133% of its price

    private Tower playerTower;
    private Tower enemyTower;

    // where entities spawn
    private final int CHARACTER_START_X;
    private final int CHARACTER_START_Y;

    private ArrayList<Entity> playerEntities = new ArrayList<>();
    private ArrayList<Entity> enemyEntities = new ArrayList<>();
    List<Arrow> projectiles = new ArrayList<>();
    private GameState gameState;
    private EnemySpawner enemySpawner;
    private List<CharacterCard> playerAvailableCharacters;

    private InputHandler inputHandler;
    private BufferedImage staticLayer;

    private boolean playerWon = false;
    private boolean gameOver = false;
    private boolean showRestartButton = false;

    public GamePanel(int width, int height) {
        this.PANEL_WIDTH = width;
        this.PANEL_HEIGHT = height;
        this.TOWER_WIDTH = width/4;
        this.TOWER_HEIGHT = TOWER_WIDTH;
        this.CHARACTER_START_X = TOWER_WIDTH +5;
        this.CHARACTER_START_Y = height-200;

        createInitialTowers();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        createInitialGameState();

        enemySpawner = new EnemySpawner(this, gameState);
        playerAvailableCharacters = CharacterDefinitions.getLevel1Characters();

        inputHandler = new InputHandler(this);
        addMouseListener(inputHandler);
        setFocusable(true);

        createStaticLayer();
    }

    public void update(double delta) {
        if (gameOver) return;

        for (int i = 0; i < playerEntities.size(); i++) {
            Entity entity = playerEntities.get(i);
            Rectangle bounds = entity.getBounds();
            boolean canWalk = true;

            Entity nextFriendly = i != 0  ? playerEntities.get(i-1) : null;
            Entity closestOpponent = !enemyEntities.isEmpty() ? enemyEntities.getFirst() : null;


            // if overlaps to next friendly entity or overlaps to enemy tower or overlaps to first enemy
            if (
                nextFriendly != null && Math.abs(bounds.x - nextFriendly.getBounds().x) < bounds.width+nextFriendly.getBounds().width ||
                bounds.x+bounds.width >= this.PANEL_WIDTH -this.TOWER_WIDTH -10 ||
                (closestOpponent != null && bounds.x+bounds.width >= closestOpponent.getBounds().x)
            )
                canWalk = false;

            long now = System.currentTimeMillis();

            if (canWalk) {
                entity.walk();
            }else if(
                    bounds.x+bounds.width+entity.getRange() >= enemyTower.getX() ||
                    (closestOpponent != null && bounds.x+bounds.width+entity.getRange() >= closestOpponent.getBounds().x)
            ){
                if(now - entity.getLastAttackTime() >= entity.getRecoilTime()) {
                    if(enemyEntities.isEmpty())
                        entity.setAttacksTower(true);
                    else
                        entity.setAttacksTower(false);

                    entity.setLastAttackTime(now);
                }
                entity.attack();
                if(enemyTower.getHealth() == 0){
                    showGameOverButton();
                    playerWon = true;
                }

            } else{
                entity.idle();
            }

            if(entity.getHealth()<=0) {
                playerEntities.remove(entity);
                gameState.addToEnemyMoney((int) (entity.getPrice()*KILL_MONEY_PERCENT));
                gameState.addToEnemyXP(entity.getPrice());
                if (gameState.getEnemyXP() >= gameState.getCurrentLevelXP(false))
                    levelUp(false);
            }
            entity.update(delta);
        }
        for (int i = 0; i < enemyEntities.size(); i++) {
            Entity entity = enemyEntities.get(i);
            Rectangle bounds = entity.getBounds();
            boolean canWalk = true;

            Entity nextFriendly = i != 0  ? enemyEntities.get(i-1) : null;
            Entity closestOpponent = !playerEntities.isEmpty() ? playerEntities.getFirst() : null;

            // if overlaps to next friendly entity or overlaps to enemy tower or overlaps to first enemy
            if (
                nextFriendly != null && Math.abs(bounds.x - nextFriendly.getBounds().x) < bounds.width+nextFriendly.getBounds().width ||
                bounds.x <= this.TOWER_WIDTH ||
                (closestOpponent != null && bounds.x-bounds.width <= closestOpponent.getBounds().x)
            )
                canWalk = false;

            long now = System.currentTimeMillis();

            if (canWalk) {
                entity.walk();
            }else if(bounds.x-entity.getRange() <= playerTower.getX()+TOWER_WIDTH ||
                    (closestOpponent != null && bounds.x-entity.getRange() <= closestOpponent.getBounds().x+closestOpponent.getBounds().width)){

                if(now - entity.getLastAttackTime() >= entity.getRecoilTime()) {
                    if(playerEntities.isEmpty())
                        entity.setAttacksTower(true);
                    else
                        entity.setAttacksTower(false);

                    entity.setLastAttackTime(now);
                }

                entity.attack();
                if(playerTower.getHealth() == 0){
                    showGameOverButton();
                    playerWon = false;
                }

            } else{
                entity.idle();
            }
            if(entity.getHealth()<=0) {
                enemyEntities.remove(entity);
                gameState.addToPlayerMoney((int) (entity.getPrice()*KILL_MONEY_PERCENT));
                gameState.addToPlayerXP(entity.getPrice());
                if (gameState.getPlayerXP() > gameState.getCurrentLevelXP(true))
                    levelUp(true);
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
        enemySpawner.spawn();
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
        // draw enemy entities
        for(Entity e : enemyEntities){
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
        g2d.drawString("Money: " + gameState.getPlayerMoney(), cardStartX, cardStartY-10);
        g2d.drawString("soldiers: " + playerEntities.size()+"/10", cardStartX+100,cardStartY-10);
        g2d.drawString("XP: " + gameState.getPlayerXP()+"/"+ gameState.getCurrentLevelXP(true), cardStartX+200,cardStartY-10);
        g2d.drawString("LVL: " + gameState.getPlayerLevel(), cardStartX+300,cardStartY-10);

        // tower health bars
        playerTower.drawHealthBar(g2d);
        enemyTower.drawHealthBar(g2d);

        for (int i = 0; i < playerAvailableCharacters.size(); i++) {
            int x = cardStartX + (i % btnPerRow) * (56 + padding);
            int y = cardStartY + (i / btnPerRow) * (108 + padding);

            playerAvailableCharacters.get(i).renderButton(g2d, x, y);
        }

        if (gameOver && showRestartButton) {
            drawGameOverScreen(g2d);
        }
    }

    public void checkCharacterClick(int mx, int my) {
        int startX = 30;
        int startY = 30;
        int padding = 2;
        int btnPerRow = 6;
        int btnWidth = 56;
        int btnHeight = 56;

        for (int i = 0; i < playerAvailableCharacters.size(); i++) {
            int x = startX + (i % btnPerRow) * (btnWidth + padding);
            int y = startY + (i / btnPerRow) * (btnHeight + padding + 56);

            if (mx >= x && mx <= x + btnWidth &&
                    my >= y && my <= y + btnHeight) {

                drawClickedCharacter(playerAvailableCharacters.get(i));
                break;
            }
        }
    }
    public void drawClickedCharacter(CharacterCard character){
        if(gameState.getPlayerMoney() < character.getPrice() || playerEntities.size() >= 10) return;

        gameState.setPlayerMoney(gameState.getPlayerMoney()-character.getPrice());
        Entity s = EntityFactory.createEntityOf(character, false, CHARACTER_START_X, CHARACTER_START_Y, this);
        playerEntities.add(s);
    }

    public void levelUp(boolean player){
        if(player){
            int playerLevel = gameState.getPlayerLevel()+1;
            gameState.setPlayerLevel(playerLevel);
            playerTower.levelUp();
            playerAvailableCharacters = CharacterDefinitions.getLevel2Characters(); // fix
        }else{
            int enemyLevel = gameState.getEnemyLevel()+1;
            gameState.setEnemyLevel(enemyLevel);
            enemyTower.levelUp();
            enemySpawner.setEnemyAvailableCharacters(CharacterDefinitions.getLevel2Characters()); // fix
        }
        createStaticLayer();
    }

    public int getCHARACTER_START_X() {
        return CHARACTER_START_X;
    }

    public int getCHARACTER_START_Y() {
        return CHARACTER_START_Y;
    }

    public int getPANEL_WIDTH() {
        return PANEL_WIDTH;
    }

    public void addProjectile(Arrow proj){
        projectiles.add(proj);
    }

    public ArrayList<Entity> getPlayerEntities() {
        return playerEntities;
    }

    public ArrayList<Entity> getEnemyEntities() {
        return enemyEntities;
    }

    public void addEnemyEntity(Entity entity){
        if(enemyEntities.size()<10)
            enemyEntities.add(entity);
    }

    public Tower getEnemyTower() {
        return enemyTower;
    }

    public Tower getPlayerTower() {
        return playerTower;
    }

    public void createStaticLayer() {
        staticLayer = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = staticLayer.createGraphics();

        // draw background
        g2d.drawImage(gameState.getLvlBGImage(), 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);

        // draw towers
        playerTower.draw(g2d, false);
        enemyTower.draw(g2d, true);

        g2d.dispose();
    }

    public void createInitialGameState(){
        gameState = new GameState(1, 1,1000, 1000);
    }

    public void createInitialTowers(){
        this.playerTower = new Tower(0, CHARACTER_START_Y - TOWER_HEIGHT +100, TOWER_WIDTH, TOWER_HEIGHT, LevelDefinitions.LVL1_TOWER_HP, 1);
        this.enemyTower = new Tower(PANEL_WIDTH- TOWER_WIDTH -1, CHARACTER_START_Y - TOWER_HEIGHT +100, TOWER_WIDTH, TOWER_HEIGHT, LevelDefinitions.LVL1_TOWER_HP, 1);
    }

    // RESTART STUFF
    private Rectangle restartButtonBounds;

    private void initializeRestartButton() {
        int RESTART_BUTTON_WIDTH = 120;
        int RESTART_BUTTON_HEIGHT = 40;
        int buttonX = (PANEL_WIDTH - RESTART_BUTTON_WIDTH) / 2;
        int buttonY = PANEL_HEIGHT / 2 + 50;

        restartButtonBounds = new Rectangle(buttonX, buttonY, RESTART_BUTTON_WIDTH, RESTART_BUTTON_HEIGHT);
    }

    public void showGameOverButton() {
        gameOver = true;
        showRestartButton = true;
        if (restartButtonBounds == null) {
            initializeRestartButton();
        }
    }

    public void checkRestartButtonClick(int mx, int my) {
        if (showRestartButton && restartButtonBounds != null && restartButtonBounds.contains(mx, my)) {
            restartGame();
        }
    }

    public void restartGame() {
        gameOver = false;
        showRestartButton = false;
        playerWon = false;

        // reset available cards
        playerAvailableCharacters = CharacterDefinitions.getLevel1Characters();
        enemySpawner.setEnemyAvailableCharacters(playerAvailableCharacters);

        // reset towers
        createInitialTowers();

        // clear all entities and projectiles
        playerEntities.clear();
        projectiles.clear();

        // reset game state
        createInitialGameState();

        // recreate static layer
        createStaticLayer();

        requestFocusInWindow();
    }
    private void drawGameOverScreen(Graphics2D g2d) {
        // blur background
        g2d.setColor(new Color(0, 0, 0, 128));
        g2d.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        // draw text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        String gameOverText = playerWon ? "You WIN!" : "Game Over :(";
        int textX = (PANEL_WIDTH - fm.stringWidth(gameOverText)) / 2;
        int textY = PANEL_HEIGHT / 2 - 50;
        g2d.drawString(gameOverText, textX, textY);

        // draw restart button
        if (restartButtonBounds != null) {
            g2d.setColor(new Color(70, 130, 180));
            g2d.fillRect(restartButtonBounds.x, restartButtonBounds.y,
                    restartButtonBounds.width, restartButtonBounds.height);

            g2d.setColor(Color.WHITE);
            g2d.drawRect(restartButtonBounds.x, restartButtonBounds.y,
                    restartButtonBounds.width, restartButtonBounds.height);

            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            fm = g2d.getFontMetrics();
            String buttonText = "Restart";
            int buttonTextX = restartButtonBounds.x + (restartButtonBounds.width - fm.stringWidth(buttonText)) / 2;
            int buttonTextY = restartButtonBounds.y + (restartButtonBounds.height + fm.getAscent()) / 2;
            g2d.drawString(buttonText, buttonTextX, buttonTextY);
        }
    }
}