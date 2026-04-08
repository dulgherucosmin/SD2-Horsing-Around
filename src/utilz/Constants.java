// Horsing Around
// Group 9

package utilz;

import main.Game;

public class Constants {

    public static class UI {

        public static class Buttons{
            public static final int B_WIDTH_DEFAULT =140;
            public static final int B_HEIGHT_DEFAULT =56;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT*Game.SCALE);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT*Game.SCALE);
        }
        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT=42;
            public static final int SOUND_SIZE=(int)(SOUND_SIZE_DEFAULT*Game.SCALE);

        }
        public static class UrmButtons{
            public static final int URM_DEFAULT_SIZE =56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE* Game.SCALE);
        }
        
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {

        // idle animation
        public static final int IDLE_LEFT = 0;
        public static final int IDLE_RIGHT = 1;

        // eat animation (prolly won't use it yet)
        public static final int EAT_LEFT = 4;
        public static final int EAT_RIGHT = 5;

        // walk animation (i thought we'd use that instead but ngl, run looks better)
        public static final int WALK_LEFT = 8;
        public static final int WALK_RIGHT = 9;

        // run animation
        public static final int RUN_LEFT = 12;
        public static final int RUN_RIGHT = 13;

        // this returns the number of frames for each of the animation 
        public static int GetSpriteAmount(int player_action) {

            switch(player_action) {

                case IDLE_LEFT:
                case IDLE_RIGHT:
                    return 3;

                case WALK_LEFT:
                case WALK_RIGHT:
                    return 8;

                case RUN_LEFT:
                case RUN_RIGHT:
                    return 6;

                default:
                    return 1;
            }
        }
    }
}
