// Horsing Around
// Group 9

package gamestates;

import static utilz.Constants.Directions.*;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import entities.Box;
import entities.Button;
import entities.Door;
import entities.Player;
import entities.Win;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utilz.LoadSave;
import levels.Level;

import static entities.Player.getSpawnPoint;

public class Playing extends State implements StateMethods {

    // player objects
    private Player player1;
    private Player player2;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;

    private Button button1;
    private Button button2;
    private Button button3;
    private Door door1;
    private Door door2;
    private Win win;
    private Box box;
    private Box box2;

    private boolean levelComplete = false;
    private long levelStartTime = 0;
    private long levelCompleteTime = 0;
    private long level1Time = 0;
    private long level2Time = 0;
    private long level3Time = 0;

    private long finalTime = 0;


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

    private static final int FIRST_LEVEL = 1;
    private static final int SECOND_LEVEL = 2;
    private static final int THIRD_LEVEL = 3;
    
    private boolean paused = false;

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
      
        player1 = new Player(1, p1Spawn[0], p1Spawn[1], LoadSave.PLAYER1_ATLAS, RIGHT, "Patrick", game);

        // load level data (in this case level 1)
        player1.loadLevelData(levelManager.getCurrentLevel().getLevelData());

        // set players internal storage of level to the current loaded level (in this case level 1)
        player1.setCurentLevel(levelManager.getCurrentLevel().level);

        player2 = new Player(2, p2Spawn[0], p2Spawn[1], LoadSave.PLAYER2_ATLAS, RIGHT, " Nathan ", game);

        player2.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player2.setCurentLevel(levelManager.getCurrentLevel().level);

        // set hitboxes for collision detection
        player1.setOtherPlayerHitBox(player2.getHitbox());
        player2.setOtherPlayerHitBox(player1.getHitbox());
        
        setupLevelObjects(); //setup buttons, doors and win condition.
        syncPlayersToCurrentLevel(); //load level data for players
        setupBoxForCurrentLevel();//setup box (box only exists in level 2)

