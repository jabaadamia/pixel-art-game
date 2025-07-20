package main.towers;

import main.characters.Targetable;
import main.state.LevelDefinitions;
import main.util.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Tower implements Targetable {
    private int x, y, width, height;
    private int health, maxHealth, level;
    private BufferedImage towerIMG;

    public Tower(int x, int y, int width, int height, int health, int level) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health;
        this.maxHealth = health;
        this.level = level;
    }

    public void draw(Graphics2D g2d, boolean rotate){
        String path = rotate ?
                String.format("rot_tower_lvl%d.png", level) :
                String.format("tower_lvl%d.png", level);

       towerIMG = SpriteLoader.load(path);
       g2d.drawImage(towerIMG, x, y, width, height, null);
    }

    public void drawHealthBar(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.drawRect((int)(x+width*0.15), y-10, (int)(width*0.7), 2);
        g2d.setColor(Color.green);
        g2d.fillRect((int)(x+width*0.15), y-10, (int)(width*0.7* (double) health / maxHealth), 2);
        g2d.setColor(Color.red);
        g2d.fillRect((int)(x+width*0.15+width*0.7* (double) health / maxHealth), y-10, (int)(width*0.7* (1 - (double) health /maxHealth)), 2);
    }

    public void levelUp(){
        if(level>1) return;
        level++;
        int newMaxHealth;

        switch (level){
            case 2 -> newMaxHealth=LevelDefinitions.LVL2_TOWER_HP;
            default -> newMaxHealth=800;
        }
        health = newMaxHealth-(maxHealth-health);
        maxHealth = newMaxHealth;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        if (health>=0 && health<=maxHealth)
            this.health = health;
        else
            this.health = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
