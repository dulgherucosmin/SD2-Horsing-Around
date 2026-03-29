//Horsing around
//Group 9

package entities;

import java.awt.*;

public class Button extends Entity {

    private boolean pressed;

     public Button(float x, float y) {
        super(x, y, 16,6);
        pressed = false;  
}
    //gets player hitboxes and intersects them with button
    // if button not pressed down and hitboxes intersect, button gets pressed
    public void update(Player p1, Player p2){ 
        updateHitBox();

        boolean player1 = hitBox.intersects(p1.getHitbox());
        boolean player2 = hitBox.intersects(p2.getHitbox());

        pressed = player1 || player2;
    }

    public boolean isPressed(){
        return pressed;
    }

    public void render (Graphics g){
        g.setColor(Color.green);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }
}
