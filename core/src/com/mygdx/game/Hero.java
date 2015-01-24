package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by durga.p on 1/24/15.
 */
public class Hero extends Character {

    boolean hasShield;
    boolean hasSword;
    boolean hasArrow;
    boolean hasAura;

    boolean usingDefensiveWeapon;
    boolean usingOffensiveWeapon;
    float timeDifference = 0;

    public static float xOffset = 0.3f;
    public static float yOffset = 0.3f;
    public static float widthOffset = 0.8f;
    public static float heightOffset = 0.7f;

    private Sword swordActor;

    Texture sh_left, sh_right, sh_up, sh_down, sword, aura;
    private Arrow arrowActor;
     Sword hitSword;

    public Hero(String fileName) {
        super(fileName);
        this.health = 100000;
        hasShield = true;
        sh_left = new Texture("weapons/shield/sh_left.png");
        sh_right = new Texture("weapons/shield/sh_right.png");
        sh_up = new Texture("weapons/shield/sh_up.png");
        sh_down = new Texture("weapons/shield/sh_down.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (usingDefensiveWeapon) {
            if (hasShield)
                drawShield(batch, parentAlpha);
            else if (hasAura)
                drawAura(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (usingDefensiveWeapon) {
            timeDifference += delta;
            if (timeDifference > 0.8f)
                reset();
        }
    }

    @Override
    public void collideWith(Actor actor) {
        super.collideWith(actor);
        if (actor instanceof Sword && actor != swordActor) {
            this.hitSword = (Sword)actor;
            this.health -= 10;
        }
        else if (actor instanceof Boss) {
            this.health -= 30;
            this.setX((getX() + 8) % 16);
            this.setY((getY() + 4) % 9);
        }
        else if (actor instanceof Arrow && actor != arrowActor) {
            Arrow arrow = (Arrow) actor;
            if (usingDefensiveWeapon && arrow.getShootDir().vector.isCollinearOpposite(currShieldDirection.vector)) {
                if (timeDifference < 0.1f) {
                    arrow.removeSelf();
                    Arrow newArrow = new Arrow();
                    getMyStage().group.addActor(newArrow);
                    newArrow.setPosition(getX()+0.5f,getY()+0.5f);
                    newArrow.shoot(currShieldDirection);
                }
                else
                    arrow.removeSelf();
            }
            else {
                this.health -= 5;
            }
        }
        if (this.health <= 0)
            Gdx.app.exit();
        Gdx.app.log("HERO", "Health is : " + this.health);
    }

    private void reset() {
        usingDefensiveWeapon = false;
        usingOffensiveWeapon = false;
        timeDifference = 0;
    }

    private void drawShield(Batch batch, float parentAlpha) {
        switch (currShieldDirection) {
            case UP:
            batch.draw(sh_up, getX() + 0.1f, getY() + 0.6f, widthOffset, heightOffset);
                break;
            case DOWN:
                batch.draw(sh_down, getX() + 0.1f, getY() + 0.2f, widthOffset, heightOffset);
                break;
            case LEFT:
                batch.draw(sh_left, getX() - 0.1f, getY() + yOffset, widthOffset, heightOffset);
                break;
            case RIGHT:
                batch.draw(sh_right, getX() + xOffset, getY() + yOffset, widthOffset, heightOffset);
                break;
        }
    }

    private void drawAura(Batch batch, float parentAlpha){
        //Draw Aura around
    }

    public void useOffensiveWeapon() {
        if(hasSword && (swordActor == null || swordActor.getParent() == null)){
            swordActor = new Sword(this, getX(), getY());
            getMyStage().group.addActor(swordActor);
            swordActor.jab(currShieldDirection);
        }
        if(hasArrow){
            if(arrowActor != null && arrowActor.getParent() != null){
                return;
            }
            arrowActor = new Arrow();
            getMyStage().group.addActor(arrowActor);
            arrowActor.setPosition(getX()+0.5f,getY()+0.5f);
            arrowActor.shoot(currShieldDirection);
        }
    }

    public void useDefensiveWeapon() {
        usingDefensiveWeapon = true;
    }

    public void setHasAura(boolean hasAura) {
        this.hasAura = hasAura;
    }

    public void setHasArrow(boolean hasArrow) {
        this.hasArrow = hasArrow;

    }

    public void setHasShield(boolean hasShield) {
        this.hasShield = hasShield;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;

    }


    @Override
    public void setX(float x) {
        super.setX(x);
        if(hasSword){
            swordActor.setX(x);
        }
    }
    @Override
    public void setY(float y) {
        super.setY(y);
        if(hasSword){
            swordActor.setY(y);
        }
    }
}
