package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion down, up, right, left, stand;
	float x,y, xv, yv;
	private Sprite sprite;
//	final Rectangle bounds = sprite.getBoundingRectangle();
//	Rectangle screenBounds = new Rectangle(0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
//	float screenLeft = screenBounds.getX();
//	float screenBottom = screenBounds.getY();
//	float screenTop = screenBottom + screenBounds.getHeight();
//	float screenRight = screenLeft + screenBounds.getWidth();
//	float spriteLeft = bounds.getX();
//	float spriteBottom = bounds.getY();
//	float spriteTop = spriteBottom + bounds.getHeight();
//	float spriteRight = spriteLeft + bounds.getWidth();


	static final int WIDTH = 16;
	static final int HEIGHT = 16;

	static final int DRAW_WIDTH = WIDTH*3;
	static final int DRAW_HEIGHT = HEIGHT*3;

	static final float CAMERA_WIDTH = 10f;
	static final float CAMERA_HEIGHT = 7f;

	static float MAX_VELOCITY = 100;
	//static final float MAX_JUMP_VELOCITY = 2000;
	//static final int GRAVITY = -50;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		stand = grid[6][2];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
	}

	@Override
	public void render () {
		move();

		TextureRegion img;
		if (yv > 0) {
			img = up;
		} else if(yv < 0) {
			img = down;
		} else if (yv == 0 && xv > 0) {
			img = right;
		} else if (yv == 0 && xv < 0) {
			img = left;
		} else {
			img = stand;
		}
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.end();
	}

	float decelerate(float velocity) {
		float deceleration = 0.95f; // the closer to 1, the slower the deceleration
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

	void move() {
//		float newX = sprite.getX();
//		float newY = sprite.getY();
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
			if (y > Gdx.graphics.getHeight()) {
				y = 0;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
			if (y < 0) {
				y = Gdx.graphics.getHeight();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			if (x > Gdx.graphics.getWidth()) {
				x = 0;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
			if (x < 0) {
				x = Gdx.graphics.getWidth();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			MAX_VELOCITY = 200;
		} else {
			MAX_VELOCITY = 100;
		}
//		if (spriteRight < screenLeft) {
//			newX = screenRight;
//		} else if (spriteLeft > screenRight){
//			newX = screenLeft;
//		}
//		if (spriteTop < screenBottom) {
//			newY = screenTop;
//		} else if (spriteBottom > screenTop) {
//			newY = screenBottom;
//		}
//		sprite.setPosition(newX, newY);

		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		yv = decelerate(yv);
		xv = decelerate(xv);
	}
}
