package screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.dennyrapp.game.FlappyGame;

import helper.Item_Status;
import helper.Scoreboard;
import objects.DoubleScore;
import objects.Invincible;
import objects.Items;
import objects.Obstacle;
import objects.Player;
import objects.Troll;
import objects.Turbo;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Denny
 *
 */

public class GameScreen implements Screen {
	final FlappyGame game;
	OrthographicCamera camera;
	static int tower_speed = -5;
	static int distance = 700;
	static int start_x = 1150, start_y=-250;
	static int gap = 80;
	static double tower_factor = 0.5;
	static double dementor_factor = 0.125;
	static double items_factor = 0.075;
	static double player_factor = 0.125;
	static int flap_factor = 5;
	static float item_timer = 0;
	static float item_duration = 5;
	private int score_factor = 1;
	BitmapFont yourBitmapFontName;
	Obstacle tower1,tower2,tower3,tower4;
	Troll trollItem;
	DoubleScore doubleScoreItem;
	Invincible invincibleItem;
	Turbo turboItem;
	Player player;
	Scoreboard score;
	Object[] arr;
	Obstacle helper;
	private float speed_timer = 0;
	private final float speed_acceleration_time = 5;
	private Boolean collisions = false;
	public GameScreen(FlappyGame game) {
		this.game = game;
		score = new Scoreboard();
		yourBitmapFontName = new BitmapFont();
		player = new Player(game.harry,player_factor,flap_factor);
		tower1 = new Obstacle(game.turm_gryffindor,tower_factor,game.dementor,dementor_factor,(int)(gap+player.getHeight()));
		tower1.setPos(start_x, start_y);
		tower2 = new Obstacle(game.turm_huffelpuff,tower_factor,game.dementor,dementor_factor,(int)(gap+player.getHeight()));
		tower2.setPos((int)(tower1.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)player.getHeight() + 1));
		tower3 = new Obstacle(game.turm_ravenclaw,tower_factor,game.dementor,dementor_factor,(int)(gap+player.getHeight()));
		tower3.setPos((int)(tower2.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)player.getHeight() + 1));
		tower4 = new Obstacle(game.turm_slytherin,tower_factor,game.dementor,dementor_factor,(int)(gap+player.getHeight()));
		tower4.setPos((int)(tower3.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)player.getHeight() + 1));
		trollItem = new Troll(game.item_blau,items_factor);
		doubleScoreItem = new DoubleScore(game.item_gruen,items_factor);
		invincibleItem = new Invincible(game.item_rot,items_factor);
		turboItem = new Turbo(game.item_silber,items_factor);
		placeItems(trollItem);
		placeItems(doubleScoreItem);
		placeItems(invincibleItem);
		placeItems(turboItem);
		arr = new Object[9];//1 Spieler + 4 Tuerme + 4 Items
		arr[0] = player;
		arr[1] = tower1;
		arr[2] = tower2;
		arr[3] = tower3;
		arr[4] = tower4;
		arr[5] = trollItem;
		arr[6] = doubleScoreItem;
		arr[7] = invincibleItem;
		arr[8] = turboItem;	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
	}	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		collisions = false;
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)&& (player.getStatus() != Item_Status.turbo)) {//check ob Taste gedrueckt
			if(player.getY()<= 550) {//check ob max hoehe
				player.fly();//fliegen
			}
		}else if(player.getY() > 0 && player.getStatus() != Item_Status.turbo){//check ob am Boden
				player.fall();//fallen
		}	
		if(player.getStatus() == Item_Status.notActive) {
			item_timer = 0;
		}
		switch(player.getStatus()) {
		case turbo:
			if(item_timer == 0) {
				player.setY(Gdx.graphics.getHeight()/2);
				for(int i = 1; i<arr.length;i++) {
					if(arr[i] instanceof Obstacle) {
						Obstacle obst = (Obstacle) arr[i];
						obst.setY((int)(Gdx.graphics.getHeight()/2-obst.getBottomHeigth())-gap/2);
					}
				}
			}
			translate_objects(5);
			tower_loop_turbo();
			item_timer += delta;
			break;
		case invincible:
			if(item_timer == 0) {
				player.setScale(0.5f,0.5f);
			}
			tower_loop();
			translate_objects(1);
			update_check_collision();
			item_timer += delta;
			break;
		case troll:
			tower_loop();
			translate_objects(1);
			update_check_collision();
			item_timer += delta;
			break;
		case doubleScore:
			score_factor = 2;
			tower_loop();
			translate_objects(1);
			update_check_collision();
			item_timer += delta;
			break;
		default:
			tower_loop();
			item_loop();
			translate_objects(1);
			update_check_collision();
			break;
		}
		
		 
		
		game.batch.begin();
		if(collisions) {
			yourBitmapFontName.setColor(Color.RED);
		}else {
			yourBitmapFontName.setColor(Color.WHITE);
		}
		player.draw(game.batch);
		tower1.draw(game.batch);
		tower2.draw(game.batch);
		tower3.draw(game.batch);
		tower4.draw(game.batch);
		trollItem.draw(game.batch);
		doubleScoreItem.draw(game.batch);
		invincibleItem.draw(game.batch);
		turboItem.draw(game.batch);
		yourBitmapFontName.draw(game.batch, "score: "+score.getScore(), 25, 100); 
		yourBitmapFontName.draw(game.batch, "collision "+collisions, 25, 500);
		yourBitmapFontName.draw(game.batch,"FPS: "+ (int)(1/delta), 25, 400);
		yourBitmapFontName.draw(game.batch, "active Item: "+player.getStatus(), 25, 300);
		yourBitmapFontName.draw(game.batch, "timer "+item_timer, 25, 200);
		yourBitmapFontName.draw(game.batch, "speed "+tower_speed+" timer"+speed_timer, 25, 600);
		game.batch.end();
		
		if(item_timer> item_duration) {
			player.setStatus(Item_Status.notActive);
			item_timer = 0;
			score_factor = 1;
		}
		if(speed_timer > speed_acceleration_time) {
			tower_speed--;
			speed_timer = 0;
		}
		speed_timer += delta;
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
	public void gameOver() {
		game.setScreen(new GameOverScreen(game,score));
		//dispose();
	}
	public void placeItemsCollision(Items i) {
		if(tower1.checkCollision(i.getCb())||tower2.checkCollision(i.getCb())||tower3.checkCollision(i.getCb())||tower4.checkCollision(i.getCb())) {
			placeItems(i);
		}
	}
	public void placeItems(Items i) {
		i.setPos(ThreadLocalRandom.current().nextInt(200, 1150), ThreadLocalRandom.current().nextInt((int)(i.getHeight()), (int)(550-trollItem.getHeight())));
		placeItemsCollision(i);
	}
	public void update_check_collision() {
		for(int i = 1;i<arr.length;i++) {
			if(arr[i] instanceof Obstacle) {
				Obstacle obst = (Obstacle) arr[i];
				if(obst.checkCollision(player.getCb())) {
					if(player.getStatus() == Item_Status.invincible && helper == null) {
						helper = (Obstacle) arr[i];
						player.setStatus(Item_Status.notActive);
					}else {
						if(obst != helper) {
							System.out.println("COLLISION");
							collisions = true;
							helper = null;
							gameOver();
						}
					}
				}
			}
			if(player.getStatus() == Item_Status.notActive) {
				if(arr[i] instanceof Items) {
					Items it = (Items) arr[i];
					if(it.collision(player.getCb())) {
						if(arr[i] instanceof Turbo) {
							player.setStatus(Item_Status.turbo);
						}else if(arr[i] instanceof Troll) {
							player.setStatus(Item_Status.troll);
						}else if(arr[i] instanceof DoubleScore) {
							player.setStatus(Item_Status.doubleScore);
						}else if(arr[i] instanceof Invincible) {
							player.setStatus(Item_Status.invincible);
						}
						placeItems(it);
					}
				}
			}
		}
	}
	public void translate_objects(int translate_facor) {
		for(int i = 1; i<arr.length;i++) {
			if(arr[i] instanceof Obstacle) {
				Obstacle obst = (Obstacle) arr[i];
				obst.translateX(tower_speed * translate_facor);
			}
			if(arr[i] instanceof Items) {
				Items it = (Items) arr[i];
				it.translateX(tower_speed * translate_facor);
			}
		}
	}
	public void tower_loop() {
		if(tower1.getX() < 0-tower1.getWidth()) {
			tower1.setPos((int)(tower4.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)player.getHeight() + 5));
			score.incrementScore(score_factor);
		}
		if(tower2.getX() < 0-tower1.getWidth()) {
			tower2.setPos((int)(tower1.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)player.getHeight() + 5));
			score.incrementScore(score_factor);
		}
		if(tower3.getX() < 0-tower1.getWidth()) {
			tower3.setPos((int)(tower2.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)player.getHeight() + 5));
			score.incrementScore(score_factor);
		}
		if(tower4.getX() < 0-tower1.getWidth()) {
			tower4.setPos((int)(tower3.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)player.getHeight()  + 5)); //+1 fuer inklusiv
			score.incrementScore(score_factor);
		}
	}
	public void tower_loop_turbo() {
		if(tower1.getX() < 0-tower1.getWidth()) {
			tower1.setPos((int)(tower4.getX()+distance), (int)(Gdx.graphics.getHeight()/2-tower1.getBottomHeigth())-gap/2);
			score.incrementScore(1);
		}
		if(tower2.getX() < 0-tower1.getWidth()) {
			tower2.setPos((int)(tower1.getX()+distance), (int)(Gdx.graphics.getHeight()/2-tower2.getBottomHeigth())-gap/2);
			score.incrementScore(1);
		}
		if(tower3.getX() < 0-tower1.getWidth()) {
			tower3.setPos((int)(tower2.getX()+distance), (int)(Gdx.graphics.getHeight()/2-tower3.getBottomHeigth())-gap/2);
			score.incrementScore(1);
		}
		if(tower4.getX() < 0-tower1.getWidth()) {
			tower4.setPos((int)(tower3.getX()+distance), (int)(Gdx.graphics.getHeight()/2-tower4.getBottomHeigth())-gap/2); //+1 fuer inklusiv
			score.incrementScore(1);
		}
	}
	public void item_loop() {
		if(invincibleItem.getX() < (0-invincibleItem.getWidth()-5)) {
			placeItems(invincibleItem);
		}
		if(trollItem.getX() < (0-trollItem.getWidth()-5)) {
			placeItems(trollItem);
		}
		if(turboItem.getX() < (0-turboItem.getWidth()-5)) {
			placeItems(turboItem);
		}
		if(doubleScoreItem.getX() < (0-doubleScoreItem.getWidth()-5)) {
			placeItems(doubleScoreItem);
		}
	}
}
