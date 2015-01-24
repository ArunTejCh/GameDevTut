package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Data;

/**
 * Created by Durga on 21-01-2015.
 */
@Data
public class MyActor extends Actor {

    private final MyGdxGame myGdxGame;
    private float ppy;
    private float ppx;

    private Texture texture = new Texture("badlogic.jpg");
    private TextureAtlas.AtlasRegion region;

    public MyActor() {
        myGdxGame = (MyGdxGame) Gdx.app.getApplicationListener();

        setX(1);
        setY(1);
        setWidth(1);
        setHeight(1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        region = myGdxGame.atlas.findRegion("chimney");
        batch.draw(region,getX(),getY(),getWidth(),getHeight());
    }


}
