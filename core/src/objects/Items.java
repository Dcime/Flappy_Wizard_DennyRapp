package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helper.CollisionBox;
import helper.Item_Status;
/*
 * Klasse die alle Item funktionen enthaelt
 */
public class Items {
	//jedes Item hat ein Sprite und eine Kollisions Box
	private Sprite item_sprite;
	private CollisionBox cb;
	private Item_Status is;
	//noramler Konstruktor
	public Items(Texture texture, double factor, Item_Status is) {
		item_sprite= new Sprite(texture);
		item_sprite.setSize((int)(texture.getWidth()*factor), (int)(texture.getHeight()*factor));
		cb = new CollisionBox(item_sprite.getX(),item_sprite.getY(),(int)(item_sprite.getWidth()),(int)(item_sprite.getHeight()));
		this.is = is;
	}
	//setzt die Position des Items
	public void setPos(int x,int y) {
		item_sprite.setPosition(x, y);
		cb.setPos(x, y);
	}
	//bewegt das Item auf der x-Achse
	public void translateX(int x) {
		item_sprite.translateX(x);
		cb.setPos((int)(item_sprite.getX()), (int)(item_sprite.getY()));
	}
	//zeichnet das Item
	public void draw(Batch batch) {
		item_sprite.draw(batch);
	}
	//checkt ob eine Kollision stattgefunden hat
	public boolean checkCollision(CollisionBox cb) {
		return this.cb.checkCollision(cb);
	}
	//gibt die Breite zurueck
	public float getWidth() {
		return item_sprite.getWidth();
	}
	//gibt die Hoehe zurueck
	public float getHeight() {
		return item_sprite.getHeight();
	}
	//gibt die x-Pos zurueck
	public float getX() {
		return item_sprite.getX();
	}
	//gibt die Kollisionsbox zurueck
	public CollisionBox getCb() {
		return cb;
	}
	//gibt zurueck was fuer ein Item das Objekt ist
	public Item_Status getKind() {
		return is;
	}
}
