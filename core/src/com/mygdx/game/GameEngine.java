package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.audio.Ogg;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by durga.p on 1/24/15.
 */
public class GameEngine {
   public int sizeX = GameDisplayEngine.GRIDX;
   public int sizeY = GameDisplayEngine.GRIDY;
   public boolean[][] collides = new boolean[sizeX][sizeY];
    public boolean[][] lava = new boolean[sizeX][sizeY];
public static final String SOUND = "sounds/gamesounds/";

  public Sound arrow = Gdx.audio.newSound(Gdx.files.internal(SOUND + "arrow_whoosh.mp3"));
  public Sound shieldReflect = Gdx.audio.newSound(Gdx.files.internal(SOUND + "shield_reflect.mp3"));
  public Sound enemyDying = Gdx.audio.newSound(Gdx.files.internal(SOUND + "enemydying.mp3"));
  public Sound shieldBlock = Gdx.audio.newSound(Gdx.files.internal(SOUND + "shield_block.mp3"));
  public Sound swordSwing = Gdx.audio.newSound(Gdx.files.internal(SOUND + "sword_swing.mp3"));
  public Sound auraBubble = Gdx.audio.newSound(Gdx.files.internal(SOUND + "aura_sabre.mp3"));
  public Ogg.Music wayBlocked = (Ogg.Music) Gdx.audio.newMusic(Gdx.files.internal(SOUND + "way_blocked.ogg"));


   public void setCollides(TiledMapTileLayer layer){
    for(int i=0; i<sizeX; i++){
        for(int j=0; j<sizeY; j++){
            TiledMapTileLayer.Cell cell = layer.getCell(i, j);
            if(cell != null && cell.getTile() != null) {
                collides[i][j] = true;
            }
            else {
                collides[i][j] = false;
            }
            lava[i][j] = false;
        }
    }
   }


    public boolean meets(Actor actorA, Actor actorB) {
        if(actorA.getX() >= actorB.getX() && actorA.getX() <= actorB.getX()+actorB.getWidth()){
            if(actorA.getY() >= actorB.getY() && actorA.getY() <= actorB.getY()+actorB.getHeight()){
                return true;
            }
        }
        return false;
    }
}
