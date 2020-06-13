package EndlessCrusade.Game.GameModel;

import EndlessCrusade.Game.GameController.GameEvent.GameEventType;
import org.junit.Test;
import static junit.framework.TestCase.*;

import java.util.Random;

public class EntityTest {
    @Test
    public void emptyConstructor(){
        Entity entity = new Entity();
        assertEquals(GameEventType.DISSIPATE, entity.onDefeat());
        assertEquals(GameEventType.STAT, entity.interact());
    }

    @Test
    public void moveTest() {
        Random random = new Random();

        int posX = random.nextInt(), posY = random.nextInt();

        Entity entity = new Entity(new Position(posX, posY), 10, EntityType.GOBLIN);

        int movX = random.nextInt(), movY = random.nextInt();

        Position newPos = entity.move(movX, movY);
        entity.setPosition(newPos);

        assertEquals(posX + movX, entity.getPosition().getX());
        assertEquals(posY + movY, entity.getPosition().getY());
    }

    @Test
    public void damageTest() {
        Random random = new Random();
        int health = random.nextInt();

        Entity entity = new Entity(new Position(0, 0), health, EntityType.GOBLIN);

        assertFalse(entity.damage(health - 1));
        assertEquals(1, entity.getHealth());
        assertTrue(entity.damage(1));
    }

    @Test
    public void toStringTest(){
        Entity entity = new Entity();
        entity.setHealth(5);
        entity.setType(EntityType.WALL);

        assertEquals(entity.getType()+" - HP:"+entity.getHealth(), entity.toString());
    }
}
