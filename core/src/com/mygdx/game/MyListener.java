package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by durga.p on 1/24/15.
 */
public class MyListener extends InputListener{
    private final Character character;

    public MyListener(Character character) {
        this.character = character;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {

        if(keycode == Input.Keys.A){
           character.move(Direction.LEFT);
        }
        else if(keycode == Input.Keys.D){
            character.move(Direction.RIGHT);
        }
        else if(keycode == Input.Keys.S){
            character.move(Direction.DOWN);
        }
        else if(keycode == Input.Keys.W){
            character.move(Direction.UP);
        }
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if(keycode == Input.Keys.A){
            character.move(null);
        }
        else if(keycode == Input.Keys.D){
            character.move(null);
        }
        else if(keycode == Input.Keys.S){
            character.move(null);
        }
        else if(keycode == Input.Keys.W){
            character.move(null);
        }
        return super.keyUp(event, keycode);
    }
}
