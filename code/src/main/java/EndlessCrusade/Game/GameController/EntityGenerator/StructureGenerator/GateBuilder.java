package EndlessCrusade.Game.GameController.EntityGenerator.StructureGenerator;

import EndlessCrusade.Game.GameController.EntityGenerator.EntityBuilder;
import EndlessCrusade.Game.GameController.GameEvent.GameEventType;
import EndlessCrusade.Game.GameModel.EntityType;

import java.util.Random;

public class GateBuilder extends EntityBuilder {
    @Override
    public void setHealth() {
        Random random = new Random();
        int health = random.nextInt(2) + 29;
        result.setHealth(health);
    }

    @Override
    public void setType() {
        result.setType(EntityType.GATE);
    }

    @Override
    public void setDefeatEvent() {
        result.setDefeatEvent(GameEventType.BREAK);
    }

    @Override
    public void setInteractEvent() {
        result.setInteractEvent(GameEventType.STAT);
    }
}
