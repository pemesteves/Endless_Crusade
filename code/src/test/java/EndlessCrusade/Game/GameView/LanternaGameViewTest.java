package EndlessCrusade.Game.GameView;

import EndlessCrusade.Game.GameController.ActionType;
import EndlessCrusade.Game.GameModel.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class LanternaGameViewTest {
    private LanternaGameView view;
    private TextGraphics graphics;
    private Screen screen;
    private GameModel mockModel;

    @Before
    public void start() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        screen = Mockito.mock(Screen.class);

        graphics = Mockito.mock(TextGraphics.class);

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));

        view = new LanternaGameView(20, 20, graphics, screen);

        mockModel = Mockito.mock(GameModel.class);

        initializeHero();
        initializeEntities();
        initializeEnemies();
    }

    private void initializeHero() {
        Hero hero = new Hero(new Position(5, 5));
        when(mockModel.getHero()).thenReturn(hero);
    }

    private void initializeEntities() {
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity(new Position(5, 5), 10, EntityType.GOBLIN));
        entities.add(new Entity(new Position(6, 5), 10, EntityType.GOBLIN));
        entities.add(new Entity(new Position(7, 5), 10, EntityType.GOBLIN));

        when(mockModel.getEntities()).thenReturn(entities);
    }

    private void initializeEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(new Position(5, 5), 10, 10, 10, EntityType.GOBLIN));
        enemies.add(new Enemy(new Position(6, 5), 10, 10, 10, EntityType.GOBLIN));
        enemies.add(new Enemy(new Position(7, 5), 10, 10, 10, EntityType.GOBLIN));

        when(mockModel.getEnemies()).thenReturn(enemies);
    }

    private void initializeLog() {
        List<String> log = new ArrayList<>();
        log.add("Log 1");
        log.add("Log 2");
        log.add("Log 3");

        when(mockModel.getLog()).thenReturn(log);
    }

    @Test
    public void constructorTest() throws IOException {
        Mockito.verify(screen, times(1)).setCursorPosition(null);
        Mockito.verify(screen, times(1)).startScreen();
    }

    @Test
    public void getInputTest() throws IOException {
        assertNotNull(view.getInput(mockModel));

        when(screen.readInput()).thenReturn(null);
        assertNull(view.getInput(mockModel));

        KeyStroke key = new KeyStroke(KeyType.ArrowUp, true, false);
        when(screen.readInput()).thenReturn(key);
        assertEquals(ActionType.CHANGE_ORIENTATION, view.getInput(mockModel).getType());
        assertEquals(Orientation.UP, view.getInput(mockModel).getOrientation());

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowLeft));
        assertEquals(Orientation.LEFT, view.getInput(mockModel).getOrientation());

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowRight));
        assertEquals(Orientation.RIGHT, view.getInput(mockModel).getOrientation());

        key = new KeyStroke('Z', false, false, false);
        when(screen.readInput()).thenReturn(key);
        assertEquals(ActionType.INTERACTION, view.getInput(mockModel).getType());

        key = new KeyStroke('X', false, false, false);
        when(screen.readInput()).thenReturn(key);
        assertEquals(ActionType.ATTACK, view.getInput(mockModel).getType());

        key = new KeyStroke('W', false, false, false);
        when(screen.readInput()).thenReturn(key);
        assertEquals(ActionType.INVALID, view.getInput(mockModel).getType());

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.EOF));
        assertEquals(ActionType.CLOSE, view.getInput(mockModel).getType());

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.Escape));
        assertEquals(ActionType.CLOSE, view.getInput(mockModel).getType());

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.Backspace));
        assertEquals(ActionType.INVALID, view.getInput(mockModel).getType());
    }

    @Test
    public void closeTest() throws IOException {
        view.close();
        verify(screen, times(1)).close();
    }
}
