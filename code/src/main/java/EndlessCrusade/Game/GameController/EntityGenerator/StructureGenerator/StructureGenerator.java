package EndlessCrusade.Game.GameController.EntityGenerator.StructureGenerator;

import EndlessCrusade.Game.GameModel.Entity;
import EndlessCrusade.Game.GameModel.EntityType;
import EndlessCrusade.Game.GameModel.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StructureGenerator {
    private List<List<Entity>> predefinedStructures;
    private Random random;
    private StructureDirector director;

    public StructureGenerator() {
        predefinedStructures = new ArrayList<>();
        random = new Random();
        this.director = new StructureDirector(null);
    }

    public void importStructure(String structureName) {
        try {
            List<String> structure = readStructureLines(structureName);
            int y = 0;
            List<Entity> entities = new ArrayList<>();

            for (String string : structure) {
                for (int x = 0; x < string.length(); x++) {
                    char c = string.charAt(x);
                    if(c == ' ')
                        continue;
                    EntityType type = getEntityType(c);
                    changeBuilder(type);
                    Entity entity = director.make();
                    entity.setPosition(new Position(x, y));
                    entities.add(entity);
                }
                y++;
            }

            predefinedStructures.add(entities);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeBuilder(EntityType type){
        switch (type) {
            case WALL:{
                director.changeBuilder(new WallBuilder());
                break;
            }
            case TREASURE:{
                director.changeBuilder(new TreasureBuilder());
                break;
            }
            case DOOR:{
                director.changeBuilder(new DoorBuilder());
                break;
            }
            case MOUNTAIN:{
                director.changeBuilder(new MountainBuilder());
                break;
            }
            case CASTLE:{
                director.changeBuilder(new CastleBuilder());
                break;
            }
            case GATE:{
                director.changeBuilder(new GateBuilder());
                break;
            }
            default: {
                return;
            }
        }

    }

    private List<String> readStructureLines(String structureName) throws IOException {
        URL resource = StructureGenerator.class.getResource("/structures/" + structureName + ".str");
        BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
        List<String> lines = new ArrayList<>();
        for (String line; (line = br.readLine()) != null;)
            lines.add(line);
        return lines;
    }

    public List<Entity> getStructure() {
        int index = random.nextInt(predefinedStructures.size());
        return predefinedStructures.get(index);
    }

    private EntityType getEntityType(char c){
        switch(c){
            case 'D':
                return EntityType.DOOR;
            case 'T':
                return EntityType.TREASURE;
            case 'M':
                return EntityType.MOUNTAIN;
            case 'C':
                return EntityType.CASTLE;
            case 'G':
                return EntityType.GATE;
            default:
                return EntityType.WALL;
        }
    }
}
