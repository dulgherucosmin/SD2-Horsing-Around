package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Game;
import ui.MenuButton;

public class Menu extends State implements StateMethods {
    
    private MenuButton[] buttons = new MenuButton[3];

    public Menu(Game game) {
        super(game);
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (35 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (105 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (175 * Game.SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (MenuButton mb : buttons) {
            mb.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }

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
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetBoolean();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }

        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
