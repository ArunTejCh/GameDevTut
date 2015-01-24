package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by durga.p on 1/24/15.
 */
public class Character extends Actor implements Collides {
    private static final int FRAME_COLS = 8;
    private static final int FRAME_ROWS = 4;
    private static final float ANIMATE_TIME = 0.1f;
    private static final float MOVE_TIME = 0.5f;
    private final Animation wDownAni;
    private final Animation wLeftAni;
    private final Animation wRightAni;
    private final Animation wUpAni;

    TextureRegion[] walkLeft = new TextureRegion[FRAME_COLS];
    TextureRegion[] walkRight = new TextureRegion[FRAME_COLS];
    TextureRegion[] walkUp = new TextureRegion[FRAME_COLS];
    TextureRegion[] walkDown = new TextureRegion[FRAME_COLS];
    private Animation currAnimation;
    private float animateTime;
    private TextureRegion currentFrame;
    private Direction currDirection;
    private boolean processDirection = true;

    public Character(String fileName) {
        this.fileName = fileName;

        Texture walkSheet = new Texture(Gdx.files.internal("sprites/" + fileName + ".png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10

        for (int i = 0; i < FRAME_COLS; i++) {
            walkDown[i] = tmp[0][i];
            walkLeft[i] = tmp[1][i];
            walkRight[i] = tmp[2][i];
            walkUp[i] = tmp[3][i];
        }

        wDownAni = new Animation(ANIMATE_TIME, walkDown);
        wLeftAni = new Animation(ANIMATE_TIME, walkLeft);
        wRightAni = new Animation(ANIMATE_TIME, walkRight);
        wUpAni = new Animation(ANIMATE_TIME, walkUp);

        setWidth(1);
        setHeight(1);

        currentFrame = wDownAni.getKeyFrame(0);

    }

    public void setAnimation(Direction direction) {
        animateTime = 0f;
        if (direction == null) {
            currAnimation = null;
            return;
        }
        switch (direction) {
            case UP:
                currAnimation = wUpAni;
                break;
            case DOWN:
                currAnimation = wDownAni;
                break;
            case LEFT:
                currAnimation = wLeftAni;
                break;
            case RIGHT:
                currAnimation = wRightAni;
                break;
        }

    }

    public void move(Direction direction) {
        currDirection = direction;
    }

    private void moveDone() {
        processDirection = true;
        setAnimation(null);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (currAnimation != null) {
            animateTime += delta;
        }
        if(processDirection && currDirection != null && isDirFeasible(currDirection)){
            Action action = Actions.moveBy(currDirection.vector.x, currDirection.vector.y, MOVE_TIME);
            Action runAction = sequence(action, new RunnableAction() {
                @Override
                public void run() {
                    moveDone();
                }
            });
            addAction(runAction);
            setAnimation(currDirection);
            processDirection = false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
        if (currAnimation != null)
            currentFrame = currAnimation.getKeyFrame(animateTime, true);
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    private String fileName;

    @Override
    public void collideWith(Actor actor) {

    }

    public boolean isDirFeasible(Direction dir){
        int x = (int) (getX() + 0.5f + dir.vector.x);
        int y = (int) (getY() + 0.5f + dir.vector.y);
        boolean[][] collides = getMyStage().gameEngine.collides;
        if(x < 0 || x >= GameDisplayEngine.GRIDX){
            return false;
        }
        if(y < 0 || y >= GameDisplayEngine.GRIDY){
            return false;
        }
        return !getMyStage().gameEngine.collides[x][y];
    }

    private MyStage getMyStage() {
        return (MyStage) getStage();
    }
}
