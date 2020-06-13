package EndlessCrusade.Game.GameModel;

public class Position {
    private int x;
    private int y;
    static Position maxBoundary;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        Position p = (Position) o;
        return x == p.getX() && y == p.getY();
    }

    public int distanceTo(Position dest){
        return (Math.abs(dest.x - this.x) + Math.abs(dest.y - this.y));
    }

    public Orientation orientationTo(Position dest){
        int movX = this.x - dest.x;
        int movY = this.y - dest.y;

        if(movX == 0 && movY == 0){
            return null;
        }

        if(Math.abs(movX) < Math.abs(movY)){
            if(movY < 0) {
                return Orientation.DOWN;
            }
            else {
                return Orientation.UP;
            }
        }
        else{
            if(movX < 0) {
                return Orientation.RIGHT;
            }
            else {
                return Orientation.LEFT;
            }
        }
    }

    public Position getAdjacent(Orientation orientation){
        if(orientation == null){
            return null;
        }

        switch(orientation) {
            default:
            case UP: {
                return new Position(x, y - 1);
            }
            case DOWN: {
                return new Position(x, y + 1);
            }
            case LEFT: {
                return new Position(x - 1, y);
            }
            case RIGHT: {
                return new Position(x + 1, y);
            }
        }
    }

    static public void setBoundary(Position max){
        maxBoundary = max;
    }

    public boolean outOfBounds(){
        if(maxBoundary == null){
            return false;
        }

        if(this.x < 0 || this.x >= maxBoundary.x || this.y < 0 || this.y >= maxBoundary.y){
            return true;
        }
        else{
            return false;
        }
    }
}
