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

/**
 * 
 * @author Denny
 *
 */

public class GameScreen implements Screen {

	//Klassenvariablen_____________________
	final FlappyGame game;
	OrthographicCamera camera;
	Sprite sprite;
	static int tower_speed = -5;
	static int distance = 700;
	static int start_x = 1150, start_y=-250;
	static int gap = 30;
	static double tower_factor = 0.5;
	static double dementor_factor = 0.125;
	private int score;
	private String score_string;
	BitmapFont yourBitmapFontName;
	CollisionBox player;
	Obstacle tower1,tower2,tower3,tower4;
	Troll trollItem;
	DoubleScore doubleScoreItem;
	Invnincible invincibleItem;
	//Ende Klassenvariablen_________________
	//--------------------------------------
	//test__________________________________
	private int collisions = 0;
	//Ende Test_____________________________
	//--------------------------------------
	//Konstruktor___________________________
	public GameScreen(FlappyGame game) {
		this.game = game;
		//Scoreboard initialisierung_____________________________________________________
		create_scoreboard();
		//Ende Scoreboard________________________________________________________________
		//-------------------------------------------------------------------------------
		//Erstellen des Spielers_________________________________________________________
		sprite = new Sprite(game.harry);
		int harry_width = (int)(game.harry.getWidth() * 0.125);
		int harry_height = (int)(game.harry.getHeight() * 0.125);
		sprite.setSize(harry_width, harry_height);
		player = new CollisionBox(sprite.getX(),sprite.getY(),harry_width,harry_height);
		//Ende der Spielererstellung_____________________________________________________
		//-------------------------------------------------------------------------------
		//Erstellen der Obstacles________________________________________________________
		tower1 = new Obstacle(game.turm_gryffindor,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower1.setPos(start_x, start_y);
		tower2 = new Obstacle(game.turm_huffelpuff,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower2.setPos((int)(tower1.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		tower3 = new Obstacle(game.turm_ravenclaw,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower3.setPos((int)(tower2.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		tower4 = new Obstacle(game.turm_slytherin,tower_factor,game.dementor,dementor_factor,(int)(gap+sprite.getHeight()));
		tower4.setPos((int)(tower3.getX()+distance), ThreadLocalRandom.current().nextInt(-600, -20-(int)sprite.getHeight() + 1));
		//Ende der Obstacles generierung___________________________________________________
		//---------------------------------------------------------------------------------
		
		camera = new OrthographicCamera();

		camera.setToOrtho(false, 1280, 720);
	}	
	//Ende Konstruktor_______________________________________________________________________
	//---------------------------------------------------------------------------------------
	//Rendern________________________________________________________________________________
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Spieler Bewegung_____________________________
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {//check ob Taste gedrueckt
			if(sprite.getY()<= 550) {//check ob max hoehe
				sprite.translateY(5);//fliegen
			}
		}else {
			if(sprite.getY() > 0){//check ob am Boden
				sprite.translateY(-2);//fallen
			}
		}
		//Ende Spieler Bewegung_________________________
		//----------------------------------------------
		//Nach der Bewegung wird der Spieler gesetzt____
		player.setPos(sprite.getX(),sprite.getY());
		//______________________________________________
		//----------------------------------------------
		//Tower Loop____________________________________
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
		//Ende Towerloop________________________________
		//----------------------------------------------
		//Tower Bewegung________________________________
		tower1.translateX(tower_speed);
		tower2.translateX(tower_speed);
		tower3.translateX(tower_speed);
		tower4.translateX(tower_speed);
		//Ende Tower Bewegung___________________________
		//----------------------------------------------
		//Kollisionscheck_______________________________
		if(tower1.checkCollision(player)||tower2.checkCollision(player)||tower3.checkCollision(player)||tower4.checkCollision(player)) {
			System.out.println("COLLISION");
			collisions ++;
			//gameOver();
		}
		//Ende Kollisionscheck__________________________
		//----------------------------------------------
		//Beginn der Zeichnung__________________________
		game.batch.begin();
		//Zeichnen des Scores___________________________
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		yourBitmapFontName.draw(game.batch, score_string, 25, 100); 
		//Ende Zeichnen des Scores______________________
		//----------------------------------------------
		//Test Kollision score!!!!!!!!!!!!!!!!!!!!!!!!!!
		yourBitmapFontName.draw(game.batch, "collision "+collisions, 25, 500);
		//Ende Test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//----------------------------------------------
		//Zeichnen des Spielers und der Obstacles_______
		sprite.draw(game.batch);
		tower1.draw(game.batch);
		tower2.draw(game.batch);
		tower3.draw(game.batch);
		tower4.draw(game.batch);
		//Ende Zeichnen des Spielers und der Obstacles_________________________________________________
		//----------------------------------------------------------------------------------------------

		//------------------------------------------------------------------------
		//Ende des Zeichnens_______________________________________________________
		game.batch.end();
	}
	//Ende Rendern_____________________________________________________________________________
	//-----------------------------------------------------------------------------------------
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
	//das Scoreboard word initialisiert
	public void create_scoreboard(){
		score = 0;
	    score_string = "score: 0";
	    yourBitmapFontName = new BitmapFont();
	}   
	
	//Der Score wird hochgezaehlt
	public void count_score_up() {
		score++;
		score_string = "score: " + score;
	}    
	public void gameOver() {
		game.setScreen(new GameOverScreen(game,String.valueOf(score)));
		dispose();
	}
	public void placeItems() {
		
	}
}
