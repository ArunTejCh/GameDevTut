package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Sudheer on 1/24/2015.
 */
public class Enemy extends Hero{

    public boolean isXDir;

    public Enemy(String fileName, boolean isXDir){
        super(fileName);
        this.isXDir = isXDir;
        if (isXDir) {
            currentFrame = wLeftAni.getKeyFrame(0);
            currDirection = Direction.LEFT;
            currShieldDirection = Direction.LEFT;
        }
        else {
            currentFrame = wDownAni.getKeyFrame(0);
            currDirection = Direction.DOWN;
            currShieldDirection = Direction.DOWN;
        }
    }

}
