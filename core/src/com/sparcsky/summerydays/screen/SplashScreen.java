package com.sparcsky.summerydays.screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sparcsky.summerydays.SummeryDays;
import com.sparcsky.summerydays.entity.LibgdxSplash;

public class SplashScreen extends BaseScreen {

    private Stage stage;

    public SplashScreen(SummeryDays game) {
        super(game);
        screenColor.set(1f, 1f, 1f, 1f);
    }

    @Override
    public void show() {
    /*    libgdxSplash.actionComplete(new RunnableAction() {
            @Override
            public void run() {
                screenColor.set(0, 0, 0, 0);
            }
        });*/
        stage = new Stage(new FitViewport(width, height));

        LibgdxSplash libgdxSplash = new LibgdxSplash(asset);
        libgdxSplash.addToStage(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
