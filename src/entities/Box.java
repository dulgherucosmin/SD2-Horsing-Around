// Horsing Around
// Group 9

package entities;

import static utilz.Utils.canMoveRaw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Box extends Entity {

    private float airSpeed = 0f;
    private final float gravity = 0.15f;
    private boolean inAir = true;

    // how fast the box moves when it's pushed
    private final float pushSpeed = 0.7f;
    private float spawnX;
    private float spawnY;

    private int[][] currentLevelData;
    private int currentLevel;

    private BufferedImage sprite;

    public Box(float x, float y, String spritePath) {
        super(x, y, 32, 32, true);

        // set box spawn position
        this.spawnX = x;
        this.spawnY = y;

        if (spritePath != null) {
            loadSprite(spritePath);
        }
    }

    public Box(float x, float y) {
        super(x, y, 32, 32, true);

        // set box spawn positions
        this.spawnX = x;
        this.spawnY = y;
    }

    private void loadSprite(String path) {
        sprite = LoadSave.GetSpriteAtlas(path);
    }

    public void loadLevelData(int[][] levelData, int level) {
        this.currentLevelData = levelData;
        this.currentLevel = level;
    }

    public void update(Player p1, Player p2) {
        applyGravity(p1, p2);
        tryPush(p1);
        tryPush(p2);
        updateHitBoxRaw();

        // reset position of box in level 2 if it gets pushed too far
        if (this.getY() > 127
            && this.getX() < 320
            && this.currentLevel == 2
        ) {
            this.x = spawnX;
            this.y = spawnY;
        }

        // reset position of box 2 in level 3 if it gets pushed too far
        if (this.getY() == 64.5
            && this.getX() > 220
            && this.currentLevel == 3
        ) {
            this.x = spawnX;
            this.y = spawnY;
        }
    }

    // this makes the box fall down if there's nothing under it
    private void applyGravity(Player p1, Player p2) {

        if (currentLevelData == null) {
            return;
        }

        // respawn box if it hits void (240 is world limit)
        if (this.y > 240) {
            this.x = spawnX;
            this.y = spawnY;
        }

        if (inAir) {
            airSpeed += gravity;

            boolean tileBlocking = !canMoveRaw(hitBox.x, hitBox.y + airSpeed, width, height, currentLevelData, currentLevel);
            boolean playerBlocking = hitsPlayer((int)(hitBox.y + airSpeed), p1) || hitsPlayer((int)(hitBox.y + airSpeed), p2);

            if (!tileBlocking && !playerBlocking) {

                // if there's nothing below, it'll keep falling
                y += airSpeed;
                updateHitBoxRaw();
            } else {

                // if it hits something, it'll move down one pixel
                int steps = 0;
                while (canMoveRaw(hitBox.x, hitBox.y + 1, width, height, currentLevelData, currentLevel) 
                        && !hitsPlayer(hitBox.y + 1, p1)
                        && !hitsPlayer(hitBox.y + 1, p2)
                        && steps < 32) {
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

    // this checks if the box would hit the player at a given y position. this stops the box from falling through a horse
    private boolean hitsPlayer(int yPos, Player p) {
        if (p == null) {
            return false;
        }
        
        return new Rectangle(hitBox.x, yPos, width, height).intersects(p.getHitbox());
    }

    // moves the box left or right when a horse walks into it
    private void tryPush(Player player) {
        if (currentLevelData == null || player == null) {
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
            g.drawImage(sprite, (int) x, (int) y + 2, 32, 32, null);

        } else {
            g.setColor(Color.GREEN);
            g.fillRect((int) x, (int) y, 32, 32);
        }
         drawHitBox(g);
    }
}
