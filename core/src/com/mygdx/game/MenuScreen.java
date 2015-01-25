package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by durga.p on 1/24/15.
 */
public class MenuScreen implements Screen{

    private Skin skin;
    private Stage stage;
    private Table table;
    public static final String STARTING_LEVEL = "oldman_1.tmx";
    public static final String INSTRUCTIONS = "instructions.tmx";
    public static final String EXIT = "quit";


    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("newskin.json"), new TextureAtlas("packed/skin.atlas"));
        stage = new Stage();
        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.setHeight(Gdx.graphics.getHeight());

        TextButton textButton1 = new MyTextButton(STARTING_LEVEL,"START GAME", skin);
        table.add(textButton1).padLeft(0 * Gdx.graphics.getWidth() * 0.4f).width(250).center().row();

//        TextButton textButton2 = new MyTextButton(INSTRUCTIONS,"INSTRUCTIONS", skin);
//        table.add(textButton2).padLeft(0 * Gdx.graphics.getWidth() * 0.4f).width(250).center().row();

        TextButton textButton3 = new MyTextButton(EXIT,"QUIT", skin);
        table.add(textButton3).padLeft(0 * Gdx.graphics.getWidth() * 0.4f).width(250).center().row();
//
//        FileHandle maps = Gdx.files.internal("tiledMaps");
//        for(FileHandle map : maps.list(".tmx")){
//            TextButton textButton = new MyTextButton(map.name(),map.name(), skin);
//            table.add(textButton).padLeft(0 * Gdx.graphics.getWidth() * 0.4f).width(250).center().row();
//
//        }

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
