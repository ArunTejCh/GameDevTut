package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class MyTextButton extends TextButton{

        private final String fName;
        private final String vName;

        public MyTextButton(final String text,final String vName, Skin skin) {
            super(vName, skin);
            this.fName = text;
            this.vName = vName;
            addCaptureListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MyGdxGame game = (MyGdxGame) Gdx.app.getApplicationListener();
                    game.setScreen(new MyScreen(fName, null));
                }
            });
        }
    }