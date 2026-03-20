package game;

import javax.swing.JFrame;

import constants.Constants;

//Creates Frame and runs main game loop
public class Init {

	public static void main(String[] args) {

		//Create a Frame and add our panel 
		JFrame frame = new JFrame();
		MyPanel panel = new MyPanel();
		frame.add(panel);
		
		//Set frame size to half screen size
		frame.setSize(Constants.SCREEN_SIZE.width/2,Constants.SCREEN_SIZE.height/2);
		frame.setVisible(true);
		
		//Frame takes focus for keyboard and mouse inputs
		frame.requestFocus();
		panel.requestFocus(); 
		
		//close program with dialog
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		long startTime = System.currentTimeMillis();

		while(true) // keep updating until dialog is closed
		{
			//set our panel to update every 100 milliseconds
			// this sets our frameRate, higher value means a slower refresh, lower value means faster.
			long elapsedTime = System.currentTimeMillis() - startTime;
			if (elapsedTime > Constants.REFRESH_RATE)  
			{ 
				panel.update();
				startTime = System.currentTimeMillis();
			}
		}



	}

}
