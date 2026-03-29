//Horsing around
//Group 9

package entities;

import java.awt.*;

public class Door extends Entity {

    private Button button1;
    private Button button2;
    private float originalY; //doors closed position.
    private float openY; //doors open position
    private float speed = 2f;

    public Door(float x, float y, Button button1, Button button2){
        super(x, y, 8, 80);

        this.button1 = button1;
        this.button2 = button2;
        this.originalY = y;
        this.openY = y - 80; //moves door up after button press.
    }

    public void update(){
        boolean open = button1.isPressed() || button2.isPressed();

        if(open){
            if(y > openY){
                y-= speed;
                if(y < openY) y = openY;
            }
        } else{
            if(y < originalY){
                y+= speed;
                if(y > originalY) y= originalY;
            }
        }
        updateHitBox();
    }

    public boolean isBlocking(Player player){
        boolean notOpen = y > openY + 2;
        return notOpen && hitBox.intersects(player.getHitbox());
    }

    public boolean isOpen(){
        return y <= openY;
    }

    public void render (Graphics g){
        g.setColor(Color.RED);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    
}
