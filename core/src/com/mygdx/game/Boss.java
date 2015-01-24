package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Sudheer on 1/24/15.
 */
public class Boss extends Character {


<<<<<<< Updated upstream
    float timeDifference = 0;
    private Sword swordActor;

=======
    private enum BossMode {
        CHASE_MODE,
        RAGE_MODE,
        SUBDUED_MODE
    };

    private BossMode mode = BossMode.CHASE_MODE;

    private final float MODE_CHANGE_TIMEOUT = 3.0f;
    private float modeTimer = 0;

    private Sword swordActor;

    private static FireBall[] fireBalls = new FireBall[4];

    Texture sh_left, sh_right, sh_up, sh_down, sword, aura;
>>>>>>> Stashed changes

    public Boss(String fileName) {
        super(fileName);
        this.health = 1000;
<<<<<<< Updated upstream
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

=======
        sh_left = new Texture("weapons/shield/sh_left.png");
        sh_right = new Texture("weapons/shield/sh_right.png");
        sh_up = new Texture("weapons/shield/sh_up.png");
        sh_down = new Texture("weapons/shield/sh_down.png");

        fireBalls[0] = new FireBall(Direction.LEFT);
        fireBalls[1] = new FireBall(Direction.RIGHT);
        fireBalls[2] = new FireBall(Direction.UP);
        fireBalls[3] = new FireBall(Direction.DOWN);
>>>>>>> Stashed changes
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

    private void flipMode() {
        if (mode == BossMode.CHASE_MODE)
            mode = BossMode.RAGE_MODE;
        else if (mode == BossMode.RAGE_MODE)
            mode = BossMode.CHASE_MODE;
    }

    @Override
    public void act(float delta) {

        if (modeTimer == 0)
            modeTimer += delta;

        if (modeTimer > MODE_CHANGE_TIMEOUT) {
            reset();
            flipMode();
        }
        if (mode == BossMode.CHASE_MODE) {
            feedMovement();
            super.act(delta);
        }
        else if (mode == BossMode.RAGE_MODE) {
            for (FireBall ball : fireBalls) {
                ball.shoot(ball.getShootDir());
            }
        }
        else if (mode == BossMode.SUBDUED_MODE) {

        }
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
<<<<<<< Updated upstream
        timeDifference = 0;
    }

=======
        modeTimer = 0;
    }

    private void drawAura(Batch batch, float parentAlpha){
        //Draw Aura around
    }

    public void useOffensiveWeapon() {
    }

    public void useDefensiveWeapon() {
    }

    public void setHasAura(boolean hasAura) {
    }

    public void setHasArrow(boolean hasArrow) {
    }

    public void setHasSword(boolean hasSword) {
    }

    public void useOffWeapon(){
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }
>>>>>>> Stashed changes
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
