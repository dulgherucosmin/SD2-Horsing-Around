

package ui;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.UrmButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;

import java.awt.event.MouseEvent;
import main.Game;

import utilz.LoadSave;


public class PauseOverlay {
    //attributes for the pause menu
    private BufferedImage backgroundImg;
    private int bgX,bgY,bgW,bgH;
    private Game game;
    private SoundButton musicButton,sfxButton;
    private UrmButton menuB,replayB, unpauseB;
    private Playing playing;
    private VolumeButton volumeButton;

    //constructor
    public PauseOverlay(Game game,Playing playing){
        this.playing = playing;
        this.game = game;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    //this creates the sound and sfxbutton and positions it
    private void createSoundButtons() {
        int soundX=bgX + 130;
        int musicY=bgY + 85;
        int sfxY=bgY + 118;
        musicButton = new SoundButton( soundX,musicY,(int)(SOUND_SIZE*0.7f),(int)(SOUND_SIZE*0.7f));
        sfxButton =new SoundButton( soundX,sfxY,(int)(SOUND_SIZE*0.7f),(int)(SOUND_SIZE*0.7f));
    }

    //creates and positions the unpause replay and menu buttons
    private void createUrmButtons(){
        int menuX=(int) (180*Game.SCALE);
        int replayX =(int) (235*Game.SCALE);
        int unpauseX =(int) (290*Game.SCALE);
        int bY = (int)(223*Game.SCALE);

        menuB =new UrmButton(menuX, bY,URM_SIZE, URM_SIZE,2);
        replayB =new UrmButton(replayX, bY,URM_SIZE, URM_SIZE,1);
        unpauseB =new UrmButton(unpauseX, bY,URM_SIZE, URM_SIZE,0);
    }

    //this creates and positions  the slider button
    private void createVolumeButton(){
        int vX = (int)(170*Game.SCALE);
        int vY = (int) (187 *Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOL_HEIGHT);
    }

    //this loads the pause menu background
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_MENU);
        //this reduces the size of the mennu to make it fit in screen
        bgW=(int)(backgroundImg.getWidth()*Game.SCALE*0.8f);
        bgH=(int)(backgroundImg.getHeight()*Game.SCALE*0.75f);
        bgX=Game.GAME_WIDTH/2  - bgW/2;
        bgY =Game.GAME_HEIGHT/2  - bgH/2;
    }
    
    //update buttons
    public void update(){
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();
        volumeButton.update();
    }

    //draw the buttons
    public void draw(Graphics g){
        bgW=Math.min(bgW,GAME_WIDTH);
        bgH=Math.min(bgH,GAME_HEIGHT);

        g.setColor(new java.awt.Color(0, 0, 0, 180));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        //pause background 
        g.drawImage(backgroundImg, bgX, bgY, bgW,bgH,null);

        //Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        volumeButton.draw(g);
    }

    //when mouse is pressed it checks what button was clicked and marks it as pressed
    public void mousePressed(MouseEvent e) {
        
        if(isIn(e, musicButton))
            //toggle music button on /off
            musicButton.setMousePressed(true);
            
        else if(isIn(e, sfxButton))
            //toggle sound effects on /off
            sfxButton.setMousePressed(true);

        else if(isIn(e, menuB)){
            //return to main menu
            menuB.setMousePressed(true);
        }

        else if(isIn(e, replayB)){
            //replay current level
            replayB.setMousePressed(true);
        }
        else if(isIn(e, unpauseB)){
            //resume game
            unpauseB.setMousePressed(true);
        }
        else if(isIn(e, volumeButton)){
            //adjust button slider
            volumeButton.setMousePressed(true);
        }

    }

    //when the mouse is released checks if it its on the button and then triggers action
    public void mouseReleased(MouseEvent e) {

        if(isIn(e, musicButton)){
            if(musicButton.isMousePressed()){
                //toggle music mute button
                musicButton.setMuted(!musicButton.isMuted());
                game.getAudioPlayer().toggleSongMute();
            }

        }
        else if(isIn(e, sfxButton)){
            if(sfxButton.isMousePressed()){
                //toggle sfxmute button
                sfxButton.setMuted(!sfxButton.isMuted());//
                game.getAudioPlayer().toggleEffectMute();
            }
        }
        else if(isIn(e, menuB)){
            if(menuB.isMousePressed()){
                playing.unpauseGame(); 
                game.getAudioPlayer().playSong(audio.AudioPlayer.MENU);
                //switches button to main menu
                Gamestate.state=Gamestate.MENU;
                //unpause the game before leaving

            }
        } 

        else if(isIn(e, replayB)){
            if(replayB.isMousePressed()){
                playing.resetGame();
            } 
        }

        else if(isIn(e, unpauseB)){
            if(unpauseB.isMousePressed()){
                //resume game
               playing.unpauseGame();
            }     
        }    


        //resets all buttons 
        musicButton.resetBooleans();
        sfxButton.resetBooleans();

        menuB.resetBooleans();
        replayB.resetBooleans();
        unpauseB.resetBooleans();
        volumeButton.resetBooleans();

    }

    //when mouse is moved fro mbutton trigger action
    public void mouseMoved(MouseEvent e) {

        // reset the button to not hovered
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);
        
        //then maek only the button the mouse is currently over
        if(isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if(isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if(isIn(e, menuB))
            menuB.setMouseOver(true);
        else if(isIn(e, replayB))
            replayB.setMouseOver(true);
        else if(isIn(e, unpauseB))
            unpauseB.setMouseOver(true);
        else if(isIn(e, volumeButton))
            volumeButton.setMouseOver(true);

    }

    //what happens when mouse is dragged
    public void mouseDragged(MouseEvent e){
        //scale the mouse button to game resolution
        float scaleX =(float) game.getGamePanel().getWidth()/GAME_WIDTH;
        int mouseX = (int)(e.getX()/scaleX);
        //only move slider if the button is clicked and held
        if(volumeButton.isMousePressed()){
            //move the slider knob to follow the mouse
            volumeButton.changeX(mouseX);
            int range = volumeButton.getMaxX() - volumeButton.getMinX();

            if (range > 0) {
                float volume = (float)(volumeButton.getButtonX() - volumeButton.getMinX()) / range;
                volume = Math.max(0f, Math.min(1f, volume));
                game.getAudioPlayer().setVolume(volume);
            }
        }
    }

 //helps fix scaling of the buttons hover by converting mouse coordinates into out game dimensions and checks if the mouse hovers over the buttons bound
    private boolean isIn(MouseEvent e, PauseButton b){
        float scaleX =(float) game.getGamePanel().getWidth()/GAME_WIDTH;
        float scaleY =(float) game.getGamePanel().getHeight()/GAME_HEIGHT;

        int mouseX = (int)(e.getX()/scaleX);
        int mouseY = (int)(e.getY()/scaleY);

        return b.getBounds().contains(mouseX,mouseY);
    }

}