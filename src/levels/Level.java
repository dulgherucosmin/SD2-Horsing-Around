// Horsing Around
// Group 9

package levels;

public class Level {

    private int[][] levelData;
    public static int level;

    public Level(int[][] levelData, int level) {
        this.levelData = levelData;
        this.level = level;
    }

    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }

    public int[][] getLevelData() {
        return this.levelData;
    }
}
