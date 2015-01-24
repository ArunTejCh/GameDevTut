package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by durga.p on 1/25/15.
 */
public class Door extends TexActor {

    public String nextLevel;

    public Door(int x, int y) {
        super(ActorType.DOOR, x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
    }
}
