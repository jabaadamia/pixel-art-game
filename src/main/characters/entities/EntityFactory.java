package main.characters.entities;

import main.GamePanel;
import main.characters.cards.CharacterCard;
import main.characters.entities.level1.Archer;
import main.characters.entities.level1.Knight;
import main.characters.entities.level1.Soldier;
import main.characters.entities.level2.Samurai;
import main.characters.entities.level2.SamuraiArcher;
import main.characters.entities.level2.SamuraiCommander;

public class EntityFactory {

    public static Entity createEntityOf(CharacterCard card, boolean isEnemy, int x, int y, GamePanel panel){
        Entity e = switch (card.getName()) {
            case "Soldier" ->
                    new Soldier(isEnemy, x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getPrice(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            case "Archer" ->
                    new Archer(isEnemy, x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getPrice(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            case "Samurai" ->
                    new Samurai(isEnemy, x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getPrice(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            case "Bow Samurai" ->
                    new SamuraiArcher(isEnemy, x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getPrice(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            case "Shogun" ->
                    new SamuraiCommander(isEnemy, x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getPrice(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            case "Knight" ->
                    new Knight(isEnemy, x, y, card.getWidth(), card.getHeight(), card.getHealth(), card.getPrice(), card.getRange(), card.getDamage(), card.getRecoilTime(), panel);
            default -> null;
        };
        return e;
    }
}
