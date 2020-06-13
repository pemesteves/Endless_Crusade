package EndlessCrusade.Game.GameModel;

import EndlessCrusade.Game.GameController.GameAction;
import EndlessCrusade.Game.GameController.ActionType;

public class Enemy extends Dynamic {
    private int aggro;

    public Enemy(Position position, int health, int atk, int aggro, EntityType type) {
        super(position, health, atk, type);
        this.aggro = aggro;
    }

    public Enemy() {
        super();
    }

    public Enemy(Enemy enemy) { // Copy Constructor
        super(enemy.getPosition(), enemy.getHealth(), enemy.getAttack(), enemy.getType());
        this.aggro = enemy.aggro;
    }

    public void setAggro(int aggro) {
        this.aggro = aggro;
    }

    public GameAction act(Position target) {
        if (target == null) {
            return null;
        }

        Orientation targetTowards = this.getPosition().orientationTo(target);

        // Target in close range
        if (this.getPosition().distanceTo(target) == 1) {
            return new GameAction(ActionType.ATTACK, targetTowards);
        }

        // Target in sight
        if (this.getPosition().distanceTo(target) <= aggro) {
            return new GameAction(ActionType.MOVEMENT, targetTowards);
        }

        // Do nothing
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (Enemy.class != obj.getClass())
            return false;

        Enemy enemy = (Enemy) obj;
        return this.getPosition().getX() == enemy.getPosition().getX()
                && this.getPosition().getY() == enemy.getPosition().getY() && this.health == enemy.health
                && this.attack == enemy.attack && this.aggro == enemy.aggro && this.getType() == enemy.getType();
    }

    @Override
    public String toString() {
        return super.toString() + " Atk:" + attack;
    }
}
