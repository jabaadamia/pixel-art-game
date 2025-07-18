package main.projectiles;

import main.characters.Targetable;
import main.characters.entities.Entity;
import main.towers.Tower;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Arrow {
    private float x, y;
    private final float startX, startY;
    private final float targetX, targetY;
    private final float dx;
    private final float speed = 5f;
    private final BufferedImage image;
    private boolean active = true;
    private Entity thrower;
    private Targetable target;

    private final float a; // parabola coefficient

    public Arrow(Entity thrower, Targetable target, BufferedImage image) {
        this.thrower = thrower;
        this.target = target;
        this.startX = thrower.getBounds().x;
        this.startY = thrower.getBounds().y;
        this.targetX = target.getBounds().x+target.getBounds().width*0.5f;
        this.targetY = target.getBounds().y+target.getBounds().height*0.5f;
        this.image = image;

        this.x = startX;
        this.y = startY;

        //determine direction along X-axis
        float distanceX = targetX - startX;
        dx = (distanceX > 0 ? 1 : -1) * speed;

        //parabola peak point
        float midX = (startX + targetX) / 2f;
        float peakY = Math.min(startY, targetY) - 50;

        //calculate coefficient a for: y = a(x - midX)^2 + peakY
        a = (startY - peakY) / ((startX - midX) * (startX - midX));
    }

    public void update() {
        x += dx;

        float midX = (startX + targetX) / 2f;
        float peakY = Math.min(startY, targetY) - 50;

        // parabolic y = a(x - midX)^2 + peakY
        y = a * (x - midX) * (x - midX) + peakY;

        if ((dx > 0 && x >= targetX) || (dx < 0 && x <= targetX)) {
            active = false;
            //deal damage when animation finished
            target.setHealth(target.getHealth()-thrower.getDamage());
        }
    }

    public void draw(Graphics2D g2d) {
        if (image != null) {
            // compute rotation angle based on current direction
            double angle = Math.atan2(
                    a * 2 * (x - (startX + targetX) / 2f),  // dy = derivative of parabola at x
                    dx                                      // dx is constant
            );

            int centerX = image.getWidth() / 2;
            int centerY = image.getHeight() / 2;

            AffineTransform oldTransform = g2d.getTransform();
            g2d.translate(x + centerX, y + centerY);
            g2d.rotate(angle);
            g2d.drawImage(image, -centerX, -centerY, null);
            g2d.setTransform(oldTransform);
        } else {
            g2d.setColor(Color.white);
            g2d.drawRect((int) x, (int) y, 5, 1);
        }
    }

    public boolean isActive() {
        return active;
    }
}
