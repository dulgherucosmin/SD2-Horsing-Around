// Horsing Around
// Group 9
package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import main.Game;

// this is basically to load up all the sprite images (levels/horses/the apple)
public class LoadSave {

    // file names for the sprites
    public static final String PLAYER1_ATLAS = "horse1_sprites.png";
    public static final String PLAYER2_ATLAS = "horse2_sprites.png";

    // this loads up the sprite sheet from the res folder
    public static BufferedImage GetSpriteAtlas(String fileName) {

        BufferedImage img = null;
        // get sprite
        InputStream is = LoadSave.class.getResourceAsStream("/res/" + fileName);

        // file was never found
        if (is == null) {
            System.err.println("Could not find the asset given: " + fileName);
            return null;
        }

        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return img;
    }

    public static int[][] getLevelData(int level) {

        int[][] levelData;

        // return level array based on level
        if (level == 1) {
            levelData = new int[][] {
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, },
                { 0,0,0,0,0,0,0,0,11,0,0,0,8,0,0,0, },
                { 0,0,0,0,0,0,0,3,3,3,0,0,8,0,0,0, },
                { 1,1,2,0,3,3,0,0,0,0,0,0,8,0,0,0, },
                { 0,6,0,0,0,0,0,0,0,0,0,0,8,0,9,0, },
                { 0,8,0,0,0,0,0,0,0,0,0,0,8,0,0,0, },
                // grass layer
                { 16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16, }
            };
            return levelData;
        }

        levelData = new int[0][0];
        return levelData;
    }
}
