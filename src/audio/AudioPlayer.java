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
    private float volume = 0.75f;
    private boolean songMute, effectMute;

    public AudioPlayer() {
        loadSongs();
        loadEffects();
        playSong(MENU);
    }

    private void loadSongs() {
        String[] names = {"menu", "level"};
        songs = new Clip[names.length];
        
        for (int i = 0; i < songs.length; i++)
            songs[i] = getClip(names[i]);
    }

    private void loadEffects() {
        String[] effectNames = {"jump", "die", "lvlcomplete", "gameover", "door"};
        effects = new Clip[effectNames.length];
        
        for (int i = 0; i < effects.length; i++)
            effects[i] = getClip(effectNames[i]);

        updateEffectsVolume();
    }

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

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    public void stopSong() {
        if (songs == null || songs[currentSongID] == null) return;

        if (songs[currentSongID].isActive())
            songs[currentSongID].stop();
    }

    public void lvlCompleted() {
        stopSong();
        playEffect(LEVEL_COMPLETE);
    }

    public void playEffect(int effect) {
        if (effect < 0 || effect >= effects.length) return;
        Clip c = effects[effect];
        if (c == null) return;

        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    public void playSong(int song) {
        stopSong();

        currentSongID = song;

        Clip current = songs[currentSongID];
        if (current == null) return;

        updateSongVolume();

        songs[currentSongID].setMicrosecondPosition(0);
        songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void toggleSongMute() {
        this.songMute = !songMute;

        for (Clip c : songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;

        for (Clip c : effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }

        if (!effectMute)
            playEffect(JUMP);
    }

    private void updateSongVolume() {
        Clip current = songs[currentSongID];

        if (current == null) return;

        FloatControl gainControl = (FloatControl) songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    private void updateEffectsVolume() {
        for(Clip c: effects) {

            if (c == null) continue;

            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    public boolean isSongPlaying(int song) {
        if (songs[song] == null) {
            return false;
        }
        
        return songs[song].isActive();
    }
}
