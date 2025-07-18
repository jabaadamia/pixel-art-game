package main.towers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tower {
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
                String.format("resources/rot-tower_lvl%d.png", level) :
                String.format("resources/tower_lvl%d.png", level);

        try {
            towerIMG = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(towerIMG != null) {
            g2d.drawImage(towerIMG, x, y, width, height, null);
        }else { // white box if not image
            g2d.setColor(Color.white);
            g2d.drawRect(x,y, width, height);
        }
    }

    public void drawHealthBar(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.drawRect((int)(x+width*0.15), y-10, (int)(width*0.7), 2);
        g2d.setColor(Color.green);
        g2d.fillRect((int)(x+width*0.15), y-10, (int)(width*0.7* (double) health / maxHealth), 2);
        g2d.setColor(Color.red);
        g2d.fillRect((int)(x+width*0.15+width*0.7* (double) health / maxHealth), y-10, (int)(width*0.7* (1 - (double) health /maxHealth)), 2);
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
}
