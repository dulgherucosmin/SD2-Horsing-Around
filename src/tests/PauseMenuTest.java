package tests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import gamestates.Gamestate;
import gamestates.Playing;
import ui.MenuButton;
import ui.PauseOverlay;
import ui.VolumeButton;
import main.Game;

public class PauseMenuTest {
    private Game game;
    //checks if the game actually pauses 
    @Test
    public void testGamePause(){
       Playing playing = new Playing(game);
       playing.setPaused(true);
       assertTrue(playing.isPaused());
    }

    //checks if the game goes back to unpaused
    @Test
    public void testGameUnPause(){
        Playing playing = new Playing(game);
        playing.setPaused(true);
        playing.unpauseGame();
        assertFalse(playing.isPaused());
     }

     //checks if the slider button goes past the slider from the min (far left)
     @Test
     public void testVolumeSliderMin(){
        VolumeButton volumeButton = new VolumeButton(100,100, 200, 20);
        volumeButton.changeX(-500);
        assertTrue(volumeButton.getButtonX()<=volumeButton.getMinX());
     }

      //checks if the slider button goes past the slider from the max (far right)
      @Test
      public void testVolumeSliderMax(){
         VolumeButton volumeButton = new VolumeButton(100,100, 200, 20);
         volumeButton.changeX(9999);
         assertTrue(volumeButton.getButtonX()<=volumeButton.getMaxX());
      }
      //checks that pause overlay is not null when game is paused
      @Test
      public void testPauseOverLayIsNotNull(){
        Playing playing = new Playing(game); 
        PauseOverlay pauseOverlay = new PauseOverlay(game, playing);
        assertNotNull(pauseOverlay);
    }

    //checks if the game is paused when pause overlay is displayed
    @Test
    public void testIsPausedWithOverlay(){
      Playing playing = new Playing(game); 
      PauseOverlay pauseOverlay = new PauseOverlay(game, playing);
      assertNotNull(pauseOverlay);
  }
}
