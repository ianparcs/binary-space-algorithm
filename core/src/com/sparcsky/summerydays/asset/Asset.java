package com.sparcsky.summerydays.asset;


import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Asset {

    public static final AssetDescriptor<BitmapFont> fontBit = new AssetDescriptor<>("font/BIT.fnt", BitmapFont.class);
    public static final AssetDescriptor<Texture> loadDiamond = new AssetDescriptor<>("image/load_diamond.png", Texture.class);
    public static final AssetDescriptor<Texture> libgdxLogo = new AssetDescriptor<>("image/libgdx_logo.png", Texture.class);

    private AssetManager manager;

    public Asset() {
        manager = new AssetManager();
    }

    public void load(AssetDescriptor assetDescriptor) {
        manager.load(assetDescriptor);
    }

    public void loadAll() {
        manager.finishLoading();
    }

    public boolean isLoadFinish() {
        return manager.update();
    }

    public float getProgress() {
        return manager.getProgress();
    }

    public <T> T get(AssetDescriptor<T> assetDescriptor) {
        return manager.get(assetDescriptor);
    }

    public void dispose() {
        manager.dispose();
    }
}
