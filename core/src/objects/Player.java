package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import helper.CollisionBox;
import helper.Item_Status;

public class Player extends Actor{
	CollisionBox cb;
	Sprite player_Sprite;
	int flyingSpeed;
	int fallingSpeed = -2;
	Item_Status status;
	public Player(Texture texture,double factor, int flyingSpeed) {
		player_Sprite = new Sprite(texture); 
		player_Sprite.setSize((int)(texture.getWidth()*factor),(int)(texture.getHeight()*factor));
		cb = new CollisionBox(player_Sprite.getX(),player_Sprite.getY(),(int)(player_Sprite.getWidth()),(int)(player_Sprite.getHeight()));
		this.flyingSpeed = flyingSpeed;
		status = status.notActive;
	}
	public void setPos(int x,int y) {
		player_Sprite.setPosition(x, y);
		cb.setPos(x, y);
	}
	public void fly() {
		player_Sprite.translateY(flyingSpeed);
		cb.translateY(flyingSpeed);
	}
	public void fall() {
		player_Sprite.translateY(fallingSpeed);
		cb.translateY(fallingSpeed);
	}
	public boolean checkCollision(CollisionBox cb) {
		return this.cb.checkCollision(cb);
	}
	public float getX() {
		return player_Sprite.getX();
	}
	public float getY() {
		return player_Sprite.getY();
	}
	public void draw(Batch batch) {
		player_Sprite.draw(batch);
	}
	public void setStatus(Item_Status status) {
		this.status = status;
	}
	public float getHeight(){
		return player_Sprite.getHeight();
	}
	public float getWidth() {
		return player_Sprite.getHeight();
	}
	public Item_Status getStatus() {
		return status;
	}
	public void setY(int y) {
		player_Sprite.setY(y);
		cb.setPos(player_Sprite.getX(), y);
	}
	public CollisionBox getCb() {
		return cb;
	}
}
