package EndlessCrusade.Menu;

import com.googlecode.lanterna.TerminalSize;
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

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class LanternaMenuViewTest {
    private TextGraphics graphics;
    private Screen screen;
    private MenuView view;

    @Before
    public void start() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(60, 20))
                .createTerminal();
        screen = Mockito.mock(Screen.class);
        graphics = Mockito.mock(TextGraphics.class);
        view = new LanternaMenuView(60, 20, graphics, screen);
    }

    @Test
    public void getInputTest() throws IOException {
        assertEquals(MenuState.EXIT_MENU, view.getInput(MenuState.INITIAL));

        when(screen.readInput()).thenReturn(null);
        assertEquals(MenuState.EXIT_MENU, view.getInput(MenuState.INITIAL));

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        assertEquals(MenuState.INITIAL, view.getInput(MenuState.INITIAL));

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        assertEquals(MenuState.EXIT, view.getInput(MenuState.INITIAL));

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        assertEquals(MenuState.ENTER_GAME, view.getInput(MenuState.INITIAL));
        assertEquals(MenuState.EXIT_MENU, view.getInput(MenuState.EXIT));
        assertEquals(MenuState.EXIT_MENU, view.getInput(MenuState.EXIT_MENU));

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.EOF));
        assertEquals(MenuState.EXIT_MENU, view.getInput(MenuState.INITIAL));

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.Escape));
        assertEquals(MenuState.EXIT_MENU, view.getInput(MenuState.INITIAL));

        when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowLeft));
        assertEquals(MenuState.INITIAL, view.getInput(MenuState.INITIAL));
    }

    @Test
    public void closeTest() throws IOException {
        view.close();
        verify(screen, times(1)).close();
    }
}
