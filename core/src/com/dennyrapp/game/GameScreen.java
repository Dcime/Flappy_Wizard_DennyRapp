package com.dennyrapp.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import java.util.concurrent.ThreadLocalRandom;


public class GameScreen implements Screen {

	final FlappyGame game;
	
	OrthographicCamera camera;
	
	Sprite sprite;
	
	static int tower_speed = -5;//-5
	static int distance = 700;
	static int start_x = 1150, start_y=-250;
	static int gap = 30;
	static double tower_factor = 0.5;
	static double dementor_factor = 0.125;
	
	//test________________________________________________
	private int collisions = 0;
	Sprite tests;
	
	//_____________________________________________________
	
	private int score;
	private String score_string;
	BitmapFont yourBitmapFontName;
	
	CollisionBox player;
	Obstacle tower1,tower2,tower3,tower4;
	public GameScreen(FlappyGame game) {
		this.game = game;
		create_scoreboard();
		
		
		sprite = new Sprite(game.harry);
		int harry_width = (int)(game.harry.getWidth() * 0.125);
		int harry_height = (int)(game.harry.getHeight() * 0.125);
		sprite.setSize(harry_width, harry_height);
		player = new CollisionBox(sprite.getX(),sprite.getY(),harry_width,harry_height);

		tower1 = new Obstacle(game.turm_gryffindor,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower1.setPos(start_x, start_y);
		tower2 = new Obstacle(game.turm_huffelpuff,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower2.setPos((int)(tower1.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		tower3 = new Obstacle(game.turm_ravenclaw,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower3.setPos((int)(tower2.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		tower4 = new Obstacle(game.turm_slytherin,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower4.setPos((int)(tower3.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		camera = new OrthographicCamera();
		
		tests = new Sprite(game.item_blau);
		tests.setSize((int)(game.item_blau.getWidth() * 0.075), (int)(game.item_blau.getHeight()*0.075));
		
		
		camera.setToOrtho(false, 1280, 720);
	}	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//System.out.println(Gdx.graphics.getWidth());
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if(sprite.getY()<= 550) {
				sprite.translateY(5);
			}
		}else {
			if(sprite.getY() > 0){
				sprite.translateY(-2);
			}
			
			
		}
		
		player.setPos(sprite.getX(),sprite.getY());
		//int randomNum = rand.nextInt((max - min) + 1) + min; max ist 0 min ist -600
		
		if(tower1.getX() < -10) {
			tower1.setPos((int)(tower4.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
			count_score_up();
		}
		if(tower2.getX() < -10) {
			tower2.setPos((int)(tower1.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
			count_score_up();
		}
		if(tower3.getX() < -10) {
			tower3.setPos((int)(tower2.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
			count_score_up();
		}
		if(tower4.getX() < -10) {
			tower4.setPos((int)(tower3.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight()  + 1)); //+1 fuer inklusiv
			count_score_up();
		}

		tower1.translateX(tower_speed);
		tower2.translateX(tower_speed);
		tower3.translateX(tower_speed);
		tower4.translateX(tower_speed);
		/*
		int harry_width = (int)(game.harry.getWidth() * 0.125);
		int harry_height = (int)(game.harry.getHeight() * 0.125);
		*/
		game.batch.begin();
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		yourBitmapFontName.draw(game.batch, score_string, 25, 100); 
		
		sprite.draw(game.batch);

		
		tower1.draw(game.batch);
		tower2.draw(game.batch);
		tower3.draw(game.batch);
		tower4.draw(game.batch);
		
		tests.setPosition(tower1.cb_bottom.x, tower1.cb_bottom.y);
		tests.draw(game.batch);
		tests.setPosition(tower1.cb_bottom.x + tower1.cb_bottom.width, tower1.cb_bottom.y);
		tests.draw(game.batch);
		tests.setPosition(tower1.cb_bottom.x + tower1.cb_bottom.width, tower1.cb_bottom.y+tower1.cb_bottom.height);
		tests.draw(game.batch);
		tests.setPosition(tower1.cb_bottom.x, tower1.cb_bottom.y+tower1.cb_bottom.height);
		tests.draw(game.batch);

		if(tower1.checkCollision(player)||tower2.checkCollision(player)||tower3.checkCollision(player)||tower4.checkCollision(player)) {
			System.out.println("COLLISION");
			collisions ++;
			//String feck = "collision "+collisions;
		}
		yourBitmapFontName.draw(game.batch, "collision "+collisions, 25, 500); 
		//System.out.println(tower_sprite4.getX());
		game.batch.end();
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	public void create_scoreboard(){
		score = 0;
	    score_string = "score: 0";
	    yourBitmapFontName = new BitmapFont();

	}   
	public void count_score_up() {
		score++;
		score_string = "score: " + score;
	}    
}
