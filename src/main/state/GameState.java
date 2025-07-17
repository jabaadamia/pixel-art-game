package main.state;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameState {

    private int level;
    private int money;
    private int health;
    private BufferedImage lvlBGimage;

    public GameState(int level, int money, int health) {
        this.level = level;
        this.money = money;
        this.health = health;
    }


    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getMoney() { return money; }
    public void setMoney(int money) { this.money = money; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public BufferedImage getLvlBGImage() {
        String path = String.format("resources/background_lvl%d.png", level);

        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
