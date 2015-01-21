package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.io.File;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	MyScreen screen;

	TextureAtlas atlas;

	@Override
	public void create () {
		batch = new SpriteBatch();
//		packTextures();
		atlas = new TextureAtlas("packed/buildings.atlas");
		screen = new MyScreen();
		screen.show();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		screen.resize(width, height);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screen.render(Gdx.graphics.getDeltaTime());
	}

	public void packTextures(){
		FileHandle allPacksDir = Gdx.files.internal("packs");
		FileHandle outputDir = Gdx.files.internal("packed");
		for(FileHandle packDir : allPacksDir.list()){
			TexturePacker.processIfModified(packDir.file().getAbsolutePath(), outputDir.file().getAbsolutePath(), packDir.file().getName());
		}
	}
}
