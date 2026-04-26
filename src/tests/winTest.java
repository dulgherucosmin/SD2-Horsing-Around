package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

import entities.Player;
import entities.Win;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

public class winTest {

     // method to set private fields
    private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    // method to get private field values
    private Object getPrivateField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    private KeyEvent enterKey() {
    return new KeyEvent(
        new Canvas(),
        KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(),
        0,
        KeyEvent.VK_ENTER,
        '\n'
    );
}

    @Test
    public void levelChangesToLevel2AfterLevel1Complete() throws Exception {
        Playing playing = new Playing((Game) null);

        // simulates level 1 completion
        setPrivateField(playing, "currentLevelNum", 1);
        setPrivateField(playing, "levelComplete", true);
        
        playing.keyPressed(enterKey());

        int currentLevelNum = (int) getPrivateField(playing, "currentLevelNum");
        assertEquals(2, currentLevelNum);
    }
    
    @Test
    public void levelChangesToLevel3AfterLevel2Complete() throws Exception{
        Playing playing = new Playing((Game) null);

        //simulates level 2 completion
        setPrivateField(playing, "currentLevelNum", 2);
        setPrivateField(playing, "levelComplete", true);

        playing.keyPressed(enterKey());

        int currentLevelNum = (int) getPrivateField(playing, "currentLevelNum");

        assertEquals(3, currentLevelNum);

    }

    @Test
    public void level3ChangesToMenuAfterComplete() throws Exception {
        Playing playing = new Playing((Game) null);

        // simulates level 3 change to menu
        setPrivateField(playing, "currentLevelNum", 3);
        setPrivateField(playing, "levelComplete", true);
        setPrivateField(playing, "levelCompleteTime", System.currentTimeMillis() - 1500);

        Gamestate.state = Gamestate.PLAYING;

        playing.keyPressed(enterKey());

        assertEquals(Gamestate.MENU, Gamestate.state);
    }
    @Test
    public void winTrueWhenBothPlayersInside() { //checks both players  are in win box.
        Win win = new Win(100, 100);

        Player player1 = new Player(1, 100, 100, null, 0, "Test");
        Player player2 = new Player(2, 100, 100, null, 0, "Test");

        player1.updateHitBox();
        player2.updateHitBox();

        assertTrue(win.completed(player1, player2));
    }

    @Test
    public void winFalseWithOnePlayerOutside() { //checks if both players are in win box, if not then false.
        Win win = new Win(100, 100);

        Player player1 = new Player(1, 100, 100, null, 0, "Test");
        Player player2 = new Player(2, 300, 300, null, 0, "Test");

        player1.updateHitBoxRaw();
        player2.updateHitBoxRaw();

        assertFalse(win.completed(player1, player2));
    }

    @Test
    public void winFalseWhenBothPlayersOutside() { //checks that player hitboxes are outside the win box.
        Win win = new Win(100, 100);

        Player player1 = new Player(1, 100, 100, null, 0, "Test");
        Player player2 = new Player(2, 300, 300, null, 0, "Test");

        assertFalse(win.completed(player1, player2));
    }
}