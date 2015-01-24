package com.mygdx.game;

/**
 * Created by durga.p on 1/24/15.
 */
public class OldMan extends TexActor{
    String message;
    public OldMan(int x, int y) {
        super(ActorType.OLD_MAN, x, y);
        message = "This is a decently long message, and its very long";
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
