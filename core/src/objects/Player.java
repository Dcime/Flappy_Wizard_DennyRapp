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
	CollisionBox cb;		//Kollisionsbox
	Sprite player_Sprite;	//Aussehen
	int flyingSpeed;		//wie schnell die Figur nach oben fliegt
	int fallingSpeed = -2;	//wird spaeter durch gravitation und gewicht ersetzt
	Item_Status status;		//Status des Spielers (ENUM)
	//Konstruktor
	public Player(Texture texture,double factor, int flyingSpeed) {
		player_Sprite = new Sprite(texture); 
		player_Sprite.setSize((int)(texture.getWidth()*factor),(int)(texture.getHeight()*factor));
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
		player_Sprite.translateY(flyingSpeed);
		cb.translateY(flyingSpeed);
	}
	//Fall nach unten
	public void fall() {
		player_Sprite.translateY(fallingSpeed);
		cb.translateY(fallingSpeed);
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
		return player_Sprite.getHeight();
	}
	//Gibt die Breite zurueck
	public float getWidth() {
		return player_Sprite.getHeight();
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
}
