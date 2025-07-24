package main.characters.entities.level2;

import main.util.SpriteLoader;

import java.awt.image.BufferedImage;

public class SamuraiResources {
    public static final BufferedImage[] walkFramesEnemy;
    public static final BufferedImage[] attackFramesEnemy;

    public static final BufferedImage[] walkFramesPlayer;
    public static final BufferedImage[] attackFramesPlayer;

    public static final String attackSoundFilePath = "/sounds/sword-blade-attack.wav";

    static {
        walkFramesEnemy = new BufferedImage[9];
        attackFramesEnemy = new BufferedImage[5];

        walkFramesPlayer = new BufferedImage[9];
        attackFramesPlayer = new BufferedImage[5];

        int frameWidth = 88;
        int frameHeight = 104;
        int frameSpacing = 128;

        BufferedImage walkIMGEnemy = SpriteLoader.load("characters/samurai/regular/rot_walk.png");
        BufferedImage attackIMGEnemy = SpriteLoader.load("characters/samurai/regular/rot_attack.png");

        BufferedImage walkIMGPlayer = SpriteLoader.load("characters/samurai/regular/walk.png");
        BufferedImage attackIMGPlayer = SpriteLoader.load("characters/samurai/regular/attack.png");

        for (int i = 0; i < 9; i++) {
            walkFramesEnemy[8 - i] = walkIMGEnemy.getSubimage(frameSpacing * i + 15, 23, frameWidth, frameHeight);
            walkFramesPlayer[i] = walkIMGPlayer.getSubimage(frameSpacing * i + 25, 23, frameWidth, frameHeight);
        }

        for (int i = 0; i < 5; i++) {
            attackFramesEnemy[4 - i] = attackIMGEnemy.getSubimage(frameSpacing * i + 15, 23, frameWidth, frameHeight);
            attackFramesPlayer[i] = attackIMGPlayer.getSubimage(frameSpacing * i + 25, 23, frameWidth, frameHeight);
        }
    }
}
