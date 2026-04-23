// Horsing Around
// Group 9

package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import entities.Button;
import entities.Door;
import entities.Player;

public class DoorTest {

    @Test
    public void doorClosedInit() { //door starts closed
        Button button1 = new Button(0, 0);
        Button button2 = new Button(200, 200);

        Door door = new Door(100, 100, 4, button1, button2);

        assertFalse(door.isOpen());
    }

    @Test
    public void twoButtonDoorOpensWithFirstButton() { //first button check
        Button button1 = new Button(0, 0);
        Button button2 = new Button(200, 200);

        Door door = new Door(100, 100, 4, button1, button2);

        Player player = new Player(1, 0, 0, null, 0, "Test");
        player.updateHitBoxRaw();

        button1.update(player, player, null);

        assertTrue(door.isOpen());
    }

    @Test
    public void twoButtonDoorOpensWithSecondButton() { //second button check
        Button button1 = new Button(0, 0);
        Button button2 = new Button(200, 200);

        Door door = new Door(100, 100, 4, button1, button2);

        Player player = new Player(1, 200, 200, null, 0, "Testing");
        player.updateHitBoxRaw();

        button2.update(player, player, null);

        assertTrue(door.isOpen());
    }

    @Test
    public void oneButtonDoorOpensWithButton() { //single button door
        Button button1 = new Button(0, 0);

        Door door = new Door(100, 100, 4, button1);

        Player player = new Player(1, 0, 0, null, 0, "Testing");
        player.updateHitBoxRaw();

        button1.update(player, player, null);

        assertTrue(door.isOpen());
    }

    @Test
    public void doorClosesWhenButtonsReleased() { //door close check
        Button button1 = new Button(0, 0);
        Button button2 = new Button(200, 200);

        Door door = new Door(100, 100, 4, button1, button2);

        Player player = new Player(1, 0, 0, null, 0, "Test");
        player.updateHitBoxRaw();

        button1.update(player, player, null);
        assertTrue(door.isOpen());

        // release (move player away)
        Player moved = new Player(1, 300, 300, null, 0, "Test");
        moved.updateHitBoxRaw();

        button1.update(moved, moved, null);

        assertFalse(door.isOpen());
    }

    @Test
    public void doorBlocksPlayerWhenClosed() { //player block test
        Button button1 = new Button(0, 0);
        Button button2 = new Button(200, 200);

        Door door = new Door(100, 100, 4, button1, button2);

        Player player = new Player(1, 100, 100, null, 0, "Test");
        player.updateHitBoxRaw();

        assertTrue(door.isBlocking(player));
    }

    @Test
    public void doorDoesNotBlockWhenOpen() { //unblock test
        Button button1 = new Button(100, 100);
        Button button2 = new Button(200, 200);

        Door door = new Door(100, 100, 4, button1, button2);

        Player player = new Player(1, 100, 100, null, 0, "Test");
        player.updateHitBoxRaw();

        button1.update(player, player, null);

        assertFalse(door.isBlocking(player));
    }
    @Test
    public void doorOpensIfEitherButtonPressed() { //either button pressed check
        Button button1 = new Button(0, 0);
        Button button2 = new Button(200, 200);

        Door door = new Door(100, 100, 4, button1, button2);

        Player player = new Player(1, 200, 200, null, 0, "Test");
        player.updateHitBoxRaw();

        button2.update(player, player, null);

        assertTrue(door.isOpen());
    }
}