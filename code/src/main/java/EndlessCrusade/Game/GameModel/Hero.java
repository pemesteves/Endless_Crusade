package EndlessCrusade.Game.GameModel;

import EndlessCrusade.Game.GameController.GameEvent.GameEventType;

public class Hero extends Dynamic {
    private Orientation orientation;

    public Hero(Position position) {
        super(position, 100, 10, EntityType.HERO);
        this.orientation = Orientation.RIGHT;
        this.setDefeatEvent(GameEventType.GAMEOVER);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
