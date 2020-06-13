package EndlessCrusade.Game.GameModel;

import EndlessCrusade.Game.GameController.GameAction;
import EndlessCrusade.Game.GameController.ActionType;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class EnemyTest {
    @Test
    public void constructorsTest(){
        Enemy e = new Enemy();
        assertEquals(0, e.getAttack());

        Enemy enemy = new Enemy(new Position(1, 1), 1, 1, 1, EntityType.GOBLIN);

        assertEquals(enemy, new Enemy(enemy));
    }

    @Test
    public void actTest() {
        Enemy enemy = new Enemy(new Position(0, 0), 10, 10, 10, EntityType.GOBLIN);

        assertNull(enemy.act(null));

        GameAction action = enemy.act(new Position(1, 0));

        assertEquals(ActionType.ATTACK, action.getType());
        assertEquals(Orientation.RIGHT, action.getOrientation());

        action = enemy.act(new Position(0, 10));

        assertEquals(ActionType.MOVEMENT, action.getType());
        assertEquals(Orientation.DOWN, action.getOrientation());

        action = enemy.act(new Position(100, 100));

        assertNull(action);
    }

    @Test
    public void toStringTest() {
        Enemy enemy = new Enemy(new Position(1, 1), 5, 5, 5, EntityType.GOBLIN);
        assertEquals(enemy.getType()+" - HP:"+enemy.getHealth()+" Atk:"+enemy.getAttack(), enemy.toString());
    }
}
