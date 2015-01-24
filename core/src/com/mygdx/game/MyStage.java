package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by durga.p on 1/24/15.
 */
public class MyStage extends Stage {
    private final Table table;
    private final Skin skin;
    private final Label label;
    Group group;
    GameEngine gameEngine;
    int ppy;
    int ppx;

    public Hero hero;
    public Boss boss;
    private float labelTimer;

    public MyStage(Hero initHero) {
        group = new Group();
        hero = initHero;
        gameEngine = new GameEngine();
        hero.setPosition(0, 0);
        group.addActor(hero);

        addActor(group);
        addCaptureListener(new MyListener(hero));
        skin = new Skin(Gdx.files.internal("newskin.json"), new TextureAtlas("packed/skin.atlas"));
        table = new Table();
        String msg = "sample";
        label = new Label(msg, skin);
        label.setAlignment(Align.center);
        table.add(label).fillX().width(Gdx.graphics.getWidth()).row();

        table.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-20);
        addActor(table);
    }

    public void resize(int width, int height) {
        ppx = width/GameDisplayEngine.GRIDX;
        ppy = height/GameDisplayEngine.GRIDY;
        group.setScale(ppx,ppy);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(labelTimer > 0){
            labelTimer -= delta;
            if(labelTimer <0){
                label.setText("");
            }
        }

        SnapshotArray<Actor> childrenA = group.getChildren();
        SnapshotArray<Actor> childrenB = group.getChildren();
        for(int i=0; i<childrenA.size; i++){
            for(int j=0; j<childrenB.size; j++){
                Actor actorA = childrenA.get(i);
                Actor actorB = childrenB.get(j);
                if(actorA != actorB) {
                    if(!gameEngine.meets(actorA, actorB))
                        continue;
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
                    hero.setPosition(i, j);
                }
                if("boss".equalsIgnoreCase(type)){
                    boss = new Boss("test");
                    boss.setPosition(i, j);
                }
                if ("eax".equalsIgnoreCase(type)) {
                    Enemy enemy = new ArrowEnemy("test", true);
                    enemy.setPosition(i, j);
                    group.addActor(enemy);
                }
                if ("eay".equalsIgnoreCase(type)) {
                    Enemy enemy = new ArrowEnemy("test", false);
                    enemy.setPosition(i, j);
                    group.addActor(enemy);
                }
                if ("esx".equalsIgnoreCase(type)) {
                    Enemy enemy = new SwordEnemy("test", true);
                    enemy.setPosition(i, j);
                    group.addActor(enemy);
                }
                if ("esy".equalsIgnoreCase(type)) {
                    Enemy enemy = new SwordEnemy("test", false);
                    enemy.setPosition(i, j);
                    group.addActor(enemy);
                }
                if("oldman".equalsIgnoreCase(type)){
                    OldMan oldMan = new OldMan(i,j);
                    oldMan.setMessage((String) tile.getProperties().get("msg"));
                    group.addActor(oldMan);
                }

                if("bow".equalsIgnoreCase(type)){
                    group.addActor(new TexActor(ActorType.SWORD, i, j));
                }
                if("door".equalsIgnoreCase(type)){
                    Door door = new Door(i,j);
                    door.nextLevel = (String) tile.getProperties().get("path");
                    group.addActor(door);
                }
                if("lava".equalsIgnoreCase(type)){
                    group.addActor(new TexActor(ActorType.SWORD, i, j));
                }
                if("shield".equalsIgnoreCase(type)){
                    group.addActor(new TexActor(ActorType.SWORD, i, j));
                }
                if("sword".equalsIgnoreCase(type)){
                    group.addActor(new TexActor(ActorType.SWORD, i, j));
                }


            }
        }
    }

    public void showMessage(String message) {
       label.setText(message);
       labelTimer = 2;
    }
}
