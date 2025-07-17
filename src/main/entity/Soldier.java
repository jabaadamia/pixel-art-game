package main.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Soldier extends Entity{

    public Soldier(float x, float y, int width, int height, int health, int range, int damage, long recoilTime) {
        super(x, y, width, height, health, range, damage, recoilTime);
        try {
            walkFrames = new BufferedImage[8];
            attackFrames = new BufferedImage[6];
            deathFrames = new BufferedImage[4];
            BufferedImage walkIMG = ImageIO.read(new File("resources/characters/soldierWalk.png"));
            BufferedImage attackIMG = ImageIO.read(new File("resources/characters/soldierAttack.png"));
            BufferedImage deathIMG = ImageIO.read(new File("resources/characters/soldierDeath.png"));

            for (int i = 0; i < 8; i++) {walkFrames[i] = walkIMG.getSubimage(100*i+40, 38, width, height);}
            for (int i = 0; i < 6; i++) {attackFrames[i] = attackIMG.getSubimage(100*i+40, 38, width, height);}
            for (int i = 0; i < 4; i++) {deathFrames[i] = deathIMG.getSubimage(100*i+40, 38, width, height);}

            this.sprite = walkIMG.getSubimage(40, 38, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(double delta) {

        if (isWalking) {
            x++;
            long now = System.currentTimeMillis();

            if (now - lastFrameTime >= frameDuration) {
                currentWalkFrame++;
                lastFrameTime = now;

                currentWalkFrame = (currentWalkFrame+1) % walkFrames.length;
                sprite = walkFrames[currentWalkFrame];
            }
        }else if (isAttacking) {
            long now = System.currentTimeMillis();
            if (now - lastFrameTime >= frameDuration) {
                currentAttackFrame++;
                lastFrameTime = now;

                currentAttackFrame = (currentAttackFrame + 1) % attackFrames.length;
                sprite = attackFrames[currentAttackFrame];
            }
        }
    }

    public void attack() {
        super.attack();
    }

    public void walk() {
        super.walk();
    }

    public void idle(){
        super.idle();
    }
}
