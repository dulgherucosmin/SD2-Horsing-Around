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

    public Entity(float x, float y, int width, int height, boolean rawPosition) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if (rawPosition) {
            hitBox = new Rectangle((int) x, (int) y, width, height);
        } else {
            initialiseHitBox();
        }
    }

    public void updateHitBoxRaw() {
        hitBox.x = (int) x;
        hitBox.y = (int) y;
    }

    // sprite dimensions
    public static final int SPRITE_WIDTH = 64;
    public static final int SPRITE_HEIGHT = 48;

    private void initialiseHitBox() {
        // center hitbox horizontally on sprite, align to bottom of sprite
        int hbX = (int)(x + (SPRITE_WIDTH - width) / 2f);
        int hbY = (int)(y + (SPRITE_HEIGHT - height));
        hitBox = new Rectangle(hbX, hbY, width, height);
    }

    public void updateHitBox() {
        hitBox.x = (int)(x + (SPRITE_WIDTH - width) / 2f);
        hitBox.y = (int)(y + (SPRITE_HEIGHT - height));
    }

    // gets hitboxes to allow for intersects between player and button.
    public Rectangle getHitbox(){
        return hitBox;
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