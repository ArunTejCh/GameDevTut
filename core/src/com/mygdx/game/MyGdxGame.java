package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Screen screen;


	@Override
	public void create () {
		batch = new SpriteBatch();
//		packTextures();
		screen = new MenuScreen();//new MyScreen();
//        screen = new MyScreen("sample.tmx");
		screen.show();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		screen.resize(width, height);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screen.render(Gdx.graphics.getDeltaTime());
	}

	public void packTextures(){
		FileHandle allPacksDir = Gdx.files.internal("packs");
		FileHandle outputDir = Gdx.files.internal("packed");
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.paddingX = 0;
        settings.paddingY = 0;


		for(FileHandle packDir : allPacksDir.list()){
            if(packDir.isDirectory())
			TexturePacker.processIfModified(settings,packDir.file().getAbsolutePath(), outputDir.file().getAbsolutePath(), packDir.file().getName());
		}
	}

    public void setScreen(Screen screen) {
        this.screen = screen;
        screen.show();
        screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
