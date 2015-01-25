package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by durga.p on 1/24/15.
 */
public class Protagonist extends Hero {

    private Direction prevDir;
    private String nextLevel;
    private Sound defSound;
    private Music shieldBLock;

    public Protagonist(String fileName) {
        super(fileName);
        reset();
        hasAura = false;
        hasShield = false;

        hasSword = false;
        hasArrow = false;

        arrowTime = 0.2f;
        SWORD_TIMEOUT = 1f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        pollKeys();
//        if(processDirection && prevDir != null){
//            move(prevDir);
//        }

        if(nextLevel != null) {
            MyGdxGame gdxGame = (MyGdxGame) Gdx.app.getApplicationListener();
            gdxGame.setScreen(new MyScreen(nextLevel,this));
        }

        if(!usingDefensiveWeapon && hasAura && defSound != null){
            defSound.stop();
        }
    }

    @Override
    public void useDefensiveWeapon() {
        super.useDefensiveWeapon();
        if(hasAura && usingDefensiveWeapon){
            defSound = getMyStage().gameEngine.auraBubble;
            defSound.loop(0.4f);
        }
    }

    @Override
    void playBlocked() {
        super.playBlocked();
        getMyStage().gameEngine.wayBlocked.setVolume(0.4f);
        getMyStage().gameEngine.wayBlocked.play();
    }

    @Override
    void firedArrow() {
        super.firedArrow();
        getMyStage().gameEngine.arrow.play(0.4f);
    }

    @Override
    void usedSword() {
        super.usedSword();
        getMyStage().gameEngine.swordSwing.play(0.4f);
    }

    @Override
    public void useOffensiveWeapon() {
        super.useOffensiveWeapon();

    }

    @Override
    void shieldBlocked() {
        super.shieldBlocked();
        getMyStage().gameEngine.shieldBlock.play(0.4f);
    }

    @Override
    void recochet() {
        super.recochet();
        getMyStage().gameEngine.shieldReflect.play(0.4f);
    }

    @Override
    public void collideWith(Actor actor) {
        super.collideWith(actor);
        if(actor instanceof  OldMan){
            getMyStage().showMessage(((OldMan) actor).message);
        }
        else if(actor instanceof Door) {
            this.nextLevel = ((Door) actor).nextLevel;
        }
        else if(actor instanceof TexActor){
            ActorType type = ((TexActor) actor).type;
            switch (type){
                case OLD_MAN:
                    break;
                case SWORD:
                    setHasSword(true);
                    break;
                case SHIELD:
                    setHasShield(true);
                    break;
                case BOW:
                    setHasArrow(true);
                    break;
                case AURA:
                    setHasAura(true);
                    break;
                case LAVA:
                    break;
                case DOOR:
                    break;
            }
            if(((TexActor) actor).message != null){
                getMyStage().showMessage(((TexActor) actor).message);
            }
        }
    }
}
