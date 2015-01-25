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
public abstract class Character extends Actor implements Collides {
    private static final int FRAME_COLS = 8;
    private static final int FRAME_ROWS = 4;
    float ANIMATE_TIME = 0.1f;
    float MOVE_TIME = 0.5f;
    final Animation wDownAni;
    final Animation wLeftAni;
    final Animation wRightAni;
    final Animation wUpAni;
    float health;
    float maxHealth;

    Texture backRect;
    Texture healthRect;

    TextureRegion[] walkLeft = new TextureRegion[FRAME_COLS];
    TextureRegion[] walkRight = new TextureRegion[FRAME_COLS];
    TextureRegion[] walkUp = new TextureRegion[FRAME_COLS];
    TextureRegion[] walkDown = new TextureRegion[FRAME_COLS];
    Animation currAnimation;
    float animateTime;
    TextureRegion currentFrame;
    public Direction currShieldDirection;
    Direction currDirection;
    protected boolean processDirection = true;

    public Character(String fileName) {
        this.fileName = fileName;
        init();
        Texture walkSheet = new Texture(Gdx.files.internal("sprites/" + fileName + ".png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10

        backRect = new Texture("misc/back_health.png");
        healthRect = new Texture("misc/fore_health.png");

        for (int i = 0; i < FRAME_COLS; i++) {
            walkDown[i] = tmp[0][i];
            walkLeft[i] = tmp[1][FRAME_COLS-1-i];
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
        currShieldDirection = Direction.DOWN;

    }

    void init() {

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
        if(direction == null) {
            currDirection = null;
            return;
        }
        if(currShieldDirection == direction){
            currDirection = direction;
        }
        else {

            setCurrShieldDir(direction);
        }

    }

    void setCurrShieldDir(Direction direction) {
        currShieldDirection = direction;
        switch (currShieldDirection){
            case UP:
                currentFrame = wUpAni.getKeyFrame(0);
                break;
            case DOWN:
                currentFrame = wDownAni.getKeyFrame(0);
                break;
            case LEFT:
                currentFrame = wLeftAni.getKeyFrame(0);
                break;
            case RIGHT:
                currentFrame = wRightAni.getKeyFrame(0);
                break;
        }
        move(currShieldDirection);
    }

    public abstract void useOffensiveWeapon();

    public abstract void useDefensiveWeapon();

    private void moveDone() {
        processDirection = true;
        setAnimation(null);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void makeItMove(float delta) {
        if (currAnimation != null) {
            animateTime += delta;
        }


        if (processDirection && currDirection != null && isDirFeasible(currDirection)) {
            Action action = Actions.moveBy(currDirection.vector.x, currDirection.vector.y, MOVE_TIME);
            Action runAction = sequence(action, new RunnableAction() {
                @Override
                public void run() {
                    moveDone();
                }
            });
            currShieldDirection = currDirection;
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
        drawHealthBar(batch);
    }

    public void drawHealthBar(Batch batch){
        float x = getX() - 0.3f;
        float y = getY() + 1f;

        float width = 2f;
        float height = 0.3f;

        batch.draw(backRect, x, y + 0.15f, width, height);


        float progressWidth = this.health/this.maxHealth * 1;
        batch.draw(healthRect, x + 0.05f, y + 0.15f + 0.05f, 2 * progressWidth, height - 0.05f);




    }

    private String fileName;

    @Override
    public void collideWith(Actor actor) {

    }

    public boolean isDirFeasible(Direction dir) {
        float x = (getX() + 0.5f + dir.vector.x);
        float y = (getY() + 0.5f + dir.vector.y);
        boolean[][] collides = getMyStage().gameEngine.collides;
        if (x < 0 || x >= GameDisplayEngine.GRIDX) {
            return false;
        }
        if (y < 0 || y >= GameDisplayEngine.GRIDY) {
            return false;
        }
        return !getMyStage().gameEngine.collides[(int) x][(int) y];
    }

     MyStage getMyStage() {
        return (MyStage) getStage();
    }
}
