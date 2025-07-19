package main.characters.entities;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class Soldier extends Entity {
    GamePanel panel;

    public Soldier(boolean isEnemy, float x, float y, int width, int height, int health, int price, int range, int damage, long recoilTime, GamePanel panel) {
        super(isEnemy, x, y, width, height, health, price, range, damage, recoilTime);
        this.panel = panel;

        if (isEnemy) {
            walkFrames = ArcherResources.walkFramesEnemy;
            attackFrames = ArcherResources.attackFramesEnemy;
            deathFrames = ArcherResources.deathFramesEnemy;
            this.sprite = ArcherResources.walkFramesEnemy[0];
        } else {
            walkFrames = ArcherResources.walkFramesPlayer;
            attackFrames = ArcherResources.attackFramesPlayer;
            deathFrames = ArcherResources.deathFramesPlayer;
            this.sprite = ArcherResources.walkFramesPlayer[0];
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
