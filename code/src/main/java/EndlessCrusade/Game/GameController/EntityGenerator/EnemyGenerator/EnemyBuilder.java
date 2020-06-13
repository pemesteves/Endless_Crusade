package EndlessCrusade.Game.GameController.EntityGenerator.EnemyGenerator;

import EndlessCrusade.Game.GameController.EntityGenerator.EntityBuilder;
import EndlessCrusade.Game.GameModel.Enemy;

public abstract class EnemyBuilder extends EntityBuilder {
    protected Enemy result;

    public void reset(){
        result = new Enemy();
    }

    public abstract void setAttack();

    public abstract void setAggro();

    public Enemy getResult(){
        return result;
    }
}
