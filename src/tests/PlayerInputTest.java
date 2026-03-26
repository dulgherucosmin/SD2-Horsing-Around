// Horsing Around
// Group 9

package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utilz.Constants.Directions.RIGHT;

import org.junit.jupiter.api.Test;

import entities.Player;

public class PlayerInputTest {
    
    // tests that the player is left when setLeft method is called
    @Test
    public void testSetLeft() {
        Player p = new Player(0, 0, null, RIGHT);
        p.setLeft(true);
        assertTrue(p.isLeft());
    }

    // tests that the player is right when setRight method is called
    @Test
    public void testSetRight() {
        Player p = new Player(0,0, null, RIGHT);
        p.setRight(true);
        assertTrue(p.isRight());
    }

    // tests that all the movement flags are cleared after the resetDirBoolean is called
    @Test
    public void testResetDirBooleans() {
        Player p = new Player(0, 0, null, RIGHT);
        p.setLeft(true);
        p.setRight(true);

        p.resetDirBooleans();

        assertFalse(p.isLeft());
        assertFalse(p.isRight());
    }
}
