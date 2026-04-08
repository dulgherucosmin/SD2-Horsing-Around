package ui;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.UrmButtons.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import main.Game;

import utilz.LoadSave;


public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int bgX,bgY,bgW,bgH;
    private Game game;
    private SoundButton musicButton,sfxButton;
    private UrmButton menuB,replayB, unpauseB;

    public PauseOverlay(Game game){
        this.game = game;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
    }

    private void createSoundButtons() {
        int soundX=bgX + 130;
        int musicY=bgY + 80;
        int sfxY=bgY + 115;
        musicButton = new SoundButton( soundX,musicY,(int)(SOUND_SIZE*0.7f),(int)(SOUND_SIZE*0.7f));
        sfxButton =new SoundButton( soundX,sfxY,(int)(SOUND_SIZE*0.7f),(int)(SOUND_SIZE*0.7f));
    }

    private void createUrmButtons(){
        int menuX=(int) (180*Game.SCALE);
        int replayX =(int) (235*Game.SCALE);
        int unpauseX =(int) (290*Game.SCALE);
        int bY = (int)(200*Game.SCALE);

        menuB =new UrmButton(menuX, bY,URM_SIZE, URM_SIZE,2);
        replayB =new UrmButton(replayX, bY,URM_SIZE, URM_SIZE,1);
        unpauseB =new UrmButton(unpauseX, bY,URM_SIZE, URM_SIZE,0);
    }
      
     private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_MENU);
        bgW=(int)(backgroundImg.getWidth()*Game.SCALE*0.8f);
        bgH=(int)(backgroundImg.getHeight()*Game.SCALE*0.7f);
        bgX=Game.GAME_WIDTH/2  - bgW/2;
        bgY =Game.GAME_HEIGHT/2  - bgH/2;
    }
        
    public void update(){
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();
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
    }

    public void mousePressed(MouseEvent e) {
        
        if(isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if(isIn(e, sfxButton))
            sfxButton.setMousePressed(true);

    }

    public void mouseReleased(MouseEvent e) {

        if(isIn(e, musicButton)){
            if(musicButton.isMousePressed()){
                musicButton.setMuted(!musicButton.isMuted());
            }

        }
        else if(isIn(e, sfxButton))
            if(sfxButton.isMousePressed()){
                sfxButton.setMuted(!sfxButton.isMuted());
            }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
 
        if(isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if(isIn(e, sfxButton))
            sfxButton.setMouseOver(true);

    }

    public void mouseDragged(MouseEvent e){

    }

    private boolean isIn(MouseEvent e, PauseButton b){
        float scaleX =(float) game.getGamePanel().getWidth()/GAME_WIDTH;
        float scaleY =(float) game.getGamePanel().getHeight()/GAME_HEIGHT;

        int mouseX = (int)(e.getX()/scaleX);
        int mouseY = (int)(e.getY()/scaleY);

        return b.getBounds().contains(mouseX,mouseY);
    }

}
