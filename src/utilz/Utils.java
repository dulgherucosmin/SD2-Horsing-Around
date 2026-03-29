package utilz;

import entities.Entity;
import main.Game;

import java.util.*;

public class Utils {

    // stores which tile IDs are solid for each level
    // key = level number, value = set of solid tile IDs
    // using a HashSet for O(1) lookup speed instead of scanning a list
    private static Map<Integer, Set<Integer>> solidTilesByLevel = new HashMap<>();

    static {
        // define solid tile IDs for each level here
        // these ints correspond to positions in the spritesheet
        // anything not in this list is either air (0) or a background tile (passable)
        solidTilesByLevel.put(1, new HashSet<>(Arrays.asList(
            2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
        )));    
    }

    // checks whether the player can move to a given position
    public static boolean canMove(float x, float y, int width, int height, int[][] levelData, int currentLevel) {

        // convert sprite position to hitbox position
        // the hitbox is centered horizontally and sits at the bottom of the sprite
        float hbX = x + (Entity.SPRITE_WIDTH - width) / 2f;
        float hbY = y + (Entity.SPRITE_HEIGHT - height);

        // check all 4 corners of the hitbox
        // the -1 on width and height keeps corners just inside the boundary
        return !isSolid(hbX, hbY, levelData, currentLevel)                                       // top left
                && !isSolid(hbX + width - 1, hbY, levelData, currentLevel)                   // top right
                && !isSolid(hbX, hbY + height - 1, levelData, currentLevel)                  // bottom left
                && !isSolid(hbX + width - 1, hbY + height - 1, levelData, currentLevel); // bottom right
    }

    // checks whether a single point in the world is inside a solid tile
    private static boolean isSolid(float x, float y, int[][] levelData, int currentLevel) {

        // treat anything outside the game window as solid
        // this prevents the player from walking or falling off screen
        if (x < 0 || x >= Game.GAME_WIDTH) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        // convert pixel position to tile array index
        int xIndex = (int) (x / Game.TILES_SIZE);
        int yIndex = (int) (y / Game.TILES_SIZE);

        // extra bounds check in case position is at the very edge of the map
        if (yIndex >= levelData.length || xIndex >= levelData[0].length) {
            return true;
        }

        // get the tile at this position from the level data array
        int tile = levelData[yIndex][xIndex];

        // tile 0 is always air, no need to check further
        if (tile == 0) {
            return false;
        }

        // look up the solid tiles for the current level
        Set<Integer> solidTiles = solidTilesByLevel.get(currentLevel);
        // if the level has no entry in the map, treat everything as passable
        if (solidTiles == null) {
            return false;
        }

        // check if this tile is in the solid set for this level
        return solidTiles.contains(tile);
    }
}