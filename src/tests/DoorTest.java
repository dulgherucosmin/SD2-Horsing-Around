package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import entities.Button;
import entities.Door;
import entities.Player;

public class DoorTest {

    @Test
    public void testDoorClosed() {
        Button button1 = new Button(0, 0);
        Button button2 = new Button(0, 0);

        Door door = new Door(100, 100, button1, button2);

        door.update();
        assertFalse(door.isOpen());
    }

    @Test
    public void testDoorOpens() {
        Button button1 = new Button(50, 50);
        Button button2 = new Button(0, 0);

        Door door = new Door(100, 100, button1, button2);

        Player player1 = new Player(50, 50, null, 0); // player on button.
        Player player2 = new Player(200, 200, null, 0);

        button1.update(player1, player2);

        for (int i = 0; i < 100; i++) { // simulates time passing
            door.update();
        }
        assertTrue(door.isOpen());
    }

    @Test
    public void doorCloseWhenButtonReleased() {
        Button button1 = new Button(50, 50);
        Button button2 = new Button(0, 0);

        Door door = new Door(100, 100, button1, button2);

        Player player1 = new Player(50, 50, null, 0);
        Player player2 = new Player(200, 200, null, 0);

        // press button
        button1.update(player1, player2);

        for (int i = 0; i < 100; i++) {
            door.update();
        }

        // move player off the button
        Player p1Moved = new Player(300, 300, null, 0);

        button1.update(p1Moved, player2);

        for (int i = 0; i < 100; i++) {
            door.update();
        }

        assertFalse(door.isOpen());
    }

    @Test
    public void doorBlocksPlayer() {
        Button button1 = new Button(0, 0);
        Button button2 = new Button(0, 0);

        Door door = new Door(100, 100, button1, button2);

        Player player = new Player(100, 100, null, 0);

        door.update();

        assertTrue(door.isBlocking(player));
    }

    @Test
    public void doorNotBlockedWhenOpen() {
        Button button1 = new Button(100, 100);
        Button button2 = new Button(0, 0);

        Door door = new Door(100, 100, button1, button2);

        Player player = new Player(100, 100, null, 0);

        button1.update(player, player);

        for (int i = 0; i < 100; i++) {
            door.update();
        }
    }
}
