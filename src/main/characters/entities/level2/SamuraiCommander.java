package main.characters.entities.level2;

import main.GamePanel;
import main.characters.entities.Entity;

public class SamuraiCommander extends Entity {
    GamePanel panel;

    public SamuraiCommander(boolean isEnemy, float x, float y, int width, int height, int health, int price, int range, int damage, long recoilTime, GamePanel panel) {
        super(isEnemy, x, y, width, height, health, price, range, damage, recoilTime);
        this.panel = panel;
        attackSoundFilePath = SamuraiCommanderResources.attackSoundFilePath;
        if (isEnemy) {
            walkFrames = SamuraiCommanderResources.walkFramesEnemy;
            attackFrames = SamuraiCommanderResources.attackFramesEnemy;
            this.sprite = SamuraiCommanderResources.walkFramesEnemy[0];
        } else {
            walkFrames = SamuraiCommanderResources.walkFramesPlayer;
            attackFrames = SamuraiCommanderResources.attackFramesPlayer;
            this.sprite = SamuraiCommanderResources.walkFramesPlayer[0];
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
