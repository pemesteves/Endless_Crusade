package EndlessCrusade.Game.GameController.EntityGenerator.EnemyGenerator;

import EndlessCrusade.Game.GameController.GameEvent.GameEventType;
import EndlessCrusade.Game.GameModel.EntityType;

import java.util.Random;

public class GoblinBuilder extends EnemyBuilder {
    @Override
    public void setHealth() {
        Random random = new Random();
        int health = random.nextInt(5) + 8;
        result.setHealth(health);
    }

    @Override
    public void setAttack() {
        Random random = new Random();
        int atk = random.nextInt(2) + 3;
        result.setAttack(atk);
    }

    @Override
    public void setAggro() {
        Random random = new Random();
        int aggro = random.nextInt(5)+ 5;
        result.setAggro(aggro);
    }

    @Override
    public void setType() {
        result.setType(EntityType.GOBLIN);
    }

    @Override
    public void setDefeatEvent() {
        result.setDefeatEvent(GameEventType.DISSIPATE);
    }

    @Override
    public void setInteractEvent() {
        result.setInteractEvent(GameEventType.STAT);
    }
}
