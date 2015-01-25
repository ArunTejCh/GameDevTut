package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sudheer on 1/25/2015.
 */
public class FireBall extends Arrow {

    TextureRegion texture;
    public boolean isReflected = false;

    public FireBall(Direction dir) {
        super(0.2f);
        texture = new TextureRegion(new Texture(Gdx.files.internal("weapons/dark_ball.png")));
        this.shootDir = dir;
        width = 0.8f;
        height = 0.8f;


    }

    @Override
    public void shoot(Direction direction) {
        super.shoot(direction);
        this.drawTexture = this.texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(drawTexture != null)
            batch.draw(drawTexture, getX(), getY(), getWidth(), getHeight());
    }
}
