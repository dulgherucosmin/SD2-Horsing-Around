// Horsing Around
// Group 9

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;

import org.junit.jupiter.api.Test;

import entities.Player;

public class PlayerMovementTest {

    // tests that moving right increases the x position
    @Test
    public void testMoveRight() {
        Player p = new Player(200, 200, null, RIGHT);
        p.setRight(true);
        p.update();

        assertTrue(p.getX() > 200);
    }

    // tests that moving left decreases the x position
    @Test
    public void testMoveLeft() {
        Player p = new Player(200, 200, null, RIGHT);
        p.setLeft(true);
        p.update();

        assertTrue(p.getX() < 200);
    }

    // tests that pressing both left and right results in no horizontal movement
    @Test
    public void testLeftAndRightCancelsOut() {
        Player p = new Player(200, 200, null, RIGHT);

        p.setLeft(true);
        p.setRight(true);

        p.update();

        assertEquals(200, p.getX());
    }

    // tests that the player does not move when there's no input
    @Test
    public void testNoMovementWhenNoInput() {
        Player p = new Player(200, 200, null, RIGHT);
        p.update();
        assertEquals(200, p.getX());
    }

    // tests that the player accumulates correctly over muliple updates
    @Test
    public void testMovementAccumulatesOverUpdates() {
        Player p = new Player(200, 200, null, RIGHT);
        p.setRight(true);
        p.update();
        p.update();
        p.update();

        assertTrue(p.getX() > 202);
    }

    // tests that the 2 players move independently
    @Test
    public void testTwoPlayersIndependentMovement() {
        Player p1 = new Player(100, 100, null, RIGHT);
        Player p2 = new Player(400, 100, null, LEFT);

        p1.setRight(true);
        p1.update();

        assertEquals(400, p2.getX());
        assertTrue(p1.getX() > 100);
    }
}
