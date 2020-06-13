package EndlessCrusade.Game.GameView;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class LanternaGameViewFactory extends GameViewFactory {
    @Override
    public GameView makeView() {
        try {
            Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(60, 25))
                    .createTerminal();
            Screen screen = new TerminalScreen(terminal);
            TextGraphics graphics;
            graphics = screen.newTextGraphics();

            return new LanternaGameView(60, 25, graphics, screen);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
