// Horsing Around
// Group 9

package entities;

import static utilz.Utils.canMoveRaw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import utilz.LoadSave;

public class Box extends Entity {

    private float airSpeed = 0f;
    private final float gravity = 0.15f;
    private boolean inAir = true;

    // how fast the box moves when it's pushed
    private final float pushSpeed = 0.7f;

    private int[][] currentLevelData;
    private int currentLevel;

    private BufferedImage sprite;

    public Box(float x, float y, String spritePath) {
        super(x, y, 32, 32, true);

        if (spritePath != null) {
            loadSprite(spritePath);
        }
    }

    public Box(float x, float y) {
        super(x, y, 32, 32, true);
    }

    private void loadSprite(String path) {
        sprite = LoadSave.GetSpriteAtlas(path);
    }

    public void loadLevelData(int[][] levelData, int level) {
        this.currentLevelData = levelData;
        this.currentLevel = level;
    }

    public void update(Player p1, Player p2) {
        applyGravity();
        tryPush(p1);
        tryPush(p2);
        updateHitBoxRaw();
    }

    // this makes the box fall down if there's nothing under it
    private void applyGravity() {

        if (currentLevelData == null) {
            return;
        }

        if (inAir) {
            airSpeed += gravity;
            
            if (canMoveRaw(hitBox.x, hitBox.y + airSpeed, width, height, currentLevelData, currentLevel)) {

                // if there's nothing below, it'll keep falling
                y += airSpeed;
                updateHitBoxRaw();
            } else {

                // if it hits something, it'll move down one pixel
                int steps = 0;
                while (canMoveRaw(hitBox.x, hitBox.y + 1, width, height, currentLevelData, currentLevel) && steps < 32) {
                    y += 1;
                    updateHitBoxRaw();
                    steps++;
                }
                airSpeed = 0;
                inAir = false;
            }
        } else {
            
            // this checks if the box walked off a ledge
            if (canMoveRaw(hitBox.x, hitBox.y + 1, width, height, currentLevelData, currentLevel)) {
                inAir = true;
            }
        }
    }

    // moves the box left or right when a horse walks into it
    private void tryPush(Player player) {
        if (currentLevelData == null) {
            return;
        }
        
        Rectangle playerHB = player.getHitbox();

        // the horse has to be at the same height as the box to push it
        boolean verticalOverlap = playerHB.y < hitBox.y + hitBox.height && playerHB.y + playerHB.height > hitBox.y;

        // the horse stops 1-2 px before actually touching the box, so we use a small buffer to still detect the push
        int tolerance = 2;

        boolean touchingLeft  = playerHB.x + playerHB.width >= hitBox.x - tolerance
                            && playerHB.x < hitBox.x;

        boolean touchingRight = playerHB.x <= hitBox.x + hitBox.width + tolerance
                            && playerHB.x + playerHB.width > hitBox.x + hitBox.width;

        if (!verticalOverlap) return;

        float push;
        
        if (touchingLeft) {
            push = pushSpeed;
        } else if (touchingRight) {
            push = -pushSpeed;
        } else {
            return;
        }

        if (canMoveRaw(hitBox.x + push, hitBox.y, width, height, currentLevelData, currentLevel)) {
            // if there's nothing in the way, move the box
            x += push;
            updateHitBoxRaw();
        } else {
            float step;

            // if there's something in the way (for eg, a wall) nudge 1px at a time until it touches
            if (push > 0) {
                step = 1f;
            } else {
                step = - 1f;
            }

            while (canMoveRaw(hitBox.x + step, hitBox.y, width, height, currentLevelData, currentLevel)) {
                x += step;
                updateHitBoxRaw();
            }
        }
    }

    // this is used to stop the horse from walking through the box
    public boolean isBlocking(Player player) {
        return hitBox.intersects(player.getHitbox());
    }

    public void render(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, (int) x, (int) y, 32, 32, null);

        } else {
            g.setColor(Color.GREEN);
            g.fillRect((int) x, (int) y, 32, 32);
        }
        // drawHitBox(g);
    }
}
