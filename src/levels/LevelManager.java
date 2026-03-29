package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import main.Game;
import utilz.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    // default to loading on level 1
    private Level currentLevel = new Level(LoadSave.getLevelData(1), 1);

    public LevelManager(Game game) {
        this.game = game;
    }

    private void importLevelSpriteSheet(int level) {

        BufferedImage img = LoadSave.GetSpriteAtlas("level_one_tilesheet2.png");

        if (img == null) {
            throw new RuntimeException("Failed to load level_one_tileset.png — check it exists in /res/");
        }

        int tileSize = 16;
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

    public void loadLevel(Graphics g, int level) {

        // load level spritesheet
        importLevelSpriteSheet(level);
        // load level data into a level object
        currentLevel = new Level(LoadSave.getLevelData(level), level);


        // load sprites
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                // get the sprite index according to position
                int index = currentLevel.getSpriteIndex(i, j); // levelData[j][i]

                // draw sprite
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i , Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    // later on this will use an array with multiple levels
    public Level getCurrentLevel() {
        return currentLevel;
    }

}
