// Horsing Around
// Group 9

package tests;

import org.junit.Test;

import static entities.Player.getSpawnPoint;
import static org.junit.Assert.*;

import entities.Player;
import entities.Button;
import main.Game;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Utils.areSpikesDisabled;
import static utilz.Utils.setSpikesDisabled;

public class SpikeTest {

    private void letPlayerLand(Player p) {
        // keep updating player until they fall on the ground prior to testing
        for (int i = 0; i < 200; i++) {
            p.update();
        }
    }

    private int[][] createSpikeLevel() {
        return new int[][] {
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,40,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2 },
                { 4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4 },
                { 4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4 },
                { 4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4 }
            };
    }
    

    @Test
    public void testSpike() {

        Player p = new Player(1, 5, 1, null, RIGHT, "Test", null);
        // load our test level into player code
        p.loadLevelData(createSpikeLevel());
        // set current level to ensure hitboxes work
        p.setCurentLevel(1);

        // let player land before testing begins
        letPlayerLand(p);

        // move right
        p.setRight(true);
        p.update();

        // make player move until they reach void
        for (int i = 0; i < 35; i++) {
            p.update();
            System.out.println(p.getX());
        }

        // player spawned then moved 1 time (so spawn pos + 1 movement tick)
        float expectedPosition = Player.getSpawnPoint(1, 1)[0] + p.getPlayerSpeed();

        // check to see if player has been reset
        assertEquals(expectedPosition, p.getX(), 0);

    }

    @Test
    public void testDisableSpikes() {

        Player p = new Player(1, 0, 0, null, RIGHT, "Test", null);
        Player p2 = new Player(2, 100, 100, null, RIGHT, "Test", null);
        Button button = new Button(0, 0);

        p.updateHitBoxRaw();

        button.update(p, p2, null, null); 

        // disable spikes if button is pressed
        if (button != null && button.isPressed()) { 
            setSpikesDisabled(true); 
        }

        assertEquals(true, areSpikesDisabled());
    }
}
