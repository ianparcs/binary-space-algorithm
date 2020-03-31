package com.sparcsky.summerydays.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.sparcsky.summerydays.Main;
import com.sparcsky.summerydays.TileMaker;
import com.sparcsky.summerydays.asset.Asset;
import com.sparcsky.summerydays.collection.BinarySpacePartitioning;


public class LevelScreen extends BaseScreen {

    private TiledMapRenderer tiledMapRenderer;
    private BinarySpacePartitioning bsp;
    private OrthographicCamera camera;
    private ShapeRenderer shape;
    private TileMaker tileMaker;
    private TiledMap map;

    public LevelScreen(Main game) {
        super(game);
        asset.load(Asset.tileSetAtlas);
        asset.loadAll();

        screenColor.set(33f / 255f, 30f / 255f, 39f / 255f, 33f / 255f);
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width / 16f, height / 16f);
        map = new TiledMap();

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1f / 16f);
        tiledMapRenderer.setView(camera);

        tileMaker = new TileMaker(asset, map);
        tileMaker.addLayer(map, (int) width, (int) height);
        tileMaker.addLayer(map, (int) width, (int) height);

        bsp = new BinarySpacePartitioning((int) width / 16, (int) height / 16);
        bsp.generateDungeon(tileMaker);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            tileMaker.removeLayer(map);
            tileMaker.removeLayer(map);

            tileMaker.addLayer(map, (int) width / 16, (int) height / 16);
            tileMaker.addLayer(map, (int) width / 16, (int) height / 16);

            bsp.generateDungeon(tileMaker);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        int[] backgroundLayers = {0};
        int[] foregroundLayers = {1};
        shape.setProjectionMatrix(camera.combined);
        tiledMapRenderer.render(foregroundLayers);
        tiledMapRenderer.render(backgroundLayers);

        boolean draw = !Gdx.input.isKeyPressed(Input.Keys.Q);

        if(draw){
            bsp.draw(shape);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 16f, height / 16f);
    }

    @Override
    public void dispose() {
        asset.dispose();
        map.dispose();
    }
}



