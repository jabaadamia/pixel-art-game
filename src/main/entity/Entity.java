package main.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected boolean isEnemy;
    protected int maxHealth, health;
    protected int range, damage;
    protected long recoilTime;
    protected long lastAttackTime = 0;
    protected Entity currentlyAttacks;
    protected boolean attacksTower = false;
    protected BufferedImage sprite;

    // for animations
    protected int currentAttackFrame = 0;
    protected int currentWalkFrame = 0;
    protected int currentDeathFrame = 0;
    protected long lastFrameTime = 0;
    protected int frameDuration = 250; // ms per frame
    // state
    protected boolean isAttacking = false;
    protected boolean isWalking = true;
    protected boolean isDying = false;

    protected BufferedImage[] walkFrames;
    protected BufferedImage[] attackFrames;
    protected BufferedImage[] deathFrames;

    public Entity(float x, float y, int width, int height, int health, int range, int damage, long recoilTime) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.range = range;
        this.damage = damage;
        this.recoilTime = recoilTime;
        this.health = health;
        this.maxHealth = health;
    }

    public abstract void update(double delta);
    public void attack() {
        if(!isAttacking) {
            isAttacking = true;
            isWalking = false;
            isDying = false;
            currentAttackFrame = 0;
            sprite = attackFrames[currentAttackFrame];
        }
    }

    public void walk() {
        if(!isWalking) {
            isWalking = true;
            isAttacking = false;
            isDying = false;
            currentWalkFrame = 0;
            sprite = walkFrames[currentWalkFrame];
        }
    }

    public void idle(){
        isWalking = false;
        isAttacking = false;
        isDying = false;
        sprite = walkFrames[0];
    }

    public void render(Graphics2D g2d) {
        if (sprite != null) {
            // entity image
            g2d.drawImage(sprite, (int)x, (int)y, width*2, height*2, null);
            // health bar
            g2d.setColor(Color.white);
            g2d.drawRect((int)(x+width*0.25), (int)y, (int)(width*1.5), 2);
            g2d.setColor(Color.green);
            g2d.fillRect((int)(x+width*0.25), (int)y, (int)(width*1.5* (double) health / maxHealth), 2);
            g2d.setColor(Color.red);
            g2d.fillRect((int)(x+width*0.25+width*1.5* (double) health / maxHealth), (int)y, (int)(width*1.5* (1 - (double) health /maxHealth)), 2);
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

    public void setHealth(int health) {
        if (health >=0 && health <= maxHealth)
            this.health = health;
    }

    public int getHealth() {
        return health;
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

    public boolean isDying() {
        return isDying;
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