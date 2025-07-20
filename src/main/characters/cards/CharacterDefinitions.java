package main.characters.cards;

import main.util.SpriteLoader;

import java.awt.image.BufferedImage;
import java.util.List;

// TODO replace with better way
public class CharacterDefinitions {
    // Level 1
    public static BufferedImage soldierIMG;

    static {
       soldierIMG = SpriteLoader.subImage("characters/knights/rot_soldierWalk.png",35,35,28,28);
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

    // Level 2?? Samurai
    public static BufferedImage regularSamuraiIMG;
    public static BufferedImage archerSamuraiIMG;
    public static BufferedImage commanderSamuraiIMG;

    static {
        regularSamuraiIMG = SpriteLoader.subImage("characters/samurai/regular/walk.png",11,58,103,65);
        archerSamuraiIMG = SpriteLoader.subImage("characters/samurai/archer/walk.png",11,58,103,65);
        commanderSamuraiIMG = SpriteLoader.subImage("characters/samurai/commander/walk.png",11,58,103,65);
    }

    public static final CharacterCard SAMURAI_REGULAR = new CharacterCard(
            "Samurai",
            2,
            300,
            250,
            25,
            10,
            500,
            regularSamuraiIMG
    );

    public static final CharacterCard SAMURAI_ARCHER = new CharacterCard(
            "Bow Samurai",
            2,
            350,
            230,
            40,
            120,
            500,
            archerSamuraiIMG
    );

    public static final CharacterCard SAMURAI_COMMANDER = new CharacterCard(
            "Shogun",
            2,
            1000,
            600,
            50,
            10,
            500,
            commanderSamuraiIMG
    );
    public static List<CharacterCard> getLevel2Characters() {
        return List.of(SAMURAI_REGULAR, SAMURAI_ARCHER, SAMURAI_COMMANDER);
    }
}
