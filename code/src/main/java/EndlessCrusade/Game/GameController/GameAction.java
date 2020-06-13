package EndlessCrusade.Game.GameController;

import EndlessCrusade.Game.GameModel.Orientation;

public class GameAction {
    private ActionType type;
    private Orientation orientation;  //shows targeted position (for an attack or movement)

    public GameAction(ActionType actionType, Orientation orientation) {
        this.type = actionType;
        this.orientation = orientation;
    }

    public ActionType getType() {
        return type;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
