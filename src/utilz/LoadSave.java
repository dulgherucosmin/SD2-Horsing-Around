// Horsing Around
// Group 9
package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import main.Game;

// this is basically to load up all the sprite images (levels/horses/the apple)
public class LoadSave {

    // file names for the sprites
    public static final String PLAYER1_ATLAS = "horse1_sprites.png";
    public static final String PLAYER2_ATLAS = "horse2_sprites.png";

    public static final String GRASS_MIDDLE_ATLAS = "grass_middle.png";

    public static final String LEVEL_ATLAS = "level_tileset.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";


    // this loads up the sprite sheet from the res folder
    public static BufferedImage GetSpriteAtlas(String fileName) {

        BufferedImage img = null;
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

    public static int[][] getLevelData() {

        //14x26        
        int[][] levelOneData = {
            { 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5},
            { 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5},
            { 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5},
            { 5,  5,  2,  2,  2,  2,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  2,  2,  2,  2,  5,  5},
            { 5,  5,  6, 13, 13,  9,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  6, 13, 13,  9,  5,  5},
            { 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5},
            { 5,  5, 10,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 10,  5,  5,  5},
            { 5,  5,  2,  2,  2,  2,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  2,  2,  2,  2,  2,  5,  5},
            { 5,  5,  6, 13, 13,  9,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  6, 13, 13, 13,  9,  5,  5},
            { 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5},
            { 0,  2,  2,  2,  1,  2,  2,  2,  2,  2,  2,  2, 17,  5,  5, 17,  2,  2,  2,  2,  2,  2,  2,  2,  2,  1},
            { 6, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  9,  5,  5,  6, 13, 13, 13, 13, 13, 13, 13, 13, 13,  9},
            { 6, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  9,  5,  5,  6, 13, 13, 13, 13, 13, 13, 13, 13, 13,  9},
            { 6, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  9},
        };

        return levelOneData;
    }
}
