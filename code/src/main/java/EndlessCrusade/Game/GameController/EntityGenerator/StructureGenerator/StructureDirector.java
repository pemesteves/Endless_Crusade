package EndlessCrusade.Game.GameController.EntityGenerator.StructureGenerator;

import EndlessCrusade.Game.GameController.EntityGenerator.EntityBuilder;
import EndlessCrusade.Game.GameModel.Entity;

public class StructureDirector {
    private EntityBuilder builder;

    public StructureDirector(EntityBuilder builder){
        this.builder = builder;
    }

    public void changeBuilder(EntityBuilder builder){
        this.builder = builder;
    }

    public Entity make(){
        builder.reset();

        builder.setHealth();
        builder.setType();
        builder.setDefeatEvent();
        builder.setInteractEvent();

        return builder.getResult();
    }
}
