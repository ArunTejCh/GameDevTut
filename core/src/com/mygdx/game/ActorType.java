package com.mygdx.game;

/**
 * Created by durga.p on 1/24/15.
 */
public enum ActorType {
    OLD_MAN("actors/oldman.png"),
    SWORD("actors/sword.png"),
    SHIELD("actors/shield.png"),
    BOW("actors/bow.png"),
    AURA("actors/aura.png"),
    LAVA("actors/lava.png"),
    DOOR("actors/door1.png");

    public final String fileName;

    ActorType(String s) {
        this.fileName = s;
    }
}
