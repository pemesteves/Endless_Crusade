package EndlessCrusade.Game.GameController.EntityGenerator.StructureGenerator;

import EndlessCrusade.Game.GameController.EntityGenerator.EntityBuilder;
import EndlessCrusade.Game.GameModel.Entity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;

public class StructureDirectorTest {
    private EntityBuilder builder;

    @Before
    public void createBuilder(){
        builder = Mockito.mock(EntityBuilder.class);
        Mockito.when(builder.getResult()).thenReturn(new Entity());
    }

    @Test
    public void makeTest(){
        StructureDirector director = new StructureDirector(builder);

        assertEquals(Entity.class, director.make().getClass());
    }
}
