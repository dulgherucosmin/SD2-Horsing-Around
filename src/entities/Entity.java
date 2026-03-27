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

    protected void drawHitBox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    private void initialiseHitBox() {
        hitBox = new Rectangle((int) x, (int) y, width, height);
    }

    protected void updateHitBox() {
        hitBox.x = (int) x;
        hitBox.y = (int) y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
