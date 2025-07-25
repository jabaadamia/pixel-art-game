package main.state;

import main.util.SpriteLoader;

import java.awt.image.BufferedImage;


public class GameState {

    private int playerLevel, enemyLevel;
    private int playerMoney, enemyMoney;
    private int playerXP, enemyXP;
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

    public int getPlayerXP() {return playerXP;}
    public void setPlayerXP(int playerXP) {this.playerXP = playerXP;}
    public void addToPlayerXP(int amount){
        if(playerXP+amount >= 0)
            playerXP += amount;
    }

    public int getEnemyXP() {return enemyXP;}
    public void setEnemyXP(int enemyXP) {this.enemyXP = enemyXP;}
    public void addToEnemyXP(int amount){
        if(enemyXP+amount >= 0)
            enemyXP += amount;
    }

    public BufferedImage getLvlBGImage() {
        String path = String.format("backgrounds/background_lvl%d.png", Math.max(playerLevel,enemyLevel));
        return SpriteLoader.load(path);
    }

    public int getCurrentLevelXP(boolean player){
        switch (player ? getPlayerLevel() : getEnemyLevel()){
            case 1 -> {
                return LevelDefinitions.LVL1XP;
            }
            case 2 -> {
                return LevelDefinitions.LVL2XP;
            }
            default -> {
                return 1000;
            }
        }
    }
}
