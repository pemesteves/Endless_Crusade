package EndlessCrusade.Game.GameView;

import EndlessCrusade.Game.GameController.ActionType;
import EndlessCrusade.Game.GameController.GameAction;
import EndlessCrusade.Game.GameModel.GameModel;
import EndlessCrusade.Game.GameModel.Hero;
import EndlessCrusade.Game.GameModel.Orientation;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import static java.awt.event.KeyEvent.*;

public class SwingGameView extends GameView implements KeyListener {
    private JFrame frame;
    private Integer lastKey;
    private Boolean ctrl;

    public SwingGameView(int width, int height) {
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
    }

    @Override
    public void drawModel(GameModel gameModel){
        if(!frame.isVisible()){
            SwingGameComponent svc = new SwingGameComponent(gameModel);
            frame.add(svc);
            frame.setVisible(true);
        }
        frame.revalidate();
        frame.repaint();
    }


    public GameAction getInput(GameModel gameModel) {
        if (lastKey == null){
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {}

            return new GameAction(ActionType.INVALID,null);
        }

        int code = lastKey;

        lastKey = null;

        Hero player = gameModel.getHero();

        ActionType type;

        if (ctrl) {
            type = ActionType.CHANGE_ORIENTATION;
        } else {
            type = ActionType.MOVEMENT;
        }

        switch (code) {
            case VK_LEFT: {
                return new GameAction(type, Orientation.LEFT);
            }
            case VK_RIGHT: {
                return new GameAction(type, Orientation.RIGHT);
            }
            case VK_UP: {
                return new GameAction(type, Orientation.UP);
            }
            case VK_DOWN: {
                return new GameAction(type, Orientation.DOWN);
            }
            case VK_ESCAPE: {
                return new GameAction(ActionType.CLOSE, null);
            }
            case VK_Z: {
                return new GameAction(ActionType.INTERACTION, player.getOrientation());
            }
            case VK_X: {
                return new GameAction(ActionType.ATTACK, player.getOrientation());
            }
            default: {
                return new GameAction(ActionType.INVALID, null);
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
        ctrl = e.isControlDown();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
