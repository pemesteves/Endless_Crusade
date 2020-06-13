package EndlessCrusade.Game.GameView;

import EndlessCrusade.Game.GameModel.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class SwingGameComponent extends JComponent {
    EnumMap<EntityType, Image> entityResources;
    EnumMap<Orientation, Image> heroResources;
    EnumMap<Tile, Image> tileResources;
    GameModel gameModel;

    SwingGameComponent(GameModel model){
        super();

        this.gameModel = model;

        tileResources = new EnumMap<Tile, Image>(Tile.class);
        entityResources = new EnumMap<EntityType, Image>(EntityType.class);
        heroResources = new EnumMap<Orientation, Image>(Orientation.class);

        loadResources();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(160,160);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Tile[][] grid = gameModel.getTileGrid();
        for(int x = 0; x < gameModel.getGridX(); ++x){
            for(int y = 0; y < gameModel.getGridY(); ++y){
                g.drawImage(tileResources.get(grid[x][y]), 16*x, 16*y, null);
            }
        }

        Hero hero = gameModel.getHero();
        if(hero != null) {
            Position position = hero.getPosition();
            if (!position.outOfBounds()) {
                g.drawImage(heroResources.get(hero.getOrientation()), 16*position.getX(), 16*position.getY(), null);
            }
            g.drawString("HP " + hero.getHealth(), 4,16 * gameModel.getGridY() + 32);
            g.drawString("Atk " + hero.getAttack(), 4,16 * gameModel.getGridY() + 48);
        }
        else{
            g.drawString("HP 0", 4,16 * gameModel.getGridY() + 32);
            g.drawString("Atk 0", 4,16 * gameModel.getGridY() + 48);
        }

        List<Entity> entities = readModel();
        for(Entity entity : entities){
            g.drawImage(entityResources.get(entity.getType()), 16*entity.getPosition().getX(), 16*entity.getPosition().getY(), null);
        }

        g.drawString("Hero", 4,16 * gameModel.getGridY() + 16);

        g.drawString("Score " + gameModel.getScore(), 4,16 * gameModel.getGridY() + 64);

        List<String> log = gameModel.getLog();
        for (int i = 0; i < log.size(); ++i) {
            g.drawString(log.get(i), 16 * 10,16 * gameModel.getGridY() + 16 * 6 - 16 * (log.size() - i));
        }
    }

    public java.util.List<Entity> readModel(){
        List<Entity> components = new ArrayList<>();

        for(Entity entity : gameModel.getEntities()){
            if(!entity.getPosition().outOfBounds()) {
                components.add(entity);
            }
        }

        for(Entity entity : gameModel.getEnemies()){
            if(!entity.getPosition().outOfBounds()) {
                components.add(entity);
            }
        }

        return components;
    }

    private void loadResources(){
        URL heroUpURL = SwingGameView.class.getResource("/SwingResources/heroUP.png");
        URL heroDownURL = SwingGameView.class.getResource("/SwingResources/heroDOWN.png");
        URL heroLeftURL = SwingGameView.class.getResource("/SwingResources/heroLEFT.png");
        URL heroRightURL = SwingGameView.class.getResource("/SwingResources/heroRIGHT.png");
        URL goblinURL = SwingGameView.class.getResource("/SwingResources/goblin.png");
        URL doorURL = SwingGameView.class.getResource("/SwingResources/door.png");
        URL fireURL = SwingGameView.class.getResource("/SwingResources/fire.png");
        URL grassURL = SwingGameView.class.getResource("/SwingResources/grass.png");
        URL mountainsURL = SwingGameView.class.getResource("/SwingResources/mountains.png");
        URL treasureURL = SwingGameView.class.getResource("/SwingResources/treasure.png");
        URL wallURL = SwingGameView.class.getResource("/SwingResources/wall.png");
        URL castleURL = SwingGameView.class.getResource("/SwingResources/castle.png");
        URL gateURL = SwingGameView.class.getResource("/SwingResources/gate.png");

        URL resource = SwingGameView.class.getResource("/SwingResources/opera_4D1qyvr4wY.png");

        try {
            Image image = ImageIO.read(goblinURL);
            Image newImageA = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            entityResources.put(EntityType.GOBLIN, newImageA);

            image = ImageIO.read(heroDownURL);
            Image newImageB1 = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            heroResources.put(Orientation.DOWN, newImageB1);

            image = ImageIO.read(heroUpURL);
            Image newImageB2 = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            heroResources.put(Orientation.UP, newImageB2);

            image = ImageIO.read(heroLeftURL);
            Image newImageB3 = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            heroResources.put(Orientation.LEFT, newImageB3);

            image = ImageIO.read(heroRightURL);
            Image newImageB4 = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            heroResources.put(Orientation.RIGHT, newImageB4);

            image = ImageIO.read(doorURL);
            Image newImageC = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            entityResources.put(EntityType.DOOR, newImageC);

            image = ImageIO.read(fireURL);
            Image newImageD = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            tileResources.put(Tile.FIRE, newImageD);

            image = ImageIO.read(wallURL);
            Image newImageE = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            entityResources.put(EntityType.WALL, newImageE);

            image = ImageIO.read(mountainsURL);
            Image newImageF = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            entityResources.put(EntityType.MOUNTAIN, newImageF);

            image = ImageIO.read(treasureURL);
            Image newImageG = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            entityResources.put(EntityType.TREASURE, newImageG);

            image = ImageIO.read(grassURL);
            Image newImageH = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            tileResources.put(Tile.ROAD, newImageH);

            image = ImageIO.read(castleURL);
            Image newImageI = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            entityResources.put(EntityType.CASTLE, newImageI);

            image = ImageIO.read(gateURL);
            Image newImageJ = image.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            entityResources.put(EntityType.GATE, newImageJ);
        } catch (IOException e) { }
    }

}
