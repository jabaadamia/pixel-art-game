package main.characters.entities.level2;

import main.GamePanel;
import main.characters.entities.Entity;

public class Samurai extends Entity {
    GamePanel panel;

    public Samurai(boolean isEnemy, float x, float y, int width, int height, int health, int price, int range, int damage, long recoilTime, GamePanel panel) {
        super(isEnemy, x, y, width, height, health, price, range, damage, recoilTime);
        this.panel = panel;
        attackSoundFilePath = SamuraiResources.attackSoundFilePath;
        if (isEnemy) {
            walkFrames = SamuraiResources.walkFramesEnemy;
            attackFrames = SamuraiResources.attackFramesEnemy;
            this.sprite = SamuraiResources.walkFramesEnemy[0];
        } else {
            walkFrames = SamuraiResources.walkFramesPlayer;
            attackFrames = SamuraiResources.attackFramesPlayer;
            this.sprite = SamuraiResources.walkFramesPlayer[0];
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
                    panel.audio.playSoundEffect(attackSoundFilePath);
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
}
