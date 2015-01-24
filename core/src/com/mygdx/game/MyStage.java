package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by durga.p on 1/24/15.
 */
public class MyStage extends Stage {
    Group group;
    GameEngine gameEngine;
    int ppy;
    int ppx;

    public MyStage() {
        group = new Group();
        Hero hero = new Hero("test");
        gameEngine = new GameEngine();


        hero.setPosition(3, 3);

//        stage.addActor(tiledActor);
        group.addActor(hero);
        addActor(group);
        addCaptureListener(new MyListener(hero));

    }

    public void resize(int width, int height) {
        ppx = width/GameDisplayEngine.GRIDX;
        ppy = height/GameDisplayEngine.GRIDY;
        group.setScale(ppx,ppy);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        SnapshotArray<Actor> children = group.getChildren();
//        for(Actor actorA : children){
//            for(Actor actorB : children){
//                if(actorA != actorB){
//                    gameEngine.meets(actorA, actorB);
//                }
//            }
//        }
    }
}
