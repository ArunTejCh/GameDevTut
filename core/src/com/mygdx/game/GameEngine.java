package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by durga.p on 1/24/15.
 */
public class GameEngine {
   public int sizeX = 16;
   public int sizeY = 9;
   public boolean[][] collides = new boolean[sizeX][sizeY];

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
        }
    }
   }
}
