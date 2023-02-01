package com.pheizx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Game extends ApplicationAdapter {

	private OrthographicCamera camera;
	private static final int CAMERA_VIEWPORT_WIDTH = 800;
	private static final int CAMERA_VIEWPORT_HEIGHT = 480;

	private SpriteBatch batch;

	private Texture dropletImage;
	private Sound dropletSound;
	private Array<Rectangle> raindrops;
	private int dropletSpeed = 200;
	private long lastDropTime;

	private Texture bucketImage;
	private Rectangle bucket;
	private int bucketSpeed = 275;
	
	@Override
	public void create () {
		logSystemInfo();

		camera			= new OrthographicCamera();
		batch 			= new SpriteBatch();

		bucketImage 	= new Texture(Gdx.files.internal("sprites/bucket.png"));
		bucket 			= new Rectangle();
		bucket.x 		= CAMERA_VIEWPORT_WIDTH / 2 - 64 / 2;
		bucket.y 		= 20;
		bucket.width 	= 64;
		bucket.height 	= 64;

		dropletImage 	= new Texture(Gdx.files.internal("sprites/droplet.png"));
		dropletSound	= Gdx.audio.newSound(Gdx.files.internal("audio/effects/droplet.wav"));
		raindrops = new Array<>();
		spawnRaindrop();

		camera.setToOrtho(false, CAMERA_VIEWPORT_WIDTH, CAMERA_VIEWPORT_HEIGHT);
	}

	@Override
	public void render () {
		doGraphics();
		handleInput();
		doGameLogic();
	}
	
	@Override
	public void dispose () {
		dropletImage.dispose();
		bucketImage.dispose();
		dropletSound.dispose();
		batch.dispose();
	}

	private void doGraphics() {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle raindrop : raindrops) {
			batch.draw(dropletImage, raindrop.x, raindrop.y);
		}
		batch.end();
	}

	private void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
			bucket.x -= bucketSpeed * Gdx.graphics.getDeltaTime();

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
			bucket.x += bucketSpeed * Gdx.graphics.getDeltaTime();
	}

	private void doGameLogic() {
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > CAMERA_VIEWPORT_WIDTH - 64) bucket.x = CAMERA_VIEWPORT_WIDTH - 64;

		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			raindrop.y -= dropletSpeed * Gdx.graphics.getDeltaTime();

			if(raindrop.y + 64 < 0) {
				iter.remove();
				continue;
			}

			if(raindrop.overlaps(bucket)) {
				dropletSound.play();
				iter.remove();
			}
		}

		if (TimeUtils.timeSinceNanos(lastDropTime) > 2000000000L) spawnRaindrop();
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, CAMERA_VIEWPORT_WIDTH - 64);
		raindrop.y = CAMERA_VIEWPORT_HEIGHT;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	private void logSystemInfo() {
		Gdx.app.log("system_info", "Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
		Gdx.app.log("system_info", "Architecture: " + System.getProperty("os.arch"));
		Gdx.app.log("system_info", "Available processor cores: " + Runtime.getRuntime().availableProcessors());
		Gdx.app.log("system_info", "Free memory: " + String.format("%.3f", (Runtime.getRuntime().freeMemory() / 1000000000f)) + "GB");
		Gdx.app.log("system_info", "Total memory: " + String.format("%.3f", (Runtime.getRuntime().totalMemory() / 1000000000f)) + "GB");
		Gdx.app.log("system_info", "Max memory: " + String.format("%.3f", (Runtime.getRuntime().maxMemory() / 1000000000f)) + "GB");
	}

}
