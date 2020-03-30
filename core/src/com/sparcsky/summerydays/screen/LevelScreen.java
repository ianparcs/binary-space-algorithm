package com.sparcsky.summerydays.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.sparcsky.summerydays.SummeryDays;
import com.sparcsky.summerydays.TileMaker;
import com.sparcsky.summerydays.asset.Asset;
import com.sparcsky.summerydays.collection.Leaf;

import java.util.ArrayList;
import java.util.List;


public class LevelScreen extends BaseScreen {

    private TiledMapRenderer tiledMapRenderer;
    private TileMaker tileMaker;
    private TiledMap map;

    public LevelScreen(SummeryDays game) {
        super(game);
        asset.load(Asset.tileSetAtlas);
        asset.loadAll();

        screenColor.set(33f / 255f, 30f / 255f, 39f / 255f, 33f / 255f);
    }

    @Override
    public void show() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        tileMaker = new TileMaker(asset);
        map = new TiledMap();

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1);
        tiledMapRenderer.setView(camera);

        generateDungeon();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            generateDungeon();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        tiledMapRenderer.render();
    }

    private void generateDungeon() {
        removeMapLayer();

        TiledMapTileLayer layer = new TiledMapTileLayer((int) width, (int) height, 1, 1);
        MapLayers mapLayers = map.getLayers();
        mapLayers.add(layer);

        Leaf root = new Leaf(0, 0, (int) width, (int) height);
        List<Leaf> leaves = new ArrayList<>();
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
        root.createRooms(layer, tileMaker);
    }

    private void removeMapLayer() {
        for (MapLayer layer : map.getLayers()) {
            map.getLayers().remove(layer);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        asset.dispose();
        map.dispose();
    }
}



