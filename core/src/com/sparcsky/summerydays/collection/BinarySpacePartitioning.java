package com.sparcsky.summerydays.collection;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.sparcsky.summerydays.TileMaker;

import java.util.ArrayList;
import java.util.List;

public class BinarySpacePartitioning {

    private int width;
    private int height;
    private List<Leaf> leaves;

    public BinarySpacePartitioning(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void generateDungeon(TileMaker tileMaker) {
        Leaf root = new Leaf(0, 0, width, height);
        leaves = new ArrayList<>();
        leaves.add(root);

        List<Leaf> temp = new ArrayList<>();
        float chance = MathUtils.random(0.0f, 1.0f);

        boolean isSplit = true;
        while (isSplit) {
            isSplit = false;
            for (Leaf leaf : leaves) {
                if (leaf.width >= Leaf.MIN_LEAF_SIZE || leaf.height >= Leaf.MIN_LEAF_SIZE || chance <= .75f) {
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
