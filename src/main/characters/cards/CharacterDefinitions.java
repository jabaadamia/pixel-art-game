package main.characters.cards;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CharacterDefinitions {
    // Level 1
    public static BufferedImage soldierIMG;

    static {
        try {
            soldierIMG = ImageIO.read(new File("resources/characters/soldierWalk.png")).getSubimage(35, 35, 28, 28);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final CharacterCard SOLDIER = new CharacterCard(
            "Soldier",
            1,
            100,
            100,
            12,
            10,
            500,
            soldierIMG
    );
    public static final CharacterCard ARCHER = new CharacterCard(
            "Archer",
            1,
            150,
            80,
            10,
            120,
            500,
            soldierIMG
    );

    public static List<CharacterCard> getLevel1Characters() {
        return List.of(SOLDIER, ARCHER);
    }
}
