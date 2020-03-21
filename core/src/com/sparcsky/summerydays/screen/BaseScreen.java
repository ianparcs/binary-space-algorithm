package com.sparcsky.summerydays.screen;

import com.badlogic.gdx.Screen;
import com.sparcsky.summerydays.SummeryDays;

public abstract class BaseScreen implements Screen {

    protected SummeryDays summeryDays;

    public BaseScreen(SummeryDays summeryDays) {
        this.summeryDays = summeryDays;
    }

    public abstract void update(float delta);

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {

    }
}
