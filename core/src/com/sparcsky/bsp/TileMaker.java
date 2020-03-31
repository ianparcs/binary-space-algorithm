package com.sparcsky.bsp;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.sparcsky.bsp.asset.Asset;

public class TileMaker {

    private TextureAtlas atlas;
    private TiledMap map;

    public TileMaker(com.sparcsky.bsp.asset.Asset asset, TiledMap map) {
        this.map = map;
        atlas = asset.get(Asset.tileSetAtlas);
        TextureRegion region = atlas.findRegion("tileName");
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(region));
    }

    public TiledMapTileLayer.Cell make(int x, int y, int width, int height) {
        String tileName = getTileName(x, y, width, height);
        TextureRegion region = atlas.findRegion(tileName);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(region));
        return cell;
    }

    private String getTileName(int x, int y, int xWidth, int xHeight) {
        String tileName = getRandomMidFloor();
        if (y == 0) {
            if (x == 0) tileName = "room_bottom_left";
            else if (x == xWidth - 1) tileName = "room_bottom_right";
            else tileName = "room_bottom_mid_1";
        }
        if (y > 0 & y < xHeight - 1) {
            if (x == 0) tileName = "room_left_mid_1";
            if (x == xWidth - 1) tileName = "room_right_mid_1";
        }
        if (y == xHeight - 1) {
            if (x == 0) tileName = "room_top_left";
            else if (x == xWidth - 1) tileName = "room_top_right";
            else tileName = "room_top_mid_1";
        }
        return tileName;
    }

    private String getRandomMidFloor() {
        String[] midTiles = {"room_mid_1", "room_mid_2"};
        float chance = MathUtils.random(0.0f, 1.0f);
        if (chance >= 0.10f) {
            return midTiles[0];
        }
        return midTiles[1];
    }

    public TiledMapTileLayer.Cell getCell(String tileName) {
        TextureRegion region = atlas.findRegion(tileName);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(region));
        return cell;
    }


    public void removeLayer(TiledMap map) {
        for (MapLayer layer : map.getLayers()) {
            map.getLayers().remove(layer);
        }
    }

    public void addLayer(TiledMap map, int width, int height) {
        MapLayers mapLayers = map.getLayers();
        mapLayers.add(new TiledMapTileLayer(width, height, 16, 16));
    }

    public TiledMapTileLayer getLayer(int index) {
        return (TiledMapTileLayer) map.getLayers().get(index);
    }
}
