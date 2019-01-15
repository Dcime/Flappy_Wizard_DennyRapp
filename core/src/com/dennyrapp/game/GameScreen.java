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
	
	Sprite sprite,tower_sprite1,tower_sprite2,tower_sprite3,tower_sprite4,dementor_sprite1;
	
	static int tower_speed = -5;
	static int distance = 700;
	static int start_x = 1150, start_y=-250;
	static int gap = 20;
	static double tower_factor = 0.5;
	static double dementor_factor = 0.125;
	
	private int score;
	private String score_string;
	BitmapFont yourBitmapFontName;
	
	CollisionBox player,tower_cb1,tower_cb2,tower_cb3,tower_cb4;
	Obstacle tower1,tower2,tower3;
	public GameScreen(FlappyGame game) {
		this.game = game;
		create_scoreboard();
		
		
		sprite = new Sprite(game.harry);
		int harry_width = (int)(game.harry.getWidth() * 0.125);
		int harry_height = (int)(game.harry.getHeight() * 0.125);
		sprite.setSize(harry_width, harry_height);
		player = new CollisionBox(sprite.getX(),sprite.getY(),harry_width,harry_height);
		
		tower_sprite1 = new Sprite(game.turm_gryffindor);
		//int penis = Gdx.graphics.getWidth();
		int tower_width1 = (int)(game.turm_gryffindor.getWidth() * 0.5);
		int tower_height1 = (int)(game.turm_gryffindor.getHeight() * 0.5);
		tower_sprite1.setSize(tower_width1, tower_height1);
		tower_sprite1.setPosition(start_x, start_y);
		tower_cb1 = new CollisionBox(tower_sprite1.getX(),tower_sprite1.getY(),tower_width1,tower_height1);

		
		tower_sprite2 = new Sprite(game.turm_huffelpuff);
		tower_sprite2.setSize(tower_width1,tower_height1);
		tower_sprite2.setPosition(tower_sprite1.getX()+distance, ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		tower_cb2 = new CollisionBox(tower_sprite2.getX(),tower_sprite2.getY(),tower_width1,tower_height1);
		
		tower_sprite3 = new Sprite(game.turm_ravenclaw);
		tower_sprite3.setSize(tower_width1,tower_height1);
		tower_sprite3.setPosition(tower_sprite2.getX()+distance, ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		tower_cb3 = new CollisionBox(tower_sprite3.getX(),tower_sprite3.getY(),tower_width1,tower_height1);
		
		tower_sprite4 = new Sprite(game.turm_slytherin);
		tower_sprite4.setSize(tower_width1,tower_height1);
		tower_sprite4.setPosition(tower_sprite3.getX()+distance, ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		tower_cb4 = new CollisionBox(tower_sprite4.getX(),tower_sprite4.getY(),tower_width1,tower_height1);
		
		dementor_sprite1= new Sprite(game.dementor);
		int dementor_width = (int)(game.dementor.getWidth() * 0.125);
		int dementor_height = (int)(game.dementor.getHeight() * 0.125);
		dementor_sprite1.setSize(dementor_width, dementor_height);
		dementor_sprite1.setPosition(tower_sprite1.getX(), tower_sprite1.getY()+tower_sprite1.getHeight()+sprite.getHeight()+2);
		
		tower1 = new Obstacle(game.turm_gryffindor,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower1.setPos(start_x-100, start_y-50);
		
		
		camera = new OrthographicCamera();
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
		
		if(tower_sprite1.getX() < -10) {
			tower_sprite1.setPosition(tower_sprite4.getX()+distance, ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
			dementor_sprite1.setPosition(tower_sprite1.getX(), tower_sprite1.getY()+tower_sprite1.getHeight()+sprite.getHeight()+10);
			count_score_up();
		}
		if(tower_sprite2.getX() < -10) {
			tower_sprite2.setPosition(tower_sprite1.getX()+distance, ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
			count_score_up();
		}
		if(tower_sprite3.getX() < -10) {
			tower_sprite3.setPosition(tower_sprite2.getX()+distance, ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
			count_score_up();
		}
		if(tower_sprite4.getX() < -10) {
			tower_sprite4.setPosition(tower_sprite3.getX()+distance, ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight()  + 1)); //+1 fuer inklusiv
			count_score_up();
		}
		tower_sprite1.translateX(tower_speed);
		tower_sprite2.translateX(tower_speed);
		tower_sprite3.translateX(tower_speed);
		tower_sprite4.translateX(tower_speed);
		dementor_sprite1.translateX(tower_speed);
		tower1.translateX(tower_speed+2);
		/*
		int harry_width = (int)(game.harry.getWidth() * 0.125);
		int harry_height = (int)(game.harry.getHeight() * 0.125);
		*/
		game.batch.begin();
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		yourBitmapFontName.draw(game.batch, score_string, 25, 100); 
		
		sprite.draw(game.batch);
		tower_sprite1.draw(game.batch);
		tower_sprite2.draw(game.batch);
		tower_sprite3.draw(game.batch);
		tower_sprite4.draw(game.batch);
		dementor_sprite1.draw(game.batch);
		
		tower1.draw(game.batch);
		/*
		game.batch.draw(game.harry
				, camera.viewportWidth / 2 - harry_width / 2
				, camera.viewportHeight / 2 - harry_height / 2
				, harry_width
				, harry_height
			);
			*/
		tower_cb1.setPos(tower_sprite1.getX(), tower_sprite1.getY());
		tower_cb2.setPos(tower_sprite2.getX(), tower_sprite2.getY());
		tower_cb3.setPos(tower_sprite3.getX(), tower_sprite3.getY());
		tower_cb4.setPos(tower_sprite4.getX(), tower_sprite4.getY());
		if(player.checkCollision(tower_cb1)||player.checkCollision(tower_cb2)||player.checkCollision(tower_cb3)||player.checkCollision(tower_cb4)) {
			System.out.println("COLLISION");
		}
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
