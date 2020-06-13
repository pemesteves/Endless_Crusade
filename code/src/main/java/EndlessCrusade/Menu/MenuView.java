package EndlessCrusade.Menu;

import java.io.IOException;

public abstract class MenuView {
    private int height;
    private int width;

    public MenuView(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void drawMenu(MenuState actual_state) throws IOException;

    public abstract MenuState getInput(MenuState actual_state);

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public abstract void close();
}
