// Horsing Around
// Group 9

package entities;
import java.awt.*;

public abstract class Entity {

    // entity positions
    protected float x, y;
    protected int width, height;
    protected Rectangle hitBox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initialiseHitBox();
    }

    // sprite dimensions
    public static final int SPRITE_WIDTH = 128;
    public static final int SPRITE_HEIGHT = 96;

    private void initialiseHitBox() {
        // center hitbox horizontally on sprite, align to bottom of sprite
        int hbX = (int)(x + (SPRITE_WIDTH - width) / 2f);
        int hbY = (int)(y + (SPRITE_HEIGHT - height));
        hitBox = new Rectangle(hbX, hbY, width, height);
    }

    protected void updateHitBox() {
        hitBox.x = (int)(x + (SPRITE_WIDTH - width) / 2f);
        hitBox.y = (int)(y + (SPRITE_HEIGHT - height));
    }

    protected void drawHitBox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
