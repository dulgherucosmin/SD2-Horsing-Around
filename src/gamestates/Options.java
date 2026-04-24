package gamestates;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Game;
import ui.OptionsOverlay;

public class Options extends State implements StateMethods {
     private OptionsOverlay optionsOverlay;

     public Options(Game game){
        super(game);
        optionsOverlay = new OptionsOverlay(game);
     }
    
     @Override
     public void update(){
        optionsOverlay.update();
     }

     @Override
     public void draw(Graphics g){
        optionsOverlay.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        Gamestate.state =Gamestate.MENU;
        
    }

    @Override
    public void keyReleased(KeyEvent e) {

        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        optionsOverlay.mouseMoved(e);
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
     optionsOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        optionsOverlay.mouseReleased(e);
        
    }
    
}   

