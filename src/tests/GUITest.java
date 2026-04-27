// Horsing Around
// Group 9

package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import ui.MenuButton;
import ui.ResetButton;

public class GUITest {
    private Game game;
    // checks if the mouse Over is correctly set to true
    @Test
    public void testMouseOver(){
        MenuButton mb = new MenuButton(256, 35, 0, Gamestate.PLAYING);
        mb.setMouseOver(true);
        mb.update(); // updating the value of setMouseOver value
        assertTrue(mb.isMouseOver());
    }

    // checks if the mouse Pressed is correctly set to true
    @Test
    public void testMousePressd(){
        MenuButton mb = new MenuButton(256, 35, 0, Gamestate.PLAYING);
        mb.setMousePressed(true);
        mb.update();
        assertTrue(mb.isMousePressed());
    }

    // checks if the buttons reset after pressed or after hovering
    @Test
    public void testReset(){

        MenuButton mb = new MenuButton(256, 35, 0, Gamestate.PLAYING);

        // sets both values
        mb.setMouseOver(true);
        mb.setMousePressed(true);

        // resets the buttons
        mb.resetBoolean();

        assertFalse(mb.isMouseOver());
        assertFalse(mb.isMousePressed());

    }

    // checks if the bounds around the menu buttons are set and not null
    @Test
    public void testBoundsNotNull(){
        MenuButton mb = new MenuButton(256, 35, 0, Gamestate.PLAYING);
       assertNotNull(mb.getBounds());

    }

    // test to see if state changes to playing
    @Test
    public void testSetGameState(){
        MenuButton mb = new MenuButton(256, 35, 0, Gamestate.PLAYING);
        mb.setGameState(); // this initializes the change
        assertEquals(mb.getGamestate(), Gamestate.state); // checks if its the same
    }

    // test to see if state changes to Quit
    @Test
    public void testSetGameStateQuit(){
        MenuButton mb = new MenuButton(256, 35, 0, Gamestate.QUIT);
        mb.setGameState(); // this initializes the change
        assertEquals(mb.getGamestate(), Gamestate.state); // checks if its the same
    }
     @Test
    public void testGameReset(){

        ResetButton resetButton = new ResetButton(100, 100, 16, 16);
        //simulate playing game and level is complete
        Playing playing = new Playing(game);
        playing.setLevelComplete(true);
        // simulate the button being pressed
        resetButton.setMousePressed(true);

        //only reset button if mouse is pressed and is inside bounds
        if(resetButton.isMousePressed() && resetButton.getBounds().contains(108,108)){
            playing.resetLevel();
        }

        //clear button states after release
        resetButton.resetBooleans();
        //game should not be complete after reset
        assertFalse(playing.isLevelComplete());
        //game should not be paused after reset

  }
}
