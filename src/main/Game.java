package main;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;

import java.awt.Graphics;

import levels.LevelManager;
import entities.Player;
import entities.Button;
import entities.Door;
import utilz.LoadSave;

// handles the game loop, updating, rendering and the game setup
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private Thread gameThread;

    // target frames per second and updates per second
    private final int FPS_SET = 60; // controls how often the screen is redrawn
    private final int UPS_SET = 100; // controls how often the game logic runs

    // player objects
    private Player player1;
    private Player player2;
    private LevelManager levelManager;
    private Button button1;
    private Button button2;
    private Door door;

    public final static int TILE_DEFAULT_SIZE = 32; // base tile size before resizing
    public final static float SCALE = 1.0f; // scaling factor

    // the number of tiles visible on screen
    public final static int TILES_IN_WIDTH = 16;
    public final static int TILES_IN_HEIGHT = 9;

    public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE); // the final tile size after scaling

    // the final game resolution
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    public Game() {

        // creates the players
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);

        // this ensures the keyboard input works
        gamePanel.requestFocus();

        // starts a separate game thread
        startGameLoop();
    }


    private void initClasses() {
        // initialize level manager
        levelManager = new LevelManager(this);
        player1 = new Player(200, 1,LoadSave.PLAYER1_ATLAS, RIGHT);
        // load level data (in this case level 1)
        player1.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        // set players internal storage of level to the current loaded level (in this case level 1)
        player1.setCurentLevel(levelManager.getCurrentLevel().level);

        player2 = new Player(275, 1, LoadSave.PLAYER2_ATLAS, LEFT);
        player2.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player2.setCurentLevel(levelManager.getCurrentLevel().level);

        button1 = new Button(208,30);
        button2 = new Button(400,160);
        door = new Door(348, 160, button1, button2);

    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player1.update();
        player2.update();
        //levelManager.update();
        button1.update(player1, player2);
        button2.update(player1, player2);
        door.update();
        //door collision checks.
        if(door.isBlocking(player1)){
            player1.undoMove();
        }
        
        if(door.isBlocking(player2)){
            player2.undoMove();
        }
        
    }

    public void render(Graphics g) {
        // render level 1
        levelManager.loadLevel(g, 1);
        player1.render(g);
        player2.render(g);
        button1.render(g);
        button2.render(g);
        door.render(g);
    }

 
    @Override
    public void run() {

        // calculates how long each frame should take
        double timePerFrame = 1000000000.0 / FPS_SET;
        
        // calculates how long each update should take
        double timePerUpdate = 1000000000.0 / UPS_SET;

        // this stores the time of the previous loop iteration
        long previousTime = System.nanoTime();
        
        // counts how many frames and updates happen per second
        int frames = 0;
        int updates = 0;

        long lastCheck = System.currentTimeMillis();

        // these store how much "update time" and "frame time" has passed. note: these are accumulated values
        double deltaU = 0; 
        double deltaF = 0;

        // the main game loop
        while(true) {

            // gets the current time at the start of this loop iteration
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate; // how many updates should have happened
            deltaF += (currentTime - previousTime) / timePerFrame;  // how many frames should have been rendered
            
            // updates the previous time for the next loop iteration
            previousTime = currentTime;

            // if enough time has passed for at least one update
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            // if enough time has passed for at least one frame
            if (deltaF >= 1) {
                gamePanel.repaint();

                frames++;
                deltaF--;

            }

            // fps/ups counter
            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    // resets the player movements when the game window is out of focus
    public void windowFocusLost() {
        player1.resetDirBooleans();
        player2.resetDirBooleans();

    }
    
    // getters for each player (hort)
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    
}
