package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by durga.p on 1/24/15.
 */
public class TiledActor {
    final TiledMap tiledMap;
    final OrthogonalTiledMapRenderer renderer;
    private final MapLayer actorLayer;
    private String tiledMapName;

    public TiledActor(String tiledMapName) {
        this.tiledMapName = tiledMapName;
        tiledMap = new TmxMapLoader().load(tiledMapName);
        float width = Gdx.graphics.getWidth()/(float)GameDisplayEngine.GRIDX;
        float unitScale = width*(1/70f);
//        unitScale = 2;
        renderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
        actorLayer = tiledMap.getLayers().get("actors");
    }

    public void setCamera(OrthographicCamera camera){
        renderer.setView(camera);
    }

    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//        renderer.render();
        renderer.getBatch().begin();
        for(MapLayer layer : tiledMap.getLayers()){
            if(layer != actorLayer){
                renderer.renderTileLayer((TiledMapTileLayer) layer);
            }
        }
        renderer.getBatch().end();
    }

    TiledMapTileLayer getCollidesLayer(){
        return (TiledMapTileLayer) tiledMap.getLayers().get("collides");
    }

    public TiledMapTileLayer getActorsLayer() {
        return (TiledMapTileLayer) tiledMap.getLayers().get("actors");
    }
}
