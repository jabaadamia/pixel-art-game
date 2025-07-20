package main.enemies;

import main.GamePanel;
import main.characters.cards.CharacterCard;
import main.characters.cards.CharacterDefinitions;
import main.characters.entities.Entity;
import main.characters.entities.EntityFactory;
import main.state.GameState;

import java.util.List;

public class EnemySpawner {
    private GamePanel gamePanel;
    private GameState gameState;
    private List<CharacterCard> enemyAvailableCharacters;
    private long lastTimeSpawned = System.currentTimeMillis()+2000; // 2 sec delay after start

    public EnemySpawner(GamePanel gamePanel, GameState gameState){
        this.gamePanel = gamePanel;
        this.gameState = gameState;
        enemyAvailableCharacters = CharacterDefinitions.getLevel1Characters();
    }

    public void spawn(){
        // spawn random character enemy can afford every 2 sec
        long now = System.currentTimeMillis();

        if(now - lastTimeSpawned > 2000){
            CharacterCard[] spawnables = enemyAvailableCharacters.stream()
                    .filter(card -> card.getPrice() < gameState.getEnemyMoney())
                    .toArray(CharacterCard[]::new);

            if(spawnables.length != 0) {
                CharacterCard randomCharacter = spawnables[(int) (Math.random() * spawnables.length)];
                gamePanel.addEnemyEntity(
                        EntityFactory.createEntityOf(
                                randomCharacter, true,
                                gamePanel.getPANEL_WIDTH()-gamePanel.getCHARACTER_START_X()-40,
                                gamePanel.getCHARACTER_START_Y(),
                                gamePanel
                        )
                );
                lastTimeSpawned = now;
                gameState.setEnemyMoney(gameState.getEnemyMoney()-randomCharacter.getPrice());
            }
        }
    }

    public void setEnemyAvailableCharacters(List<CharacterCard> enemyAvailableCharacters) {
        this.enemyAvailableCharacters = enemyAvailableCharacters;
    }
}
