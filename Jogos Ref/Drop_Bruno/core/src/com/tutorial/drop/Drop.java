package com.tutorial.drop;

import java.sql.ResultSet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public BitmapFont font;
    public static Database db;
    public static Integer bestScore;
	public static Music rain;
	public static Sound drop;
	public static Texture dropletImg;
	public static Texture bucketImg;
    
    private static void schema() throws Exception {
    	db.connect("score");
    	db.execute("CREATE TABLE IF NOT EXISTS Score ("
    			+ "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
    			+ "points INTEGER DEFAULT 0"
    			+ ");");
    }
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
		rain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		drop = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		dropletImg = new Texture("droplet.png");
		bucketImg = new Texture("bucket.png");
		font = new BitmapFont(Gdx.files.internal("segoeprint.fnt"));
        
        camera.setToOrtho(false, 800, 480);
        bestScore = 0;
        
        // Creating Database
        try {
	        db = new Database();
	        schema();
	        
        	ResultSet scores = Drop.db.read("SELECT * FROM Score;");
        	int score = 0;
        	
        	while(scores.next()) {
        		score = scores.getInt("points");
        		if (score >= bestScore) {
        			bestScore = score;
        		}
        	}
	        
	        db.commit();
	        db.desconnect();
        }
        
        catch(Exception e) {
			System.out.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(1);
		}
        //
        
        font = new BitmapFont(Gdx.files.internal("segoeprint.fnt"));
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
		drop.dispose();
		rain.dispose();
    }
}