package ui;
import java.awt.Graphics;
import java.awt.GraphicsConfigTemplate;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.ResetButton.*;
import main.Game;
import utilz.LoadSave;

public class ResetButton {
    private int x, y, width, height;
    private Rectangle bounds;
    private BufferedImage img;
    private boolean mouseOver,mousePressed;

    public ResetButton(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadImg();
    }

    
    public Rectangle getBounds() {
        bounds = new Rectangle(x,y,width,height);
        return bounds;
    }
    
    private void loadImg(){
        img = LoadSave.GetSpriteAtlas(LoadSave.RESET_BUTTONS);
    }
    
    public void draw (Graphics g){
        if(mousePressed)
            g.drawImage(img, x+1, y+1, RESET_SIZE -2, RESET_SIZE-2 , null);
        else
            g.drawImage(img, x, y, RESET_SIZE, RESET_SIZE,null);
    }
    public void resetBooleans(){
        mouseOver =false;
        mousePressed =false;
    }
    public boolean isMouseOver(){
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }
    public boolean isMousePressed(){
        return mousePressed;
    }
    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}   

