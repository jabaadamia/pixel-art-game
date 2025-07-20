package main.characters.cards;

import java.awt.*;
import java.awt.image.BufferedImage;

// class for all character cards
public class CharacterCard {
    private BufferedImage sprite;
    private final String name;
    private final int level;
    private final int price;
    private final int health;
    private final int damage;
    private final int range;
    private final long recoilTime; // ms
    private boolean isLocked;
    private BufferedImage img;
    private final int width = 30;
    private final int height = 30;

    public CharacterCard(String name, int level, int price, int health, int damage, int range, long recoilTime, BufferedImage img) {
        this.name = name;
        this.level = level;
        this.price = price;
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.recoilTime = recoilTime;
        this.img = img;
    }

    public void renderButton(Graphics2D g2d, int x, int y) {
        g2d.drawImage(img, x, y, width*2, height*2, null);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, width*2, height*2);
        // draw character info
        Font font = new Font("Arial", Font.PLAIN, 10);
        g2d.setFont(font);
        g2d.drawString(name, x, y + height*2 + 15);
        g2d.drawString("Price: " + price, x, y + height*2 + 30);
        g2d.drawString("HP: " + health, x, y + height*2 + 45);
        g2d.drawString("DMG: " + damage, x, y + height*2 + 60);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getPrice() {
        return price;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getRecoilTime() {
        return recoilTime;
    }
}