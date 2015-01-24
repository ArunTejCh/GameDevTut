package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

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

    Batch batch;

    public static float xOffset = 0.3f;
    public static float yOffset = 0.3f;
    public static float widthOffset = 0.8f;
    public static float heightOffset = 0.7f;

    private Sword swordActor;

    Texture sh_left, sh_right, sh_up, sh_down, sword, aura;

    public Hero(String fileName) {
        super(fileName);
        hasShield = true;
        sh_left = new Texture("weapons/shield/sh_left.png");
        sh_right = new Texture("weapons/shield/sh_right.png");
        sh_up = new Texture("weapons/shield/sh_up.png");
        sh_down = new Texture("weapons/shield/sh_down.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.batch = batch;
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
        timeDifference += delta;
        if (timeDifference > 0.8)
            reset();
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
        if(hasSword){
            swordActor.jab(currShieldDirection);
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

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
        if(hasSword){
            swordActor = new Sword(this);
            getMyStage().group.addActor(swordActor);
        }
    }

    public void useOffWeapon(){
        if(hasSword){
            swordActor.jab(currShieldDirection);
        }
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
