package com.sparcsky.summerydays.entity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.sparcsky.summerydays.TileMaker;

public class Room extends Entity {

    public float doorX;
    public float doorY;

    public Room(int x, int y, int width, int height) {
        setPosition(x, y);
        setSize(width, height);

        do {
            doorX = MathUtils.random(x + 16, x + (width - 16));
            doorY = MathUtils.random(y + 16, y + (height - 16));
        } while (doorX % 16 != 0 && doorY % 16 != 0);

    }

    public void draw(TiledMapTileLayer layer, TileMaker tileMaker) {
        int xHeight = (int) height / 16;
        int xWidth = (int) width / 16;

        for (int i = 0; i < xHeight; i++) {
            for (int j = 0; j < xWidth; j++) {
                TiledMapTileLayer.Cell cell = tileMaker.make(j, i, xWidth, xHeight);
                layer.setCell((j * 16) + (int) x, (i * 16) + (int) y, cell);
            }
        }
    }
}