        levelStartTime = System.currentTimeMillis();
    }

    private String formatTime(long milliseconds){
        long totalSeconds = milliseconds / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long millis = (milliseconds % 1000) / 10;

        return String.format("%02d:%02d.%02d", minutes, seconds, millis);
    }

    private void setupLevelObjects(){
        if (currentLevelNum == 1) {
            button1 = new Button(20 * TILES_SIZE, 8 * TILES_SIZE);
            button2 = new Button(26 * TILES_SIZE, 14 * TILES_SIZE);
            button3 = new Button(-1000, -1000);

            door1 = new Door(24 * TILES_SIZE, 11 * TILES_SIZE, 4, button1, button2);
            door2 = new Door(-1000, -1000, 3,  button3);

            win = new Win(455, 190);

        } else if (currentLevelNum == 2) {
            button1 = new Button(16 * TILES_SIZE, 4 * TILES_SIZE);
            button2 = new Button(28 * TILES_SIZE, 12 * TILES_SIZE);
            button3 = new Button(14 * TILES_SIZE, 15 * TILES_SIZE);

            door1 = new Door(11 * TILES_SIZE, 13 * TILES_SIZE, 3, button3);
            door2 = new Door(25 * TILES_SIZE, 4 * TILES_SIZE, 3, button1, button2);

            win = new Win(455, 160);
        } else if (currentLevelNum == 3) {
            button1 = new Button(8 * TILES_SIZE, 12 * TILES_SIZE);
            button2 = new Button(4 * TILES_SIZE, 5 * TILES_SIZE);
            button3 = new Button(25 * TILES_SIZE, 12 * TILES_SIZE);

            door1 = new Door(28 * TILES_SIZE, 9 * TILES_SIZE, 4, button2);
            door2 = new Door(-1000, -1000, 3, button2);
            win = new Win(455,160);

        }
    }

    private void syncPlayersToCurrentLevel(){
        int [][] levelData = levelManager.getCurrentLevel().getLevelData();
        int currentLevelNum = levelManager.getCurrentLevel().getLevelNumber();

        player1.loadLevelData(levelData);
        player1.setCurentLevel(currentLevelNum);

        player2.loadLevelData(levelData);
        player2.setCurentLevel(currentLevelNum);
    }

    private void completeLevel(){
        levelComplete = true;
        paused = false;

        player1.lockMovement();
        player2.lockMovement();

        if (levelManager.getCurrentLevel().getLevelNumber() != SECOND_LEVEL){
            levelManager.initLevel(SECOND_LEVEL);
            syncPlayersToCurrentLevel();
        }
    }

    private void loadNextLevel(){ //loads next level 
        currentLevelNum++;

        levelManager.initLevel(currentLevelNum);
        setupLevelObjects();

        float[] p1Spawn = getSpawnPoint(1, currentLevelNum);
        float[] p2Spawn = getSpawnPoint(2, currentLevelNum);

        //resets players to spawn.
        player1.setX(p1Spawn[0]);
        player1.setY(p1Spawn[1]);
        player1.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player1.setCurentLevel(levelManager.getCurrentLevel().getLevelNumber());

        player1.unlockMovement();

        player2.setX(p2Spawn[0]);
        player2.setY(p2Spawn[1]);
        player2.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player2.setCurentLevel(levelManager.getCurrentLevel().getLevelNumber());
        player2.unlockMovement();

        //reconnect player collision
        player1.setOtherPlayerHitBox(player2.getHitbox());
        player2.setOtherPlayerHitBox(player1.getHitbox());

        //setup box for new level
        setupBoxForCurrentLevel();
    
        levelComplete = false;

        //restart timer for next level.
        levelStartTime = System.currentTimeMillis();
    }

    private void setupBoxForCurrentLevel() { // creates or removes box depending on level
    if (currentLevelNum == 2) {
            box = new Box(18 * TILES_SIZE, 2 * TILES_SIZE, "box.png");
            box.loadLevelData(levelManager.getCurrentLevel().getLevelData(), levelManager.getCurrentLevel().level);

            box2 = null;

            player1.setBoxHitBox(box.getHitbox());
            player2.setBoxHitBox(box.getHitbox());

            player1.setBox2HitBox(null);
            player2.setBox2HitBox(null);
        } else if(currentLevelNum == 3) {
            box = new Box(8 * TILES_SIZE,  10 * TILES_SIZE, "box.png");
            box.loadLevelData(levelManager.getCurrentLevel().getLevelData(), levelManager.getCurrentLevel().level);

            box2 = new Box(8 * TILES_SIZE, 2 * TILES_SIZE, "box.png");
            box2.loadLevelData(levelManager.getCurrentLevel().getLevelData(), levelManager.getCurrentLevel().level);
            
            player1.setBoxHitBox(box.getHitbox());
            player2.setBoxHitBox(box.getHitbox());

            player1.setBox2HitBox(box2.getHitbox());
            player2.setBox2HitBox(box2.getHitbox());
        }else {
            box = null;
            box2 = null;

            player1.setBoxHitBox(null);
            player2.setBoxHitBox(null);

            player1.setBox2HitBox(null);
            player2.setBox2HitBox(null);
        }
    }

    @Override
    public void update() {
        //if game is not paused then update all features
        if(!paused) {
            if(!levelComplete){ // only update movement if level not complete
                player1.update();
                player2.update();
                
            }

            //update buttons, pressed by players or box.
            if (button1 != null) button1.update(player1, player2, box, box2);
            if (button2 != null) button2.update(player1, player2, box, box2);
            if (button3 != null) button3.update(player1, player2, box, box2);

            boolean disableSpikes = (button1 != null && button1.isPressed()) ||
            (button3 != null && button3.isPressed());
            utilz.Utils.setSpikesDisabled(disableSpikes);

            //update doors
            if (door1 != null) door1.update();
            if (door2 != null) door2.update();

            //update box movement
            if (box != null) {
                box.update(player1, player2);
            }

            if (box2 != null) {
            box2.update(player1, player2);
            }

            // door collision checks
            if (door1.isBlocking(player1) || door2.isBlocking(player1)) {
                player1.undoMove();
            }

            if (door1.isBlocking(player2) || door2.isBlocking(player2)) {
                player2.undoMove();
            }

            //check win condition
            if (!levelComplete && win != null && win.completed(player1, player2)) {
                levelComplete = true;
                levelCompleteTime = System.currentTimeMillis();

                long levelTime = levelCompleteTime - levelStartTime;

                if(currentLevelNum == 1){
                    level1Time = levelTime;
                } else if(currentLevelNum == 2){
                    level2Time = levelTime;
                } else if(currentLevelNum == 3){
                    level3Time = levelTime;
                    finalTime = level1Time + level2Time + level3Time;
                }
                
                player1.lockMovement();
                player2.lockMovement();
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
        
        if (button1 != null) button1.render(g);
        if (button2 != null) button2.render(g);
        if (button3 != null) button3.render(g);

        if (door1 != null) door1.render(g);
        if (door2 != null) door2.render(g);

        if (box != null) {
            box.render(g);
        }
        if (box2 != null) {
        box2.render(g);
        }

        if (win != null) {
            win.render(g, levelComplete);
        }

        long displayTime;
        if(levelComplete){
            displayTime = levelCompleteTime - levelStartTime;
        } else{
            displayTime = System.currentTimeMillis() - levelStartTime;
        }
        g.setColor(java.awt.Color.WHITE);
        g.drawString("Level Time: " + formatTime(displayTime), 20, 20);

        //if paused then draw pause overlay
        if(paused){
        pauseOverlay.draw(g);
        }

        if (levelComplete) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

            g.setColor(new java.awt.Color(0, 0, 0, 220));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);


            Font pixelFont = LoadSave.loadFont("PressStart2P-Regular.ttf").deriveFont(10f);
            g.setFont(pixelFont);
            g.setColor(java.awt.Color.WHITE);

            String text = "LEVEL COMPLETE!";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(text, GAME_WIDTH / 2 - fm.stringWidth(text) / 2, GAME_HEIGHT / 2);

            Font smallFont = LoadSave.loadFont("PressStart2P-Regular.ttf").deriveFont(6f);
            g.setFont(smallFont);

            FontMetrics fmSmall = g.getFontMetrics();

            String levelTimeText = "";

            if(currentLevelNum == 1){
                levelTimeText = "Level 1: " + formatTime(level1Time);
            } else if(currentLevelNum == 2){
                levelTimeText = "Level 2: " + formatTime(level2Time);
            } else if (currentLevelNum == 3){
                levelTimeText = "Level 3: " + formatTime(level3Time);
            }

            g.drawString(levelTimeText, GAME_WIDTH / 2 - fmSmall.stringWidth(levelTimeText) /2,
            GAME_HEIGHT /2 + 40);

            if (currentLevelNum == 3){
                String finalTimeText = "Final time: " + formatTime(finalTime);

                g.drawString(finalTimeText, GAME_WIDTH / 2 - fmSmall.stringWidth(finalTimeText) / 2,
                 GAME_HEIGHT / 2 + 60);
            }

            String text1 = "Press ENTER to continue";
            g.drawString(text1, GAME_WIDTH / 2 - fmSmall.stringWidth(text1) / 2, GAME_HEIGHT / 2 + 20);
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
        
        if (levelComplete) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) { //key to change level
                if (currentLevelNum < 3) {
                    loadNextLevel();
                } else {
                    Gamestate.state = Gamestate.MENU;
                }
            }
            return;
        }

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
    public void resetGame(){
        //go back to level one
        currentLevelNum =1;
        levelManager.initLevel(currentLevelNum);

        //reset players to level 1 spawn  point
        float[] p1Spawn = getSpawnPoint(1, currentLevelNum);
        float[] p2Spawn = getSpawnPoint(2, currentLevelNum);

        //reload level data for both players
        syncPlayersToCurrentLevel();

        //resets players to spawn.
        player1.setX(p1Spawn[0]);
        player1.setY(p1Spawn[1]);
        player2.setX(p2Spawn[0]);
        player2.setY(p2Spawn[1]);
        
        //reset Level Objects
        setupLevelObjects();

        //remove box since level one has no box
        setupBoxForCurrentLevel();

        //reconnect player collision
        player1.setOtherPlayerHitBox(player2.getHitbox());
        player2.setOtherPlayerHitBox(player1.getHitbox());
    
        levelComplete = false;
        levelCompleteTime = 0;
        paused =false;
        levelStartTime = System.currentTimeMillis();

        level1Time = 0;
        level2Time = 0;
        level3Time = 0;
        finalTime = 0;
    }
    public int getCurrentLevelNum(){
        return currentLevelNum;
    }
}
