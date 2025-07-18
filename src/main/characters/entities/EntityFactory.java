package main.characters.entities;

import main.GamePanel;
import main.characters.cards.CharacterCard;

public class EntityFactory {

    public static Entity createEntityOf(CharacterCard card, int x, int y, GamePanel panel){
        Entity e = switch (card.getName()) {
            case "Soldier" ->
                    new Soldier(x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getRange(), card.getDamage(), card.getRecoilTime());
            case "Archer" ->
                    new Archer(x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            default -> null;
        };
        return e;
    }
}
