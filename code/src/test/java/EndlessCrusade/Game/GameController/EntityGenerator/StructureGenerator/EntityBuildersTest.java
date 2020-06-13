package EndlessCrusade.Game.GameController.EntityGenerator.StructureGenerator;

import EndlessCrusade.Game.GameController.EntityGenerator.EntityBuilder;
import EndlessCrusade.Game.GameController.GameEvent.GameEventType;
import EndlessCrusade.Game.GameModel.Entity;
import EndlessCrusade.Game.GameModel.EntityType;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EntityBuildersTest {
    EntityBuilder builder;

    private void createEntity(){
        builder.reset();
        builder.setHealth();
        builder.setType();
        builder.setDefeatEvent();
        builder.setInteractEvent();
    }

    @Test
    public void treasureBuilderTest(){
        builder = new TreasureBuilder();
        createEntity();
        Entity entity = builder.getResult();
        assertEquals(EntityType.TREASURE, entity.getType());
        assertEquals(GameEventType.BREAK, entity.onDefeat());
        assertEquals(GameEventType.COLLECT, entity.interact());
    }

    @Test
    public void WallBuilderTest(){
        builder = new WallBuilder();
        createEntity();
        Entity entity = builder.getResult();
        assertEquals(EntityType.WALL, entity.getType());
        assertEquals(GameEventType.BREAK, entity.onDefeat());
        assertEquals(GameEventType.STAT, entity.interact());
    }

    @Test
    public void MountainBuilderTest(){
        builder = new MountainBuilder();
        createEntity();
        Entity entity = builder.getResult();
        assertEquals(EntityType.MOUNTAIN, entity.getType());
        assertEquals(GameEventType.BREAK, entity.onDefeat());
        assertEquals(GameEventType.STAT, entity.interact());
    }

    @Test
    public void GateBuilderTest(){
        builder = new GateBuilder();
        createEntity();
        Entity entity = builder.getResult();
        assertEquals(EntityType.GATE, entity.getType());
        assertEquals(GameEventType.BREAK, entity.onDefeat());
        assertEquals(GameEventType.STAT, entity.interact());
    }

    @Test
    public void DoorBuilderTest(){
        builder = new DoorBuilder();
        createEntity();
        Entity entity = builder.getResult();
        assertEquals(EntityType.DOOR, entity.getType());
        assertEquals(GameEventType.BREAK, entity.onDefeat());
        assertEquals(GameEventType.STAT, entity.interact());
    }

    @Test
    public void CastleBuilderTest(){
        builder = new CastleBuilder();
        createEntity();
        Entity entity = builder.getResult();
        assertEquals(EntityType.CASTLE, entity.getType());
        assertEquals(GameEventType.BREAK, entity.onDefeat());
        assertEquals(GameEventType.STAT, entity.interact());
    }
}
