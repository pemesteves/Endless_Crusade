package EndlessCrusade.Game.GameView;
import EndlessCrusade.Game.GameController.GameAction;
import EndlessCrusade.Game.GameController.ActionType;
import EndlessCrusade.Game.GameModel.*;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.List;

import static java.lang.Character.toUpperCase;

public class LanternaGameView extends GameView {
    private TextGraphics graphics;
    private Screen screen;

    public LanternaGameView(int width, int height, TextGraphics graphics, Screen screen) throws IOException {
        super(width, height);
        this.graphics = graphics;
        this.screen = screen;

        screen.setCursorPosition(null); // we don't need a cursor
        screen.startScreen(); // screens must be started
        screen.doResizeIfNecessary(); // resize screen if necessary
    }

    public void drawModel(GameModel gameModel) throws IOException {
        drawTileGrid(gameModel);

        // Draw Hero
        Hero hero = gameModel.getHero();
        if (hero != null) {
            if(!hero.getPosition().outOfBounds()) {
                setTileColorAt(hero.getPosition(), gameModel);
                drawHero(hero, gameModel);
            }
        }

        // Draw Entities
        for (Entity entity : gameModel.getEntities()) {
            Position entityPos = entity.getPosition();
            if(!entityPos.outOfBounds()) {
               setTileColorAt(entity.getPosition(), gameModel);
                drawEntity(entity);
            }
        }

        // Draw Enemies
        for (Entity entity : gameModel.getEnemies()) {
            Position entityPos = entity.getPosition();
            if(!entityPos.outOfBounds()) {
                setTileColorAt(entity.getPosition(), gameModel);
                drawEntity(entity);
            }
        }

        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#888888"));

        graphics.fillRectangle(new TerminalPosition(0, gameModel.getGridY()), new TerminalSize(gameModel.getGridX(), gameModel.getGridY() + 5), ' ');

        graphics.putString(new TerminalPosition(0, this.getHeight() - 5), "Hero");
        if(hero != null) {
            graphics.putString(new TerminalPosition(0, this.getHeight() - 4), "HP " + hero.getHealth());
            graphics.putString(new TerminalPosition(0, this.getHeight() - 3), "Atk " + hero.getAttack());
        }
        else{
            graphics.putString(new TerminalPosition(0, this.getHeight() - 4), "HP 0");
            graphics.putString(new TerminalPosition(0, this.getHeight() - 3), "Atk 0");
        }
        graphics.putString(new TerminalPosition(0, this.getHeight() - 2), "Score " + gameModel.getScore());

        List<String> log = gameModel.getLog();
        for (int i = 0; i < log.size(); ++i) {
            String message = log.get(i);
            writeMessage(message, log.size() - i);
        }

        screen.refresh();
        screen.doResizeIfNecessary();
    }

    private void drawEntity(Entity entity) {
        String color, charLook;
        switch (entity.getType()) {
            case HERO: {
                color = "#00FF00";
                charLook = "H";
                break;
            }
            case WALL: {
                color = "#333333";
                charLook = "X";
                break;
            }
            case GOBLIN: {
                color = "#FF0000";
                charLook = "G";
                break;
            }
            case TREASURE:{
                color = "#FFFF66";
                charLook = "T";
                break;
            }
            case DOOR:{
                color = "#996633";
                charLook = "D";
                break;
            }
            case MOUNTAIN:{
                color = "#808080";
                charLook = "M";
                break;
            }
            case CASTLE:{
                color = "#555555";
                charLook = "C";
                break;
            }
            case GATE:{
                color = "#4D3319";
                charLook = "G";
                break;
            }
            default:
                return;
        }

        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(entity.getPosition().getX(), entity.getPosition().getY()), charLook);
    }

    private void drawHero(Hero hero, GameModel model) {
        drawEntity(hero);

        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        Position targetPos = hero.getPosition().getAdjacent(hero.getOrientation());

        if (targetPos.outOfBounds()) {
            return;
        }

        String targetLook;
        switch (hero.getOrientation()) {
            case UP: {
                targetLook = "⇧";
                break;
            }
            case DOWN: {
                targetLook = "⇩";
                break;
            }
            case LEFT: {
                targetLook = "⇦";
                break;
            }
            case RIGHT: {
                targetLook = "⇨";
                break;
            }
            default: {
                targetLook = " ";
                break;
            }
        }

        setTileColorAt(targetPos, model);
        graphics.putString(new TerminalPosition(targetPos.getX(), targetPos.getY()), targetLook);
    }

    public GameAction getInput(GameModel gameModel) {
        KeyStroke key;
        try {
            key = screen.readInput();
        } catch (IOException e) {
            return null;
        }

        if (key == null) {
            return null;
        }

        Hero player = gameModel.getHero();

        ActionType type;

        if (key.isCtrlDown()) {
            type = ActionType.CHANGE_ORIENTATION;
        } else {
            type = ActionType.MOVEMENT;
        }

        switch (key.getKeyType()) {
            case ArrowLeft: {
                return new GameAction(type, Orientation.LEFT);
            }
            case ArrowRight: {
                return new GameAction(type, Orientation.RIGHT);
            }
            case ArrowUp: {
                return new GameAction(type, Orientation.UP);
            }
            case ArrowDown: {
                return new GameAction(type, Orientation.DOWN);
            }
            case Character: {
                if (toUpperCase(key.getCharacter()) == 'Z') {
                    return new GameAction(ActionType.INTERACTION, player.getOrientation());
                }
                if (toUpperCase(key.getCharacter()) == 'X') {
                    return new GameAction(ActionType.ATTACK, player.getOrientation());
                }
                return new GameAction(ActionType.INVALID, null);
            }
            case EOF: case Escape:{
                return new GameAction(ActionType.CLOSE, null);
            }
            default: {
                return new GameAction(ActionType.INVALID, null);
            }
        }
    }

    private void writeMessage(String message, int order) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.putString(new TerminalPosition(15, this.getHeight() - order), message);
    }

    public void close() {
        try {
            screen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTileColor(Tile tile){
        switch (tile) {
            case ROAD:
                graphics.setBackgroundColor(TextColor.Factory.fromString("#e1e1d0"));
                break;
            case FIRE:
                graphics.setBackgroundColor(TextColor.Factory.fromString("#ff471a"));
                break;
        }
    }

    private void setTileColorAt(Position position, GameModel model){
        Tile[][] tileGrid = model.getTileGrid();

        setTileColor(tileGrid[position.getX()][position.getY()]);
    }

    private void drawTileGrid(GameModel model){
        Tile[][] tileGrid = model.getTileGrid();

        for(int x = 0; x < model.getGridX(); ++x){
            for(int y = 0; y < model.getGridY(); ++y){
                setTileColor(tileGrid[x][y]);

                graphics.putString(new TerminalPosition(x, y), " ");
            }
        }
    }
}