package screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.dennyrapp.game.FlappyGame;
import helper.CollisionBox;
import helper.Item_Status;
import helper.Scoreboard;
import objects.Cloud;
import objects.Items;
import objects.Obstacle;
import objects.Player;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Denny
 *
 */

public class GameScreen implements Screen {
	//Tweak variablen um zentral das Spielerlebnis aendern zu koennen
	//und normale Variaben fuer die Schrift usw.
	private FlappyGame game;
	private OrthographicCamera camera;
	private static int tower_speed = -5;
	private static final int distance = 700;
	private static final int start_x = 1150, start_y=-250;
	private static final int gap = 80;
	private static final double tower_factor = 0.5;
	private static final double dementor_factor = 0.125;
	private static final double items_factor = 0.075;
	private static final double player_factor = 0.125;
	private static final int flap_factor = 4;
	private static float item_timer = 0;
	private static final float item_duration = 5;
	private int score_factor = 1;
	private boolean debug = false;
	private BitmapFont debugBitmapFont;//zum Testen
	private BitmapFont pixelFont;
	private GlyphLayout pixelLayout;
	private Items trollItem, doubleScoreItem, invincibleItem, turboItem;
	private Player player;
	private Scoreboard score;
	private Obstacle last_hit;
	private float speed_timer = 0;
	private final float speed_acceleration_time = 5;
	private Boolean collisions = false;
	private Obstacle[] arr_obst;
	private Items[] arr_it;
	private Cloud[] arr_cloud;
	//----------------------------------
	public GameScreen(FlappyGame game) {
		this.game = game;
		score = new Scoreboard();
		debugBitmapFont = new BitmapFont();
		pixelFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
		//initialisiert alle Objekte
		init();	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
	}
	//Mein Gameloop (eine Update Klasse waere besser gewesen)
	@Override
	public void render(float delta) {
		//Hintergrund setzten
		Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//debug Variable;
		collisions = false;
		//Key Abfrage
		if(player.getStatus() != Item_Status.turbo) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {//check ob Taste gedrueckt
				if(player.getY()<= (710-player.getHeight())) {//check ob max hoehe
					player.fly();//fliegen
				}
			} 
			if(player.getY() > 0){//check ob am Boden
					player.fall(delta);//fallen
			}else {
				gameOver();
			}
		}
		//Zum Testen 
		//ansosnten auskommentieren
		//-----------------------------------------------------------------------------------------------
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {//check ob Taste gedrueckt
			player.setStatus(Item_Status.turbo);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {//check ob Taste gedrueckt
			player.setStatus(Item_Status.invincible);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3)) {//check ob Taste gedrueckt
			player.setStatus(Item_Status.doubleScore);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {//check ob Taste gedrueckt
			player.setStatus(Item_Status.troll);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_5)) {//check ob Taste gedrueckt
			player.setStatus(Item_Status.notActive);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_6)) {//check ob Taste gedrueckt
			debug = !debug;
		}
		//-----------------------------------------------------------------------------------------------
		//falls kein Item aktiv wird der Timer zurueckgesetzt
		//kann z.B. durch eine Kollision passieren wenn man invincible ist
		if(player.getStatus() == Item_Status.notActive) {
			item_timer = 0;
		}
		//checkt ob ein Item aktiv ist und handelt dementsprechend
		switch(player.getStatus()) {
		case turbo:
			if(item_timer == 0) {
				player.setY(720/2);
				player.setVector(0);
				//debug
				//System.out.println(Gdx.graphics.getHeight());
				for(int i = 0; i<arr_obst.length;i++) {
					arr_obst[i].setY((int)(720/2-arr_obst[i].getBottomHeigth())-gap/2);
				}
			}
			translate_objects(5);
			tower_loop_turbo();
			item_timer += delta;
			break;
		case invincible:
			if(item_timer == 0) {
				player.getSmall();
			}
			tower_loop();
			translate_objects(1);
			update_check_collision();
			item_timer += delta;
			break;
		case troll:
			setGapFactor(ThreadLocalRandom.current().nextDouble(0.25,1));
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
			player.getNormal();
			setGap();
			tower_loop();
			item_loop();
			translate_objects(1);
			update_check_collision();
			break;
		}
		//updated das Layout
		pixelLayout = new GlyphLayout(pixelFont,""+score.getScore());
		//zeichnen
		game.batch.begin();
		//-----------------debug zeug------------------
		if(collisions) {
			debugBitmapFont.setColor(Color.RED);
		}else {
			debugBitmapFont.setColor(Color.WHITE);
		}
		//---------------------------------------------
		//zeichnet Wolken
		drawBackground();
		//Zeichnet Hindernisse
		drawObstacles();
		//Zeichnet Items sofern gerade keins aktiv ist
		if(player.getStatus() == Item_Status.notActive) {
			drawItems();
		}else {
			replaceItems();
		}
		//zeichnet score
		pixelFont.draw(game.batch,pixelLayout,640,700);
		//zeichne item status
		pixelLayout = new GlyphLayout(pixelFont,"active item: "+player.getStatus());
		pixelFont.draw(game.batch,pixelLayout,10,50);
		//fps
		pixelLayout = new GlyphLayout(pixelFont,"FPS: "+(int)(1/delta));
		pixelFont.draw(game.batch,pixelLayout,10,700);
		//zeichnet den spieler
		player.draw(game.batch);
		//-------------debug zeug--------------------------------------------------
		if(debug) {
			debugBitmapFont.draw(game.batch, "score: "+score.getScore(), 25, 100); 
			debugBitmapFont.draw(game.batch, "collision "+collisions, 25, 500);
			debugBitmapFont.draw(game.batch,"FPS: "+ (int)(1/delta), 25, 400);
			debugBitmapFont.draw(game.batch, "active Item: "+player.getStatus(), 25, 300);
			debugBitmapFont.draw(game.batch, "timer: "+item_timer, 25, 200);
			debugBitmapFont.draw(game.batch, "speed: "+tower_speed+" timer: "+speed_timer, 25, 600);
		}
		//----------------------------------------------------------------------------
		game.batch.end();
		//timer check
		//alle 5 sekunden wird das Spiel schneller
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
	//wechselt zum GameOverScreen und uebergibgt den Score
	public void gameOver() {
		game.setScreen(new GameOverScreen(game,score));
		//dispose();
	}
	//checkt ob die random platzierten Items mit irgendwas kollidieren
	public void placeItemsCollision(Items it) {
		CollisionBox icb = it.getCb();
		for(int i = 0; i< arr_obst.length;i++) {
			if(arr_it[i] != it) {
				if(arr_it[i].checkCollision(icb)) {
					placeItems(it);
				}
			}
			if(arr_obst[i].checkCollision(icb)) {
				placeItems(it);
			}
		}
	}
	//plaziert die Items random
	public void placeItems(Items i) {
		i.setPos(ThreadLocalRandom.current().nextInt((int)(1280+i.getWidth()), 2560),
				ThreadLocalRandom.current().nextInt((int)(i.getHeight()), (int)(550-i.getHeight())));
		placeItemsCollision(i);
	}
	//checkt ob mit etwas kollidiert wird und handelt dementsprechend
	public void update_check_collision() {
		//mit last_hit wird gecheckt ob das Hinderniss mit dem man Kollidiert immernoch das Gleiche wie beim
		//letzten mal ist
		if(arr_obst.length != arr_it.length) {
			Gdx.app.log("Err", "nicht gleich viele items und tuerme");
		}
		for(int i = 0;i<arr_obst.length;i++) {
			if(arr_obst[i].checkCollision(player.getCb())) {
				if(player.getStatus() == Item_Status.invincible && last_hit == null) {
					last_hit = arr_obst[i];
					player.getNormal();
					player.setStatus(Item_Status.notActive);
				}else if(arr_obst[i] != last_hit) {
					//debug
					//System.out.println("COLLISION");
					collisions = true;
					last_hit = null; //wegen tests
					gameOver();
				}
			}
			if(player.getStatus() == Item_Status.notActive && arr_it[i].checkCollision(player.getCb())) {
				player.setStatus(arr_it[i].getKind());
			}
		}
	}
	//bewegt alle Objekte nach Links ausser dem Spieler
	public void translate_objects(int translate_factor) {
		if(arr_obst.length != arr_it.length) {
			Gdx.app.log("Err", "nicht gleich viele items und tuerme");
		}
		for(int i = 0;i<arr_obst.length;i++) {
			arr_obst[i].translateX(tower_speed* translate_factor);
			arr_it[i].translateX(tower_speed*translate_factor);
		}
	}
	//setzt die Hindernisse neu sobald diese auserhalb des Sichtbereichs sind
	public void tower_loop() {
		for(int i = 0;i<arr_obst.length;i++) {
			if(i == 0) {
				if(arr_obst[i].getX() < 0-arr_obst[i].getWidth()) {
					arr_obst[i].setPos((int)(arr_obst[arr_obst.length-1].getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-((int)player.getHeight() + 20)));
					score.incrementScore(score_factor);
				}
			}else {
				if(arr_obst[i].getX() < 0-arr_obst[i].getWidth()) {
					arr_obst[i].setPos((int)(arr_obst[i-1].getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-((int)player.getHeight() + 20)));
					score.incrementScore(score_factor);
				}
			}
		}
	}
	//andere towerloop f�r den Fall, dass das Turbo Item aktiv ist
	public void tower_loop_turbo() {
		for(int i = 0;i<arr_obst.length;i++) {
			if(i == 0) {
				if(arr_obst[i].getX() < 0-arr_obst[i].getWidth()) {
					arr_obst[i].setPos((int)(arr_obst[arr_obst.length-1].getX()+distance), (int)(720/2-arr_obst[i].getBottomHeigth())-gap/2);
					score.incrementScore(score_factor);
				}
			}else {
				if(arr_obst[i].getX() < 0-arr_obst[i].getWidth()) {
					arr_obst[i].setPos((int)(arr_obst[i-1].getX()+distance), (int)(720/2-arr_obst[i].getBottomHeigth())-gap/2);
					score.incrementScore(score_factor);
				}
			}
		}
	}
	//setzt die Items in einem Loop
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
	//initialisiert alle Objekte
	private void init() {
		player = new Player(game.harry,player_factor,flap_factor);
		player.setY(720/2);
		tower_speed = -5;
		item_timer = 0;
		speed_timer = 0;
		init_obstacle();
		init_items();
		init_background();
	}
	private void init_background(){
		arr_cloud = new Cloud[4];
		for(int i = 0; i<arr_cloud.length;i++) {
			arr_cloud[i] = new Cloud(game.cloud);
		}
	}
	private void init_obstacle() {
		arr_obst = new Obstacle[4];
		for(int i = 0 ; i < arr_obst.length;i++) {
			arr_obst[i] = new Obstacle(game.texture_arr_tower[i],tower_factor,game.dementor,dementor_factor,(int)(gap+player.getHeight()));
			if(i == 0) {
				arr_obst[i].setPos(start_x, start_y);
			}else {
				arr_obst[i].setPos((int)(arr_obst[i-1].getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)player.getHeight() + 1));
			}
		}
	}
	private void init_items() {
		arr_it = new Items[4];
		doubleScoreItem = new Items(game.item_blau,items_factor,Item_Status.doubleScore);
		arr_it[0] = doubleScoreItem;
		trollItem = new Items(game.item_gruen,items_factor,Item_Status.troll);
		arr_it[1] = trollItem;
		invincibleItem = new Items(game.item_rot,items_factor,Item_Status.invincible);
		arr_it[2] = invincibleItem;
		turboItem = new Items(game.item_silber,items_factor,Item_Status.turbo);
		arr_it[3] = turboItem;
		for(int i = 0;i<arr_it.length;i++) {
			placeItems(arr_it[i]);
		}
	}
	private void drawObstacles() {
		for(int i = 0;i<arr_obst.length;i++) {
			arr_obst[i].draw(game.batch);
		}
	}
	private void drawItems() {
		for(int i = 0;i<arr_it.length;i++) {
			arr_it[i].draw(game.batch);
		}
	}
	private void setGapFactor(double gap_fact) {
		for(int i = 0; i< arr_obst.length;i++) {
			arr_obst[i].setGapFactor(gap_fact);
		}
	}
	private void setGap() {
		for(int i = 0; i< arr_obst.length;i++) {
			arr_obst[i].setGap((int)(gap+player.getHeight()));
		}
	}
	private void drawBackground() {
		for(int i = 0; i< arr_cloud.length;i++) {
			arr_cloud[i].loop(tower_speed);
			arr_cloud[i].draw(game.batch);
		}
	}
	private void replaceItems() {
		for(int i = 0;i<arr_it.length;i++) {
			placeItems(arr_it[i]);
		}
	}
}
