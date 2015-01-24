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
        switch(keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                character.move(Direction.LEFT);
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                character.move(Direction.RIGHT);
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                character.move(Direction.DOWN);
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                character.move(Direction.UP);
                break;
            case Input.Keys.E: //Offensive weapon
                character.useOffensiveWeapon();
                break;
            case Input.Keys.Q: //Defensive weapon
                character.useDefensiveWeapon();
                break;
        }
        return super.keyDown(event, keycode);
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        character.move(null);
        return super.keyUp(event, keycode);
    }
}
