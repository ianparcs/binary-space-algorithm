package com.sparcsky.bsp.collection;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sparcsky.bsp.TileMaker;
import com.sparcsky.bsp.entity.Room;

import java.util.ArrayList;
import java.util.List;

public class Leaf {

    public static int MIN_LEAF_SIZE = 16;
    public int width;
    public int height;
    Leaf right;
    Leaf left;
    private int x;
    private int y;

    private com.sparcsky.bsp.entity.Room room;

    Leaf(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean split() {
        if (left != null || right != null) return false;

        boolean splitH = false;

        if (height > width && (float) height / width >= 1.25) splitH = true;
        int max = (splitH ? height : width) - MIN_LEAF_SIZE;
        if (max <= MIN_LEAF_SIZE)
            return false;

        int split = MathUtils.random(MIN_LEAF_SIZE, max);

        if (splitH) {
            left = new Leaf(x, y, width, split);
            right = new Leaf(x, y + split, width, height - split);
        } else {
            left = new Leaf(x, y, split, height);
            right = new Leaf(x + split, y, width - split, height);
        }
        return true;
    }

    public void draw(ShapeRenderer shapeDrawer) {
        shapeDrawer.setColor(Color.GREEN);
        shapeDrawer.rect(x, y, width, height);
    }

    public void createRooms(com.sparcsky.bsp.TileMaker tileMaker) {
        if (left != null || right != null) {
            if (left != null) left.createRooms(tileMaker);
            if (right != null) right.createRooms(tileMaker);

            if (left != null && right != null) {
                createHalls(tileMaker);
            }
        } else {
            int randX = MathUtils.random(x + 3, (x + width) - 5);
            int randY = MathUtils.random(y + 3, (y + height) - 5);

            int randWidth = MathUtils.random(5, Math.abs(x + width - randX) + 1);
            int randHeight = MathUtils.random(5, Math.abs(y + height - randY) + 1);

            room = new com.sparcsky.bsp.entity.Room(randX, randY, randWidth, randHeight);
            room.draw(tileMaker);
        }
    }

    private void createHalls(TileMaker tileMaker) {
        com.sparcsky.bsp.entity.Room roomL = left.getRoom();
        com.sparcsky.bsp.entity.Room roomR = right.getRoom();
        if (roomL == null || roomR == null) return;
        TiledMapTileLayer.Cell cell = tileMaker.getCell("room_mid_1");

        roomL.doorX = roomL.x + roomL.width;
        roomL.doorY = MathUtils.random(roomL.y, Math.abs(roomL.y + roomL.height));

        roomR.doorX = roomR.x;
        roomR.doorY = MathUtils.random(roomR.y, Math.abs(roomR.y + roomR.height));

        List<Vector2> points = walk(roomL, roomR);
        for (Vector2 point : points) {
            for (int i = 0; i < points.size(); i++) {
                tileMaker.getLayer(1).setCell((int) point.x, (int) point.y, cell);
            }
        }
    }

    private List<Vector2> walk(com.sparcsky.bsp.entity.Room left, com.sparcsky.bsp.entity.Room right) {
        int dx = right.x - left.x;
        int dy = right.y - left.y;

        float nx = Math.abs(dx);
        float ny = Math.abs(dy);
        int sign_x = dx > 0 ? 1 : -1, sign_y = dy > 0 ? 1 : -1;

        Vector2 p = new Vector2(left.x, left.y);
        List<Vector2> points = new ArrayList<>();
        points.add(p);

        for (int ix = 0, iy = 0; ix < nx || iy < ny; ) {
            if ((1 + ix) / nx < (1 + iy) / ny) {
                p.x += sign_x;
                ix++;
            } else {
                p.y += sign_y;
                iy++;
            }
            points.add(new Vector2(p.x, p.y));
        }
        return points;
    }

    private Room getRoom() {
        if (room != null)
            return room;
        else {
            if (left != null) {
                return left.getRoom();
            }
            if (right != null) {
                return right.getRoom();
            }
        }
        return null;
    }
}
