package gamestates;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Button;
import entities.Door;
import entities.Player;
import entities.Win;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

public class Playing extends State implements StateMethods {
    // player objects
    private Player player1;
    private Player player2;
    private LevelManager levelManager;

    private Button button1;
    private Button button2;
    private Door door;
    private Win win;
    private boolean levelComplete = false;

    public final static int TILE_DEFAULT_SIZE = 16; // base tile size before resizing
    public final static float SCALE = 1.0f; // scaling factor
    public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE); // the final tile size after scaling

    // the number of tiles visible on screen
    public final static int TILES_IN_WIDTH = 32;
    public final static int TILES_IN_HEIGHT = 18;

    // the final game resolution
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
       // initialize level manager
       levelManager = new LevelManager(game);
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

    @Override
    public void update() {
        player1.update();
        player2.update();

        // levelManager.update();
        button1.update(player1, player2);
        button2.update(player1, player2);
        door.update();

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
    }

    @Override
    public void draw(Graphics g) {
        // render level 1
        levelManager.loadLevel(g, 1);
        player1.render(g);
        player2.render(g);
        button1.render(g);
        button2.render(g);
        door.render(g);
        win.render(g, levelComplete);

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

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

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
            case KeyEvent.VK_ESCAPE:
                Gamestate.state = Gamestate.MENU;
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // stops the movement when the key is released
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                // gamePanel.getGame().getPlayer1().setAutoJump(false);
                break;
            case KeyEvent.VK_A:
                player1.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player1.setRight(false);
                break;

            case KeyEvent.VK_UP:
                // gamePanel.getGame().getPlayer2().setAutoJump(false);
                break;

            case KeyEvent.VK_LEFT:
                player2.setLeft(false);
                break;

            case KeyEvent.VK_RIGHT:
                player2.setRight(false);
                break;
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
