// Horsing Around
// Group 9

package gamestates;

import static utilz.Constants.Directions.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Box;
import entities.Button;
import entities.Door;
import entities.Player;
import entities.Win;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements StateMethods {

    // player objects
    private Player player1;
    private Player player2;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;

    private Button button1;
    private Button button2;
    private Door door;
    private Win win;
    private Box box;

    private boolean levelComplete = false;

    private int currentLevelNum = 1;

    public final static int TILE_DEFAULT_SIZE = 16; // base tile size before resizing
    public final static float SCALE = 1.0f; // scaling factor
    public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE); // the final tile size after scaling

    // the number of tiles visible on screen
    public final static int TILES_IN_WIDTH = 32;
    public final static int TILES_IN_HEIGHT = 18;

    // the final game resolution
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    
    private boolean paused =false;
    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {

        // initialize level manager
        levelManager = new LevelManager(game, currentLevelNum);
       //initializing pauseOverlay class
        pauseOverlay = new PauseOverlay(game,this);

        float[] p1Spawn = getSpawnPoint(1, currentLevelNum);
        float[] p2Spawn = getSpawnPoint(2, currentLevelNum);
      
        player1 = new Player(1, p1Spawn[0], p1Spawn[1], LoadSave.PLAYER1_ATLAS, RIGHT, "Patrick");

        // load level data (in this case level 1)
        player1.loadLevelData(levelManager.getCurrentLevel().getLevelData());

        // set players internal storage of level to the current loaded level (in this case level 1)
        player1.setCurentLevel(levelManager.getCurrentLevel().level);

        player2 = new Player(2, p2Spawn[0], p2Spawn[1], LoadSave.PLAYER2_ATLAS, RIGHT, "Nathan");

        player2.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player2.setCurentLevel(levelManager.getCurrentLevel().level);

        // set hitboxes for collision detection
        player1.setOtherPlayerHitBox(player2.getHitbox());
        player2.setOtherPlayerHitBox(player1.getHitbox());

        button1 = new Button(20 * TILES_SIZE, 8 * TILES_SIZE);
        button2 = new Button(26 * TILES_SIZE, 14 * TILES_SIZE);

        door = new Door(24 * TILES_SIZE, 11 * TILES_SIZE, button1, button2);
        win = new Win(455, 190);

        box = new Box(16 * TILES_SIZE, 2 * TILES_SIZE, "box.png");
        box.loadLevelData(levelManager.getCurrentLevel().getLevelData(), levelManager.getCurrentLevel().level);

        player1.setBoxHitBox(box.getHitbox());
        player2.setBoxHitBox(box.getHitbox());
    }

    private float[] getSpawnPoint(int player, int level) {
        switch (level) {
            case 1: // level 1 spawns
                if (player == 1) {
                    return new float[]{5, 1};
                } else {
                    return new float[]{40, 1};
                }
            case 2: // level 2 spawns
                if (player == 1) {
                    return new float[]{2 * 16, 3 * 16};
                } else {
                    return new float[]{4 * 16, 3 * 16};
                }
            default:
                return new float[]{0, 0};
        }
    }

    @Override
    public void update() {
        //if game is not paused then update all features
        if(!paused) {
            player1.update();
            player2.update();

            button1.update(player1, player2);
            button2.update(player1, player2);
            door.update();

            if (box != null) {
                box.update(player1, player2);
            }

            // door collision checks.
            if (door.isBlocking(player1)) {
                player1.undoMove();
            }

            if (door.isBlocking(player2)) {
                player2.undoMove();
            }

            if (win.completed(player1, player2)) {
                levelComplete = true;
            }

        //if paused display pause overlay
        } else {
            pauseOverlay.update();
        }
       
    }

    @Override
    public void draw(Graphics g) {
        // render level 1
        levelManager.drawLevel(g, currentLevelNum);
        player1.render(g);
        player2.render(g);
        button1.render(g);
        button2.render(g);
        door.render(g);

        if (box != null) {
            box.render(g);
        }

        win.render(g, levelComplete);

        //if paused then draw pause overlay
        if(paused){
        pauseOverlay.draw(g);
        }

        if (levelComplete) {
            g.setColor(new java.awt.Color(0, 0, 0, 150));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

            g.setColor(java.awt.Color.WHITE);
            g.drawString("LEVEL COMPLETE!", GAME_WIDTH / 2 - 50, GAME_HEIGHT / 2);
        }
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    //when the game is paused all mouse inputs direct to pause overlay
    @Override
    public void mousePressed(MouseEvent e) {
        if(paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(paused)
            pauseOverlay.mouseMoved(e);
    }


    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            // horse 1 controls (wad)
            case KeyEvent.VK_W:
                player1.jump();
                break;
            case KeyEvent.VK_A:
                player1.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player1.setRight(true);
                break;

            // horse 2 controls (up, down, left, right)
            case KeyEvent.VK_UP:
                player2.jump();
                break;
            case KeyEvent.VK_LEFT:
                player2.setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player2.setRight(true);
                break;
                //when escape is pressed changes the vaue of paused 
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // stops the movement when the key is released
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player1.setJumpHeld(false);
                break;
            case KeyEvent.VK_A:
                player1.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player1.setRight(false);
                break;

            case KeyEvent.VK_UP:
                player2.setJumpHeld(false);
                break;

            case KeyEvent.VK_LEFT:
                player2.setLeft(false);
                break;

            case KeyEvent.VK_RIGHT:
                player2.setRight(false);
                break;
        }
    }

    public void mouseDragged (MouseEvent e){
        if(paused)
            pauseOverlay.mouseDragged(e);
    }

    //sets paused to unpause when the play buttons is pressed
    public void unpauseGame(){
        paused = false;
    }
  

    // resets the player movements when the game window is out of focus
    public void windowFocusLost() {
        player1.resetDirBooleans();
        player2.resetDirBooleans();

    }
    

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    // getters for each player (hort)
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
