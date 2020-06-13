package EndlessCrusade.Game.GameController.EntityGenerator;

import EndlessCrusade.Game.GameModel.Entity;

public abstract class EntityBuilder {
    protected Entity result;

    public void reset(){
        result = new Entity();
    }

    public abstract void setHealth();

    public abstract void setType();

    public abstract void setDefeatEvent();

    public abstract void setInteractEvent();

    public Entity getResult(){
        return result;
    }
}
