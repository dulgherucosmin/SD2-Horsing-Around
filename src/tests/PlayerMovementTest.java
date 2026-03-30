// Horsing Around
// Group 9

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;

import main.Game;
import org.junit.jupiter.api.Test;

import entities.Player;

public class PlayerMovementTest {

    // create a simple level with solid floor
    private int[][] createFlatLevel() {
        int[][] levelData = new int[Game.TILES_IN_WIDTH][Game.TILES_IN_HEIGHT];

        // in our collision system, row 9 for level 1 is always solid
        // use 15 as maximum for tilesheet
        for (int row = 9; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                levelData[row][col] = 1;
            }
        }
        return levelData;
    }

    private void letPlayerLand(Player p) {
        // keep updating player until they fall on the ground prior to testing
        for (int i = 0; i < 200; i++) {
            p.update();
        }
    }

    // tests that moving right increases the x position
    @Test
    public void testMoveRight() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);
        float xPosition = p.getX();

        // move right
        p.setRight(true);
        p.update();

        // check if position is greater
        assertTrue(p.getX() > xPosition);
    }

    // tests that moving left decreases the x position
    @Test
    public void testMoveLeft() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);
        float xPosition = p.getX();

        // move right
        p.setLeft(true);
        p.update();

        // check if position is less
        assertTrue(p.getX() < xPosition);
    }

    // tests that pressing both left and right results in no horizontal movement
    @Test
    public void testLeftAndRightCancelsOut() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);
        float xPosition = p.getX();

        // move in both directions at once
        p.setLeft(true);
        p.setRight(true);
        p.update();

        // check if x position is unchanged
        assertEquals(xPosition, p.getX());
    }

    // tests that the player does not move when there's no input
    @Test
    public void testNoMovementWhenNoInput() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);
        float xPosition = p.getX();

        // check if x position is unchanged
        assertEquals(xPosition, p.getX());
    }

    // tests that the player accumulates correctly over muliple updates
    @Test
    public void testMovementAccumulatesOverUpdates() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);
        float xPosition = p.getX();

        // move right a few times
        p.setRight(true);
        p.update();
        p.update();
        p.update();

        // player moves at playerSpeed pixels each update, therefore multiply that by the amount of updates to get distance
        int distanceTravelled = (int) (xPosition + ((p.getPlayerSpeed() * 3)));
        assertTrue(p.getX() > distanceTravelled);
    }

    // tests that the 2 players move independently
    @Test
    public void testTwoPlayersIndependentMovement() {
        int pSpawn = 100;
        int p2Spawn = 200;

        Player p = new Player(pSpawn, 10, null, RIGHT);
        Player p2 = new Player(p2Spawn, 10, null, LEFT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        p2.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);
        p2.setCurentLevel(1);

        // move and test player 1 without player 2
        p.setRight(true);
        p.update();

        // save player 1 position after moving
        float p1Position = p.getX();

        // player 1 has moved
        assertTrue(p.getX() > pSpawn);
        // player 2 has remained still
        assertEquals(p2Spawn, p2.getX());

        // move and test player 2 without player 1
        p2.setRight(true);
        p2.update();
        p2.update();

        // player 2 has moved
        assertTrue(p2.getX() > p2Spawn);
        // player 1 has remained still
        assertEquals(p1Position, p.getX());
    }
}
