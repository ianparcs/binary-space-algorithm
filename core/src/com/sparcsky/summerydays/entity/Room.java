package com.sparcsky.summerydays.entity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.sparcsky.summerydays.TileMaker;

public class Room {

    public int x;
    public int y;
    public int width;
    public int height;

    public int doorX;
    public int doorY;

    public Room(int x, int y, int width, int height) {
        setPosition(x, y);
        setSize(width, height);
    }

    public void draw(TileMaker tileMaker) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                TiledMapTileLayer.Cell cell = tileMaker.make(j, i, width, height);
                tileMaker.getLayer(0).setCell(j + x, i + y, cell);
            }
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
