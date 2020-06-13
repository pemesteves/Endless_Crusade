package EndlessCrusade.Game.GameModel;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class PositionTest {
    @Test
    public void baseTest(){
        Position positionA = new Position(1,3);

        Assert.assertEquals(positionA.getX(), 1);
        Assert.assertEquals(positionA.getY(), 3);

        Position positionB = new Position(-2,50);

        Assert.assertEquals(positionB.getX(), -2);
        Assert.assertEquals(positionB.getY(), 50);

        positionA.setX(79);
        positionA.setY(-54);

        Assert.assertEquals(positionA.getX(), 79);
        Assert.assertEquals(positionA.getY(), -54);

        positionB.setX(-35);
        positionB.setY(-2154);

        Assert.assertEquals(positionB.getX(), -35);
        Assert.assertEquals(positionB.getY(), -2154);
    }

    @Test
    public void baseTestRandom(){
        Random random = new Random();
        int xA = random.nextInt(), yA = random.nextInt();
        Position positionA = new Position(xA,yA);

        Assert.assertEquals(positionA.getX(), xA);
        Assert.assertEquals(positionA.getY(), yA);

        int xB = random.nextInt(), yB = random.nextInt();
        Position positionB = new Position(xB,yB);

        Assert.assertEquals(positionB.getX(), xB);
        Assert.assertEquals(positionB.getY(), yB);

        xA = random.nextInt();
        yA = random.nextInt();

        positionA.setX(xA);
        positionA.setY(yA);

        Assert.assertEquals(positionA.getX(), xA);
        Assert.assertEquals(positionA.getY(), yA);

        xB = random.nextInt();
        yB = random.nextInt();

        positionB.setX(xB);
        positionB.setY(yB);

        Assert.assertEquals(positionB.getX(), xB);
        Assert.assertEquals(positionB.getY(), yB);
    }

    @Test
    public  void equalsTest(){
        Position positionA = new Position(1,3);
        Position positionB = new Position(-2,50);

        Assert.assertNotEquals(positionA,positionB);

        Position positionC = positionA;

        Assert.assertEquals(positionA, positionC);
        Assert.assertNotEquals(positionB, positionC);

        positionC = new Position(-2,50);

        Assert.assertEquals(positionB, positionC);
        Assert.assertNotEquals(positionA, positionC);

        Assert.assertNotEquals(positionA, null);
        //Assert.assertNotEquals(positionB, new GameEvent(0,""));
    }

    @Test
    public void equalsTestRandom(){
        Random random = new Random();

        int xA = random.nextInt(), yA = random.nextInt();

        int xB = random.nextInt(), yB = random.nextInt();

        while(xB == xA && yB == yA){
            xB = random.nextInt();
            yB = random.nextInt();
        }

        Position positionA = new Position(xA,yA);
        Position positionB = new Position(xB,yB);

        Assert.assertNotEquals(positionA,positionB);

        Position positionC = new Position(xA,yA);

        Assert.assertEquals(positionA, positionC);
        Assert.assertNotEquals(positionB, positionC);

        positionC.setX(xB);
        positionC.setY(yB);

        Assert.assertEquals(positionB, positionC);
        Assert.assertNotEquals(positionA, positionC);
    }

    @Test
    public void distanceTest(){
        Position positionA = new Position(21,33);
        Position positionB = new Position(-12,13);
        Position positionC = new Position(5,4);

        Assert.assertEquals(positionA.distanceTo(positionB), 53);
        Assert.assertEquals(positionA.distanceTo(positionC), 45);
        Assert.assertEquals(positionB.distanceTo(positionC), 26);
    }

    @Test
    public void orientationTest(){
        Position positionO = new Position(0,0);
        Position positionA = new Position(-12,0);
        Position positionB = new Position(5,5);
        Position positionC = new Position(2,12);
        Position positionD = new Position(11,-12);

        Assert.assertEquals(Orientation.LEFT, positionO.orientationTo(positionA));
        Assert.assertEquals(Orientation.RIGHT, positionO.orientationTo(positionB));
        Assert.assertEquals(Orientation.DOWN, positionO.orientationTo(positionC));
        Assert.assertEquals(Orientation.UP, positionO.orientationTo(positionD));

        Assert.assertEquals(Orientation.RIGHT, positionA.orientationTo(positionB));
        Assert.assertEquals(Orientation.RIGHT, positionA.orientationTo(positionC));
        Assert.assertEquals(Orientation.RIGHT, positionA.orientationTo(positionD));

        Assert.assertEquals(Orientation.DOWN, positionB.orientationTo(positionC));
        Assert.assertEquals(Orientation.UP, positionB.orientationTo(positionD));

        Assert.assertEquals(Orientation.UP, positionC.orientationTo(positionD));

        Assert.assertNull(positionO.orientationTo(positionO));
    }

    @Test
    public void adjacentTest(){
        Position position = new Position(-12,0);

        Position posRight = new Position(-11,0);
        Position posLeft = new Position(-13,0);
        Position posUp = new Position(-12,1);
        Position posDown = new Position(-12,-1);

        Assert.assertEquals(posRight, position.getAdjacent(Orientation.RIGHT));
        Assert.assertEquals(posLeft, position.getAdjacent(Orientation.LEFT));
        Assert.assertEquals(posUp, position.getAdjacent(Orientation.DOWN));
        Assert.assertEquals(posDown, position.getAdjacent(Orientation.UP));
    }

    @Test
    public void adjacentTestRandom(){
        Random random = new Random();
        int x = random.nextInt(), y = random.nextInt();

        Position position = new Position(x,y);

        Position posRight = new Position(x + 1,y);
        Position posLeft = new Position(x - 1,y);
        Position posUp = new Position(x,y + 1);
        Position posDown = new Position(x,y - 1);

        Assert.assertEquals(posRight, position.getAdjacent(Orientation.RIGHT));
        Assert.assertEquals(posLeft, position.getAdjacent(Orientation.LEFT));
        Assert.assertEquals(posUp, position.getAdjacent(Orientation.DOWN));
        Assert.assertEquals(posDown, position.getAdjacent(Orientation.UP));
        Assert.assertNull(position.getAdjacent(null));
    }

    @Test
    public void outOfBoundsTest(){
        Position p = new Position(6, 1);
        Assert.assertEquals(false, p.outOfBounds());

        Position.setBoundary(new Position(5, 5));
        Assert.assertEquals(true, p.outOfBounds());

        p.setX(-1);
        Assert.assertEquals(true, p.outOfBounds());

        p.setX(1);
        Assert.assertEquals(false, p.outOfBounds());

        p.setY(-1);
        Assert.assertEquals(true, p.outOfBounds());

        p.setY(6);
        Assert.assertEquals(true, p.outOfBounds());
    }
}
