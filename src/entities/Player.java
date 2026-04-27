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
import static utilz.Utils.collidesWithHitBox;
import static utilz.Utils.isDeadly;

import java.awt.*;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {

    private String displayName;
    private int playerType;

    // this stores all the animation frames [action][frame]
    private BufferedImage[][] animations;

    private int aniTick, aniIndex;

    // controls how fast each frame changes (lower value = faster)
    private int aniSpeed = 10;

    // tracks the current animation of the horse
    private int playerAction = IDLE_RIGHT;

    // tracks the current direction of the horse
    private int playerDir = RIGHT;

    private boolean moving = false;
    private boolean left, right;

    // how many pixels the player (horse) moves per update
    private float playerSpeed = 1.5f;

    private String spritePath;
    
    // Jumping and gravity
    private float airSpeed = 0f;
    private float gravity = 0.15f;
    private float jumpSpeed = -3.8f;
    private boolean inAir = false;

    private boolean jumpHeld = false;
    private float jumpCutSpeed = -1.5f;

    // Door collision
    private float originX;
    private float originY;

    private int[][] currentLevelData;
    private int currentLevel;


    private Rectangle otherPlayerHitBox;
    private Rectangle boxHitBox;
    private Rectangle box2HitBox;
    private boolean movementLocked;
    private Game game;

    private String currentColour = "default";

    public Player(int playerType, float x, float y, String spritePath, int startDir, String displayName, Game game) {
        // width and height here are hitbox sizes
        super(x, y, 16, 16);
        this.game = game;
        this.spritePath = spritePath;
        this.playerDir = startDir;
        this.displayName = displayName;

        this.playerType = playerType;

        if (spritePath != null) {
            loadAnimations();
        }
    }

    // this updates player logic every frame
    public void update() {
        originX = x;
        originY = y;
        updatePos();
        // update hitbox upon player moving
        updateHitBox();
        updateAnimationTick();
        setAnimation();

        checkDeadlyTiles();
    }

    private void checkDeadlyTiles() {
        float hbX = hitBox.x;
        float hbY = hitBox.y;

        if (isDeadly(hbX, hbY, currentLevelData, currentLevel)
                || isDeadly(hbX + width - 1, hbY, currentLevelData, currentLevel)
                || isDeadly(hbX, hbY + height - 1, currentLevelData, currentLevel)
                || isDeadly(hbX + width - 1, hbY + height - 1, currentLevelData, currentLevel)) {
            resetPosition();
            inAir = true;
            airSpeed = 0;
        }
    }

    public void undoMove(){
        x = originX;
        y = originY;
        updateHitBox();
    }

    // this draws the current animation frame at the player's position
    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 64, 48, null);
        
        Color displayNameColour = new Color(0, 0, 0, 0);

        if (playerType == 1) {
            // colour matches player 1
            displayNameColour = new Color(35, 200, 145, 85);
        } else {
            // colour matches player 2
            displayNameColour = new Color(35, 200, 145, 85);
        }

        // draw a semi-transparent dark background pill behind the name
        g.setColor(displayNameColour);
        // width 32 (half of player sprite), height 16 (half of player sprite)
        g.fillRoundRect((int)x + 20, (int)y + 12, 24, 8, 5, 5);

        // set a small bold font for the name
        g.setFont(new Font("Comic Sans", Font.BOLD, 6));

        // measure how wide the text will be in pixels
        FontMetrics fm = g.getFontMetrics();
        int displayNameWidth = fm.stringWidth(displayName);

        /*
        x+20 -> start at the left edge
        + ((24 - displayNameWidth) / 2)) -> center text in the middle of display name
        y + 18 -> align y value so that it sits in the middle of the display bubble
         */
        int textX = (int)x + 20 + (24 - displayNameWidth) / 2;
        int textY = (int)y + 18;
        

        g.setColor(Color.WHITE);
        g.drawString(displayName, textX, textY);
        
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

        if (y > 240) {
            System.out.println("Void reached, teleporting to spawn.");
            float[] sp = getSpawnPoint(playerType, currentLevel);
            // teleport player
            this.x = sp[0];
            this.y = sp[1];
        }

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

            // run a check to see if player is blocked by a tile or collides with another player
            boolean tileCollision = !canMove(x, y + airSpeed, width, height, currentLevelData, currentLevel);
            boolean playerCollision = otherPlayerHitBox != null && collidesWithHitBox(x, y + airSpeed, width, height, otherPlayerHitBox);
            boolean boxCollisionVertical = (boxHitBox != null && collidesWithHitBox(x, y + airSpeed, width, height, boxHitBox))
            || (box2HitBox != null && collidesWithHitBox(x,y + airSpeed, width, height, box2HitBox));

            if (!jumpHeld && airSpeed < jumpCutSpeed) {
                airSpeed = jumpCutSpeed;
            }

            // check if player can move to the next vertical position
            if (!tileCollision && !playerCollision && !boxCollisionVertical) {
                // nothing blocking vertically, continue moving up or falling down
                y += airSpeed;

            } else {
                if (airSpeed > 0) {
                    // player fell and hit a solid tile, fall down 1px until they touch the ground/ a tile
                    // canMove will be false once they touch the ground/a tile
                    // also run a check to see if player is blocked by a tile or collides with another player
                    while (canMove(x, y + 1, width, height, currentLevelData, currentLevel) 
                            && !collidesWithHitBox(x, y + 1, width, height, otherPlayerHitBox)
                            && !(boxHitBox != null && collidesWithHitBox(x, y + 1, width, height, boxHitBox))
                            && !(box2HitBox != null && collidesWithHitBox(x, y + 1, width, height, box2HitBox))
                        ) {
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
                // this checks if the player is standing on another player or box
                boolean standingOnOtherPlayer = otherPlayerHitBox != null && collidesWithHitBox(x, y + 1, width, height, otherPlayerHitBox);
                boolean standingOnBox =
                    (boxHitBox != null && collidesWithHitBox(x, y + 1, width, height, boxHitBox))
                    || (box2HitBox != null && collidesWithHitBox(x, y + 1, width, height, box2HitBox));

                if (!standingOnOtherPlayer && !standingOnBox) {
                    inAir = true;
                }
            }
        }

        // horizontal movement
        if (xSpeed != 0) {
            // run a check to see if player is blocked by a tile or collides with another player
            boolean tileCollision = !canMove(x + xSpeed, y, width, height, currentLevelData, currentLevel);
            boolean playerCollision = otherPlayerHitBox != null && collidesWithHitBox(x + xSpeed, y, width, height, otherPlayerHitBox);

            boolean boxCollisionHorizontal =
                (boxHitBox != null && collidesWithHitBox(x + xSpeed, y, width, height, boxHitBox))
                || (box2HitBox != null && collidesWithHitBox(x + xSpeed, y, width, height, box2HitBox));

            if (!tileCollision && !playerCollision && !boxCollisionHorizontal) {
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

    // helper method to set other players hitbox
    public void setOtherPlayerHitBox(Rectangle otherPlayerHitBox) {
        this.otherPlayerHitBox = otherPlayerHitBox;
    }

    // helper method to quickly reset player position
    public void resetPosition() {

        float[] sp = getSpawnPoint(playerType, this.currentLevel);
        this.x = sp[0];
        this.y = sp[1];
    }

    public static float[] getSpawnPoint(int playerType, int levelNum) {
        switch (levelNum) {
            case 1: // level 1 spawns
                if (playerType == 1) {
                    return new float[]{5, 1};
                } else {
                    return new float[]{40, 1};
                }
            case 2: // level 2 spawns
                if (playerType == 1) {
                    return new float[]{2 * 16, 3 * 16};
                } else {
                    return new float[]{4 * 16, 3 * 16};
                }
            case 3: // level 3 spawns
                if (playerType == 1) {
                    return new float[]{2 * 16, 7 * 16};
                } else {
                    return new float[]{4 * 16, 7 * 16};
                }
            default:
                return new float[]{0, 0};
        }
    }

    public void lockMovement() {
        movementLocked = true;
        left = false;
        right = false;
        jumpHeld = false;
        inAir = false;
        airSpeed = 0;
    }

    public void unlockMovement() {
        movementLocked = false;
    }

    public void setBoxHitBox(Rectangle boxHitBox) {
        this.boxHitBox = boxHitBox;
    }

    public void setBox2HitBox(Rectangle box2HitBox) {
        this.box2HitBox = box2HitBox;
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
            jumpHeld = true;
            game.getAudioPlayer().playEffect(AudioPlayer.JUMP);
        }
    }

    public void setJumpHeld(boolean held) {
        this.jumpHeld = held;
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

    public void setX(float x) {
        this.x = x;
        updateHitBox(); 
    }
    public void setY(float y) {
        this.y = y;
        updateHitBox(); 
    }
    //changes the colour by reloading the spritesheet
    public void setColour(String newSpritePath){
        this.spritePath =newSpritePath;
        loadAnimations(); //reload animations with new sprie path
    }

    public String getSpritePath(){
        return spritePath;
    }
}