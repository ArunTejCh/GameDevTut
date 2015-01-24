package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Durga on 21-01-2015.
 */
public class MyScreen implements Screen {

    private Stage stage;
    protected float ppy;
    protected float ppx;
    private Group group;
    private TiledActor tiledActor;

    @Override
    public void show() {
        stage = new Stage();
        group = new Group();
//        group.addActor(new MyActor());


        Character character = new Character("test");
        GameEngine gameEngine = new GameEngine();
        tiledActor = new TiledActor("tiledMaps/sample.tmx");
        tiledActor.setCamera((com.badlogic.gdx.graphics.OrthographicCamera) stage.getCamera());
        gameEngine.setCollides(tiledActor.getCollidesLayer());


        character.setAnimation(Direction.UP);
        character.setPosition(2,2);


//        stage.addActor(tiledActor);
        group.addActor(character);
        stage.addActor(group);



        Gdx.input.setInputProcessor(stage);
        stage.addCaptureListener(new MyListener(character));
    }

    @Override
    public void render(float delta) {

        stage.getBatch().begin();
        tiledActor.draw(stage.getBatch(),1);
        stage.getBatch().end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        ppx = width/GameDisplayEngine.GRIDX;
        ppy = height/GameDisplayEngine.GRIDY;
        group.setScale(ppx,ppy);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
