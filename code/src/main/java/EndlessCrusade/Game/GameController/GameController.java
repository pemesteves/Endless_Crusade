package EndlessCrusade.Game.GameController;

import EndlessCrusade.Game.GameController.EntityGenerator.EnemyGenerator.EnemyDirector;
import EndlessCrusade.Game.GameController.EntityGenerator.EnemyGenerator.EnemyGenerator;
import EndlessCrusade.Game.GameController.EntityGenerator.StructureGenerator.StructureGenerator;
import EndlessCrusade.Game.GameController.GameEvent.GameEventType;
import EndlessCrusade.Game.GameModel.*;
import EndlessCrusade.Game.GameView.GameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class GameController {
    private final GameModel gameModel;
    private final GameView gameView;
    private StructureGenerator structureGenerator;
    private EnemyGenerator enemyGenerator;
    private TileGenerator tileGenerator;
    private int ticks;
    private Random random;
    private int currentState;

    private List<Entity> toRemoveOnUpdate;

    private enum ActionValidity {
        INVALID,
        SEMIVALID,
        VALID
    }

    public GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.currentState = 0;
        this.structureGenerator = new StructureGenerator();

        EnemyDirector director = new EnemyDirector(null);
        enemyGenerator = new EnemyGenerator(director);

        tileGenerator = new TileGenerator();

        this.ticks = 0;
        random = new Random();

        importStructures();

        toRemoveOnUpdate = new ArrayList<>();
    }

    private void importStructures(){
        structureGenerator.importStructure("house");
        structureGenerator.importStructure("wall");
        structureGenerator.importStructure("mountain");
        structureGenerator.importStructure("big_house");
        structureGenerator.importStructure("castle");
    }

    public void start() throws IOException {
        Hero hero = new Hero(new Position(5, 5));
        gameModel.setHero(hero);

        Position boundaryPos = new Position(gameModel.getGridX(), gameModel.getGridY());
        Position.setBoundary(boundaryPos);

        enemyGenerator.generateEnemies(gameModel);

        tileGenerator.generateTileGrid(gameModel);
        tileGenerator.extendTileGrid(gameModel);

        gameView.drawModel(gameModel);
    }

    public void update() throws IOException {
        Hero hero = gameModel.getHero();

        if(hero == null){
            gameModel.addLogMessage("Game Over!!!");
            gameView.drawModel(gameModel);
            currentState = -1;
            return;
        }

        if(hero.getPosition().outOfBounds()){
            gameModel.addLogMessage("Game Over!!!");
            gameView.drawModel(gameModel);
            currentState = -1;
            return;
        }

        GameAction userAction = gameView.getInput(gameModel);
        if(userAction == null){
            return;
        }

        ActionValidity val = processHeroAction(hero, userAction);

        handleTile(hero, gameModel.getTileAt(hero.getPosition()));

        if (val == ActionValidity.INVALID){
            if (userAction.getType() == ActionType.CLOSE) {
                currentState = -1;
            }
            return;
        }

        List<Enemy> enemies = gameModel.getEnemies();
        for (Enemy enemy : enemies) {
            GameAction enemyAction = enemy.act(hero.getPosition());

            if(val == ActionValidity.VALID && enemy.getHealth() > 0) {processAction(enemy, enemyAction);}

            handleTile(enemy, gameModel.getTileAt(enemy.getPosition()));

            if(currentState == -1){
                break;
            }
        }

        for(Entity entity : toRemoveOnUpdate){
            this.gameModel.removeEntity(entity);
        }
        toRemoveOnUpdate.clear();

        if(val == ActionValidity.VALID) {
            ticks++;

            if (ticks % 3 == 0) {
                if(random.nextInt(10) == 9) {
                    List<Entity> structure = structureGenerator.getStructure();
                    int initial_x = this.gameModel.getGridX();
                    int initial_y = random.nextInt(this.gameModel.getGridY());
                    for(Entity entity : structure){
                        Entity entity1 = new Entity(new Position(initial_x+entity.getPosition().getX(), initial_y+entity.getPosition().getY()),
                                entity.getHealth(), entity.getType());
                        entity1.setInteractEvent(entity.interact());
                        entity1.setDefeatEvent(entity.onDefeat());
                        this.gameModel.addEntity(entity1);
                    }
                }
                moveWorld();
            }

            if (ticks % (gameModel.getGeneratedLength() * 3) == 0) {
                enemyGenerator.generateEnemies(gameModel);
                tileGenerator.extendTileGrid(gameModel);
            }
        }
        
        gameView.drawModel(gameModel);
    }

    private ActionValidity processAction(Dynamic actor, GameAction action) {
        if (action == null) {
            return ActionValidity.INVALID;
        }

        Position targetPos = actor.getPosition().getAdjacent(action.getOrientation());

        if (action.getType() == ActionType.MOVEMENT) {
            if (gameModel.getEntityAt(targetPos) == null && !targetPos.outOfBounds()) {
                actor.setPosition(targetPos);
                return ActionValidity.VALID;
            } else {
                return ActionValidity.SEMIVALID;
            }
        }

        Entity target = gameModel.getEntityAt(targetPos);

        if (action.getType() == ActionType.ATTACK) {
            if (target == null) {
                return ActionValidity.VALID;
            }
            if (target.damage(actor.getAttack())) {
                eventHandler(target, target.onDefeat());
            }
            return ActionValidity.VALID;
        }

        if (action.getType() == ActionType.INTERACTION) {
            if (target == null) {
                return ActionValidity.INVALID;
            }
            eventHandler(target, target.interact());
            return ActionValidity.SEMIVALID;
        }

        return ActionValidity.INVALID;
    }

    private ActionValidity processHeroAction(Hero actor, GameAction action) {
        if (action == null) {
            return ActionValidity.INVALID;
        }

        if (action.getType() == ActionType.CHANGE_ORIENTATION || action.getType() == ActionType.MOVEMENT) {
            actor.setOrientation(action.getOrientation());

            if (action.getType() == ActionType.CHANGE_ORIENTATION)
                return ActionValidity.SEMIVALID;
            else
                return processAction(actor, action);
        } else {
            return processAction(actor, action);
        }
    }

    public void eventHandler(Entity actor, GameEventType type) {
        switch (type) {
            case DISSIPATE: {
                toRemoveOnUpdate.add(actor);
                gameModel.increaseScore(200);
                gameModel.addLogMessage("Killed " + actor.getType() + "!");
                break;
            }
            case COLLECT:{
                toRemoveOnUpdate.add(actor);
                switch(random.nextInt(5)) {
                    case 0:
                    case 1:
                    case 2:
                        gameModel.increaseScore(500);
                        gameModel.addLogMessage("Collected " + actor.getType() + "!");
                        break;
                    case 3:
                        gameModel.getHero().setAttack(gameModel.getHero().getAttack()+random.nextInt(2)+1);
                        gameModel.addLogMessage("Improved attack!");
                        break;
                    default:
                        gameModel.getHero().setHealth(gameModel.getHero().getHealth()+random.nextInt(10)+5);
                        int player_health = gameModel.getHero().getHealth();
                        if(player_health > 100){
                            gameModel.getHero().setHealth(100);
                        }
                        gameModel.addLogMessage("Gained health!");
                        break;
                }
                break;
            }
            case BREAK: {
                toRemoveOnUpdate.add(actor);
                gameModel.addLogMessage("Destroyed " + actor.getType() + "!");
                break;
            }
            case STAT: {
                gameModel.addLogMessage(actor.toString());
                break;
            }
            case GAMEOVER: {
                toRemoveOnUpdate.add(actor);
                gameModel.addLogMessage("GAME OVER!!!");
                currentState = -1;
                break;
            }
        }
    }

    public int getCurrentState() {
        return currentState;
    }

    private void moveWorld() {
        Tile[][] tileGrid = gameModel.getTileGrid();
        for(int x = 0; x < gameModel.getGridX() + gameModel.getGeneratedLength() - 1; ++x){
            for(int y = 0; y < gameModel.getGridY(); ++y){
                tileGrid[x][y] = tileGrid[x + 1][y];
            }
        }

        Hero hero = gameModel.getHero();
        if(hero != null) {
            Position heroPosition = hero.getPosition();
            heroPosition.setX(heroPosition.getX() - 1);
            hero.setPosition(heroPosition);
        }

        List<Entity> entities = gameModel.getEntities();
        List<Entity> entToRemove = new ArrayList<>();
        for (Entity entity : entities) {
            Position p = entity.getPosition();
            p.setX(p.getX() - 1);
            entity.setPosition(p);
            if(p.getX() < -gameModel.getGeneratedLength()){
                if(p.outOfBounds()) {
                    entToRemove.add(entity);
                }
            }
        }
        List<Enemy> enemies = gameModel.getEnemies();

        for (Enemy enemy : enemies) {
            Position p = enemy.getPosition();
            p.setX(p.getX() - 1);
            enemy.setPosition(p);
            if(p.getX() < gameModel.getGridX()){
                if(p.outOfBounds()) {
                    entToRemove.add(enemy);
                }
            }
        }
        for(Entity entity : entToRemove){
            gameModel.removeEntity(entity);
        }

    }

    private void handleTile(Entity entity, Tile tile){
        if(tile == null)
            return;
        switch (tile) {
            case ROAD:
                break;
            case FIRE:
                if(entity.damage(5)){
                    eventHandler(entity, entity.onDefeat());
                }
                break;
        }
    }
}
