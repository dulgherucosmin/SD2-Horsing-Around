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
import static utilz.Utils.canMove;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
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
    private boolean left, right;

    // how many pixels the player (horse) moves per update
    private float playerSpeed = 2.5f;

    private String spritePath;
    
    //Jumping and gravity
    private float airSpeed =0f;
    private float gravity =0.2f;
    //private float groundLevel = 400;
    private float jumpSpeed =-5f;
    private boolean inAir = false;
    private boolean autoJump = false;

    private int[][] currentLevelData;
    private int currentLevel;

    public Player(float x, float y, String spritePath, int startDir) {
        // width and height here are hitbox sizes
        super(x, y, 40, 40);
        this.spritePath = spritePath;
        this.playerDir = startDir;

        if (spritePath != null) {
            loadAnimations();
        }
    }

    // this updates player logic every frame
    public void update() {
        updatePos();
        // update hitbox upon player moving
        updateHitBox();
        updateAnimationTick();
        setAnimation();
    }

    // this draws the current animation frame at the player's position
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 128, 96, null);
        drawHitBox(g);
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

        // store horizontal speed
        float xSpeed = 0;

        if (left && !right) {
            // left is negative x direction
            xSpeed = -playerSpeed;
            playerDir = LEFT;
        } else if (right && !left) {
            // right is positive x direction
            xSpeed = playerSpeed;
            playerDir = RIGHT;
        }

        if (inAir) {
            // airSpeed starts negative (jumping up) and increases until positive (falling down)
            airSpeed += gravity;

            // check if player can move to the next vertical position
            if (canMove(x, y + airSpeed, width, height, currentLevelData, currentLevel)) {
                // nothing blocking vertically, continue moving up or falling down
                y += airSpeed;
            } else {
                if (airSpeed > 0) {
                    // player fell and hit a solid tile, fall down 1px until they touch the ground/ a tile
                    // canMove will be false once they touch the ground/a tile
                    while (canMove(x, y + 1, width, height, currentLevelData, currentLevel)) {
                        y += 1;
                    }
                }
                // stop all vertical movement whether we hit the floor or a ceiling
                inAir = false;
                airSpeed = 0;
            }
            // player is on the ground (inAir = false)
        } else {
            // check if theres a solid tile beneath
            if (canMove(x, y + 1, width, height, currentLevelData, currentLevel)) {
                // no solid tile, player has fallen off
                inAir = true;
            }
        }

        // only attempt horizontal movement if a key is being held
        if (xSpeed != 0) {
            // check if there is not a wall blocking player (prevents walking into walls)
            if (canMove(x + xSpeed, y, width, height, currentLevelData, currentLevel)) {
                x += xSpeed;
                moving = true;
            }
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

    // helper class to get current level int 2d array
    public void loadLevelData(int[][] currentLevelData) {
        this.currentLevelData = currentLevelData;
    }

    // helper class to set current level in the player directly
    public void setCurentLevel(int level) {
        this.currentLevel = level;
    }

    // this resets all the movement inputs when game is out of focus
    public void resetDirBooleans() {
        left = false;
        right = false;

    }
    // makes the  players jump
    public void jump(){
        //checks if palyer is in air
        if(!inAir){
            //then sets the vertical speed to jump speed
            airSpeed = jumpSpeed;
            inAir=true;
        }
    }
  
    // getters and setters for the movement input
    
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public boolean isInAir(){
        return inAir;
    }

    public float getAirSpeed(){
        return airSpeed;
    }

    public void setAutoJump(boolean autoJump){
        this.autoJump = autoJump;
    }

    /*
    public void setGroundLevel(float ground){
        this.groundLevel=ground;
    }
    */
   

}
