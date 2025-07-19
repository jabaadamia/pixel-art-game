package main.characters.entities;

import main.util.SpriteLoader;

import java.awt.image.BufferedImage;

public class SoldierResources {
    public static final BufferedImage[] walkFramesEnemy;
    public static final BufferedImage[] attackFramesEnemy;
    public static final BufferedImage[] deathFramesEnemy;

    public static final BufferedImage[] walkFramesPlayer;
    public static final BufferedImage[] attackFramesPlayer;
    public static final BufferedImage[] deathFramesPlayer;

    static {

        walkFramesEnemy = new BufferedImage[8];
        attackFramesEnemy = new BufferedImage[6];
        deathFramesEnemy = new BufferedImage[4];

        walkFramesPlayer = new BufferedImage[8];
        attackFramesPlayer = new BufferedImage[6];
        deathFramesPlayer = new BufferedImage[4];

        BufferedImage walkIMGEnemy = SpriteLoader.load("characters/rot_soldierWalk.png");
        BufferedImage attackIMGEnemy = SpriteLoader.load("characters/rot_soldierAttack.png");
        BufferedImage deathIMGEnemy = SpriteLoader.load("characters/rot_soldierDeath.png");

        BufferedImage walkIMGPlayer = SpriteLoader.load("characters/soldierWalk.png");
        BufferedImage attackIMGPlayer = SpriteLoader.load("characters/soldierAttack.png");
        BufferedImage deathIMGPlayer = SpriteLoader.load("characters/soldierDeath.png");

        for (int i = 0; i < 8; i++) {
            walkFramesEnemy[7 - i] = walkIMGEnemy.getSubimage(100 * i + 29, 38, 28, 28);
            walkFramesPlayer[i] = walkIMGPlayer.getSubimage(100 * i + 40, 38, 28, 28);
        }

        for (int i = 0; i < 6; i++) {
            attackFramesEnemy[5 - i] = attackIMGEnemy.getSubimage(100 * i + 29, 38, 28, 28);
            attackFramesPlayer[i] = attackIMGPlayer.getSubimage(100 * i + 40, 38, 28, 28);
        }

        for (int i = 0; i < 4; i++) {
            deathFramesEnemy[3 - i] = deathIMGEnemy.getSubimage(100 * i + 29, 38, 28, 28);
            deathFramesPlayer[i] = deathIMGPlayer.getSubimage(100 * i + 40, 38, 28, 28);
        }
    }
}
