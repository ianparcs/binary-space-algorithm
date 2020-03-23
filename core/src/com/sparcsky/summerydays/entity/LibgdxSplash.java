package com.sparcsky.summerydays.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.sparcsky.summerydays.asset.Asset;

import java.util.ArrayList;
import java.util.List;

public class LibgdxSplash extends Entity {

    private List<Actor> flash;
    private Actor bg;

    public LibgdxSplash(Asset asset) {
        Texture logo = asset.get(Asset.libgdxLogo);
        logo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        int wWidth = Gdx.graphics.getWidth();
        int wHeight = Gdx.graphics.getHeight();
        this.x = (wWidth / 2f) - (wWidth / 4f) / 2f;
        this.y = (wHeight / 2f) + (wHeight / 12f) / 2f;

        flash = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Actor actor = new Image(logo);
            actor.setSize(wWidth / 4f,wHeight / 12f);
            actor.setX(-actor.getWidth() - (i * actor.getWidth()));
            actor.setY(y);

            AlphaAction logoFadeOut = Actions.fadeOut(4.5f);
            MoveToAction moveAction = createMoveAction();

            if (i != 0) {
                actor.addAction(Actions.parallel(moveAction, logoFadeOut));
            } else {
                actor.addAction(Actions.sequence(moveAction, logoFadeOut));
            }
            flash.add(actor);
        }
        bg = createBackground(wWidth, wHeight);
    }

    private MoveToAction createMoveAction() {
        MoveToAction moveAction = Actions.action(MoveToAction.class);
        moveAction.setInterpolation(Interpolation.exp5);
        moveAction.setPosition(x, y);
        moveAction.setDuration(4);
        return moveAction;
    }

    private Actor createBackground(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.drawRectangle(width, height, width, height);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        Texture txBackground = new Texture(pixmap);
        pixmap.dispose();

        AlphaAction bgFadeOut = Actions.fadeOut(4.5f);

        Actor actor = new Image(txBackground);
        actor.addAction(Actions.sequence(bgFadeOut, Actions.color(Color.BLACK, 4.5f)));
        return actor;
    }
/*
    public void actionComplete(RunnableAction runnableAction) {
        SequenceAction sequenceAction = (SequenceAction) actor.getActions().get(actor.getActions().size - 1);
        sequenceAction.addAction(runnableAction);
    }*/

    public void addToStage(Stage stage) {
        stage.addActor(bg);
        for (Actor actor : flash) {
            stage.addActor(actor);
        }
    }

}
