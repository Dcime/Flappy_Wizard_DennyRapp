package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import helper.CollisionBox;
import helper.Item_Status;
/*
 * Beschreibt die Spielfigur Logik
 */
public class Player extends Actor{
	private CollisionBox cb;		//Kollisionsbox
	private Sprite player_Sprite;	//Aussehen
	private int flyingSpeed;		//wie schnell die Figur nach oben fliegt
	private double fallingSpeed = 1;	//wird spaeter durch gravitation und gewicht ersetzt
	private Item_Status status;		//Status des Spielers (ENUM)
	private final double g = 9.81;	//gravitationskonstante
	private float vector = 0;
	private int normalW,normalH;
	//Konstruktor
	public Player(Texture texture,double factor, int flyingSpeed) {
		player_Sprite = new Sprite(texture); 
		normalW = (int)(texture.getWidth()*factor);
		normalH = (int)(texture.getHeight()*factor);
		player_Sprite.setSize(normalW,normalH);
		cb = new CollisionBox(player_Sprite.getX(),player_Sprite.getY(),(int)(player_Sprite.getWidth()),(int)(player_Sprite.getHeight()));
		this.flyingSpeed = flyingSpeed;
		status = status.notActive;
	}
	//Setzt die Position
	public void setPos(int x,int y) {
		player_Sprite.setPosition(x, y);
		cb.setPos(x, y);
	}
	//Bewegung nach oben
	public void fly() {
		/*
		if(player_Sprite.getY()+flyingSpeed > 550) {
			player_Sprite.setY(550);
		}else {
			player_Sprite.translateY(flyingSpeed);
		}
		cb.setY(player_Sprite.getY());
		fallingSpeed = 1;
		*/
		if(vector < 0) {
			vector = 0;
		}
		if(vector + flyingSpeed <10) {
			vector += flyingSpeed;
		}
		//fallingSpeed = 1;
		move();
	}
	//Fall nach unten
	public void fall(float delta) {
		//v = g*t
		/*
		if(fallingSpeed < 60) {
			fallingSpeed += g* delta;
		}
		player_Sprite.translateY(-(int)fallingSpeed);
		cb.translateY(-(int)fallingSpeed);
		*/
		/*
		if(fallingSpeed < 60) {
			fallingSpeed += g* delta;
		}
		vector -= fallingSpeed;
		*/
		if(vector > -10) {
			vector -= (g*delta);
		}
		move();
	}
	//Kollisionscheck
	public boolean checkCollision(CollisionBox cb) {
		return this.cb.checkCollision(cb);
	}
	//Gibt die x-Pos zurueck
	public float getX() {
		return player_Sprite.getX();
	}
	//Gibt die y-Pos zurueck
	public float getY() {
		return player_Sprite.getY();
	}
	//Zeichnet den Spieler
	public void draw(Batch batch) {
		player_Sprite.draw(batch);
	}
	//setzt den Spieler-Status
	public void setStatus(Item_Status status) {
		this.status = status;
	}
	//Gibt die Hoehe zurueck
	public float getHeight(){
		return normalH;
	}
	//Gibt die Breite zurueck
	public float getWidth() {
		return normalW;
	}
	//Gibt den Status zurueck
	public Item_Status getStatus() {
		return status;
	}
	//Setzt die y-Pos
	public void setY(int y) {
		player_Sprite.setY(y);
		cb.setPos(player_Sprite.getX(), y);
	}
	//Gibt die Kollisions Box zurueck
	public CollisionBox getCb() {
		return cb;
	}
	private void move() {
		if(player_Sprite.getY()+vector > 550) {
			player_Sprite.setY(550);
			cb.setY(550);
			vector = 0;
		}else {
			player_Sprite.translateY((int)vector);
			cb.translateY((int)vector);
		}
		System.out.println(vector);
	}
	public void setVector(float vec) {
		vector = vec;
	}
	public void getSmall() {
		player_Sprite.setSize(player_Sprite.getWidth()/2,player_Sprite.getHeight()/2);
		cb = new CollisionBox(player_Sprite.getX(),player_Sprite.getY(),(int)(player_Sprite.getWidth()),(int)(player_Sprite.getHeight()));
	}
	public void getNormal() {
		player_Sprite.setSize(normalW,normalH);
		cb = new CollisionBox(player_Sprite.getX(),player_Sprite.getY(),(int)(player_Sprite.getWidth()),(int)(player_Sprite.getHeight()));
	}
	
}
