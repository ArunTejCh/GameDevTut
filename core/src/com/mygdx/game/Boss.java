package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import javafx.util.Pair;

/**
 * Created by Sudheer on 1/24/15.
 */
public class Boss extends Character {

    private Direction UP;
    private float nextArrowUse;
    private float ARROW_TIMEOUT = 2f;
    private boolean onLava = false;
    private float onLavaTime;

    private enum BossMode {
        CHASE_MODE,
        RAGE_MODE,
        SUBDUED_MODE,
        LAVA_SUBDUED_MODE
    }

    ;

    private BossMode mode = BossMode.CHASE_MODE;

    private final float MODE_CHANGE_TIMEOUT = 3.0f;
    private final float MODE_SUBDUED_TIMEOUT = 3.0f;
    private float modeTimer = 0;

    private Sword swordActor;

    private static FireBall[] fireBalls = new FireBall[4];
    private static Pair<Integer, Integer>[] safeZones = new Pair[5];

    public Boss(String fileName) {
        super(fileName);
        this.health = 1000;
        this.maxHealth = 1000;
        setWidth(2);
        setHeight(2);
        resetFireBalls();

        safeZones[0] = new Pair<Integer, Integer>(0, 0);
        safeZones[1] = new Pair<Integer, Integer>(0, 0);
        safeZones[2] = new Pair<Integer, Integer>(0, 0);
        safeZones[3] = new Pair<Integer, Integer>(0, 0);
        safeZones[4] = new Pair<Integer, Integer>(0, 0);

        setMode(BossMode.CHASE_MODE);
    }

    @Override
    void init() {
        super.init();
        MOVE_TIME = 0.3f;
        ANIMATE_TIME = 0.21f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        Gdx.app.log("Alpha", getColor().a + " : " + getActions().size);
        batch.setColor(getColor());
        super.draw(batch, parentAlpha);
        batch.setColor(getColor().r, getColor().g, getColor().b, 1.0f);
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


        Direction possibleYDirection = takeYDir(x, y, heroX, heroY);
        Direction possibleXDirection = takeXDir(x, y, heroX, heroY);
        boolean isXPoss = isDirFeasible(possibleXDirection);
        boolean isYPoss = isDirFeasible(possibleYDirection);
        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (isXPoss)
                currDirection = possibleXDirection;
            else if (isYPoss)
                currDirection = possibleYDirection;
        } else if (isDirFeasible(possibleYDirection)) {
            if (isYPoss)
                currDirection = possibleYDirection;
            else if (isXPoss)
                currDirection = possibleXDirection;
        } else
            currDirection = Direction.reverse(currDirection);


