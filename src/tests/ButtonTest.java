// Horsing Around
// Group 9

package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import entities.Button;
import entities.Player;
import entities.Box;

public class ButtonTest {

   @Test
   public void buttonStartsUnpressed() {
      Button button = new Button(0, 0);
      assertFalse(button.isPressed());
   }

   @Test
   public void buttonPressedWhenPlayerOnIt() {
      Button button = new Button(0, 0);

      Player p1 = new Player(1, 0, 0, null, 0, "Test", null);
      Player p2 = new Player(2, 100, 100, null, 0, "Test", null);

      p1.updateHitBoxRaw();
      p2.updateHitBoxRaw();

      button.update(p1, p2, null);

      assertTrue(button.isPressed());
   }

   @Test
   public void buttonNotPressedWhenNoPlayerOnIt() {
      Button button = new Button(0, 0);

      Player p1 = new Player(1, 100, 100, null, 0, "Test", null);
      Player p2 = new Player(2, 200, 200, null, 0, "Test", null);

      p1.updateHitBoxRaw();
      p2.updateHitBoxRaw();

      button.update(p1, p2, null);

      assertFalse(button.isPressed());
   }

   @Test
   public void buttonPressedByEitherPlayer() {
      Button button = new Button(0, 0);

      Player p1 = new Player(1, 100, 100, null, 0, "Test", null);
      Player p2 = new Player(2, 0, 0, null, 0, "Test", null);

      p1.updateHitBoxRaw();
      p2.updateHitBoxRaw();

      button.update(p1, p2, null);

      assertTrue(button.isPressed());
   }

   @Test
    public void buttonPressedByBox() {
        Button button = new Button(0, 0);

        Player p1 = new Player(1, 100, 100, null, 0, "Testing", null);
        Player p2 = new Player(2, 200, 200, null, 0, "Testing", null);
        p1.updateHitBoxRaw();
        p2.updateHitBoxRaw();

        // Place the box lower so it overlaps the button hitbox,
        // since Button.update uses y + yOffset for the button hitbox.
        Box box = new Box(0, 0, "box.png");
        box.updateHitBoxRaw();

        button.update(p1, p2, box);

        assertTrue(button.isPressed());
    }
}