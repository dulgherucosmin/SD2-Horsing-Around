package entities;

import java.awt.*;

public class Button extends Entity {

    private boolean pressed;

     public Button(float x, float y) {
        super(x, y, 24, 10);
        pressed = false;  
}

    public void update(Player p1, Player p2){
        if(!pressed && (hitBox.intersects(p1.getHitbox()) || hitBox.intersects(p2.getHitbox()) )){
            pressed = true;
        }
        updateHitBox();
    }

    public boolean isPressed(){
        return pressed;
    }

    public void render (Graphics g){
        g.setColor(Color.green);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.width);
    }
}
