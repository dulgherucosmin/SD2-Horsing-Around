package tests;

import org.junit.Test;

import gamestates.Playing;
import utilz.LoadSave;
import main.Game;
import ui.OptionsOverlay;

import static org.junit.Assert.*;

public class OptionsTest {
    private Game game; 
    //checks if the player 1 color actually changes when selected
    @Test
    public void testPlayer1ColourChange(){
        Playing playing = new Playing(game);
        String newHorse = LoadSave.ALL_HORSES[2];
        playing.getPlayer1().setColour(newHorse);
        assertEquals(newHorse, playing.getPlayer1().getSpritePath());
    }

     //checks if the player 1 color actually changes when selected
    @Test
    public void testPlayer2ColourChange(){
        Playing playing = new Playing(game);
        String newHorse = LoadSave.ALL_HORSES[2];
        playing.getPlayer2().setColour(newHorse);
        assertEquals(newHorse, playing.getPlayer2().getSpritePath());
    }


    //two playes cannot select the same color so
    @Test
    public void testPlayer1Selection(){
        OptionsOverlay options =new OptionsOverlay(game);
        options.setP1SelectedHorse(0);
        options.setP2SelectedHorse(0);
        //ensures the colours are not the same
        assertNotEquals(options.getP1SelectedHorse(), options.getP2SelectedHorse());
    }


    //two playes cannot select the same color so
    @Test
    public void testPlayer2Selection(){
        OptionsOverlay options =new OptionsOverlay(game);
        options.setP2SelectedHorse(2);
        options.setP2SelectedHorse(0);
        options.setP1SelectedHorse(0);
        //ensures the colors are not the same
        assertNotEquals(options.getP2SelectedHorse(), options.getP1SelectedHorse());
    }

    //this tests if the fps  changes
    @Test
    public void testFPSChange(){
        Game game = new Game();
        game.setFPS(30);
        assertEquals(30, game.getFPS());
    }
 
}
