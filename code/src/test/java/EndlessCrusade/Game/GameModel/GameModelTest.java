package EndlessCrusade.Game.GameModel;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static junit.framework.TestCase.*;

public class GameModelTest {
    @Test
    public void positionCheckTest() {
        GameModel model = new GameModel(100, 100, 100);

        Hero hero = new Hero(new Position(10, 10));
        model.setHero(hero);

        assertNull(model.getEntityAt(new Position(0, 1)));
        assertEquals(hero, model.getEntityAt(new Position(10, 10)));

        Entity entity = new Entity(new Position(2, 4), 10, EntityType.GOBLIN);

        model.addEntity(entity);

        assertEquals(entity, model.getEntityAt(new Position(2, 4)));

        Enemy enemy = new Enemy(new Position(49, 23), 100, 100, 50, EntityType.GOBLIN);

        model.addEnemy(enemy);

        assertEquals(enemy, model.getEntityAt(new Position(49, 23)));
    }

    @Test
    public void removeEntityTest(){
        GameModel gameModel = new GameModel(100, 100, 100);

        Entity entity = Mockito.mock(Entity.class);
        gameModel.addEntity(entity);
        assertEquals(1, gameModel.getEntities().size());
        gameModel.removeEntity(entity);
        assertEquals(0, gameModel.getEntities().size());

        Enemy enemy = Mockito.mock(Enemy.class);
        gameModel.addEnemy(enemy);
        assertEquals(1, gameModel.getEnemies().size());
        gameModel.removeEntity(enemy);
        assertEquals(0, gameModel.getEnemies().size());

        Hero hero = Mockito.mock(Hero.class);
        gameModel.setHero(hero);
        assertEquals(hero, gameModel.getHero());
        gameModel.removeEntity(hero);
        assertEquals(null, gameModel.getHero());
    }

    @Test
    public void addLogMessageTest() {
        GameModel gameModel = new GameModel(100, 100, 100);

        gameModel.addLogMessage("message");
        assertEquals(1, gameModel.getLog().size());

        gameModel.addLogMessage("message");
        gameModel.addLogMessage("message");
        gameModel.addLogMessage("message");
        gameModel.addLogMessage("message");

        assertEquals(5, gameModel.getLog().size());

        gameModel.addLogMessage("new message");
        assertEquals(5, gameModel.getLog().size());
        assertEquals("new message", gameModel.getLog().get(4));
    }

    @Test
    public void getTileAtTest(){
        GameModel gameModel = new GameModel(100, 100, 100);

        assertEquals(null, gameModel.getTileAt(new Position(0, 0)));
        assertEquals(null, gameModel.getTileAt(new Position(-1, 0)));
        assertEquals(null, gameModel.getTileAt(new Position(0, -1)));

    }
}
