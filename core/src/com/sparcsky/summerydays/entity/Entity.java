package com.sparcsky.summerydays.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sparcsky.summerydays.asset.Asset;

public abstract class Entity {

    protected float width;
    protected float height;
    protected float x;
    protected float y;

    public abstract void update(float delta);

    public abstract void draw(SpriteBatch batch);

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
