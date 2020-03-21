package com.sparcsky.summerydays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.sparcsky.summerydays.screen.LoadScreen;

public class SummeryDays extends Game {

    private LoadScreen loadScreen;
    @Override
    public void create() {
        setScreen(new LoadScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


    }

    @Override
    public void dispose() {

    }
}
