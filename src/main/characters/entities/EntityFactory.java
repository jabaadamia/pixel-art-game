package main.characters.entities;

import main.GamePanel;
import main.characters.cards.CharacterCard;

public class EntityFactory {

    public static Entity createEntityOf(CharacterCard card, boolean isEnemy, int x, int y, GamePanel panel){
        Entity e = switch (card.getName()) {
            case "Soldier" ->
                    new Soldier(isEnemy, x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getPrice(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            case "Archer" ->
                    new Archer(isEnemy, x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getPrice(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            default -> null;
        };
        return e;
    }
}
