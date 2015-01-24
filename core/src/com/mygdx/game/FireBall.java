package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sudheer on 1/25/2015.
 */
public class FireBall extends Arrow {

    TextureRegion texture;

    public FireBall(Direction dir) {
        texture = new TextureRegion(new Texture(Gdx.files.internal("weapons/dark_ball.png")));
        this.shootDir = dir;
    }

    @Override
    public void shoot(Direction direction) {
        super.shoot(direction);
        this.drawTexture = this.texture;
    }
}
