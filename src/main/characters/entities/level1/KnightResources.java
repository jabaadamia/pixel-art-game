package main.characters.entities.level1;

import main.util.SpriteLoader;

import java.awt.image.BufferedImage;

public class KnightResources {
    public static final BufferedImage[] walkFramesEnemy;
    public static final BufferedImage[] attackFramesEnemy;

    public static final BufferedImage[] walkFramesPlayer;
    public static final BufferedImage[] attackFramesPlayer;

    public static final String attackSoundFilePath = "/sounds/sword-blade-attack.wav";

    static {
        walkFramesEnemy = new BufferedImage[8];
        attackFramesEnemy = new BufferedImage[5];

        walkFramesPlayer = new BufferedImage[8];
        attackFramesPlayer = new BufferedImage[5];

        int frameWidth = 88;
        int frameHeight = 104;
        int frameSpacing = 128;

        BufferedImage walkIMGEnemy = SpriteLoader.load("characters/knights/rot_knightWalk.png");
        BufferedImage attackIMGEnemy = SpriteLoader.load("characters/knights/rot_knightAttack.png");

        BufferedImage walkIMGPlayer = SpriteLoader.load("characters/knights/knightWalk.png");
        BufferedImage attackIMGPlayer = SpriteLoader.load("characters/knights/knightAttack.png");

        for (int i = 0; i < 8; i++) {
            walkFramesEnemy[7 - i] = walkIMGEnemy.getSubimage(frameSpacing * i + 30, 23, frameWidth, frameHeight);
            walkFramesPlayer[i] = walkIMGPlayer.getSubimage(frameSpacing * i + 10, 23, frameWidth, frameHeight);
        }

        for (int i = 0; i < 5; i++) {
            attackFramesEnemy[4 - i] = attackIMGEnemy.getSubimage(frameSpacing * i + 30, 23, frameWidth, frameHeight);
            attackFramesPlayer[i] = attackIMGPlayer.getSubimage(frameSpacing * i + 10, 23, frameWidth, frameHeight);
        }
    }
}
