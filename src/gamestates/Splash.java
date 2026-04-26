// Horsing Around
// Group 9

package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Game;
import utilz.LoadSave;

public class Splash extends State implements StateMethods {

    private long startTime; // time when splash screen started
    private static final long SPLASH_DURATION = 5000; // duration splash screen displays (5 seconds)
    private float alpha = 0.0f; // transparency level for fade effects (0.0 = invisible, 1.0 = fully visible)
    private boolean fadingIn = true; 
    private boolean fading = false; 
    private Font pixelFont; 

    public Splash(Game game) {
        super(game);
        startTime = System.currentTimeMillis(); 
        pixelFont = LoadSave.loadFont("PressStart2P-Regular.ttf"); 
    }


    @Override
    public void update() {
        long elapsed = System.currentTimeMillis() - startTime;

        // fade in effect. this is done by gradually increasing the alpha value
        if (fadingIn) {
            alpha += 0.005f; 
            if (alpha >= 1.0f) {
                alpha = 1.0f; 
                fadingIn = false; 
            }
        } 
        // checks if splash duration has elapsed
        else if (elapsed >= SPLASH_DURATION) {
            fading = true; // begin fade-out
        }

        // fade out effect. this is done by gradually decrasing the alpha value
        if (fading) {
            alpha -= 0.01f; 
            if (alpha <= 0f) {
                alpha = 0.0f; 
                setGamestate(Gamestate.MENU); 
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g; 

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        // sets transparency for fade effect
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        // sets the font size
        g2d.setFont(pixelFont.deriveFont(12f)); 
        g2d.setColor(Color.WHITE);

        // text content
        String line1 = "GROUP 9";
        String line2 = "PRESENTS";
        FontMetrics fm = g2d.getFontMetrics();

        // this draws the centered text on screen
        g2d.drawString(line1, (Game.GAME_WIDTH - fm.stringWidth(line1)) / 2, Game.GAME_HEIGHT / 2 - 10);
        g2d.drawString(line2, (Game.GAME_WIDTH - fm.stringWidth(line2)) / 2, Game.GAME_HEIGHT / 2 + 14);

        // resets the transparency to default
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    // skips the splash screen if a key is pressed or in mouse press
    private void skip() {
        fadingIn = false; 
        fading = false; 
        alpha = 0.0f; 
        setGamestate(Gamestate.MENU); 
    }

    @Override 
    public void keyPressed(KeyEvent e) { 
        skip(); 
    }

    @Override 
    public void mousePressed(MouseEvent e) { 
        skip(); 
    }
    
    @Override 
    public void mouseClicked(MouseEvent e) {

    }

    @Override 
    public void mouseReleased(MouseEvent e) {

    }

    @Override 
    public void mouseMoved(MouseEvent e) {

    }

    @Override 
    public void keyReleased(KeyEvent e) {

    }
}