// Horsing Around
// Group 9 

package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import entities.Player;
import entities.Win;

public class winTest {
    @Test
    public void winTrueWhenBothPlayersInside() {
        Win win = new Win(100, 100);

        Player player1 = new Player(1, 100, 100, null, 0, "Test");
        Player player2 = new Player(2, 100, 100, null, 0, "Test");

        assertTrue(win.completed(player1, player2));
    }

    @Test
    public void winFalseWithOnePlayer() {
        Win win = new Win(100, 100);

        Player player1 = new Player(1, 100, 100, null, 0, "Test");
        Player player2 = new Player(2, 300, 300, null, 0, "Test");

        assertFalse(win.completed(player1, player2));
    }

    @Test
    public void winFalseWhenBothPlayersOutside() {
        Win win = new Win(100, 100);

        Player player1 = new Player(1, 100, 100, null, 0, "Test");
        Player player2 = new Player(2, 300, 300, null, 0, "Test");

        assertFalse(win.completed(player1, player2));
    }
}
