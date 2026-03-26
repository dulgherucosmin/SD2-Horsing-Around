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
        Player p= new Player(0, 300, null, 1);
        //calling jump method and updating its position
        p.jump();
        p.update();
        //storing the position
        float afterJump =p.getY();

        //updating the position again when the player in back on the ground
        p.update();
        //storing it
        float afterGravity = p.getY();
        //checking if gravity actually pulled the player down
        assertTrue(afterGravity >afterJump);
    }
    //this checks if the player actually still in the air after jumping
    @Test
    public void testLanding(){
        Player p= new Player(0, 0, null, 1);
        p.jump();
        //checks multiple frames
        for(int i=0; i<100; i++){
            p.update();
        }
        assertFalse(p.isInAir());
    }



}



