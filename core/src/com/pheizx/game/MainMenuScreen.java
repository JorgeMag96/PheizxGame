package com.pheizx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.pheizx.game.config.LoggingUtils.logSystemInfo;
import static com.pheizx.game.config.PheizxGameProperties.CAMERA_VIEWPORT_HEIGHT;
import static com.pheizx.game.config.PheizxGameProperties.CAMERA_VIEWPORT_WIDTH;

public class MainMenuScreen implements Screen {

    final PheizxGame pheizxGame;

    OrthographicCamera camera;

    public MainMenuScreen(final PheizxGame game) {
        this.pheizxGame = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_VIEWPORT_WIDTH, CAMERA_VIEWPORT_HEIGHT);
        logSystemInfo();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        pheizxGame.batch.setProjectionMatrix(camera.combined);

        pheizxGame.batch.begin();
        pheizxGame.font.draw(pheizxGame.batch, "Welcome to Pheizx!!! ", (CAMERA_VIEWPORT_WIDTH / 2) - 100, 150);
        pheizxGame.font.draw(pheizxGame.batch, "Tap anywhere to begin!", (CAMERA_VIEWPORT_WIDTH / 2) - 100, 100);
        pheizxGame.batch.end();

        if (Gdx.input.isTouched()) {
            pheizxGame.setScreen(new GameScreen(pheizxGame));
            dispose();
        }
    }

    @Override
    public void show() {

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
