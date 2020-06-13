package EndlessCrusade.Menu;

import EndlessCrusade.Game.GameModel.*;
import EndlessCrusade.Menu.MenuState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class SwingMenuComponent extends JComponent {
    MenuState actual_state;
    int width,height;

    SwingMenuComponent(int width, int height){
        super();

        this.width = width;
        this.height = height;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(new Color(225, 225, 208));
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        g.setColor(new Color(0, 153, 255));
        g.drawString( "ENDLESS CRUSADE",this.getWidth() / 2 - 55, 3 * 16);

        show_play_game_box(actual_state, g);
        show_exit_box(actual_state, g);
        showInstructions(g);
    }

    private void showInstructions(Graphics g){
        g.setColor(new Color(0, 51, 204));
        g.drawString("INSTRUCTIONS", 10, this.getHeight() - 5 * 15);
        g.drawString("  Move: arrows", 10, this.getHeight() - 4 * 15);
        g.drawString("  Change directions: ctrl+arrows", 10, this.getHeight() - 3 * 15);
        g.drawString("  Attack: X", 10, this.getHeight() - 2 * 15);
        g.drawString("  Interact: Z", 10, this.getHeight() - 1 * 15);
    }

    private void show_play_game_box(MenuState actual_state, Graphics g){
        Color color;
        if(actual_state == MenuState.INITIAL || actual_state == MenuState.ENTER_GAME){
            color = new Color(0, 51, 204);
        }
        else{
            color = new Color(0, 153, 255);
        }
        g.setColor(color);
        g.fillRect(this.getWidth() / 2 - 16 * 10, this.getHeight() / 2 - 15 * 5, 16 * 20, 3 * 16);
        g.setColor(Color.BLACK);
        g.drawString("Play Game", this.getWidth() / 2 - 14 * 2, this.getHeight() / 2 - 4 * 11);
    }

    private void show_exit_box(MenuState actual_state, Graphics g){
        Color color;
        if(actual_state == MenuState.EXIT || actual_state == MenuState.EXIT_MENU){
            color = new Color(0, 51, 204);
        }
        else{
            color = new Color(0, 153, 255);
        }
        g.setColor(color);
        g.fillRect(this.getWidth() / 2 - 16 * 10, this.getHeight() / 2 - 15, 16 * 20, 3 * 16);
        g.setColor(Color.BLACK);
        g.drawString("Exit", this.getWidth() / 2 - 9, this.getHeight() / 2 + 15);
    }

    public void setState(MenuState actual_state) {
        this.actual_state = actual_state;
    }
}
