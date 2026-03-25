// Horsing Around
// Group 9

package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entities.Player;
import static utilz.Constants.Directions.RIGHT;

public class EntityTest {

    // tests if the entity spaws at the correct x value
    @Test
    public void testEntityInitialXPosition() {
        Player p = new Player(100, 200, null, RIGHT);
        assertEquals(100, p.getX());
    }

    // tests if the entity spawns at the correct y value
    @Test
    public void testEntityIntialYPosition() {
        Player p = new Player(100, 200, null, RIGHT);
        assertEquals(200, p.getY());
    }
}
