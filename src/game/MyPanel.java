package game;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

//import gameConstants.Constants;


//Draws to Panel and listens for key inputs
@SuppressWarnings("serial")
public class MyPanel extends JPanel implements  KeyListener {

	private GameManager game;

	public MyPanel() {
		addKeyListener(this); // set up keyboard input event listener
		 game = new GameManager();
	}

	//Method to draw to panel
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);


		Graphics2D graphics = (Graphics2D)g;
		//improves image rendering
		RenderingHints hints = new RenderingHints( RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHints(hints);
		
		drawBackGround(graphics);
		
		//draw game sprites
		game.drawSprites(graphics,this);
		
		this.repaint();
	}
	
	public void drawBackGround(	Graphics2D graphics) {
		//blue background, size of screen	
		
        /*graphics.setColor(Constants.SKY_BLUE);
		graphics.fillRect(0,0,Constants.SCREEN_SIZE.width,Constants.SCREEN_SIZE.height);
		
		//green rectangle for ground  
		graphics.setColor(Constants.GRASS_GREEN);
		graphics.fillRect(0,Constants.GROUND_HEIGHT ,Constants.SCREEN_SIZE.width,Constants.SCREEN_SIZE.height);
        */
    }
	
	public void update()
	{
		game.update();
		this.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e.getKeyCode());
		this.repaint();
	}

	//not implemented
	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e.getKeyCode());
		this.repaint();
	}

}
