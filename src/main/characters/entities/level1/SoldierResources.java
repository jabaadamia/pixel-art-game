package main.characters.entities.level1;

import main.util.SpriteLoader;

import java.awt.image.BufferedImage;

public class SoldierResources {
    public static final BufferedImage[] walkFramesEnemy;
    public static final BufferedImage[] attackFramesEnemy;

    public static final BufferedImage[] walkFramesPlayer;
    public static final BufferedImage[] attackFramesPlayer;

    public static final String attackSoundFilePath = "/sounds/sword-strikes-armor.wav";

    static {

        walkFramesEnemy = new BufferedImage[8];
        attackFramesEnemy = new BufferedImage[6];

        walkFramesPlayer = new BufferedImage[8];
        attackFramesPlayer = new BufferedImage[6];

        int frameWidth = 40;
        int frameHeight = 40;
        int frameSpacing = 100;

        BufferedImage walkIMGEnemy = SpriteLoader.load("characters/knights/rot_soldierWalk.png");
        BufferedImage attackIMGEnemy = SpriteLoader.load("characters/knights/rot_soldierAttack.png");

        BufferedImage walkIMGPlayer = SpriteLoader.load("characters/knights/soldierWalk.png");
        BufferedImage attackIMGPlayer = SpriteLoader.load("characters/knights/soldierAttack.png");

        for (int i = 0; i < 8; i++) {
            walkFramesEnemy[7 - i] = walkIMGEnemy.getSubimage(frameSpacing * i + 20, 20, frameWidth, frameHeight);
            walkFramesPlayer[i] = walkIMGPlayer.getSubimage(frameSpacing * i + 40, 20, frameHeight, frameHeight);
        }

        for (int i = 0; i < 6; i++) {
            attackFramesEnemy[5 - i] = attackIMGEnemy.getSubimage(frameSpacing * i + 20, 20, frameWidth, frameHeight);
            attackFramesPlayer[i] = attackIMGPlayer.getSubimage(frameSpacing * i + 40, 20, frameWidth, frameHeight);
        }
    }
}
