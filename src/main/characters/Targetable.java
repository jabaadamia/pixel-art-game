package main.characters;

import java.awt.*;

public interface Targetable {
    Rectangle getBounds();
    void setHealth(int health);
    int getHealth();
}
