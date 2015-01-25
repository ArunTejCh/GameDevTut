package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by durga.p on 1/24/15.
 */
public class TexActor extends Actor{

    final ActorType type;
    private final Texture texture;

    public String message;
    public TexActor(ActorType type,int x ,int y){
        this.type = type;
        setPosition(x,y);
        setHeight(0.7f);
        setWidth(0.7f);
        texture = new Texture(type.fileName);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), 1, 1);
    }
}
