package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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

    public Hero hero;
    public Boss boss;

    public MyStage() {
        group = new Group();
        hero = new Protagonist("test");
        boss = new Boss("test");
        gameEngine = new GameEngine();

        hero.setPosition(0, 0);
        boss.setPosition(14,7);

        group.addActor(hero);

        group.addActor(new TexActor(ActorType.OLD_MAN, 4,4));
        addActor(group);
        addCaptureListener(new MyListener(hero));
        hero.setHasShield(true);
//        hero.setHasSword(true);
        hero.setHasArrow(true);
    }

    public void resize(int width, int height) {
        ppx = width/GameDisplayEngine.GRIDX;
        ppy = height/GameDisplayEngine.GRIDY;
        group.setScale(ppx,ppy);
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        SnapshotArray<Actor> childrenA = group.getChildren();
        SnapshotArray<Actor> childrenB = group.getChildren();
        for(int i=0; i<childrenA.size; i++){
            for(int j=0; j<childrenB.size; j++){
                Actor actorA = childrenA.get(i);
                Actor actorB = childrenA.get(j);
                if(actorA != actorB) {
                    gameEngine.meets(actorA, actorB);
                    if(actorA instanceof Collides){
                        ((Collides) actorA).collideWith(actorB);
                    }
                    if(actorB instanceof Collides){
                        ((Collides) actorB).collideWith(actorA);
                    }
                }
            }
        }
    }

    public void loadActors(TiledMapTileLayer layer){
        if(layer == null) return;
        int width = layer.getWidth();
        int height = layer.getHeight();
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                TiledMapTileLayer.Cell cell = layer.getCell(i,j);
                if(cell == null) continue;
                TiledMapTile tile = cell.getTile();
                if(tile == null) continue;
                String type = (String) tile.getProperties().get("type");
                if("hero".equalsIgnoreCase(type)){
                    hero.setPosition(i, Math.abs(j));
                }
                if("boss".equalsIgnoreCase(type)){
                    boss.setPosition(i, Math.abs(j));
                }

            }
        }
    }
}
