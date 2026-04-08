package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton{
    private BufferedImage []imgs;
    private BufferedImage slider;
    private int index =0;
    private int buttonX,minX,maxX;
    private boolean mouseOver,mousePressed;
    public VolumeButton(int x, int y, int width, int height ) {
        //starting the position of the slider button at the middle
        super(x+width/2, y, VOL_WIDTH, height);
        bounds.x-= VOL_WIDTH/2;
        buttonX = x+width/2;
        this.x = x;
        this.width =width;
        minX = x+VOL_WIDTH/2;
        maxX = x+width-VOL_WIDTH/2;
        loadImgs();
    }


    private void loadImgs(){
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        imgs = new BufferedImage[3];
        for(int i =0; i<imgs.length; i++){
            imgs[i] = temp.getSubimage(i*VOL_DEFAULT_WIDTH, 0, VOL_DEFAULT_WIDTH, VOL_DEFAULT_HEIGHT);
        }

        slider = temp.getSubimage(3*VOL_DEFAULT_WIDTH, 0,SLIDER_DEFAULT_WIDTH, VOL_DEFAULT_HEIGHT);
    }
    
    public void update(){
        index =0;
        if(mouseOver)
            index =1;
        if(mousePressed)
            index =2;
    } 
      
    public void draw (Graphics g){
        g.drawImage(slider, x, y,width, height, null);
        g.drawImage(imgs[index], buttonX-VOL_WIDTH/2, y, VOL_WIDTH, height, null);
    }

    public void changeX(int x){
        if(x<minX){
            buttonX = minX;
        }
        else if(x>maxX){
            buttonX = maxX;
        }
        else
            buttonX = x;

        bounds.x = buttonX-VOL_WIDTH/2;
    }

    public void resetBooleans(){
        mouseOver =false;
        mousePressed =false;
    }
    public boolean isMouseOver() {
         return mouseOver;
     }
     public void setMouseOver(boolean mouseOver) {
         this.mouseOver = mouseOver;
     }
     public boolean isMousePressed() {
         return mousePressed;
     }
     public void setMousePressed(boolean mousePressed) {
         this.mousePressed = mousePressed;
     }
     
}


 