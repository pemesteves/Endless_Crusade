package EndlessCrusade.Game.GameModel;

public abstract class Dynamic extends Entity {
    protected int attack;

    public Dynamic() {
        super();
    }

    public Dynamic(Position position, int health, int atk, EntityType type) {
        super(position, health, type);
        this.attack = atk;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
