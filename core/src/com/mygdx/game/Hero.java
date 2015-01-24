package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by durga.p on 1/24/15.
 */
public class Hero extends Character {

    boolean hasShield;
    boolean hasSword;
    boolean hasArrow;
    boolean hasAura;

    public static float xOffset = 0.3f;
    public static float yOffset = 0.3f;
    public static float widthOffset = 0.8f;
    public static float heightOffset = 0.7f;

    Texture sh_left, sh_right, sh_up, sh_down, sword, aura;


    public Hero(String fileName) {
        super(fileName);
        sh_left = new Texture("weapons/shield/sh_left.png");
        sh_right = new Texture("weapons/shield/sh_right.png");
        sh_up = new Texture("weapons/shield/sh_up.png");
        sh_down = new Texture("weapons/shield/sh_down.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (hasShield) {
            switch (currShieldDirection) {
                case UP:
                batch.draw(sh_up, getX() + 0.1f, getY() + 0.6f, widthOffset, heightOffset);
                    break;
                case DOWN:
                    batch.draw(sh_down, getX() + 0.1f, getY() + 0.2f, widthOffset, heightOffset);
                    break;
                case LEFT:
                    batch.draw(sh_left, getX() - 0.1f, getY() + yOffset, widthOffset, heightOffset);
                    break;
                case RIGHT:
                    batch.draw(sh_right, getX() + xOffset, getY() + yOffset, widthOffset, heightOffset);
                    break;
            }
        }
    }

    public void setHasShield(boolean hasShield) {
        this.hasShield = hasShield;
    }

    public void setHasAura(boolean hasAura) {
        this.hasAura = hasAura;
    }

    public void setHasArrow(boolean hasArrow) {
        this.hasArrow = hasArrow;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
    }
}
