//Horsing around
//Group 9

package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Door extends Entity {

    private Button button1;
    private Button button2;
    private BufferedImage doorSprite;

    public Door(float x, float y, Button button1, Button button2){
        super(x, y, 6, 64, true);

        this.button1 = button1;
        this.button2 = button2;
        loadSprites();
    }
       public void loadSprites() { //loads door sprite
        BufferedImage sheet = LoadSave.GetSpriteAtlas("level_one_tilesheet2.png");
        doorSprite = sheet.getSubimage(0 * 16, 3 * 16, 16, 16);
    }

    public void update(){
      
            }
        
        
    

    public boolean isBlocking(Player player){
       /* boolean notOpen = y > openY + 2;
        return notOpen && hitBox.intersects(player.getHitbox());*/
        return !isOpen() && hitBox.intersects(player.getHitbox());
    }

    public boolean isOpen(){
        //return y <= openY;
         return button1.isPressed() || button2.isPressed();
    }

    public void render (Graphics g){
       if (!isOpen()) {
            for (int i = 0; i < 4; i++) {
                g.drawImage(doorSprite, (int) x, (int) y + (i * 16), 16, 16,null);
            }
           // drawHitBox(g);
        }
    }

    
}
