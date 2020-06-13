package EndlessCrusade.Game.GameController;

import EndlessCrusade.Game.GameModel.GameModel;
import EndlessCrusade.Game.GameModel.Tile;

import java.util.EnumMap;
import java.util.Random;

public class TileGenerator {
    EnumMap<Tile, Integer> spawnChances;

    public TileGenerator(){

        spawnChances = new EnumMap<>(Tile.class);

        setSpawnChances();
    }

    private void setSpawnChances(){
        spawnChances.put(Tile.ROAD, 99);
        spawnChances.put(Tile.FIRE, 1);
    }

    Tile getRandomTile(){
        Random random = new Random();

        int value = random.nextInt(100);

        if(value < 99){
            return Tile.ROAD;
        }

        return Tile.FIRE;
    }

    public void generateTileGrid(GameModel model){
        Tile[][] grid = model.getTileGrid();

        for(int x = 0; x < model.getGridX(); ++x){
            for(int y = 0; y < model.getGridY(); ++y){
                grid[x][y] = getRandomTile();
            }
        }
    }

    public void extendTileGrid(GameModel model){
        Tile[][] grid = model.getTileGrid();

        for(int x = model.getGridX(); x < model.getGridX() + model.getGeneratedLength(); ++x){
            for(int y = 0; y < model.getGridY(); ++y){
                grid[x][y] = getRandomTile();
            }
        }
    }
}
