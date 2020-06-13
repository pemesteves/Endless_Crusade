package EndlessCrusade.Menu;

import EndlessCrusade.Game.GameView.GameView;
import EndlessCrusade.Game.GameView.SwingGameView;

public class SwingMenuViewFactory extends MenuViewFactory {

    @Override
    public MenuView makeView() {
        return new SwingMenuView(30 * 16,16 * 20);
    }
}
