package tests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entities.Player;
public class MovementTest {
    //testing if the player is in the air
    @Test
    public void testJumpInAir(){
        //creating a new player
        Player p =new Player(0,300,null,1);
        //calling jump method
        p.jump();
        //checking if its true
        assertTrue(p.isInAir());
    }
    //testing gravity
    @Test
    public void testGravity(){
        //set groung level;
        float groundLevel =100;
        Player p= new Player(0, groundLevel, null, 1);
        //use players current ground level
        p.setGroundLevel(groundLevel);
        //calling jump method and updating its position
        p.jump();

        //store splayers inittial position
        float beforeJump = p.getY();
        boolean hasFallen = false;

        //loops through frames to update loop
        for(int i =0; i<200; i++){
            p.update();
            //get new players position
            float afterJump =p.getY();
            //check if the player has started falling
            if(afterJump>beforeJump){
                hasFallen =true;
                break;

        }
        //update jump 
        beforeJump =afterJump;
    }

        assertTrue(hasFallen);
    }
    //this checks if the player actually still in the air after jumping
    @Test
    public void testLanding(){
        float groundLevel =300;
        Player p= new Player(0, groundLevel, null, 1);
        p.setGroundLevel(groundLevel);
        p.jump();
        //checks multiple frames
        for(int i=0; i<200; i++){
            p.update();
        }
        assertFalse(p.isInAir());
    }


}



