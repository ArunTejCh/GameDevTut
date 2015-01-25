package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by Sudheer on 1/24/2015.
 */
public class SwordEnemy extends Enemy {

    private Sword swordActor;

    public SwordEnemy(String fileName, boolean isXDir){
        super(fileName, isXDir);
        this.health = 30;
        this.maxHealth = 30;
        hasSword = true;
        hasArrow = false;
    }

    @Override
    protected void setParent(Group parent) {
        super.setParent(parent);
//        swordActor = new Sword(this, getX(), getY());
//        parent.addActor(swordActor);
    }

    @Override
    public void collideWith(Actor actor) {
        if (actor instanceof Sword && actor != swordActor && actor != hitSword) {
            this.hitSword = (Sword) actor;
            this.health -= 15;
        }
        else if (actor instanceof Arrow) {
            this.health -= 10;
            ((Arrow) actor).removeSelf();
        }
        if (this.health <= 0) {
            this.removeSelf = true;
            getMyStage().gameEngine.enemyDying.play(0.4f);
        }
    }

    @Override
    public void act(float delta) {
        feedMovement();
        super.act(delta);
    }

    private void feedMovement() {
        Hero hero = getMyStage().hero;
        Direction heroDirection = hero.currDirection;
        float x = getX();
        float y = getY();

        float heroX = hero.getX();
        float heroY = hero.getY();

        float xDiff = Math.abs(x - heroX);
        float yDiff = Math.abs(y - heroY);

        if (isXDir) {
            if (xDiff <= 1f && yDiff < 0.01F) {
                if (x > heroX) {
                    currDirection = Direction.LEFT;
                    if (!isDirFeasible(currDirection))
                        currDirection = Direction.RIGHT;
                    else
                        useOffensiveWeapon();
                }
                else {
                    currDirection = Direction.RIGHT;
                    if (!isDirFeasible(currDirection))
                        currDirection = Direction.LEFT;
                    else
                        useOffensiveWeapon();
                }
            }
            if (currDirection == Direction.LEFT && !isDirFeasible(Direction.LEFT))
                currDirection = Direction.RIGHT;
            if (currDirection == Direction.RIGHT && !isDirFeasible(Direction.RIGHT))
                currDirection = Direction.LEFT;
        }
        else {
            if (yDiff <= 1f && xDiff < 0.01F) {
                if (y > heroY) {
                    currDirection = Direction.DOWN;
                    if (!isDirFeasible(currDirection))
                        currDirection = Direction.UP;
                    else
                        useOffensiveWeapon();
                }
                else {
                    currDirection = Direction.UP;
                    if (!isDirFeasible(currDirection))
                        currDirection = Direction.DOWN;
                    else
                        useOffensiveWeapon();
                }
            }
            if (currDirection == Direction.DOWN && !isDirFeasible(Direction.DOWN))
                currDirection = Direction.UP;
            if (currDirection == Direction.UP && !isDirFeasible(Direction.UP))
                currDirection = Direction.DOWN;
        }
    }

//    public void useOffensiveWeapon() {
//        if (swordActor == null) {
//            swordActor = new Sword(this, getX(), getY());
//            getMyStage().group.addActor(swordActor);
//        }
//        swordActor.jab(currShieldDirection);
//    }

    @Override
    public void setX(float x) {
        super.setX(x);
        swordActor.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        swordActor.setY(y);
    }
}
