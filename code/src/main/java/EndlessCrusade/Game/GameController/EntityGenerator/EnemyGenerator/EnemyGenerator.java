package EndlessCrusade.Game.GameController.EntityGenerator.EnemyGenerator;

import EndlessCrusade.Game.GameModel.Enemy;
import EndlessCrusade.Game.GameModel.EntityType;
import EndlessCrusade.Game.GameModel.GameModel;
import EndlessCrusade.Game.GameModel.Position;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class EnemyGenerator {
    EnemyDirector director;
    EnumMap<EntityType, Integer> spawnChances;

    public EnemyGenerator(EnemyDirector director){
        this.director = director;

        spawnChances = new EnumMap<>(EntityType.class);

        setSpawnChances();
    }

    private void setSpawnChances(){
        spawnChances.put(EntityType.GOBLIN, 5);
    }

    void spawnEnemy(EntityType type, GameModel model){
        Random random = new Random();
        int baseX = model.getGridX(), height = model.getGridY(), width = model.getGeneratedLength();

        Position position = new Position(0,0);
        do {
            position.setX(random.nextInt(width) + baseX);
            position.setY(random.nextInt(height));
        }while(model.getEntityAt(position) != null);

        Enemy newEnemy;

        switch (type) {
            case GOBLIN:{
                director.changeBuilder(new GoblinBuilder());
                break;
            }
            default: {
                return;
            }
        }

        newEnemy = director.make();
        newEnemy.setPosition(position);

        model.addEnemy(newEnemy);
    }

    public void generateEnemies(GameModel model){
        Random random = new Random();
        Set<Map.Entry<EntityType, Integer>> entries = spawnChances.entrySet();

        for(Map.Entry<EntityType, Integer> entry : entries){
            int chance = entry.getValue() * model.getGeneratedLength();

            while(random.nextInt(100) < chance){
                spawnEnemy(entry.getKey(), model);
                chance -= entry.getValue() * 2;
            }
        }
    }
}
