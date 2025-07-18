package main.characters.entities;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Soldier extends Entity {
    GamePanel panel;

    public Soldier(boolean isEnemy, float x, float y, int width, int height, int health, int range, int damage, long recoilTime, GamePanel panel) {
        super(isEnemy, x, y, width, height, health, range, damage, recoilTime);
        this.panel = panel;
        try {
            walkFrames = new BufferedImage[8];
            attackFrames = new BufferedImage[6];
            deathFrames = new BufferedImage[4];
            BufferedImage walkIMG;
            BufferedImage attackIMG;
            BufferedImage deathIMG;
            // get rotated images if enemy
            if(isEnemy){
                walkIMG = ImageIO.read(new File("resources/characters/rot-soldierWalk.png"));
                attackIMG = ImageIO.read(new File("resources/characters/rot-soldierAttack.png"));
                deathIMG = ImageIO.read(new File("resources/characters/rot-soldierDeath.png"));
                for (int i = 7; i >= 0; i--) {walkFrames[i] = walkIMG.getSubimage(100*i+29, 38, width, height);}
                for (int i = 5; i >= 0; i--) {attackFrames[i] = attackIMG.getSubimage(100*i+29, 38, width, height);}
                for (int i = 3; i >= 0; i--) {deathFrames[i] = deathIMG.getSubimage(100*i+29, 38, width, height);}
            }else{
                walkIMG = ImageIO.read(new File("resources/characters/soldierWalk.png"));
                attackIMG = ImageIO.read(new File("resources/characters/soldierAttack.png"));
                deathIMG = ImageIO.read(new File("resources/characters/soldierDeath.png"));
                for (int i = 0; i < 8; i++) {walkFrames[i] = walkIMG.getSubimage(100*i+40, 38, width, height);}
                for (int i = 0; i < 6; i++) {attackFrames[i] = attackIMG.getSubimage(100*i+40, 38, width, height);}
                for (int i = 0; i < 4; i++) {deathFrames[i] = deathIMG.getSubimage(100*i+40, 38, width, height);}
            }

            this.sprite = walkIMG.getSubimage(40, 38, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(double delta) {

        if (isWalking) {
            if (isEnemy)
                x--;
            else
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

                currentAttackFrame = (currentAttackFrame + 1);

                if (currentAttackFrame >= attackFrames.length) {
                    currentAttackFrame = 0;

                    // bow animation completed trigger arrow
                    if(attacksTower){
                        if (isEnemy)
                            panel.getPlayerTower().setHealth(panel.getPlayerTower().getHealth()-damage);
                        else{
                            panel.getEnemyTower().setHealth(panel.getEnemyTower().getHealth()-damage);
                        }
                    }else{
                        if(isEnemy)
                            panel.getPlayerEntities().getFirst().setHealth(panel.getPlayerEntities().getFirst().getHealth()-damage);
                        else
                            panel.getEnemyEntities().getFirst().setHealth(panel.getEnemyEntities().getFirst().getHealth()-damage);
                    }

                }
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
