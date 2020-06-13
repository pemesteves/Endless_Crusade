package EndlessCrusade.Game.GameController.EntityGenerator.EnemyGenerator;

import EndlessCrusade.Game.GameController.GameEvent.GameEventType;
import EndlessCrusade.Game.GameModel.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class EnemyGeneratorTest {
    private EnemyGenerator enemyGenerator;
    private EnemyBuilder enemyBuilder;
    private EnemyDirector enemyDirector;
    private GameModel model;

    @Before
    public void createEnemyGenerator(){
        this.enemyBuilder = new GoblinBuilder();
        this.enemyDirector = new EnemyDirector(enemyBuilder);
        this.enemyGenerator = new EnemyGenerator(enemyDirector);
        this.model = new GameModel(100, 100, 100);
        this.model.setHero(new Hero(new Position(5, 5)));
    }

    @Test
    public void setSpawChances(){
        enemyGenerator.spawnEnemy(EntityType.GOBLIN, model);

        List<Enemy> enemyList = model.getEnemies();
        assertEquals(1, enemyList.size());

        assertEquals(EntityType.GOBLIN, enemyList.get(0).getType());
    }

    @Test
    public void makeTest(){
        assertEquals(Enemy.class, enemyDirector.make().getClass());
    }

    @Test
    public void GoblinBuilderTest(){
        enemyBuilder.reset();
        enemyBuilder.setHealth();
        enemyBuilder.setType();
        enemyBuilder.setDefeatEvent();
        enemyBuilder.setInteractEvent();

        Enemy enemy = enemyBuilder.getResult();
        assertEquals(EntityType.GOBLIN, enemy.getType());
        assertEquals(GameEventType.DISSIPATE, enemy.onDefeat());
        assertEquals(GameEventType.STAT, enemy.interact());
    }
}
