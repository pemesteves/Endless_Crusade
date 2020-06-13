package EndlessCrusade.Game.GameModel;

public abstract class ScreenObject {
    private Position p;

    ScreenObject(Position position){
        this.p = position;
    }

    public ScreenObject(){}

    public Position getPosition() {
        return p;
    }

    public void setPosition(Position p) {
        this.p = p;
    }

    public Position move(int x, int y) {
        return new Position(p.getX() + x, p.getY() + y);
    }
}
