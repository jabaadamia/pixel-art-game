package main;

import javax.swing.*;

public class Game {
    private JFrame frame;
    private GamePanel gamePanel;
    private GameLoop gameLoop;

    public Game() {
        frame = new JFrame("very cool 2D game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        gamePanel = new GamePanel(1200, 600);
        frame.add(gamePanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gameLoop = new GameLoop(gamePanel);
        gameLoop.start();
    }

    public static void main(String[] args) {
        new Game();
    }
}