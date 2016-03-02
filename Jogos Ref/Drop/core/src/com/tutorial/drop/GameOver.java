package com.tutorial.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GameOver implements Screen {
	final Drop game;
	boolean goToMainMenu;
	
	public GameOver(final Drop gam) {
		this.game = gam;
		goToMainMenu = false;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        
        game.batch.begin();
        game.font.draw(game.batch, "Game Over", 320, 330);
        game.font.draw(game.batch, "Your Score: " + Main.points, 300, 220);
        if (Main.points >= Drop.bestScore) {
        	game.font.draw(game.batch, "Congratulations, you are the new High Score!", 50, 170);
        }
        
        Timer.schedule(new Task(){
            @Override
            public void run() {
            	goToMainMenu = true;
            }
        }, 2);
        
        if (goToMainMenu) { 
	        game.setScreen(new MainMenuScreen(game));
	    	dispose();
        }
    	
        game.batch.end();
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
