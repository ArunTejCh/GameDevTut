package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

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

    protected Sword swordActor;

    Texture sh_left, sh_right, sh_up, sh_down, sword, aura;
    Arrow arrowActor;
    Sword hitSword;
    private float nextSwordUse = -1;
    float SWORD_TIMEOUT = 1.3f;
    float ARROW_TIMEOUT = 0.8f;
    protected float nextArrowUse = -1;
    Arrow hitArrow;
    boolean onLava = false;
    float RECOCHET_TIME = 0.25f;
    float arrowTime = 0.3f;

    public Hero(String fileName) {
        super(fileName);
        this.health = 100;
        this.maxHealth = 100;
        hasShield = false;
        hasAura = false;
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

        makeItMove(delta);

        if(onLava){
            onLava = getMyStage().gameEngine.lava[(int) getX()][(int)getY()];
        }

        if (hasShield && usingDefensiveWeapon) {
            timeDifference += delta;
            if (timeDifference > SHIELD_TIME)
                reset();
        }
        if (hasAura && usingDefensiveWeapon) {
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
                if(hasAura){
                    if(usingDefensiveWeapon){
                        onLava = true;
                        timeDifference = 0;
                    } else {
                        getMyStage().gameOver();
                    }
                }
                else {
                    getMyStage().gameOver();
                }
            }
            if (type == ActorType.AURA)
                this.hasAura = true;
            if (type == ActorType.SHIELD)
                this.hasShield = true;
            if (type == ActorType.SWORD)
                this.hasSword = true;
            if (type == ActorType.BOW)
                this.hasArrow = true;
        }
        if (actor instanceof Sword && actor != swordActor && actor != hitSword) {
            this.hitSword = (Sword)actor;
            if (usingDefensiveWeapon) {
                if (hasAura) {
                    //Nothing happens
                }
                if (hasShield && hitSword.getJabDirection().vector.isCollinearOpposite(currShieldDirection.vector)) {
                    shieldBlocked();
                }
                else
                    this.health -= 10;
            }
        }
        else if (actor instanceof Boss && ((Boss)actor).mode != Boss.BossMode.SUBDUED_MODE) {
            if (!usingDefensiveWeapon || (!hasShield && !hasAura))
                this.health -= 10;

            float x = (getX() + 8) % 16;
            float y = (getY() + 4) % 9;
            final Action hAction = new RunnableAction(){
                @Override
                public void run() {
                    if (!usingDefensiveWeapon)
                        health -= 30;
                }
            };
            Action move;
            if(Math.random() > 0.5){
                move = Actions.moveTo(2, 9, 0.4f);
            }
            else {
                move = Actions.moveTo(20, 9, 0.4f);
            }
            addAction(Actions.sequence(move,hAction));

        }
        else if (actor instanceof Arrow && actor != arrowActor && actor != hitArrow) {
            this.hitArrow = (Arrow) actor;
            if (usingDefensiveWeapon) {
                    if (hasAura) {
                        hitArrow.removeSelf();
                    }
                    if (hasShield && hitArrow.getShootDir().vector.isCollinearOpposite(currShieldDirection.vector)) {
                        if (timeDifference < RECOCHET_TIME) {
                            hitArrow.removeSelf();
                            Arrow newArrow;
                            if (actor instanceof FireBall) {
                                newArrow = new FireBall(Direction.reverse(hitArrow.getShootDir()));
                                ((FireBall)newArrow).isReflected = true;
                            }
                            else
                                newArrow = new Arrow(hitArrow.moveTime);
                            getMyStage().group.addActor(newArrow);
                            newArrow.setPosition(getX()+0.5f,getY()+0.5f);
                            newArrow.shoot(currShieldDirection);
                            recochet();
                        }
                        else {
                            hitArrow.removeSelf();
                            shieldBlocked();
                        }
                    }
            }
            else {
                if (actor instanceof FireBall)
                    this.health -= 15;
                else
                    this.health -= 5;
                hitArrow.removeSelf();
            }
        }

        if (this.health <= 0)
            getMyStage().gameOver();
    }

    void recochet() {

    }

    void shieldBlocked() {

    }

    void reset() {
        usingDefensiveWeapon = false;
        usingOffensiveWeapon = false;
        timeDifference = 0;
//        onLava = false;
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
        //(swordActor == null || swordActor.getParent() == null) &&
        if(hasSword && ( nextSwordUse < 0)){
            swordActor = new Sword(this, getX(), getY());
            getMyStage().group.addActor(swordActor);
            swordActor.jab(currShieldDirection);
            nextSwordUse = SWORD_TIMEOUT;
            usedSword();
        }
//        Gdx.app.log("arrow",hasArrow+"x"+nextArrowUse);
        if(hasArrow && nextArrowUse < 0){
            if(arrowActor != null && arrowActor.getParent() != null){
//                return;
            }
            arrowActor = new Arrow(arrowTime);
            getMyStage().group.addActor(arrowActor);
            arrowActor.setPosition(getX() + 0.5f, getY() + 0.5f);
            arrowActor.shoot(currShieldDirection);
            nextArrowUse = ARROW_TIMEOUT;
            firedArrow();
        }
    }

    void usedSword() {
    }

    void firedArrow() {

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
