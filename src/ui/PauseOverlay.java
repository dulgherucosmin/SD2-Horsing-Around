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
    private BufferedImage backgroundImg;
    private int bgX,bgY,bgW,bgH;
    private Game game;
    private SoundButton musicButton,sfxButton;
    private UrmButton menuB,replayB, unpauseB;
    private Playing playing;
    private VolumeButton volumeButton;

    public PauseOverlay(Game game,Playing playing){
        this.playing = playing;
        this.game = game;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    private void createSoundButtons() {
        int soundX=bgX + 130;
        int musicY=bgY + 85;
        int sfxY=bgY + 118;
        musicButton = new SoundButton( soundX,musicY,(int)(SOUND_SIZE*0.7f),(int)(SOUND_SIZE*0.7f));
        sfxButton =new SoundButton( soundX,sfxY,(int)(SOUND_SIZE*0.7f),(int)(SOUND_SIZE*0.7f));
    }

    private void createUrmButtons(){
        int menuX=(int) (180*Game.SCALE);
        int replayX =(int) (235*Game.SCALE);
        int unpauseX =(int) (290*Game.SCALE);
        int bY = (int)(223*Game.SCALE);

        menuB =new UrmButton(menuX, bY,URM_SIZE, URM_SIZE,2);
        replayB =new UrmButton(replayX, bY,URM_SIZE, URM_SIZE,1);
        unpauseB =new UrmButton(unpauseX, bY,URM_SIZE, URM_SIZE,0);
    }

    private void createVolumeButton(){
        int vX = (int)(170*Game.SCALE);
        int vY = (int) (187 *Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOL_HEIGHT);
    }

     private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_MENU);
        bgW=(int)(backgroundImg.getWidth()*Game.SCALE*0.8f);
        bgH=(int)(backgroundImg.getHeight()*Game.SCALE*0.75f);
        bgX=Game.GAME_WIDTH/2  - bgW/2;
        bgY =Game.GAME_HEIGHT/2  - bgH/2;
    }
        
    public void update(){
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();
        volumeButton.update();
    }

    public void draw(Graphics g){
        bgW=Math.min(bgW,GAME_WIDTH);
        bgH=Math.min(bgH,GAME_HEIGHT);
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

    public void mousePressed(MouseEvent e) {
        
        if(isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if(isIn(e, sfxButton))
            sfxButton.setMousePressed(true);

        else if(isIn(e, menuB)){
            menuB.setMousePressed(true);
        }
        else if(isIn(e, replayB)){
            replayB.setMousePressed(true);
        }
        else if(isIn(e, unpauseB)){
            unpauseB.setMousePressed(true);
        }
        else if(isIn(e, volumeButton)){
            volumeButton.setMousePressed(true);
        }

    }

    public void mouseReleased(MouseEvent e) {

        if(isIn(e, musicButton)){
            if(musicButton.isMousePressed()){
                musicButton.setMuted(!musicButton.isMuted());
            }

        }
        else if(isIn(e, sfxButton)){
            if(sfxButton.isMousePressed()){
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        }
        else if(isIn(e, menuB)){
            if(menuB.isMousePressed()){
               Gamestate.state=Gamestate.MENU;
               playing.unpauseGame(); 
            }
        }

        else if(isIn(e, replayB)){
            if(replayB.isMousePressed()){
            System.out.println("replay level");
            } 
        }

        else if(isIn(e, unpauseB)){
            if(unpauseB.isMousePressed()){
               playing.unpauseGame();
            }     
        }    



        musicButton.resetBooleans();
        sfxButton.resetBooleans();

        menuB.resetBooleans();
        replayB.resetBooleans();
        unpauseB.resetBooleans();
        volumeButton.resetBooleans();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);
 
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

    public void mouseDragged(MouseEvent e){
        float scaleX =(float) game.getGamePanel().getWidth()/GAME_WIDTH;
        int mouseX = (int)(e.getX()/scaleX);
        if(volumeButton.isMousePressed()){
            volumeButton.changeX(mouseX);
        }
    }

    private boolean isIn(MouseEvent e, PauseButton b){
        float scaleX =(float) game.getGamePanel().getWidth()/GAME_WIDTH;
        float scaleY =(float) game.getGamePanel().getHeight()/GAME_HEIGHT;

        int mouseX = (int)(e.getX()/scaleX);
        int mouseY = (int)(e.getY()/scaleY);

        return b.getBounds().contains(mouseX,mouseY);
    }

}
