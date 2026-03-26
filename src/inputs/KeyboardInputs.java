// Horsing Around
// Group 9

package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyboardInputs implements KeyListener {
    
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {

            // horse 1 controls (wasd)
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer1().jump();
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer1().setLeft(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer1().setRight(true);
                break;

            // horse 2 controls (up, down, left, right)
            case KeyEvent.VK_UP:
                gamePanel.getGame().getPlayer2().jump();
                break;
            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getPlayer2().setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getPlayer2().setRight(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // stops the movement when the key is released
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer1().setLeft(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer1().setRight(false);
                break;

            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getPlayer2().setLeft(false);
                break;

            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getPlayer2().setRight(false);
                break;
        }
    }   
}
