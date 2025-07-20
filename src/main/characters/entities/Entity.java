package main.characters.entities;

import main.characters.Targetable;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity implements Targetable {
    protected float x, y;
    protected int width, height;
    protected boolean isEnemy;
    protected int maxHealth, health, price;
    protected int range, damage;
    protected long recoilTime;
    protected long lastAttackTime = 0;
    protected Entity currentlyAttacks;
    protected boolean attacksTower = false;
    protected BufferedImage sprite;

    // for animations
    protected int currentAttackFrame = 0;
    protected int currentWalkFrame = 0;
    protected long lastFrameTime = 0;
    protected int frameDuration = 250; // ms per frame
    // state
    protected boolean isAttacking = false;
    protected boolean isWalking = true;

    protected BufferedImage[] walkFrames;
    protected BufferedImage[] attackFrames;

    public Entity(boolean isEnemy, float x, float y, int width, int height, int health, int price, int range, int damage, long recoilTime) {
        this.isEnemy = isEnemy;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.range = range;
        this.damage = damage;
        this.recoilTime = recoilTime;
        this.health = health;
        this.maxHealth = health;
        this.price = price;
    }

    public abstract void update(double delta);
    public void attack() {
        if(!isAttacking) {
            isAttacking = true;
            isWalking = false;
            currentAttackFrame = 0;
            sprite = attackFrames[currentAttackFrame];
        }
    }

    public void walk() {
        if(!isWalking) {
            isWalking = true;
            isAttacking = false;
            currentWalkFrame = 0;
            sprite = walkFrames[currentWalkFrame];
        }
    }

    public void idle(){
        isWalking = false;
        isAttacking = false;
        sprite = walkFrames[0];
    }

    public void render(Graphics2D g2d) {
        if (sprite != null) {
            int spriteWidth = width * 2;
            int healthBarWidth = width;
            int healthBarX;
            // entity image
            g2d.drawImage(sprite, (int)x, (int)y, spriteWidth, height*2, null);
//            g2d.setColor(Color.RED); // or any color for the hitbox
//
//            // rectangle for testing
//            g2d.drawRect((int)x, (int)y, spriteWidth, height * 2);
            // health bar
            if (isEnemy) {
                healthBarX = (int)(x + (spriteWidth - healthBarWidth) / 2 + width * 0.1);
            } else {
                healthBarX = (int)(x + (spriteWidth - healthBarWidth) / 2 - width * 0.1);
            }

            int healthBarY = (int)y-5;

            // health bar border
            g2d.setColor(Color.WHITE);
            g2d.drawRect(healthBarX, healthBarY, healthBarWidth, 2);

            // green health portion
            g2d.setColor(Color.GREEN);
            g2d.fillRect(healthBarX, healthBarY, (int)(healthBarWidth * (double) health / maxHealth), 2);

            // red damage portion
            g2d.setColor(Color.RED);
            g2d.fillRect(healthBarX + (int)(healthBarWidth * (double) health / maxHealth), healthBarY,
                    (int)(healthBarWidth * (1 - (double) health / maxHealth)), 2);
        } else {
            // Fallback rendering if sprite isn't loaded
            g2d.setColor(Color.RED);
            g2d.fillRect((int)x, (int)y, width, height);
        }
    }


    // collision rectangle for hit detection
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    @Override
    public void setHealth(int health) {
        if (health >=0 && health <= maxHealth)
            this.health = health;
        else
            this.health = 0;
    }

    public int getHealth() {
        return health;
    }

    public int getPrice() {
        return price;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public long getRecoilTime() {
        return recoilTime;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public void setAttacksTower(boolean attacksTower) {
        this.attacksTower = attacksTower;
    }
}