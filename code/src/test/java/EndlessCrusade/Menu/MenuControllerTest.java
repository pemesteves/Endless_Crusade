package EndlessCrusade.Menu;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MenuControllerTest {
    MenuController controller;
    MenuView view;

    @Before
    public void start() throws IOException {
        view = Mockito.mock(LanternaMenuView.class);
        controller = new MenuController(view);
    }

    @Test
    public void startTest() throws IOException {
        controller.start();
        verify(view, times(1)).drawMenu(controller.getCurrentState());
    }

    @Test
    public void updateTest() throws IOException {
        when(view.getInput(controller.getCurrentState())).thenReturn(MenuState.INITIAL);
        controller.update();
        assertEquals(MenuState.INITIAL, controller.getCurrentState());
        verify(view, times(1)).drawMenu(MenuState.INITIAL);
    }
}
