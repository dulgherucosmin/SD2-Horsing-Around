package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import entities.Button;
import entities.Player;

public class ButtonTest {
    
    @Test
       public void buttonStartUnpressed(){
        Button button = new Button(0,0);
        assertFalse(button.isPressed());
       }

       @Test //button gets pressed when player touches it.
       public void buttonPresses(){
        Button button = new Button(0, 0);

        Player p1 = new Player(100,100, null, 0);
        Player p2 = new Player(0, 0, null, 0);

        p1.updateHitBox();
        p2.updateHitBox();
        button.update(p1, p2);

        assertTrue(button.isPressed());

       }

    }

    

