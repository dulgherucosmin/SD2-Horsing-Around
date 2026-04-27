// Horsing Around
// Group 9

package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;
import utilz.Utils;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage bgImage;
    
    // default to loading on level 1
    private Level currentLevel = new Level(LoadSave.getLevelData(1), 1);

    public LevelManager(Game game, int level) {
        this.game = game;
        initLevel(level);
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

    public void initLevel(int level) {

        if (bgImage == null) {
            bgImage = LoadSave.GetSpriteAtlas("bg.png");
        }

        importLevelSpriteSheet(level);
        currentLevel = new Level(LoadSave.getLevelData(level), level);
    }

    private void drawBackground(Graphics g) {
        if (bgImage == null) return;

        int imgW = bgImage.getWidth();
        int imgH = bgImage.getHeight();
        int gameW = Game.GAME_WIDTH;
        int gameH = Game.GAME_HEIGHT;

        float scale = (float) gameH / imgH;
        int scaledW = (int)(imgW * scale);
        int scaledH = gameH;

        if (scaledW < gameW) {
            scale = (float) gameW / imgW;
            scaledW = gameW;
            scaledH = (int)(imgH * scale);
        }

        int xOffset = (gameW - scaledW) / 2;
        int yOffset = (gameH - scaledH) / 2;

        g.drawImage(bgImage, xOffset, yOffset, scaledW, scaledH, null);
    }

    public void drawLevel(Graphics g, int level) {
        drawBackground(g);

        // load sprites
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                // get the sprite index according to position
                int index = currentLevel.getSpriteIndex(i, j); // levelData[j][i]

                if (index == 40 && Utils.areSpikesDisabled()) {
                    continue;
                }

                // draw sprite
                if (index == 40) {
                    g.drawImage(levelSprite[index], Game.TILES_SIZE * i , Game.TILES_SIZE * j + 4, Game.TILES_SIZE, Game.TILES_SIZE, null);
                } else {
                    g.drawImage(levelSprite[index], Game.TILES_SIZE * i , Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
                }
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