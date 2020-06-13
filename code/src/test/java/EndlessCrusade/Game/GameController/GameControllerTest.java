package EndlessCrusade.Game.GameController;

import EndlessCrusade.Game.GameController.GameEvent.GameEventType;
import EndlessCrusade.Game.GameModel.*;
import EndlessCrusade.Game.GameView.GameView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.*;

public class GameControllerTest {
    private GameController controller;

    private GameView view;
    private GameModel model;

    @Captor
    private ArgumentCaptor<Hero> heroArgumentCaptor;

    @Captor
    private ArgumentCaptor<Enemy> enemyArgumentCaptor;

    @Before
    public void setUpController() {
        view = Mockito.mock(GameView.class);
        model = Mockito.spy(new GameModel(100, 100, 100));
        controller = Mockito.spy(new GameController(model, view));
        heroArgumentCaptor = ArgumentCaptor.forClass(Hero.class);
        enemyArgumentCaptor = ArgumentCaptor.forClass(Enemy.class);
    }

    @Test
    public void startTest() throws IOException {
        controller.start();
        verify(model, times(1)).setHero(heroArgumentCaptor.capture());
        verify(view, times(1)).drawModel(model);
    }

    @Test
    public void invalidInputTest() {

        /*GameAction action = null;

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(1)).getInput(model);
            verify(view, times(0)).drawModel(model);
        } catch (IOException e) {
            fail();
        }

        action = null;

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(2)).getInput(model);
            verify(view, times(0)).drawModel(model);
        } catch (IOException e) {
            fail();
        }*/
    }

    @Test
    public void testEOF() {
        /*GameAction action = new GameAction(ActionType.CLOSE, Orientation.DOWN);

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(1)).getInput(model);
            verify(view, times(0)).drawModel(model);
        } catch (IOException e) {
            fail();
        }

        assertEquals(controller.getCurrentState(), -1);
    }

    @Test
    public void orientationTest() {
        Hero hero = new Hero(new Position(10, 10));
        model.setHero(hero);
        Position original = hero.getPosition();
        hero.setOrientation(Orientation.RIGHT);

        GameAction action = new GameAction(ActionType.CHANGE_ORIENTATION, Orientation.DOWN);

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(1)).getInput(model);
            assertEquals(Orientation.DOWN, hero.getOrientation());
            assertEquals(original, hero.getPosition());
            verify(view, times(1)).drawModel(model);
        } catch (IOException e) {
            fail();
        }*/
    }

    @Test
    public void moveTest() {
        Hero hero = new Hero(new Position(10, 10));
        model.setHero(hero);
        Position original = hero.getPosition();
        hero.setOrientation(Orientation.RIGHT);

        GameAction action = new GameAction(ActionType.MOVEMENT, Orientation.UP);

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(1)).getInput(model);
            assertEquals(original.getY() - 1, hero.getPosition().getY());
            assertEquals(original.getX(), hero.getPosition().getX());
            verify(view, times(1)).drawModel(model);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void enemyMoveTest() {
        Hero hero = new Hero(new Position(0, 0));
        model.setHero(hero);

        Enemy enemy = new Enemy(new Position(2, 0), 10, 10, 10, EntityType.GOBLIN);
        model.addEnemy(enemy);

        GameAction action = new GameAction(ActionType.MOVEMENT, Orientation.DOWN);

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(1)).getInput(model);

            assertEquals(1, hero.getPosition().getY());
            assertEquals(0, hero.getPosition().getX());

            Position newEnemyPos = enemy.getPosition();
            assertEquals(0, newEnemyPos.getY());
            assertEquals(1, newEnemyPos.getX());

            verify(view, times(1)).drawModel(model);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void enemyAttackTest() {
        Hero hero = new Hero(new Position(0, 0));
        model.setHero(hero);

        Enemy enemy = new Enemy(new Position(2, 0), 10, 99, 10, EntityType.GOBLIN);
        model.addEnemy(enemy);

        GameAction action = new GameAction(ActionType.MOVEMENT, Orientation.RIGHT);

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(1)).getInput(model);

            assertEquals(0, hero.getPosition().getY());
            assertEquals(1, hero.getPosition().getX());

            Position newEnemyPos = enemy.getPosition();
            assertEquals(0, newEnemyPos.getY());
            assertEquals(2, newEnemyPos.getX());

            assertEquals(1, hero.getHealth());

            verify(view, times(1)).drawModel(model);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void entityDefeatTest() {
        Hero hero = new Hero(new Position(0, 0));
        model.setHero(hero);

        Entity entity = new Entity(new Position(1, 0), hero.getAttack(), EntityType.GOBLIN);

        model.addEntity(entity);

        GameAction action = new GameAction(ActionType.ATTACK, Orientation.RIGHT);

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(1)).getInput(model);

            assertEquals(0, hero.getPosition().getY());
            assertEquals(0, hero.getPosition().getX());

            assertEquals(0, entity.getHealth());

            assertEquals(0, model.getEntities().size());

            verify(view, times(1)).drawModel(model);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void enemyDefeatTest() {
        Hero hero = new Hero(new Position(0, 0));
        model.setHero(hero);

        Enemy enemy = new Enemy(new Position(1, 0),0, hero.getAttack(), 10, EntityType.GOBLIN);
        model.addEnemy(enemy);

        GameAction action = new GameAction(ActionType.ATTACK, Orientation.DOWN);

        try {
            when(view.getInput(model)).thenReturn(action);
            controller.update();
            verify(view, times(1)).getInput(model);

            assertEquals(0, hero.getPosition().getY());
            assertEquals(0, hero.getPosition().getX());

            Position newEnemyPos = enemy.getPosition();
            assertEquals(0, newEnemyPos.getY());
            assertEquals(1, newEnemyPos.getX());

            assertEquals(0, enemy.getHealth());

            assertEquals(1, model.getEnemies().size());

            verify(view, times(1)).drawModel(model);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void eventHandlerTest(){
        Entity actor = new Entity(new Position(5, 5), 5, EntityType.WALL);
        model.addEntity(actor);
        int score = model.getScore();
        controller.eventHandler(actor, GameEventType.DISSIPATE);
        assertEquals(score + 200, model.getScore());

        controller.eventHandler(actor, GameEventType.GAMEOVER);
        assertEquals(-1, controller.getCurrentState());
    }
}
