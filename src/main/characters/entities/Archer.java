package main.characters.entities;

import main.GamePanel;
import main.characters.Targetable;
import main.projectiles.Arrow;

import java.awt.image.BufferedImage;

public class Archer extends Entity {
    GamePanel panel;
    BufferedImage arrowIMG;
    public Archer(boolean isEnemy, float x, float y, int width, int height, int health, int price, int range, int damage, long recoilTime, GamePanel panel) {
        super(isEnemy, x, y, width, height, health, price, range, damage, recoilTime);
        this.panel = panel;
        this.arrowIMG = ArcherResources.arrowIMG;

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
                            fireArrow(this, panel.getPlayerTower(),  arrowIMG);
                        else{
                            fireArrow(this, panel.getEnemyTower(), arrowIMG);
                        }
                    }else{
                        if(isEnemy)
                            fireArrow(this, panel.getPlayerEntities().getFirst(), arrowIMG);
                        else
                            fireArrow(this, panel.getEnemyEntities().getFirst(), arrowIMG);
                    }

                }
                sprite = attackFrames[currentAttackFrame];
            }
        }
    }

    private void fireArrow(Entity thrower, Targetable target, BufferedImage image) {

        Arrow arrow = new Arrow(thrower, target, image);
        panel.addProjectile(arrow);
    }
}
