// Horsing Around
// Group 9

package main;

import java.awt.Graphics;
import java.lang.classfile.ClassFile.Option;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.*;

// handles the game loop, updating, rendering and the game setup
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private Thread gameThread;
    private Playing playing;
    private Menu menu;
    private Options options;

    public AudioPlayer audioPlayer;

    // target frames per second and updates per second
    private  int FPS_SET = 120; // controls how often the screen is redrawn
    private final int UPS_SET = 100; // controls how often the game logic runs

    public final static int TILE_DEFAULT_SIZE = 16; // base tile size before resizing
    public final static float SCALE = 1.0f; // scaling factor
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
        audioPlayer = new AudioPlayer();

        playing = new Playing(this);
        menu = new Menu(this);
        options = new Options(this);
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
            // if its in playing state
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                options.update();
                break;
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
            
            case OPTIONS:
                    options.draw(g);
                    break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        // this stores the time of the previous loop iteration
        long previousTime = System.nanoTime();

        // counts how many frames and updates happen per second
        int frames = 0;
        int updates = 0;

        long lastCheck = System.currentTimeMillis();

        // these store how much "update time" and "frame time" has passed. note: these
        // are accumulated values
        double deltaU = 0;
        double deltaF = 0;

        // the main game loop
        while (true) {


            // calculates how long each frame should take
            double timePerFrame = 1000000000.0 / FPS_SET;

            // calculates how long each update should take
            double timePerUpdate = 1000000000.0 / UPS_SET;
            // gets the current time at the start of this loop iteration
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate; // how many updates should have happened
            deltaF += (currentTime - previousTime) / timePerFrame; // how many frames should have been rendered

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
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    // resets the player movements when the game window is out of focus
    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer1().resetDirBooleans();
            playing.getPlayer2().resetDirBooleans();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Options getOptions(){
        return options;
    }
    public void setFPS(int fps) {
        FPS_SET = fps;
    }
    
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    public int getFPS(){
        return FPS_SET;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}