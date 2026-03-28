package tests;
import main.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static utilz.Constants.Directions.RIGHT;

import entities.Player;
public class MovementTest {

    // create a simple level with solid floor
    private int[][] createFlatLevel() {
        int[][] levelData = new int[Game.TILES_IN_WIDTH][Game.TILES_IN_HEIGHT];

        // in our collision system, row 9 for level 1 is always solid
        for (int row = 9; row < Game.TILES_IN_HEIGHT; row++) {
            for (int col = 0; col < Game.TILES_IN_WIDTH; col++) {
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

    //testing if the player is in the air
    @Test
    public void testJumpInAir(){
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);

        p.jump();
        // ensure player is in the air after jumping
        assertTrue(p.isInAir());
    }

    //testing gravity
    @Test
    public void testGravity(){
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);

        p.jump();

        float peakY = p.getY();
        boolean hasFallen = false;

        // run enough frames for full jump arc
        for (int i = 0; i < 200; i++) {
            p.update();
            // player has moved downwards (so started falling)
            if (p.getY() > peakY) {
                hasFallen = true;
                break;
            }
            // track highest point of the jump
            if (p.getY() < peakY) {
                peakY = p.getY();
            }
        }

        assertTrue(hasFallen);
    }

    //this checks if the player actually still in the air after jumping
    @Test
    public void testLanding(){
        Player p = new Player(100, 10, null, RIGHT);
        // load our test level into player code
        p.loadLevelData(createFlatLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);

        p.jump();

        // run enough frames for full jump arc
        for (int i = 0; i < 200; i++) {
            p.update();
        }

        assertFalse(p.isInAir());
    }


}



