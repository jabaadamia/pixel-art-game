package main.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import main.GamePanel;

public class InputHandler implements MouseListener {
    private GamePanel gamePanel;

    // Constructor to receive GamePanel reference
    public InputHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        gamePanel.checkCharacterClick(mx, my);
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
