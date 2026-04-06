package ui;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import org.w3c.dom.events.MouseEvent;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int bgX,bgY,bgW,bgH;
    public PauseOverlay(){
        loadBackground();
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_MENU);
        bgW=(int)(backgroundImg.getWidth()*Game.SCALE);
        bgH=(int)(backgroundImg.getHeight()*Game.SCALE*0.7);
        bgX=Game.GAME_WIDTH/2  - bgW/2;
        bgY =Game.GAME_HEIGHT/2  - bgH/2;
    }
        
    public void update(){

    }
    public void draw(Graphics g){
        bgW=Math.min(bgW,GAME_WIDTH);
        bgW=Math.min(bgW,GAME_HEIGHT);
        g.drawImage(backgroundImg, bgX, bgY, bgW,bgH,null);
    }

    public void mousePressed(MouseEvent e) {

    }


    public void mouseReleased(MouseEvent e) {

    }


    public void mouseMoved(MouseEvent e) {

    }
    public void mouseDragged(MouseEvent e){

    }


}
