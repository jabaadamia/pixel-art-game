package main.graphics;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.IOException;

public class Sprite {
    private BufferedImage image;

    public Sprite(String path) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}