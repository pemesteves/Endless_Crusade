package EndlessCrusade.Menu;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.awt.event.KeyEvent.*;

public class SwingMenuView extends MenuView implements KeyListener {
    private JFrame frame;
    private Integer lastKey;

    SwingMenuComponent smc;

    public SwingMenuView(int width, int height) {
        super(width, height);

        frame = new JFrame("Endless Crusade");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);

        frame.addKeyListener(this);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        smc = new SwingMenuComponent(this.getWidth(), this.getHeight());
        frame.add(smc);
    }

    public void drawMenu(MenuState actual_state){
        if(!frame.isVisible()){
            frame.setVisible(true);
        }

        smc.setState(actual_state);

        frame.revalidate();
        frame.repaint();
    }

    public MenuState getInput(MenuState actual_state) {
        if (lastKey == null){
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {}

            return actual_state;
        }

        int code = lastKey;

        lastKey = null;

        switch (code) {
            case VK_ENTER: {
                if(actual_state == MenuState.INITIAL){
                    return MenuState.ENTER_GAME;
                }
                else{
                    return MenuState.EXIT_MENU;
                }
            }
            case VK_UP: {
                return MenuState.INITIAL;
            }
            case VK_DOWN: {
                return MenuState.EXIT;
            }
            case VK_ESCAPE: {
                return MenuState.EXIT_MENU;
            }
            default: {
                return actual_state;
            }
        }
    }

    public void close() {
        frame.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        lastKey = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static class LanternaMenuViewFactory extends MenuViewFactory {
        @Override
        public MenuView makeView() {
            try {
                Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(60, 20))
                        .createTerminal();
                Screen screen = new TerminalScreen(terminal);
                TextGraphics graphics;
                graphics = screen.newTextGraphics();

                return new LanternaMenuView(60, 20, graphics, screen);
            }
            catch(IOException e){}

            return null;
        }
    }
}
