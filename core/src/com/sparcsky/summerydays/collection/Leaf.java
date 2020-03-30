package com.sparcsky.summerydays.collection;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.sparcsky.summerydays.TileMaker;
import com.sparcsky.summerydays.entity.Room;

public class Leaf {

    public static int MIN_LEAF_SIZE = 320;
    public Leaf right;
    public Leaf left;
    public int width;
    public int height;
    private int x;
    private int y;

    private Room room;

    public Leaf(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean split() {
        if (left != null || right != null) return false;
        boolean splitH = false;

        if (height > width && (float) height / width >= 1.25)
            splitH = true;

        int max = (splitH ? height : width) - MIN_LEAF_SIZE; // determine the maximum height or width
        if (max <= MIN_LEAF_SIZE)
            return false;

        int split = 0;
        do {
            split = MathUtils.random(MIN_LEAF_SIZE, max);
        } while (split % 16 != 0);

        if (splitH) {
            left = new Leaf(x, y, width, split);
            right = new Leaf(x, y + split, width, height - split);
        } else {
            left = new Leaf(x, y, split, height);
            right = new Leaf(x + split, y, width - split, height);
        }
        return true;
    }

    public void createRooms(TiledMapTileLayer layer, TileMaker tileMaker) {
        if (left != null || right != null) {
            if (left != null) {
                left.createRooms(layer, tileMaker);
            }
            if (right != null) {
                right.createRooms(layer, tileMaker);
            }
            if (left != null && right != null) {
                createHalls(tileMaker, layer);
            }
        } else {
            int randX, randY, randWidth, randHeight;
            float randomDiv = 2f;
            do {
                randX = MathUtils.random(x, (x + width));
                randY = MathUtils.random(y, (y + height));
                randWidth = MathUtils.random(0, Math.abs(randX - (width + x)));
                randHeight = MathUtils.random(0, Math.abs(randY - (height + y)));
                float chance = MathUtils.random(0.00f, 1.00f);
                if (chance <= .2f) randomDiv = 3f;
                if (chance <= .1f) randomDiv = 4f;

            } while (randWidth <= MIN_LEAF_SIZE / randomDiv || randHeight <= MIN_LEAF_SIZE / randomDiv ||
                    (!isMultipleBy(randWidth) || !isMultipleBy(randHeight) || !isMultipleBy(randX) || !isMultipleBy(randY)));

            room = new Room(randX, randY, randWidth, randHeight);
            room.draw(layer, tileMaker);
        }
    }

    private boolean isMultipleBy(float num) {
        return num % 16 == 0;
    }

    private void createHalls(TileMaker tileMaker, TiledMapTileLayer layer) {
        Room roomL = left.getRoom();
        Room roomR = right.getRoom();
        if (roomL == null || roomR == null) return;

        float pointX;
        float pointY;

        do {
            pointX = MathUtils.random(roomL.x + roomL.width, roomR.x);
        } while (!isMultipleBy(pointX));
        do {
            pointY = MathUtils.random(roomL.y + roomL.height, roomR.y);
        } while (!isMultipleBy(pointY));

        TiledMapTileLayer.Cell cell = tileMaker.getCell("room_mid_1");

        for (int i = (int) (roomL.x + roomL.width) - 16; i < pointX; i += 16) {
            layer.setCell(i, (int) roomL.doorY, cell);
        }

        int tempX = 0;
        for (int i = (int) roomR.x; i >= pointX; i -= 16) {
            tempX = i;
            layer.setCell(i, (int) roomR.doorY, cell);
        }

        for (int i = (int) roomR.doorY; i <= roomL.doorY; i += 16) {
            layer.setCell(tempX, i, cell);
        }
        for (int i = (int) roomR.doorY; i >= roomL.doorY; i -= 16) {
            layer.setCell(tempX, i, cell);
        }

        //TODO

        for (int i = (int) (roomL.y + roomL.height); i <= pointY + 16; i += 16) {
            layer.setCell((int) roomL.x, i, cell);
        }
        int tempY = 0;
        for (int i = (int) roomR.y; i > pointY; i -= 16) {
            tempY = i;
            layer.setCell((int) roomR.x, i, cell);
        }

        for (int i = (int) roomL.x; i < roomR.x; i += 16) {
            layer.setCell(i, tempY, cell);
        }

        for (int i = (int) roomL.x; i > roomR.x; i -= 16) {
            layer.setCell(i, tempY, cell);
        }

    }

/*    private List<Vector2> createHall(TileMaker left, TiledMapTileLayer right) {
        if (left == null || right == null) return null;
        float dx = right.doorX - left.doorX;
        float dy = right.doorY - left.doorY;
        float nx = Math.abs(dx);
        float ny = Math.abs(dy);

        float signX = dx > 0 ? 1 : -1;
        float signY = dy > 0 ? 1 : -1;

        Vector2 p = new Vector2(left.doorX, left.doorY);
        List<Vector2> points = new ArrayList<>();
        points.add(p);

        for (int ix = 0, iy = 0; ix < nx / 16f || iy < ny / 16f; ) {
            if (0.5 + ix / nx < 0.5 + iy / ny) {
                p.x += 16 * signX;
                ix++;
            } else {
                p.y += 16 * signY;
                iy++;
            }
            points.add(new Vector2(p.x, p.y));
        }

        return points;
    }*/


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
