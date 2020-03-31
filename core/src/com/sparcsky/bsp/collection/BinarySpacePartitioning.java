package com.sparcsky.bsp.collection;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.sparcsky.bsp.TileMaker;

import java.util.ArrayList;
import java.util.List;

public class BinarySpacePartitioning {

    private int width;
    private int height;
    private List<com.sparcsky.bsp.collection.Leaf> leaves;

    public BinarySpacePartitioning(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void generateDungeon(TileMaker tileMaker) {
        com.sparcsky.bsp.collection.Leaf root = new com.sparcsky.bsp.collection.Leaf(0, 0, width, height);
        leaves = new ArrayList<>();
        leaves.add(root);

        List<com.sparcsky.bsp.collection.Leaf> temp = new ArrayList<>();
        float chance = MathUtils.random(0.0f, 1.0f);

        boolean isSplit = true;
        while (isSplit) {
            isSplit = false;
            for (com.sparcsky.bsp.collection.Leaf leaf : leaves) {
                if (leaf.width >= com.sparcsky.bsp.collection.Leaf.MIN_LEAF_SIZE || leaf.height >= com.sparcsky.bsp.collection.Leaf.MIN_LEAF_SIZE || chance <= .75f) {
                    if (leaf.split()) {
                        temp.add(leaf.right);
                        temp.add(leaf.left);
                        isSplit = true;
                    }
                }
            }
            leaves.addAll(temp);
        }
        root.createRooms(tileMaker);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.begin();
        for (Leaf leaf : leaves) {
            leaf.draw(renderer);
        }
        renderer.end();
    }


}
