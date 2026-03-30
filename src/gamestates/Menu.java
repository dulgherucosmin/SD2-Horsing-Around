package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Game;
import ui.MenuButton;

public class Menu extends State implements StateMethods {

    //array holding the three menu buttons
    private MenuButton[] buttons = new MenuButton[3];

    public Menu(Game game) {
        super(game);
        loadButtons();
    }

    //creates and positions the menu buttons 
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (35 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (105 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (175 * Game.SCALE), 2, Gamestate.QUIT);
    }

    //it changes the state of the buttons depending on the mouse input
    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }

    //this is what displays the menu buttons or draws the menu on the screen
    @Override
    public void draw(Graphics g) {
        for (MenuButton mb : buttons) {
            mb.draw(g);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    //just sets the button as pressed
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true); 
                break;//this ensures that only one button can be pressed at a time
            }
        }
    }

    //this methods checks if a button is released after it was pressed it should go its state
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.setGameState();
                    break;
                }
            }
        }
        //used the reset the buttons when released
        resetButtons();
    }

    //method used to take buttons back to original state
    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBoolean();
        }
    }

    //it clears the mouse over state when moves and changes it to which ever button is being hovered
    @Override
    public void mouseMoved(MouseEvent e) {
        //this clears
        for (MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }
        //this changes it to the button thats currently hovered
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
