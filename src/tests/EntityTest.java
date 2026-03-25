// Horsing Around
// Group 9

package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entities.Player;
import static utilz.Constants.Directions.RIGHT;

public class EntityTest {
    @Test
    public void testEntityInitialPosition() {
        Player p = new Player(100, 200, null, RIGHT);
        assertNotNull(p);
    }
}
