package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        // get sprite for the level graphics
        //levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);

        importOutSideSprites();

        levelOne = new Level(LoadSave.getLevelData());

    }

    private void importOutSideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        
        System.out.println("Image width: " + img.getWidth());   
        System.out.println("Image height: " + img.getHeight());

        int tileSize = 32;
        int spriteSheetWidth = img.getWidth() / tileSize;   // auto-calculate
        int spriteSheetHeight = img.getHeight() / tileSize; // auto-calculate

        levelSprite = new BufferedImage[spriteSheetWidth * spriteSheetHeight];
        for (int j = 0; j < spriteSheetHeight; j++) {
            for (int i = 0; i < spriteSheetWidth; i++) {
                int index = j * spriteSheetWidth + i;
                levelSprite[index] = img.getSubimage(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }
    }

    public void draw(Graphics g) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = levelOne.getSpriteIndex(i, j);

                if (index == 5) {
                    continue;
                }
                
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i , Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

}
