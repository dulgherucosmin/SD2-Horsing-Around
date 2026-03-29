// Horting Around
// Group 9

package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

// this is where the game is drawn. it handles all the rendering and input listeners
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }    

    // this sets the panel size based on the game dimensions
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);

        System.out.println("size : " + GAME_WIDTH + "x" + GAME_HEIGHT);
    }

    // pretty sure this was needed for the actual game stuff but i'm lazy to check 
    public void updateGame() {

    }

    // paints all the game elements
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // this uses the nearest neighbor so that the pixel art stays sharp when scaled up instead of blurring
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // this calculates how much to scale based on the current window size vs the base resolution
        float scaleX = (float) getWidth() / GAME_WIDTH;
        float scaleY = (float) getHeight() / GAME_HEIGHT;

        g2d.scale(scaleX, scaleY);

        game.render(g2d);
    }

    public Game getGame() {
        return game;
    }
}
