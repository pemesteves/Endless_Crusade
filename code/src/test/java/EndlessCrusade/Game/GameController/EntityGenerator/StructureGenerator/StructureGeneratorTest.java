package EndlessCrusade.Game.GameController.EntityGenerator.StructureGenerator;

import EndlessCrusade.Game.GameModel.Entity;
import EndlessCrusade.Game.GameModel.EntityType;
import EndlessCrusade.Game.GameModel.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StructureGeneratorTest {
    StructureGenerator structureGenerator;
    @Before
    public void createStructureGenerator(){
        this.structureGenerator = new StructureGenerator();
    }

    @Test
    public void wallImportTest(){
        List<Entity> entities = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            entities.add(new Entity(new Position(0, i), 0, EntityType.WALL));
        }

        structureGenerator.importStructure("wall");

        List<Entity> structure = structureGenerator.getStructure();
        assertEquals(entities.size(), structure.size());

        for(int i = 0; i < 9; i++){
            assertEquals(entities.get(i).getType(), structure.get(i).getType());
            assertEquals(entities.get(i).getPosition(), structure.get(i).getPosition());
        }
    }
}
