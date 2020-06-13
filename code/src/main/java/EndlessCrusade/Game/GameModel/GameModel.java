package EndlessCrusade.Game.GameModel;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private int gridX;
    private int gridY;

    private int generatedLength;

    private int score;

    private Hero hero;
    private List<Entity> entities;
    private List<Enemy> enemies;

    private Tile[][] tileGrid;

    private List<String> log;

    public GameModel(int gridX, int gridY, int genLength){
        this.gridX = gridX;
        this.gridY = gridY;
        this.generatedLength = genLength;
        this.score = 0;

        this.tileGrid = new Tile[gridX + genLength][gridY];

        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        log = new ArrayList<>();
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getGridY() {
        return gridY;
    }

    public int getGridX() {
        return gridX;
    }

    public Entity getEntityAt(Position position) {
        if (hero.getPosition().equals(position)) {
            return hero;
        }

        for (Entity entity : entities) {
            if (entity.getPosition().equals(position)) {
                return entity;
            }
        }

        for (Entity entity : enemies) {
            if (entity.getPosition().equals(position)) {
                return entity;
            }
        }

        return null;
    }

    public void removeEntity(Entity removed){
        if (removed.equals(hero)) {
            hero = null;
            return;
        }

        for (Entity entity : entities) {
            if (removed.equals(entity)) {
                entities.remove(entity);
                return;
            }
        }

        for (Entity entity : enemies) {
            if (removed.equals(entity)) {
                enemies.remove(entity);
                return;
            }
        }
    }
  
    public void increaseScore(int scoreGained){
        this.score += scoreGained;
    }

    public void addLogMessage(String message){
        this.log.add(message);
        while(this.log.size() > 5){
            this.log.remove(0);
        }
    }

    public List<String> getLog(){
        return log;
    }

    public int getScore() {
        return score;
    }

    public int getGeneratedLength() {
        return generatedLength;
    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }

    public Tile getTileAt(Position position){
        if(tileGrid.length - 1 < position.getX() || position.getX() < 0)
            return null;
        if(tileGrid[position.getX()].length-1 < position.getY() || position.getY() < 0)
            return null;
        return tileGrid[position.getX()][position.getY()];
    }
}
