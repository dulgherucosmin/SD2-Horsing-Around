// Horsing Around
// Group 9

package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import entities.Player;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;

public class EntityTest {

    // tests if the entity spaws at the correct x value
    @Test
    public void testEntityInitialXPosition() {
        Player p = new Player(1, 100, 200, null, RIGHT, "Test");
        assertEquals(100, p.getX(), 0);
    }

    // tests if the entity spawns at the correct y value
    @Test
    public void testEntityIntialYPosition() {
        Player p = new Player(1, 100, 200, null, RIGHT, "Test");
        assertEquals(200, p.getY(), 0);
    }

    // tests that both players have separate positions and that they don't share the same coordinates
    @Test
    public void testEntitiesHaveIndependentXPositions() {
        Player p = new Player(1, 100, 200, null, RIGHT, "Test");
        Player p2 = new Player(2, 400, 200, null, LEFT, "Test");

        assertNotEquals(p.getX(), p2.getX());
    }
}
