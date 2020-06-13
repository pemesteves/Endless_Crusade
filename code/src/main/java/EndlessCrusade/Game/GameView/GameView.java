package EndlessCrusade.Game.GameView;

import EndlessCrusade.Game.GameController.GameAction;
import EndlessCrusade.Game.GameModel.GameModel;

import java.io.IOException;

public abstract class GameView {
    protected int height;
    protected int width;

    public GameView(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void drawModel(GameModel gameModel) throws IOException;

    public abstract GameAction getInput(GameModel gameModel);

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public abstract void close();
}
