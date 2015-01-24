package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

/**
 * Created by durga.p on 1/24/15.
 */
public class Sword extends Actor {
    private final Hero hero;
    TextureRegion drawTexture;
    boolean jabbing;
    float jabTime = 0.2f;
    float miniJabTime = 0.05f;
    float miniJabDist = 0.1f;
    float size = 0.7f;

    TextureRegion swordRight, swordDown, swordUp, swordLeft;
    private float jabDist = 0.6f;

    public Sword(Hero hero) {

        swordRight = new TextureRegion( new Texture(Gdx.files.internal("weapons/sword_right.png")));
        swordLeft  = new TextureRegion( new Texture(Gdx.files.internal("weapons/sword_right.png")));
        swordLeft.flip(true, false);
        swordDown = new TextureRegion( new Texture(Gdx.files.internal("weapons/sword_down.png")));
        swordUp  = new TextureRegion( new Texture(Gdx.files.internal("weapons/sword_down.png")));
        swordUp.flip(false, true);
        this.hero = hero;
        setWidth(size);
        setHeight(size);
    }


    public void jab(Direction dir){
        jabbing = true;
        switch (dir){
            case UP:
                drawTexture = swordUp;
                break;
            case DOWN:
                drawTexture = swordDown;
                break;
            case LEFT:
                drawTexture = swordLeft;
                break;
            case RIGHT:
                drawTexture = swordRight;
                break;
        }
        Action forward = Actions.moveBy(dir.vector.x*jabDist, dir.vector.y*jabDist, jabTime);
        Action jab = Actions.sequence(
                Actions.moveBy(dir.vector.x * miniJabDist, dir.vector.y * miniJabDist, miniJabTime),
                Actions.moveBy(-dir.vector.x * miniJabDist, -dir.vector.y * miniJabDist, miniJabTime)
        );

        Action back = Actions.moveBy(-dir.vector.x, -dir.vector.y, jabTime);
        Action done = new RunnableAction(){
            @Override
            public void run() {
                jabbing = false;
                setPosition(0,0);
            }
        };

        Action complete = Actions.sequence(
                forward,
                Actions.repeat(3,jab),
                back,
                done
        );
        addAction(complete);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(jabbing == true && drawTexture != null){
            batch.draw(drawTexture, hero.getX() + 0.25f + getX(), hero.getY()+0.25f + getY(), getWidth(), getHeight());
        }


    }
}