        if (Math.abs(xDiff) > Math.abs(yDiff))
            takeXDir(x, y, heroX, heroY);
        else
            takeYDir(x, y, heroX, heroY);


//        if(!isDirFeasible(currDirection)){
//            Direction walkedFrom = Direction.reverse(currShieldDirection);
//            List<Direction> canGoIn = new ArrayList<Direction>();
//            for(Direction tryDir : Direction.values()){
//                if(tryDir != currDirection && tryDir != walkedFrom){
//                    if(isDirFeasible(tryDir)){
//                        canGoIn.add(tryDir);
//                    }
//                }
//            }
//            if(canGoIn.size() > 0){
//                Direction goIn = canGoIn.get(MathUtils.random(0, canGoIn.size()-1));
//                currDirection = goIn;
//            } else {
//                currDirection = walkedFrom;
//            }
//
//        }

    }

    private Direction takeXDir(float x, float y, float heroX, float heroY) {
        Direction possibleDir;
        if (x > heroX)
            possibleDir = Direction.LEFT;
        else
            possibleDir = Direction.RIGHT;
        return possibleDir;
    }

    private Direction takeYDir(float x, float y, float heroX, float heroY) {
        Direction possibleDir;
        if (y > heroY)
            possibleDir = Direction.DOWN;
        else
            possibleDir = Direction.UP;
        return possibleDir;
    }

    private void flipMode() {
        if (mode == BossMode.CHASE_MODE)
            setMode(BossMode.RAGE_MODE);
        else if (mode == BossMode.RAGE_MODE)
            setMode(BossMode.CHASE_MODE);
    }

    @Override
    public void act(float delta) {
        if (nextArrowUse >= 0) {
            nextArrowUse -= delta;
        }
        if (onLava) {
            onLavaTime += delta;
            if (onLavaTime >= MOVE_TIME) {
                setMode(BossMode.LAVA_SUBDUED_MODE);
            }
        }


        if (modeTimer < MODE_CHANGE_TIMEOUT)
            modeTimer += delta;

        if ((mode == BossMode.SUBDUED_MODE || mode == BossMode.LAVA_SUBDUED_MODE) && modeTimer > MODE_SUBDUED_TIMEOUT) {
            removeAction(getActions().first());
            reset();
            setMode(BossMode.CHASE_MODE);
        }
        if (mode != BossMode.SUBDUED_MODE && mode != BossMode.LAVA_SUBDUED_MODE && modeTimer > MODE_CHANGE_TIMEOUT) {
            reset();
            flipMode();
        }
        if (mode == BossMode.CHASE_MODE) {
            feedMovement();
            super.act(delta);
            makeItMove(delta);
        } else if (mode == BossMode.RAGE_MODE && nextArrowUse < 0) {
            for (FireBall ball : fireBalls) {
                ball.shoot(ball.getShootDir());
                getMyStage().group.addActor(ball);
                ball.setPosition(getX() + 0.5f, getY() + 0.5f);
            }
            resetFireBalls();
        } else if (mode == BossMode.SUBDUED_MODE || mode == BossMode.LAVA_SUBDUED_MODE) {
            super.act(delta);
        }
    }

    private void setMode(BossMode mode) {
        this.mode = mode;
        if (mode == BossMode.SUBDUED_MODE || mode == BossMode.LAVA_SUBDUED_MODE) {
            Action action = Actions.alpha(0.4f, 0.2f);
            Action actionBack = Actions.alpha(1f, 0.2f);
            Action addAction = Actions.sequence(Actions.forever(Actions.sequence(action, actionBack)));
            addAction(addAction);
        }
    }

    private void resetFireBalls() {
        fireBalls[0] = new FireBall(Direction.LEFT);
        fireBalls[1] = new FireBall(Direction.RIGHT);
        fireBalls[2] = new FireBall(Direction.UP);
        fireBalls[3] = new FireBall(Direction.DOWN);
        nextArrowUse = ARROW_TIMEOUT;
    }

    @Override
    public void collideWith(Actor actor) {
        super.collideWith(actor);
        if (actor instanceof Sword && actor != swordActor) {
            if (mode == BossMode.SUBDUED_MODE)
                this.health -= 50;
            else
                this.health -= 5;
            swordActor = (Sword) actor;
        } else if (actor instanceof FireBall) {
            if (((FireBall) actor).isReflected) {
                setMode(BossMode.SUBDUED_MODE);
                reset();
            }
        } else if (actor instanceof Arrow) {
            if (mode == BossMode.LAVA_SUBDUED_MODE)
                this.health -= 50;
            else
                this.health -= 1;
        } else if (actor instanceof Arrow && !(actor instanceof FireBall))
            this.health -= 1;
        if (actor instanceof TexActor) {
            ActorType type = ((TexActor) actor).type;
            switch (type) {
                case LAVA:
                    if (onLava) {

                    }
                    if (getMyStage().hero.onLava) {
                        onLava = true;
                        onLavaTime = 0;
                    } else {
                        setPosition(3, 4);
                    }
                    break;
            }
        }
        if (this.health <= 0)
            Gdx.app.exit();

//        Gdx.app.log("BOSS", "Health is : " + this.health);
    }

    private void reset() {
        modeTimer = 0;
    }

    private void drawAura(Batch batch, float parentAlpha) {
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

    public void useOffWeapon() {
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public boolean isDirFeasible(Direction dir) {
        return isFeasibleFrom(dir, getX(), getY())
                && isFeasibleFrom(dir, getX(), getY() + getHeight() / 2f)
                && isFeasibleFrom(dir, getX() + getWidth() / 2f, getY())
                && isFeasibleFrom(dir, getX() + getWidth() / 2f, getY() + getHeight() / 2f);

    }


    private boolean isFeasibleFrom(Direction dir, float px, float py) {
        float x = (px + dir.vector.x);
        float y = (py + dir.vector.y);
        boolean[][] collides = getMyStage().gameEngine.collides;
        boolean isHeroOnLava = getMyStage().hero.onLava;
        if (x < 0 || x >= GameDisplayEngine.GRIDX) {
            return false;
        }
        if (y < 0 || y >= GameDisplayEngine.GRIDY) {
            return false;
        }
        if (isHeroOnLava) {
            return !collides[(int) x][(int) y];
        } else {
            return !collides[(int) x][(int) y] && getMyStage().gameEngine.lava[(int) x][(int) y];
        }
    }
}
