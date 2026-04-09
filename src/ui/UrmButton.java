package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.UrmButtons.*;

public class UrmButton extends PauseButton{
    //attributes for Urm buttons
    private BufferedImage[] imgs;
    private int rowIndex,index;
    private boolean mouseOver, mousePressed;

    //constructor 
    public UrmButton(int x, int y, int width, int height,int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
      
    }
    // gets urm button from res folder and stores it in an array
    private void loadImgs(){
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for(int i =0; i<imgs.length; i++){
            imgs[i] = temp.getSubimage(i*URM_DEFAULT_SIZE, rowIndex*URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }

    }
    //update the state of button in relation to the mouse input
    public void update(){
        index =0;
        if(mouseOver)
            index =1;
        if(mousePressed)
            index =2;
    } 

    //draw the button with its position
    public void draw (Graphics g){
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE,  null);
    }

        //getters and setters
        
    //reset the mouse inputs
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
