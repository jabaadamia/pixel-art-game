package main.characters.entities.level2;

import main.util.SpriteLoader;

import java.awt.image.BufferedImage;

public class SamuraiArcherResources {
    public static final BufferedImage arrowIMG;

    public static final BufferedImage[] walkFramesEnemy;
    public static final BufferedImage[] attackFramesEnemy;

    public static final BufferedImage[] walkFramesPlayer;
    public static final BufferedImage[] attackFramesPlayer;

    static {
        arrowIMG = SpriteLoader.load("arrow.png");

        walkFramesEnemy = new BufferedImage[8];
        attackFramesEnemy = new BufferedImage[14];

        walkFramesPlayer = new BufferedImage[8];
        attackFramesPlayer = new BufferedImage[14];

        int frameWidth = 88;
        int frameHeight = 104;
        int frameSpacing = 128;

        BufferedImage walkIMGEnemy = SpriteLoader.load("characters/samurai/archer/rot_walk.png");
        BufferedImage attackIMGEnemy = SpriteLoader.load("characters/samurai/archer/rot_shot.png");

        BufferedImage walkIMGPlayer = SpriteLoader.load("characters/samurai/archer/walk.png");
        BufferedImage attackIMGPlayer = SpriteLoader.load("characters/samurai/archer/shot.png");

        for (int i = 0; i < 8; i++) {
            walkFramesEnemy[7 - i] = walkIMGEnemy.getSubimage(frameSpacing * i + 15, 23, frameWidth, frameHeight);
            walkFramesPlayer[i] = walkIMGPlayer.getSubimage(frameSpacing * i + 25, 23, frameWidth, frameHeight);
        }

        for (int i = 0; i < 14; i++) {
            attackFramesEnemy[13 - i] = attackIMGEnemy.getSubimage(frameSpacing * i + 15, 23, frameWidth, frameHeight);
            attackFramesPlayer[i] = attackIMGPlayer.getSubimage(frameSpacing * i + 25, 23, frameWidth, frameHeight);
        }
    }
}
