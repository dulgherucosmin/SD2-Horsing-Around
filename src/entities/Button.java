// Horsing around
// Group 9

package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Button extends Entity {

    private boolean pressed;
    private BufferedImage unpressedButton;
    private BufferedImage pressedButton;

    private int yOffset = 10;

    public Button(float x, float y) {
        super(x, y, 16, 6, true);
        pressed = false;
        loadSprites();

    }

    private void loadSprites() { //load button sprite
        BufferedImage sheet = LoadSave.GetSpriteAtlas("level_one_tilesheet2.png");
        unpressedButton = sheet.getSubimage(2 * 16, 3 * 16, 16, 16);
        pressedButton = sheet.getSubimage(3 * 16, 3 * 16, 16, 16);
    }

    // gets player hitboxes and intersects them with button
    // if button not pressed down and hitboxes intersect, button gets pressed
    public void update(Player p1, Player p2, Box box) {
        hitBox.x = (int) x;
        hitBox.y = (int) y + yOffset; //aligns button hitbox with button sprite.
        

        boolean player1 = hitBox.intersects(p1.getHitbox());
        boolean player2 = hitBox.intersects(p2.getHitbox());
        boolean boxPressed = box != null && hitBox.intersects(box.getHitbox()); // lets box push buttons

        pressed = player1 || player2 || boxPressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void render(Graphics g) {
        if (pressed) {
            g.drawImage(pressedButton, (int) x, (int) y, 16, 16, null);
        } else {
            g.drawImage(unpressedButton, (int) x, (int) y, 16, 16, null);
        }
       /*  g.setColor(Color.green);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);*/
    }
}
