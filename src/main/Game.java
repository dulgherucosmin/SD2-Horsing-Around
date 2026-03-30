package main;
import java.awt.Graphics;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;


// handles the game loop, updating, rendering and the game setup
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private Thread gameThread;
    private Playing playing;
    private Menu menu;

    // target frames per second and updates per second
    private final int FPS_SET = 60; // controls how often the screen is redrawn
    private final int UPS_SET = 100; // controls how often the game logic runs

    private boolean levelComplete = false;

    public final static int TILE_DEFAULT_SIZE = 16; // base tile size before resizing
    public final static float SCALE = 1.f; // scaling factor
    public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE); // the final tile size after scaling

    // the number of tiles visible on screen
    public final static int TILES_IN_WIDTH = 32;
    public final static int TILES_IN_HEIGHT = 18;

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

        button1 = new Button(20 * TILES_SIZE, 8 * TILES_SIZE);
        button2 = new Button(26 * TILES_SIZE, 14 * TILES_SIZE);

        door = new Door(24 * TILES_SIZE, 11 * TILES_SIZE, button1, button2);
        win = new Win (455,190);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
       // a check to see if the game is in a particular state
        switch (Gamestate.state) {
            case MENU:
            menu.update();
                break;
            //if its in playing state
            case PLAYING:
            playing.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
        
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU:
            menu.draw(g);
                break;
            case PLAYING:
             playing.draw(g);
                break;
        
            default:
                break;
        }
       
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
     if(Gamestate.state ==Gamestate.PLAYING){
        playing.getPlayer1().resetDirBooleans();
        playing.getPlayer2().resetDirBooleans();
     }
    }
    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }
    public GamePanel getGamePanel(){
        return gamePanel;
    }

    
}