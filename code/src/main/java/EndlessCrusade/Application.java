package EndlessCrusade;


import EndlessCrusade.Game.GameView.*;
import EndlessCrusade.Game.GameController.GameController;
import EndlessCrusade.Game.GameModel.GameModel;
import EndlessCrusade.Menu.*;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Application {
    Boolean swing;

    public static void main(String[] args) throws InterruptedException, IOException {
        Boolean swingVal = false;
        if(args.length != 0) {
            if (args[0].equals("-Swing")) {
                swingVal = true;
            }
        }

        Application app = new Application(swingVal);

        MenuView menuView;

        if(!swingVal){
            SwingMenuView.LanternaMenuViewFactory lanternaMenuViewFactory = new SwingMenuView.LanternaMenuViewFactory();
            menuView = lanternaMenuViewFactory.makeView();
        }
        else{
            SwingMenuViewFactory swingMenuViewFactory = new SwingMenuViewFactory();
            menuView = swingMenuViewFactory.makeView();
        }

        MenuController menuController = new MenuController(menuView);
        menuController.start();

        do{
            menuController.update();
            if(menuController.getCurrentState() == MenuState.ENTER_GAME){
                app.run();
                menuController.setCurrentState(MenuState.INITIAL);
            }
        }while(menuController.getCurrentState() != MenuState.EXIT_MENU);
        menuView.close();
    }

    public Application(Boolean swing){
        this.swing = swing;
    }

    public void run() throws InterruptedException {

        GameModel gameModel = new GameModel(60, 20, 20);
        GameView gameView;

        if(!swing) {
            LanternaGameViewFactory lanternaGameViewFactory = new LanternaGameViewFactory();
            gameView = lanternaGameViewFactory.makeView();
        }
        else{
            GameViewFactory factory = new SwingGameViewFactory();
            gameView = factory.makeView();
        }

        GameController gameController = new GameController(gameModel, gameView);

        try {
            gameController.start();

            while (gameController.getCurrentState() == 0) {
                gameController.update();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TimeUnit.SECONDS.sleep(5);
        gameView.close();
    }
}
