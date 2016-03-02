package com.tutorial.drop;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Main implements Screen {
	final Drop game;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	public static int points;
	private long dropFrequency;
	private int raindropSpeed;	
	
	public Main(final Drop gam) {
        this.game = gam;
		bucket = new Rectangle();
		raindrops = new Array<Rectangle>();
        
		spawnRaindrop();
		
		Drop.rain.setLooping(true);
	    Drop.rain.play();
	    
	    game.camera.setToOrtho(false, 800, 480);
		
		bucket = new Rectangle();
	    bucket.x = 800 / 2 - 64 / 2;
	    bucket.y = 20;
	    bucket.width = 64;
	    bucket.height = 64;
	    
	    dropFrequency = 1000000000;
	    points = 0;
	    raindropSpeed = 200;
	}
	
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); // cleaned color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Input for mouse
		
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			game.camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		
		// Input for keyboard
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			bucket.x += 200 * Gdx.graphics.getDeltaTime();
		}
		
		if(bucket.x < 0) {
			bucket.x = 0;
		}
		
		if(bucket.x > 800 - 64) {
			bucket.x = 800 - 64;
		}
		
		// Instantiate raindrops 1 per second
		
		if(TimeUtils.nanoTime() - lastDropTime > dropFrequency) spawnRaindrop();
		
		// Make raindrops move

		Iterator<Rectangle> iter = raindrops.iterator();
		
		while(iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= raindropSpeed * Gdx.graphics.getDeltaTime();
			
			// Collision check
			
			if(raindrop.overlaps(bucket)) {
				Drop.drop.play();
				iter.remove();
				points += 1;
				raindropSpeed += 1;
				dropFrequency -= 10000000;
			}
			
			if(raindrop.y + 64 < 0) {
				
		        // Populate best score
		        try {
		        	Drop.db.connect("score");
		        	
		        	Drop.db.execute("INSERT INTO Score(points) VALUES('" + points + "');");
		        	
		            Drop.db.commit();
			        Drop.db.desconnect();
		        }
		        
		        catch(Exception e) {
					System.out.println(e.getClass().getName() + ":" + e.getMessage());
					System.exit(1);
				}
		        //
				
		        if (points >= Drop.bestScore) {
        			Drop.bestScore = points;
        		}
		        
		        Drop.rain.stop();
				iter.remove();
				
				game.setScreen(new GameOver(game));
				dispose();
			}
		}
		
		// Update loop
		
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		game.batch.begin();
		
		game.batch.draw(Drop.bucketImg, bucket.x, bucket.y);
		
		for(Rectangle raindrop: raindrops) {
			game.batch.draw(Drop.dropletImg, raindrop.x, raindrop.y);
		}
		
		if (points > 99) game.font.draw(game.batch, "Score: " + Integer.toString(points), 570, 430);
		else if (points > 9) game.font.draw(game.batch, "Score: " + Integer.toString(points), 600, 430);
		else game.font.draw(game.batch, "Score: " + Integer.toString(points), 630, 430);
		
		game.batch.end();
	}
	
	// Clean the cache
	
	@Override
	public void dispose() {
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
}
