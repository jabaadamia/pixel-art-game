package main.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SpriteLoader {
    private static final Map<String, BufferedImage> cache = new HashMap<>();

    public static BufferedImage load(String path) {
        if (cache.containsKey(path)) return cache.get(path);

        try (InputStream in = SpriteLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) throw new RuntimeException("Image not found: " + path);
            BufferedImage img = ImageIO.read(in);
            cache.put(path, img);
            return img;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

    public static BufferedImage subImage(String path, int x, int y, int w, int h) {
        return load(path).getSubimage(x, y, w, h);
    }

    public static void clearCache() {
        cache.clear();
    }
}
