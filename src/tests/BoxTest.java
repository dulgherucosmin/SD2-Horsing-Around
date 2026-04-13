package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilz.Constants.Directions.RIGHT;

import org.junit.Test;

import entities.Box;
import entities.Player;
import main.Game;

public class BoxTest {

    // this creates a simple flat level with a solid floor at row 9
    private int[][] createFlatLevel() {
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];

        for (int row = 9; row < Game.TILES_IN_HEIGHT; row++) {
            for (int col = 0; col < Game.TILES_IN_WIDTH; col ++) {
                levelData[row][col] = 1;
            }
        }

        return levelData;
    }

    // this creates a level with a wall on the right side
    private int[][] createLevelWithRightWall() {
        int[][] levelData = createFlatLevel();
        
        for (int row = 0; row < Game.TILES_IN_HEIGHT; row++) {
            levelData[row][Game.TILES_IN_WIDTH - 1] = 1;
        }

        return levelData;
    }

    private void letBoxLand(Box box) {
        
        // this keeps updating the box till it lands on the ground
        for (int i = 0; i < 200; i++) {
            box.update(null, null);
        }
    }

    private void letPlayerLand(Player p) {
        for (int i = 0; i < 200; i++) {
            p.update();
        }
    }

    // this checks if the box is spawn within the game's boundaries
    @Test
    public void testBoxSpawnsOnMap() {
        Box box = new Box(5 * Game.TILES_SIZE, 2 * Game.TILES_SIZE);
        box.loadLevelData(createFlatLevel(), 1);

        // x and y should be within the game window
        assertTrue(box.getX() >= 0 && box.getX() < Game.GAME_WIDTH);
        assertTrue(box.getY() >= 0 && box.getY() < Game.GAME_HEIGHT);
    }  

    // this checks if the box sprite loads without crashing
    @Test
    public void testBoxSpriteLoads() {

        // if the sprite file is missing, getspriteatlas would return null and it draws a rectangle instead of crashing
        Box box = new Box(100, 100, "box.png");
        box.loadLevelData(createFlatLevel(), 1);

        // if there's no exception, then the sprite has loaded correctly
        assertTrue(box.getX() == 100);
    }

    // this checks that the box will fall if there's nothing underneath it
    @Test
    public void testBoxFalls() {
        Box box = new Box(100, 10);
        box.loadLevelData(createFlatLevel(), 1);

        float startY = box.getY();

        for (int i = 0; i < 10; i++) {
            box.update(null, null);
        }

        assertTrue(box.getY() > startY);
    }
    
    // this checks that the box stops falling when it hits the ground
    @Test
    public void testBoxLandsOnGround() {
        Box box = new Box(100, 10);
        box.loadLevelData(createFlatLevel(), 1);

        letBoxLand(box);
        
        // the floor is at row 9 so the box should be sitting just above it
        assertTrue(box.getY() < (9 * Game.TILES_SIZE));
    }

    // this checks if the box does not move sideways on its own
    @Test
    public void testBoxDoesntMoveSideways() {
        Box box = new Box(100,10);
        box.loadLevelData(createFlatLevel(), 1);

        letBoxLand(box);

        float startX = box.getX();

        for (int i = 0; i < 100; i++) {
            box.update(null, null);
        }

        // the x value should not have changed since nothing's pushing it
        assertEquals(startX, box.getX(), 0.001f);
    }

    // this checks that the box is moved when a horse walks into it
    @Test
    public void testBoxMovesWhenPushed() {
        Box box = new Box(8 * Game.TILES_SIZE, 10);
        box.loadLevelData(createFlatLevel(), 1);

        letBoxLand(box);

        float startX = box.getX();

        // this sets up a horse to the left of the box walking right
        Player p = new Player(1, 2 * Game.TILES_SIZE, 10, null, RIGHT, "Testing");

        p.loadLevelData(createFlatLevel());
        p.setCurentLevel(1);
        p.setRight(true);

        for (int i = 0; i < 50; i++) {
            p.update();
            box.update(p, null);
        }

        assertTrue(box.getX() > startX);
    }

    // this checks that the box does not clip through a wall when pushed into it
    @Test
    public void testBoxDoesntClipThroughWall() {

        int[][] level = createLevelWithRightWall();
        Box box = new Box(5 * Game.TILES_SIZE, 10);
        box.loadLevelData(level, 1);

        letBoxLand(box);

        // this sets up a horse pushing the box toward the right wall
        Player p = new Player(1, 2 * Game.TILES_SIZE, 10, null, RIGHT, "Testing");
        p.loadLevelData(level);
        p.setCurentLevel(1);

        letPlayerLand(p);

        p.setRight(true);

        for (int i = 0; i < 500; i++) {
            p.update();
            box.update(p, null);
        }

        // the right edge of the box should never go past the wall
        assertTrue(box.getX() + 32 <= (Game.TILES_IN_WIDTH - 1) * Game.TILES_SIZE);
    }

    // this checks that the horse does not end up inside the box when spawned nearby 
    @Test
    public void testPlayerDoesntSpawnInBox() {
        Box box = new Box(5 * Game.TILES_SIZE, 10);
        box.loadLevelData(createFlatLevel(), 1);

        Player p = new Player(1, 2 * Game.TILES_SIZE, 10, null, RIGHT, "Testing");
        p.loadLevelData(createFlatLevel());
        p.setCurentLevel(1);

        letBoxLand(box);
        letPlayerLand(p);

        assertFalse(box.isBlocking(p));
    }

    // this checks that the box stops when it lands on the horse
    @Test
    public void testBoxStopsOnPlayer() {
        int[][] level = createFlatLevel();

        Player p = new Player(1, 5 * Game.TILES_SIZE, 10, null, RIGHT, "Testing");
        p.loadLevelData(level);
        p.setCurentLevel(1);
        letPlayerLand(p);

        Box box = new Box(p.getHitbox().x, p.getHitbox().y - 32);
        box.loadLevelData(level, 1);

        for (int i = 0; i < 200; i++) {
            box.update(p, null);
        }
        
        /* 
        System.out.println("Player HB: " + p.getHitbox());
        System.out.println("Box HB: " + box.getHitbox());
        System.out.println("Box Bottom: " + (box.getHitbox().y + box.getHitbox().height));
        System.out.println("Player Top: " + p.getHitbox().y);
        */

        assertTrue(box.getHitbox().y + box.getHitbox().height <= p.getHitbox().y + 1);
    }
}
