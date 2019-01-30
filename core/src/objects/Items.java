package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helper.CollisionBox;

public class Items {
	Sprite item_sprite;
	CollisionBox cb;
	
	public Items(Texture texture, double factor) {
		item_sprite= new Sprite(texture);
		item_sprite.setSize((int)(texture.getWidth()*factor), (int)(texture.getHeight()*factor));
		cb = new CollisionBox(item_sprite.getX(),item_sprite.getY(),(int)(item_sprite.getWidth()),(int)(item_sprite.getHeight()));
	}
	public void effect() {
		
	}
	public void setPos(int x,int y) {
		item_sprite.setPosition(x, y);
		cb.setPos(x, y);
	}
	public void translateX(int x) {
		item_sprite.translateX(x);
		cb.setPos((int)(item_sprite.getX()), (int)(item_sprite.getY()));
	}
	public void draw(Batch batch) {
		item_sprite.draw(batch);
	}
	public boolean collision(CollisionBox cb) {
		return this.cb.checkCollision(cb);
	}
	public float getWidth() {
		return item_sprite.getWidth();
	}
	public float getHeight() {
		return item_sprite.getHeight();
	}
	public float getX() {
		return item_sprite.getX();
	}
	public CollisionBox getCb() {
		return cb;
	}

}
