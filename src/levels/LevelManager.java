package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import main.Game;
import entities.*;
import utilz.LoadSave;

public class LevelManager {

    private Game game;
    private Button button1;
    private Button button2;
    private Door door;
    private Win win;

    private int button1X, button1Y;
    private int button2X, button2Y;

    private int doorTileX, doorTileY;
    private int coinTileX, coinTileY;

    private int[][] currentLevelData;

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
        int spriteSheetWidth = img.getWidth() / tileSize; // auto-calculate
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
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE,
                        Game.TILES_SIZE, null);
            }
        }
    }

    public void loadObjects(int[][] data) {
        currentLevelData = data;

        int tileSize = Game.TILES_SIZE;
        int buttonCount = 0;

        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {

                int tile = data[y][x];

                //button tiles
                if (tile == 17) {
                    if (buttonCount == 0) {
                        button1 = new Button(295, 95);
                        button1X = x;
                        button1Y = y;
                    } else if (buttonCount == 1) {

                        button2 = new Button(393, 190);
                        button2X = x;
                        button2Y = y;

                    }

                    buttonCount++;
                }

                //door tiles
                if(tile == 14){
                    doorTileX = x; 
                    doorTileY = y;
                }

                //coin tile
                if(tile == 19){
                    win = new Win(455,190);
                    coinTileX = x;
                    coinTileY = y;
                }
            }
        }
        door = new Door(doorTileX, doorTileY, button1, button2);
    }

    public void update(Player player1, Player player2) {
        
    //update buttons
    if (button1 != null) button1.update(player1, player2);
    if (button2 != null) button2.update(player1, player2);

    // update door
    if (door != null) door.update();

    //swaps button tiles
    if (button1 != null) {
        currentLevelData[button1Y][button1X] = button1.isPressed() ? 18 : 17;
    }

    if (button2 != null) {
        currentLevelData[button2Y][button2X] = button2.isPressed() ? 18 : 17;
    }

    //removes the door tiles when its open
    if (door != null && door.isOpen()) {
        currentLevelData[doorTileY][doorTileX] = 0;
        currentLevelData[doorTileY + 1][doorTileX] = 0;
        currentLevelData[doorTileY + 2][doorTileX] = 0;
    }

    //pick up coin
    if (win != null && win.completed(player1, player2)) {
        currentLevelData[coinTileY][coinTileX] = 0;
    }
}

    public void renderObjects(Graphics g, boolean levelComplete){
         if (button1 != null) button1.render(g);
    if (button2 != null) button2.render(g);

    if (door != null && !door.isOpen()) {
        door.render(g);
    }

    if (win != null) {
        win.render(g, levelComplete);
    }
    }

    // later on this will use an array with multiple levels
    public Level getCurrentLevel() {
        return currentLevel;
    }

}
