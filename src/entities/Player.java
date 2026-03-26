// Horsing Around
// Group 9

package entities;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE_LEFT;
import static utilz.Constants.PlayerConstants.IDLE_RIGHT;
import static utilz.Constants.PlayerConstants.WALK_LEFT;
import static utilz.Constants.PlayerConstants.WALK_RIGHT;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Player extends Entity {

    // this stores all the animation frames [action][frame]
    private BufferedImage[][] animations;

    private int aniTick, aniIndex;

    // controls how fast each frame changes (lower value = faster)
    private int aniSpeed = 15;

    // tracks the current animation of the horse
    private int playerAction = IDLE_RIGHT;

    // tracks the current direction of the horse
    private int playerDir = RIGHT;

    private boolean moving = false;
    private boolean left, up, right, down;

    // how many pixels the player (horse) moves per update
    private float playerSpeed = 2.0f;

    private String spritePath;
    
  

    public Player(float x, float y, String spritePath, int startDir) {
        super(x, y);
        this.spritePath = spritePath;
        this.playerDir = startDir;

        if (spritePath != null) {
            loadAnimations();
        }
    }

    // this updates player logic every frame
    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    // this draws the current animation frame at the player's position
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 128, 96, null);
    }

    // this controls the animation frame switching
    private void updateAnimationTick() {

        aniTick++;

        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            
            // loops the animated frames
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
            }
        }
    }

    // this determines which animations should be played
    private void setAnimation() {

        int startAni = playerAction;

        if (moving) {
            if (playerDir == LEFT) {
                playerAction = WALK_LEFT;
            } else {
                playerAction = WALK_RIGHT;
            }
        } else {
            if (playerDir == LEFT) {
                playerAction = IDLE_LEFT;
            } else {
                playerAction = IDLE_RIGHT;
            }
        }

        // resets the animation if the state changes
        if (startAni != playerAction) 
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    // this updates the player's position based on input
    private void updatePos() {

        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
            playerDir = LEFT;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
            playerDir = RIGHT;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (!up && down) {
            y += playerSpeed;
            moving = true;
        }
    }

    // this loads up the sprite sheet and splits them into individual frames (didn't realize how annoying sprite sheets were bruh)
    private void loadAnimations() {
            BufferedImage img = LoadSave.GetSpriteAtlas(spritePath);

            animations = new BufferedImage[24][8];

            for (int j = 0; j < animations.length; j++) 
                for (int i = 0; i < animations[j].length; i++) 
                    animations[j][i] = img.getSubimage(i * 64, j * 48, 64, 48);
    }

    // this resets all the movement inputs when game is out of focus
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    // getters and setters for the movement input
    
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    public boolean isInAir(){
        return false;
    }
    public void jump(){
        
    }
    public float getAirSpeed(){
        return -1;
    }


}
