// Horsing Around
// Group 9
package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

// this is basically to load up all the sprite images (levels/horses/the apple)
public class LoadSave {

    // file names for the sprites
    public static final String PLAYER1_ATLAS = "horse1_sprites.png";
    public static final String PLAYER2_ATLAS = "horse2_sprites.png";

    // this loads up the sprite sheet from the res folder
    public static BufferedImage GetSpriteAtlas(String fileName) {

        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/res/" + fileName);

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
}
