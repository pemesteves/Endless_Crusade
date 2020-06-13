package EndlessCrusade.Game.GameController.EntityGenerator.EnemyGenerator;

import EndlessCrusade.Game.GameModel.Enemy;

public class EnemyDirector {
    private EnemyBuilder builder;

    public EnemyDirector(EnemyBuilder builder){
        this.builder = builder;
    }

    public void changeBuilder(EnemyBuilder builder){
        this.builder = builder;
    }

    public Enemy make(){
        builder.reset();

        builder.setHealth();
        builder.setAttack();
        builder.setAggro();
        builder.setType();

        return builder.getResult();
    }
}
