package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

    public static int MENU = 0;
    public static int LEVEL = 1;

    public static int JUMP = 0;
    public static int DIE = 1;
    public static int LEVEL_COMPLETE = 2;
    public static int GAMEOVER = 3;

    private Clip[] songs, effects;

    private int currentSongID;
    private float volume = 0.5f;
    private boolean songMute, effectMute;

    public AudioPlayer() {
        loadSongs();
        loadEffects();
        playSong(MENU);
        setVolume(volume);
    }

    // this loads bg music clips onto the memory
    private void loadSongs() {
        String[] names = {"menu", "level"};
        songs = new Clip[names.length];
        
        for (int i = 0; i < songs.length; i++)
            songs[i] = getClip(names[i]);
    }

    // this loads the sound effect clips onto the memory
    private void loadEffects() {
        String[] effectNames = {"jump"};
        effects = new Clip[effectNames.length];
        
        for (int i = 0; i < effects.length; i++)
            effects[i] = getClip(effectNames[i]);

        updateEffectsVolume();
    }

    // this loads an audio file and converts it to a clip
    private Clip getClip(String name) {
        URL url = getClass().getResource("/res/audio/" + name + ".wav");
        AudioInputStream audio;

        if (url == null) {
            System.err.println("Audio file not found: /audio/" + name + ".wav");
            return null;
        }

        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;
    }

    // this sets the global audio for both the sounds and effects
    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    // this stops the current playing song
    public void stopSong() {
        if (songs == null || songs[currentSongID] == null) return;

        if (songs[currentSongID].isActive())
            songs[currentSongID].stop();
    }

    // this plays a sound effect once
    public void playEffect(int effect) {
        if (effect < 0 || effect >= effects.length) return;
        Clip c = effects[effect];
        if (c == null) return;

        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    // this plays a looping background song
    public void playSong(int song) {
        stopSong();

        currentSongID = song;

        Clip current = songs[currentSongID];
        if (current == null) return;

        updateSongVolume();

        songs[currentSongID].setMicrosecondPosition(0);
        songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);
    }

    // this toggles the song mute
    public void toggleSongMute() {
        this.songMute = !songMute;

        for (Clip c : songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    // this toggles the effect mute
    public void toggleEffectMute() {
        this.effectMute = !effectMute;

        for (Clip c : effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }

        if (!effectMute)
            playEffect(JUMP);
    }

    // this updates the volume for the currently playing song
    private void updateSongVolume() {
        Clip current = songs[currentSongID];

        if (current == null) return;

        FloatControl gainControl = (FloatControl) songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gain = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), gain));
        gainControl.setValue(gain);
    }

    // this updates the volume for all the sound effects
    private void updateEffectsVolume() {
        for(Clip c: effects) {

            if (c == null) continue;

            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gain = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), gain));
            gainControl.setValue(gain);
        }
    }

    // this checks if a given song is currently playing
    public boolean isSongPlaying(int song) {
        if (songs[song] == null) {
            return false;
        }
        
        return songs[song].isActive();
    }

    // this returns the id of the currently played song
    public int getCurrentSongID() {
        return currentSongID;
    }
}
