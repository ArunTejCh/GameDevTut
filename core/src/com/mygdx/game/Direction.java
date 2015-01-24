package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by durga.p on 1/24/15.
 */
public enum Direction {
    UP(new Vector2(0,1)),
    DOWN(new Vector2(0,-1)),
    LEFT(new Vector2(-1,0)),
    RIGHT(new Vector2(1,0));

    final Vector2 vector;

    Direction(Vector2 vector2) {
        this.vector = vector2;
    }

    public static Direction reverse(Direction dir) {
        Direction reverseDir = LEFT;
        switch(dir){
            case LEFT:
                reverseDir = RIGHT;
                break;
            case RIGHT:
                reverseDir = LEFT;
                break;
            case UP:
                reverseDir = DOWN;
                break;
            case DOWN:
                reverseDir = UP;
                break;
        }
        return reverseDir;
    }
}
