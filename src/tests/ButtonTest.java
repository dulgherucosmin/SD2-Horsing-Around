package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import entities.Button;
import entities.Player;

public class ButtonTest {

   @Test
   public void buttonStartsUnpressed() {
      Button button = new Button(0, 0);
      assertFalse(button.isPressed());
   }

   @Test
   public void buttonPressedWhenPlayerOnIt() {
      Button button = new Button(0, 0);

      Player p1 = new Player(0, 0, null, 0);
      Player p2 = new Player(100, 100, null, 0);

      p1.updateHitBox();
      p2.updateHitBox();

      button.update(p1, p2);

      assertTrue(button.isPressed());
   }

   @Test
   public void buttonNotPressedWhenNoPlayerOnIt() {
      Button button = new Button(0, 0);

      Player p1 = new Player(100, 100, null, 0);
      Player p2 = new Player(200, 200, null, 0);

      p1.updateHitBox();
      p2.updateHitBox();

      button.update(p1, p2);

      assertFalse(button.isPressed());
   }

   @Test
   public void buttonPressedByEitherPlayer() {
      Button button = new Button(0, 0);

      Player p1 = new Player(100, 100, null, 0);
      Player p2 = new Player(0, 0, null, 0);

      p1.updateHitBox();
      p2.updateHitBox();

      button.update(p1, p2);

      assertTrue(button.isPressed());
   }
}