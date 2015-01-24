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

    Texture shield,sword,aura;


    public Hero(String fileName) {
        super(fileName);
        shield = new Texture("weapons/shield.png");

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(hasShield){
            batch.draw(shield,getX()+0.2f, getY()+0.2f, 0.3f, 0.3f);
        }
    }

    public void setHasSheild(boolean hasShield) {
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
