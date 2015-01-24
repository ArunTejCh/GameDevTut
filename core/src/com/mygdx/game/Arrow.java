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
public class Arrow extends Actor {
    private final int maxSize;
    private final int minSize;
    TextureRegion drawTexture;
    float moveTime = 0.2f;
    float width = 0.7f;
    float height = 0.3f;
    private boolean removeSelf = false;

    TextureRegion right, down, up, left;
    Direction shootDir;

    public Arrow() {

        right = new TextureRegion(new Texture(Gdx.files.internal("weapons/arrow_right.png")));
        left = new TextureRegion(new Texture(Gdx.files.internal("weapons/arrow_right.png")));
        left.flip(true, false);
        down = new TextureRegion(new Texture(Gdx.files.internal("weapons/arrow_up.png")));
        down.flip(false, true);
        up = new TextureRegion(new Texture(Gdx.files.internal("weapons/arrow_up.png")));

        maxSize = Math.max(up.getRegionWidth(), up.getRegionHeight());
        minSize = Math.max(up.getRegionWidth(), up.getRegionHeight());

    }

    public Direction getShootDir(){
        return shootDir;
    }

    public void removeSelf(){
        removeSelf = true;
    }

    public void shoot(Direction direction) {
        shootDir = direction;
        switch (direction) {
            case UP:
                drawTexture = up;
                setHeight(width);
                setWidth(height);
                break;
            case DOWN:
                drawTexture = down;
                setHeight(width);
                setWidth(height);
                break;
            case LEFT:
                drawTexture = left;
                setHeight(height);
                setWidth(width);
                break;
            case RIGHT:
                drawTexture = right;
                setHeight(height);
                setWidth(width);
                break;
        }
        Action step = Actions.moveBy(direction.vector.x, direction.vector.y, moveTime);
        Action check = new RunnableAction() {
            @Override
            public void run() {
                if(!isDirFeasible(shootDir)){
                    remove();
                }
            }
        };
        Action flow = Actions.forever(Actions.sequence(step, check));
        addAction(flow);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (removeSelf)
            this.remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (drawTexture != null) {

            batch.draw(drawTexture, getX(), getY(), getWidth(), getHeight());
        }
    }
    public boolean isDirFeasible(Direction dir) {
        float x = (getX() + 0.2f + dir.vector.x);
        float y = (getY() + 0.2f + dir.vector.y);
        boolean[][] collides = ((MyStage)getStage()).gameEngine.collides;
        if (x < 0 || x >= GameDisplayEngine.GRIDX) {
            return false;
        }
        if (y < 0 || y >= GameDisplayEngine.GRIDY) {
            return false;
        }
        return !collides[(int) x][(int) y];
    }
}
