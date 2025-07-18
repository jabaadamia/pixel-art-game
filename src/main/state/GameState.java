package main.state;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameState {

    private int playerLevel;
    private int enemyLevel;
    private int playerMoney;
    private int enemyMoney;
    private BufferedImage lvlBGimage;

    public GameState(int playerLevel, int enemyLevel, int playerMoney, int enemyMoney) {
        this.playerLevel = playerLevel;
        this.enemyLevel = enemyLevel;
        this.playerMoney = playerMoney;
        this.enemyMoney = enemyMoney;
    }

    public int getPlayerLevel() { return playerLevel; }
    public void setPlayerLevel(int playerLevel) { this.playerLevel = playerLevel; }

    public int getEnemyLevel() {return enemyLevel;}
    public void setEnemyLevel(int enemyLevel) {this.enemyLevel = enemyLevel;}

    public int getPlayerMoney() { return playerMoney; }
    public void setPlayerMoney(int playerMoney) { this.playerMoney = playerMoney; }
    public void addToPlayerMoney(int amount){
        if (playerMoney+amount >= 0)
            playerMoney+=amount;
    }

    public int getEnemyMoney() {return enemyMoney;}
    public void setEnemyMoney(int enemyMoney) {this.enemyMoney = enemyMoney;}
    public void addToEnemyMoney(int amount){
        if(enemyMoney+amount >= 0)
            enemyMoney += amount;
    }

    public BufferedImage getLvlBGImage() {
        String path = String.format("resources/background_lvl%d.png", Math.max(playerLevel,enemyLevel));

        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
