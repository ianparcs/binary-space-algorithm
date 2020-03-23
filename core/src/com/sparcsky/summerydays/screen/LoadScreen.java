package com.sparcsky.summerydays.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sparcsky.summerydays.SummeryDays;
import com.sparcsky.summerydays.asset.Asset;
import com.sparcsky.summerydays.util.TextureSplitter;

public class LoadScreen extends BaseScreen {

    private Animation<TextureRegion> loadAnim;
    private TextureRegion loadFrame;
    private GlyphLayout layout;
    private BitmapFont font;

    private Viewport viewport;

    private String[] dots = {".", ". .", ". . ."};
    private float stateTime = 0;
    private int dotIndex;
    private float loadX;
    private float loadY;

    public LoadScreen(SummeryDays game) {
        super(game);
        screenColor.set(0.156f, 0.156f, 0.156f, 0.156f);
    }

    @Override
    public void show() {
        viewport = new FitViewport(width, height, new OrthographicCamera());
        font = asset.get(Asset.fontBit);
        layout = new GlyphLayout();

        Texture texture = asset.get(Asset.loadDiamond);
        TextureRegion[] loadFrames = TextureSplitter.split(texture, 2, 2);
        loadAnim = new Animation<>(0.15f, loadFrames);

        setDotsTimer();

        //TODO add asset later here
        asset.load(Asset.libgdxLogo);
    }

    private void setDotsTimer() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dotIndex++;
                if (dotIndex >= dots.length) dotIndex = 0;
            }
        }, 0, 0.15f);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        loadFrame = loadAnim.getKeyFrame(stateTime, true);

        loadX = (width / 2f) - loadFrame.getRegionWidth() / 2f;
        loadY = (height / 2f) + loadFrame.getRegionHeight() / 2f;

        viewport.getCamera().update();

        if (asset.isLoadFinish()) {
            screenManager.setScreen(new SplashScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        String loadResource = "Loading resources";
        layout.setText(font, loadResource);
        float loadResourceX = (width / 2f) - layout.width / 2f;
        float loadResourceY = loadY - layout.height * 2f;

        String loadProgress = (int) asset.getProgress() * 100 + "%";
        layout.setText(font, loadProgress);
        float loadProgressX = (width / 2f) - layout.width / 2f;
        float loadProgressY = loadY - layout.height / 2f;

        String loadFullName = loadResource + dots[dotIndex];

        batch.begin();
        batch.draw(loadFrame, loadX, loadY);
        font.draw(batch, loadProgress, loadProgressX, loadProgressY);
        font.draw(batch, loadFullName, loadResourceX, loadResourceY);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        asset.dispose();
    }
}
