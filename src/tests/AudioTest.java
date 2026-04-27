// Horsing Around
// Group 9

package tests;

import org.junit.Test;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.State;
import main.Game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.net.URL;

import static org.junit.Assert.*;

public class AudioTest {

    // this stores the list of audio files that needs to be there in the res folder
    private String[] files = {"menu", "level", "jump"};

    // this checks that all audio files exist in resources
    @Test
    public void testAudioFilesExist() {
        for (String name : files) {
            URL url = getClass().getResource("/res/audio/" + name + ".wav");
            assertNotNull(name + ".wav is missing", url);
        }
    }

    // this checks that all audio files can actually be loaded
    @Test
    public void testAudioFilesLoad() {
        for (String name : files) {
            try {
                URL url = getClass().getResource("/res/audio/" + name + ".wav");
                assertNotNull(name + ".wav is missing", url);

                AudioInputStream stream = AudioSystem.getAudioInputStream(url);

                // if this is working, that means the file format is valid
                assertNotNull(name + ".wav failed to load", stream);

                stream.close();

            } catch (Exception e) {
                fail(name + ".wav threw exception: " + e.getMessage());
            }
        }
    }

    // this checks that clips can be created and opened
    @Test
    public void testClipsCanOpen() {
        for (String name : files) {
            try {
                URL url = getClass().getResource("/res/audio/" + name + ".wav");
                assertNotNull(url);

                AudioInputStream stream = AudioSystem.getAudioInputStream(url);

                var clip = AudioSystem.getClip();
                clip.open(stream);

                // if this clip opens sucessfully, the audio system accepts the file
                assertTrue(name + " clip failed to open", clip.isOpen());

                clip.close();
                stream.close();

            } catch (Exception e) {
                fail(name + ".wav failed to open clip: " + e.getMessage());
            }
        }
    }

    // this tests that the menu state switches correctly to the menu music
    @Test
    public void testMenuStateSetsMenuSong() {
        Game game = new Game();
        State state = new State(game);

        state.setGamestate(Gamestate.MENU);

        assertEquals(AudioPlayer.MENU, game.getAudioPlayer().getCurrentSongID());
    }

    // this tests that the playing state switches correctly to the level music
    @Test
    public void testPlayingStateSetsLevelSong() {
        Game game = new Game();
        State state = new State(game);

        state.setGamestate(Gamestate.PLAYING);

        assertEquals(AudioPlayer.LEVEL, game.getAudioPlayer().getCurrentSongID());
    }
}