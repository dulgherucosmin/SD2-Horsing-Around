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
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
                    { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
                    { 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,  0,  0,  0,  0 },
                    { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63,  0,  0,  0,  0 },
                    { 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79,  0,  0,  0,  0 },
                    { 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95,  0,  0,  0,  0 },
                    { 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111,  0,  0,  0,  0 },
                    { 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127,  0,  0,  0,  0 },
                    { 128,  129,  130,  131,  132,  133,  134,  135,  136,  137,  138,  139,  140,  141,  142,  143,  0,  0,  0,  0 },
            };
            return levelData;
        }

        levelData = new int[0][0];
        return levelData;
    }
}
