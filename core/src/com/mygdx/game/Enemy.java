package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Sudheer on 1/24/2015.
 */
public class Enemy extends Hero{

    public boolean isXDir;
    boolean removeSelf = false;

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

    @Override
    public void act(float delta) {
        super.act(delta);
        if(removeSelf){
            remove();
            Gdx.app.log(this.getClass().getSimpleName(),"dead");
        }
    }

//    @Override
//    public boolean remove() {
//        if(getMyStage() != null)
//            getMyStage().gameEngine.enemyDying.play(0.4f);
//        return super.remove();
//
//    }

//    @Override
//    public void removeSelf() {
//        super.removeSelf();
//
//    }
}
