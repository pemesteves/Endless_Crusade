package EndlessCrusade.Menu;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class LanternaMenuView extends MenuView {
    private TextGraphics graphics;
    private Screen screen;

    public LanternaMenuView(int width, int height, TextGraphics graphics, Screen screen) throws IOException {
        super(width, height);
        this.graphics = graphics;
        this.screen = screen;

        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen(); // screens must be started
        screen.doResizeIfNecessary(); // resize screen if necessary
    }

    public void drawMenu(MenuState actual_state) throws IOException {
        graphics.setForegroundColor(TextColor.Factory.fromString("#0099FF"));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#E1E1D0"));

        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(this.getWidth(), this.getHeight()), ' ');
        graphics.putString(new TerminalPosition(this.getWidth()/2-7, 3), "ENDLESS CRUSADE");

        show_play_game_box(actual_state);
        show_exit_box(actual_state);
        showInstructions();

        screen.refresh();
        screen.doResizeIfNecessary();
    }

    private void showInstructions(){
        graphics.setForegroundColor(TextColor.Factory.fromString("#0033CC"));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#E1E1D0"));
        graphics.putString(new TerminalPosition(1, this.getHeight() - 6), "INSTRUCTIONS");
        graphics.putString(new TerminalPosition(1, this.getHeight() - 5), "  Move: arrows");
        graphics.putString(new TerminalPosition(1, this.getHeight() - 4), "  Change directions: ctrl+arrows");
        graphics.putString(new TerminalPosition(1, this.getHeight() - 3), "  Attack: X");
        graphics.putString(new TerminalPosition(1, this.getHeight() - 2), "  Interact: Z");
    }

    private void show_play_game_box(MenuState actual_state){
        String color;
        if(actual_state == MenuState.INITIAL || actual_state == MenuState.ENTER_GAME){
            color = "#0033CC";
        }
        else{
            color = "#0099FF";
        }
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setBackgroundColor(TextColor.Factory.fromString(color));
        graphics.fillRectangle(new TerminalPosition(this.getWidth()/2-10, this.getHeight()/2-4), new TerminalSize(20, 3), ' ');
        graphics.putString(new TerminalPosition(this.getWidth()/2-4, this.getHeight()/2-3), "Play Game");
    }

    private void show_exit_box(MenuState actual_state){
        String color;
        if(actual_state == MenuState.EXIT || actual_state == MenuState.EXIT_MENU){
            color = "#0033CC";
        }
        else{
            color = "#0099FF";
        }
        graphics.setBackgroundColor(TextColor.Factory.fromString(color));
        graphics.fillRectangle(new TerminalPosition(this.getWidth()/2-10, this.getHeight()/2), new TerminalSize(20, 3), ' ');
        graphics.putString(new TerminalPosition(this.getWidth()/2-2, this.getHeight()/2+1), "Exit");
    }

    public MenuState getInput(MenuState actual_state) {
        KeyStroke key;
        try {
            key = screen.readInput();
        } catch (IOException e) {
            return MenuState.EXIT_MENU;
        }

        if (key == null) {
            return MenuState.EXIT_MENU;
        }

        switch (key.getKeyType()) {
            case ArrowUp: {
                return MenuState.INITIAL;
            }
            case ArrowDown: {
                return MenuState.EXIT;
            }
            case Enter: {
                if(actual_state == MenuState.INITIAL)
                    return MenuState.ENTER_GAME;
                else if(actual_state == MenuState.EXIT)
                    return MenuState.EXIT_MENU;
                return MenuState.EXIT_MENU;
            }
            case EOF: case Escape:{
                return MenuState.EXIT_MENU;
            }
            default: {
                return actual_state;
            }
        }
    }

    public void close() {
        try {
            screen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
