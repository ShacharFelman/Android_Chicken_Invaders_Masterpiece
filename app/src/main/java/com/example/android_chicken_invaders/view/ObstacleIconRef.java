package com.example.android_chicken_invaders.view;


import com.example.android_chicken_invaders.R;

public enum ObstacleIconRef {

        OBSTACLE(R.drawable.ic_egg),
        REWARD(R.drawable.gif_gift3),
        CRASH(R.drawable.gif_egg3),
        EARNED(R.drawable.gif_firwork1);

        private final int drawableRef;

        ObstacleIconRef(int drawableRef) {
            this.drawableRef = drawableRef;
        }

        public int getDrawableRef() {
            return drawableRef;
        }

        public static ObstacleIconRef fromIcon(ObstacleIconRef icon) {
            switch (icon) {
                case OBSTACLE:
                    return OBSTACLE;
                case REWARD:
                    return REWARD;
                case CRASH:
                    return CRASH;
                case EARNED:
                    return EARNED;
                default:
                    throw new IllegalArgumentException("Invalid icon reference value");
            }
        }
}

