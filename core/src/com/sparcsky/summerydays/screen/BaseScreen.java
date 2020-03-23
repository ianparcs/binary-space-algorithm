package com.sparcsky.summerydays.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sparcsky.summerydays.SummeryDays;
import com.sparcsky.summerydays.asset.Asset;

public abstract class BaseScreen implements Screen {

    protected ScreenManager screenManager;
    protected SpriteBatch batch;
    protected SummeryDays game;
    protected Asset asset;

    protected Color screenColor;

    protected float width = Gdx.graphics.getWidth();
    protected float height = Gdx.graphics.getHeight();

    public BaseScreen(SummeryDays game) {
        this.screenManager = game.screenManager;
        this.asset = game.asset;
        this.batch = game.render;
        this.game = game;

        screenColor = new Color();
    }

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        clearScreen(screenColor.r, screenColor.g, screenColor.b, 1);
    }

    protected void clearScreen(float r, float g, float b, float a) {
        Gdx.gl.glClearColor(r, g, b, a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

}
