package EndlessCrusade.Menu;

import java.io.IOException;

public class MenuController {
    private final MenuView menuView;

    private MenuState currentState;

    public MenuController(MenuView menuView) {
        this.menuView = menuView;
        this.currentState = MenuState.INITIAL;
    }

    public void start() throws IOException {
        menuView.drawMenu(currentState);
    }

    public void update() throws IOException {
        MenuState newState = menuView.getInput(currentState);
        this.currentState = newState;
        menuView.drawMenu(currentState);
        if(currentState == MenuState.ENTER_GAME){
            menuView.close();
        }
    }

    public MenuState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MenuState state) { this.currentState = state; }
}
