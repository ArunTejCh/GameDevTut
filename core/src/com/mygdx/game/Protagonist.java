package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by durga.p on 1/24/15.
 */
public class Protagonist extends Hero {

    private Direction prevDir;

    public Protagonist(String fileName) {
        super(fileName);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        pollKeys();
//        if(processDirection && prevDir != null){
//            move(prevDir);
//        }

    }

    private void pollKeys() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            prevDir = Direction.UP;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            prevDir = Direction.DOWN;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            prevDir = Direction.RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            prevDir = Direction.LEFT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            if(processDirection)
                useOffensiveWeapon();
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            if(processDirection)
                useDefensiveWeapon();
        }
        if(processDirection){
            move(prevDir);
        }
    }
}
