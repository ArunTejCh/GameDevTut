package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Sudheer on 1/24/15.
 */
public class Boss extends Character {


    float timeDifference = 0;
    private Sword swordActor;


    public Boss(String fileName) {
        super(fileName);
        this.health = 1000;
        setWidth(2);
        setHeight(2);


    }

    @Override
    void init() {
        super.init();
        MOVE_TIME = 0.3f;
        ANIMATE_TIME = 0.21f;
    }

    @Override
    public void useOffensiveWeapon() {

    }

    @Override
    public void useDefensiveWeapon() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float progressWidth = this.health/this.maxHealth * 1;
        batch.draw(healthRect, getX() + 0.05f, getY() + getHeight() + 0.15f + 0.05f, 2 * progressWidth, getHeight() - 0.05f);
    }

    private void feedMovement() {
//        currDirection = Direction.rand();
//        while (! isDirFeasible(currDirection)) {
//            currDirection = Direction.rand();
//        }


        Hero hero = getMyStage().hero;
        Direction heroDirection = hero.currDirection;
        float x = getX();
        float y = getY();

        float heroX = hero.getX();
        float heroY = hero.getY();

        float xDiff = x - heroX;
        float yDiff = y - heroY;

        if (Math.abs(xDiff) > Math.abs(yDiff))
            takeXDir(x, y, heroX, heroY);
        else
            takeYDir(x, y, heroX, heroY);
    }

    private void takeXDir(float x, float y, float heroX, float heroY) {
        if (!(isDirFeasible(Direction.LEFT) || isDirFeasible(Direction.RIGHT)))
            return;
        if (x > heroX)
            currDirection = Direction.LEFT;
        else
            currDirection = Direction.RIGHT;
        if (!isDirFeasible(currDirection))
            takeYDir(x, y, heroX, heroY);
    }

    private void takeYDir(float x, float y, float heroX, float heroY) {
        if (!(isDirFeasible(Direction.DOWN) || isDirFeasible(Direction.UP)))
            return;
        if (y > heroY)
            currDirection = Direction.DOWN;
        else
            currDirection = Direction.UP;
        if (!isDirFeasible(currDirection))
            takeXDir(x, y, heroX, heroY);
    }

    @Override
    public void act(float delta) {
        feedMovement();
        super.act(delta);
    }

    @Override
    public void collideWith(Actor actor) {
        super.collideWith(actor);
        if (actor instanceof Sword && actor != swordActor) {
            this.health -= 5;
            swordActor = (Sword) actor;
        } else if (actor instanceof Arrow)
            this.health -= 1;
        if (this.health <= 0)
            Gdx.app.exit();
        Gdx.app.log("BOSS", "Health is : " + this.health);
    }

    private void reset() {
        timeDifference = 0;
    }

    @Override
    public boolean isDirFeasible(Direction dir) {
        return isFeasibleFrom(dir, getX(), getY())
                && isFeasibleFrom(dir, getX(), getY() + getHeight())
                && isFeasibleFrom(dir, getX() + getWidth(), getY())
                && isFeasibleFrom(dir, getX() + getWidth(), getY() + getHeight());

    }

    private boolean isFeasibleFrom(Direction dir, float px, float py) {
        float x = (px + 0.5f + dir.vector.x);
        float y = (py + 0.5f + dir.vector.y);
        boolean[][] collides = getMyStage().gameEngine.collides;
        if (x < 0 || x >= GameDisplayEngine.GRIDX) {
            return false;
        }
        if (y < 0 || y >= GameDisplayEngine.GRIDY) {
            return false;
        }
        return !collides[(int) x][(int) y];
    }
}
