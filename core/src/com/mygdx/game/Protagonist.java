package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by durga.p on 1/24/15.
 */
public class Protagonist extends Hero {

    private Direction prevDir;
    private String nextLevel;

    public Protagonist(String fileName) {
        super(fileName);
        reset();
//        hasAura = true;
//        hasArrow = true;
        hasShield = true;
        hasSword = true;
        arrowTime = 0.2f;
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
        }
    }
}
