package main.characters.entities.level1;

import main.util.SpriteLoader;

import java.awt.image.BufferedImage;

public class ArcherResources {
    public static final BufferedImage arrowIMG;

    public static final BufferedImage[] walkFramesEnemy;
    public static final BufferedImage[] attackFramesEnemy;

    public static final BufferedImage[] walkFramesPlayer;
    public static final BufferedImage[] attackFramesPlayer;

    static {
        arrowIMG = SpriteLoader.load("arrow.png");

        walkFramesEnemy = new BufferedImage[8];
        attackFramesEnemy = new BufferedImage[9];

        walkFramesPlayer = new BufferedImage[8];
        attackFramesPlayer = new BufferedImage[9];

        int frameWidth = 40;
        int frameHeight = 40;
        int frameSpacing = 100;

        BufferedImage walkIMGEnemy = SpriteLoader.load("characters/rot_soldierWalk.png");
        BufferedImage attackIMGEnemy = SpriteLoader.load("characters/rot_archerAttack.png");

        BufferedImage walkIMGPlayer = SpriteLoader.load("characters/soldierWalk.png");
        BufferedImage attackIMGPlayer = SpriteLoader.load("characters/archerAttack.png");

        for (int i = 0; i < 8; i++) {
            walkFramesEnemy[7 - i] = walkIMGEnemy.getSubimage(frameSpacing * i + 20, 20, frameWidth, frameHeight);
            walkFramesPlayer[i] = walkIMGPlayer.getSubimage(frameSpacing * i + 40, 20, frameWidth, frameHeight);
        }

        for (int i = 0; i < 9; i++) {
            attackFramesEnemy[8 - i] = attackIMGEnemy.getSubimage(frameSpacing * i + 20, 20, frameWidth, frameHeight);
            attackFramesPlayer[i] = attackIMGPlayer.getSubimage(frameSpacing * i + 40, 20, frameHeight, frameWidth);
        }
    }
}
