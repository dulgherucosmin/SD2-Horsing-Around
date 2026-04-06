package ui;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utilz.Constants.UI.PauseButtons.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.awt.event.MouseEvent;
import gamestates.Gamestate;
import main.Game;

import utilz.LoadSave;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int bgX,bgY,bgW,bgH;
    private Game game;
    private SoundButton musicButton,sfxButton;

    public PauseOverlay(Game game){
        this.game = game;
        loadBackground();
        createSoundButtons();
    }

    private void createSoundButtons() {
        int soundX=bgX + 130;
        int musicY=bgY + 80;
        int sfxY=bgY + 115;
        musicButton = new SoundButton( soundX,musicY,(int)(SOUND_SIZE*0.7f),(int)(SOUND_SIZE*0.7f));
        sfxButton =new SoundButton( soundX,sfxY,(int)(SOUND_SIZE*0.7f),(int)(SOUND_SIZE*0.7f));
    }
      
     private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_MENU);
        bgW=(int)(backgroundImg.getWidth()*Game.SCALE*0.8f);
        bgH=(int)(backgroundImg.getHeight()*Game.SCALE*0.7);
        bgX=Game.GAME_WIDTH/2  - bgW/2;
        bgY =Game.GAME_HEIGHT/2  - bgH/2;
    }
        
    public void update(){
        musicButton.update();
        sfxButton.update();
    }

    public void draw(Graphics g){
        bgW=Math.min(bgW,GAME_WIDTH);
        bgH=Math.min(bgH,GAME_HEIGHT);
        //pause background 
        g.drawImage(backgroundImg, bgX, bgY, bgW,bgH,null);

        //Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);
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
