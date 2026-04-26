// Horsing Around
// Group 9

/* 
package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Game;
import ui.MenuButton;

public class Menu extends State implements StateMethods {

    // array holding the three menu buttons
    private MenuButton[] buttons = new MenuButton[3];

    public Menu(Game game) {
        super(game);
        loadButtons();
    }

    // creates and positions the menu buttons 
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (35 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (105 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (175 * Game.SCALE), 2, Gamestate.QUIT);
    }

    // it changes the state of the buttons depending on the mouse input
    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }

    // this is what displays the menu buttons or draws the menu on the screen
    @Override
    public void draw(Graphics g) {
        for (MenuButton mb : buttons) {
            mb.draw(g);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // just sets the button as pressed
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true); 
                break; // this ensures that only one button can be pressed at a time
            }
        }
    }

    // this methods checks if a button is released after it was pressed it should go its state
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    setGamestate(mb.getState());
                    break;
                }
            }
        }
        // used the reset the buttons when released
        resetButtons();
    }

    // method used to take buttons back to original state
    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBoolean();
        }
    }

    // it clears the mouse over state when moves and changes it to which ever button is being hovered
    @Override
    public void mouseMoved(MouseEvent e) {
        // this clears
        for (MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }
        // this changes it to the button thats currently hovered
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

*/

// Horsing Around
// Group 9

package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Game;
import utilz.LoadSave;

public class Menu extends State implements StateMethods {

    private String[] labels = {"PLAY", "OPTIONS", "QUIT"};
    private Gamestate[] states = {Gamestate.PLAYING, Gamestate.OPTIONS, Gamestate.QUIT};
    private Rectangle[] bounds = new Rectangle[3];
    private boolean[] hovered = new boolean[3];
    private Font pixelFont;

    private int leftMargin = 20;
    private int titleY = 80;
    private int startY = 120;
    private int gap = 20;

    public Menu(Game game) {
        super(game);
        pixelFont = LoadSave.loadFont("PressStart2P-Regular.ttf");
        for (int i = 0; i < 3; i++)
            bounds[i] = new Rectangle(0, 0, 0, 0);
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        // title
        g.setFont(pixelFont.deriveFont(20f));
        g.setColor(Color.BLACK);
        g.drawString("HORSING AROUND", leftMargin, titleY);

        // buttons
        for (int i = 0; i < 3; i++) {
            g.setFont(pixelFont.deriveFont(8f));
            FontMetrics fm = g.getFontMetrics();

            int textW = fm.stringWidth(labels[i]);
            int textH = fm.getAscent();
            int textX = leftMargin;
            int textY = startY + i * gap;

            bounds[i] = new Rectangle(textX, textY - textH, textW, textH + 4);

            if (hovered[i]) {
                g.setColor(new Color(255, 215, 0));
            } else {
                g.setColor(Color.BLACK);
            }

            g.drawString(labels[i], textX, textY);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < 3; i++) {
            if (getScaledBounds(i).contains(e.getX(), e.getY())) {
                setGamestate(states[i]);
                return;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (int i = 0; i < 3; i++)
            hovered[i] = getScaledBounds(i).contains(e.getX(), e.getY());
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private Rectangle getScaledBounds(int i) {
        float scaleX = (float) game.getGamePanel().getWidth() / Game.GAME_WIDTH;
        float scaleY = (float) game.getGamePanel().getHeight() / Game.GAME_HEIGHT;
        Rectangle b = bounds[i];
        return new Rectangle(
            (int)(b.x * scaleX), (int)(b.y * scaleY),
            (int)(b.width * scaleX), (int)(b.height * scaleY)
        );
    }
}