package EndlessCrusade.Game.GameView;

import EndlessCrusade.Menu.SwingMenuView;

public class SwingGameViewFactory extends GameViewFactory{

    @Override
    public GameView makeView() {
        return new SwingGameView(976,16 * 28);
    }
}
