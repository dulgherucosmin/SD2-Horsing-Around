//Horsing around
//Group 9

package entities;

import java.awt.*;

public class Win extends Entity {

    public Win (float x, float y){
        super(x, y, 20, 15);
    }

    public boolean completed (Player p1, Player p2){
        return hitBox.intersects(p1.getHitbox()) && hitBox.intersects(p2.getHitbox());
    } 
    
    public void render (Graphics g, boolean done){
        g.setColor(Color.YELLOW);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

}
