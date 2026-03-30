// Horsing Around
// Group 9

package gamestates;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

public class State {
    protected Game game;

    public State(Game game){
        this.game = game;

    }
    public Game getGame(){
        return game;
    }

    //helps fix scaling of the buttons hover by converting mouse coordinates into out game dimensions and checks if the mouse hovers over the button
    public boolean isIn(MouseEvent e, MenuButton mb){
        
        float scaleX =(float) game.getGamePanel().getWidth()/Game.GAME_WIDTH;
        float scaleY =(float) game.getGamePanel().getHeight()/Game.GAME_HEIGHT;
        
        int mx = (int)(e.getX()/scaleX);
        int my = (int)(e.getY()/scaleY);

        return mb.getBounds().contains(mx,my);
    }
}
