package tests;

import entities.Player;
import main.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static utilz.Constants.Directions.RIGHT;

public class CollisionTest {

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

    @Test
    public void testPlayerLanding() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);

        // player should no longer be in the air if they landed
        assertFalse(p.isInAir());
    }

    @Test
    public void testFloorCollision() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);

        // floor is at row 9, player should be above this
        assertTrue(p.getY() < (9 * Game.TILES_SIZE));
    }

    @Test
    public void testLevelRightBoundary() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);

        // walk until we're past game width
        p.setRight(true);
        for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
            p.update();
        }

        // x position should never be greater than game width
        assertTrue(p.getX() < Game.GAME_WIDTH);
    }

    @Test
    public void testLevelLeftBoundary() {
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);

        // try to walk to the left (off screen)
        p.setLeft(true);
        for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
            p.update();
        }

        // x should never go negative (starting position 0,0)
        assertTrue(p.getX() >= 0);
    }

}
