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

    boolean removeSelf = false;

    boolean usingDefensiveWeapon;
    boolean usingOffensiveWeapon;
    float timeDifference = 0;

    public static float AURA_TIME = 3.0f;
    public static float SHIELD_TIME = 0.8f;

    public static float xOffset = 0.3f;
    public static float yOffset = 0.3f;
    public static float widthOffset = 0.8f;
    public static float heightOffset = 0.7f;

    private Sword swordActor;

    Texture sh_left, sh_right, sh_up, sh_down, sword, aura;
    private Arrow arrowActor;
    Sword hitSword;
    private float nextSwordUse = -1;
    private float SWORD_TIMEOUT = 2f;
    private float ARROW_TIMEOUT = 1.5f;
    private float nextArrowUse = -1;
    Arrow hitArrow;

    public Hero(String fileName) {
        super(fileName);
        this.health = 30;
        this.maxHealth = 30;
        hasShield = false;
        hasAura = true;
        aura = new Texture("weapons/aura.png");
        sh_left = new Texture("weapons/shield/sh_left.png");
        sh_right = new Texture("weapons/shield/sh_right.png");
        sh_up = new Texture("weapons/shield/sh_up.png");
        sh_down = new Texture("weapons/shield/sh_down.png");
    }

    public void removeSelf(){
        removeSelf = true;
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
        if (hasShield) {
            timeDifference += delta;
            if (timeDifference > SHIELD_TIME)
                reset();
        }
        if (hasAura) {
            timeDifference += delta;
            if (timeDifference > AURA_TIME)
                reset();
        }

        if(nextSwordUse >= 0){
            nextSwordUse -= delta;
        }
        if(nextArrowUse >= 0){
            nextArrowUse -= delta;
        }

        if (removeSelf)
            this.remove();
    }

    @Override
    public void collideWith(Actor actor) {
        super.collideWith(actor);
        if (actor instanceof TexActor) {
            TexActor texActor = (TexActor) actor;
            ActorType type = texActor.type;
            if (type == ActorType.LAVA) {
                getMyStage().gameOver();
            }
        }
        if (actor instanceof Sword && actor != swordActor && actor != hitSword) {
            this.hitSword = (Sword)actor;
            if (usingDefensiveWeapon) {
                if (hasAura) {
                    //Nothing happens
                }
                if (hasShield && hitSword.getJabDirection().vector.isCollinearOpposite(currShieldDirection.vector)) {
                    if (timeDifference < 0.1f) {
                        hitArrow.removeSelf();
                        Arrow newArrow = new Arrow();
                        getMyStage().group.addActor(newArrow);
                        newArrow.setPosition(getX()+0.5f,getY()+0.5f);
                        newArrow.shoot(currShieldDirection);
                    }
                    else
                        hitArrow.removeSelf();
                }
            }
            this.health -= 10;
        }
        else if (actor instanceof Boss) {
            this.health -= 30;
            this.setX((getX() + 8) % 16);
            this.setY((getY() + 4) % 9);
        }
        else if (actor instanceof Arrow && actor != arrowActor && actor != hitArrow) {
            this.hitArrow = (Arrow) actor;
            if (usingDefensiveWeapon) {
                    if (hasAura) {
                        hitArrow.removeSelf();
                    }
                    if (hasShield && hitArrow.getShootDir().vector.isCollinearOpposite(currShieldDirection.vector)) {
                        if (timeDifference < 0.1f) {
                            hitArrow.removeSelf();
                            Arrow newArrow = new Arrow();
                            getMyStage().group.addActor(newArrow);
                            newArrow.setPosition(getX()+0.5f,getY()+0.5f);
                            newArrow.shoot(currShieldDirection);
                        }
                        else
                            hitArrow.removeSelf();
                    }
            }
            else {
                this.health -= 5;
                hitArrow.removeSelf();
            }
        }

        if (this.health <= 0)
            getMyStage().gameOver();
        Gdx.app.log("HERO", "Health is : " + this.health);
    }

    void reset() {
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
        float x = getX() - 0.2f;
        float y = getY() - 0.1f;
        float width = 1.4f;
        float height = 1.4f;
        batch.draw(aura, x, y, width, height);
    }

    public void useOffensiveWeapon() {
        if(hasSword && (swordActor == null || swordActor.getParent() == null) && nextSwordUse < 0){
            swordActor = new Sword(this, getX(), getY());
            getMyStage().group.addActor(swordActor);
            swordActor.jab(currShieldDirection);
            nextSwordUse = SWORD_TIMEOUT;
        }
        if(hasArrow && nextArrowUse < 0){
            if(arrowActor != null && arrowActor.getParent() != null){
                return;
            }
            arrowActor = new Arrow();
            getMyStage().group.addActor(arrowActor);
            arrowActor.setPosition(getX()+0.5f,getY()+0.5f);
            arrowActor.shoot(currShieldDirection);
            nextArrowUse = ARROW_TIMEOUT;
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
