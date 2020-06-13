package EndlessCrusade.Game.GameModel;

import EndlessCrusade.Game.GameController.GameEvent.GameEventType;

public class Entity extends ScreenObject {
    protected int health;
    private EntityType type;
    private GameEventType defeatEvent, interactEvent;

    public Entity() {
        super();
        defeatEvent = GameEventType.DISSIPATE;
        interactEvent = GameEventType.STAT;
    }

    public Entity(Position position, int health, EntityType type) {
        super(position);
        this.health = health;
        this.type = type;
        this.defeatEvent = GameEventType.DISSIPATE;
        this.interactEvent = GameEventType.STAT;
    }

    public int getHealth() {
        return health;
    }

    public boolean damage(int power) {
        health -= power;
        return (health <= 0);
    }

    public GameEventType onDefeat() {
        return defeatEvent;
    }

    public GameEventType interact() {
        return interactEvent;
    }

    public void setDefeatEvent(GameEventType defeatEvent) {
        this.defeatEvent = defeatEvent;
    }

    public void setInteractEvent(GameEventType interactEvent) {
        this.interactEvent = interactEvent;
    }

    public EntityType getType() {
        return type;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "" + type + " - HP:" + health;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(null == obj)
            return false;
        if(Entity.class != obj.getClass())
            return false;
        Entity e = (Entity)obj;
        return this.health == e.getHealth() && this.type == e.getType() && this.defeatEvent == e.onDefeat() && this.interactEvent == e.interact();
    }
}
