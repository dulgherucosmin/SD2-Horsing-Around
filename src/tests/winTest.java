package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

import entities.Player;
import entities.Win;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

public class winTest {

     // Helper to set private fields using reflection
    private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    // Helper to get private field values
    private Object getPrivateField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    @Test
    public void levelChangesToLevel2AfterLevel1Complete() throws Exception {
        Playing playing = new Playing((Game) null);

        // Simulate level 1 being completed
        setPrivateField(playing, "currentLevelNum", 1);
        setPrivateField(playing, "levelComplete", true);
        setPrivateField(playing, "levelCompleteTime", System.currentTimeMillis() - 1500);

        playing.update();

        int currentLevelNum = (int) getPrivateField(playing, "currentLevelNum");
        assertEquals(2, currentLevelNum);
    }

    @Test
    public void level2ChangesToMenuAfterComplete() throws Exception {
        Playing playing = new Playing((Game) null);

        // Simulate level 2 being completed
        setPrivateField(playing, "currentLevelNum", 2);
        setPrivateField(playing, "levelComplete", true);
        setPrivateField(playing, "levelCompleteTime", System.currentTimeMillis() - 1500);

        Gamestate.state = Gamestate.PLAYING;

        playing.update();

        assertEquals(Gamestate.MENU, Gamestate.state);
    }
    @Test
    public void winTrueWhenBothPlayersInside() {
        Win win = new Win(100, 100);

        Player player1 = new Player(100, 100, null, 0);
        Player player2 = new Player(100, 100, null, 0);

        player1.updateHitBox();
        player2.updateHitBox();

        assertTrue(win.completed(player1, player2));
    }

    @Test
    public void winFalseWithOnePlayerOutside() {
        Win win = new Win(100, 100);

        Player player1 = new Player(100, 100, null, 0);
        Player player2 = new Player(300, 300, null, 0);

        player1.updateHitBoxRaw();
        player2.updateHitBoxRaw();

        assertFalse(win.completed(player1, player2));
    }

    @Test
    public void winFalseWhenBothPlayersOutside() {
        Win win = new Win(100, 100);

        Player player1 = new Player(300, 300, null, 0);
        Player player2 = new Player(400, 400, null, 0);

        player1.updateHitBoxRaw();
        player2.updateHitBoxRaw();

        assertFalse(win.completed(player1, player2));
    }
}