// Horsing Around
// Group 9

package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import static utilz.Constants.Directions.RIGHT;

import entities.Player;

public class PlayerInputTest {
    
    // tests that the player is left when setLeft method is called
    @Test
    public void testSetLeft() {
        Player p = new Player(1, 0, 0, null, RIGHT, "Test", null);
        p.setLeft(true);
        assertTrue(p.isLeft());
    }

    // tests that the player is right when setRight method is called
    @Test
    public void testSetRight() {
        Player p = new Player(1, 0,0, null, RIGHT, "Test", null);
        p.setRight(true);
        assertTrue(p.isRight());
    }

    // tests that all the movement flags are cleared after the resetDirBoolean is called
    @Test
    public void testResetDirBooleans() {
        Player p = new Player(1, 0, 0, null, RIGHT, "Test", null);
        p.setLeft(true);
        p.setRight(true);

        p.resetDirBooleans();

        assertFalse(p.isLeft());
        assertFalse(p.isRight());
    }
}
