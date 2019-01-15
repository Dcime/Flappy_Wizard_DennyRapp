package com.dennyrapp.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Obstacle {
	/**
	 * 
	 * @author Denny
	 * Klassenvariablen
	 *
	 */
	CollisionBox cb_bottom, cb_top, cb_top2;
	Sprite sprite_bottom, sprite_top,sprite_top2;
	int gap;
	
	
	public Obstacle(Texture texture_bottom, double factor_bottom, Texture texture_top, double factor_top, int gap) {
		sprite_bottom = new Sprite(texture_bottom);
		sprite_bottom.setSize((int)(texture_bottom.getWidth()*factor_bottom),(int)(texture_bottom.getHeight()*factor_bottom));
		cb_bottom = new CollisionBox(sprite_bottom.getX(),sprite_bottom.getY(),(int)sprite_bottom.getWidth(),(int)sprite_bottom.getHeight());
		
		sprite_top = new Sprite(texture_top);
		sprite_top.setSize((int)(texture_top.getWidth()*factor_top),(int)(texture_top.getHeight()*factor_top));
		cb_top = new CollisionBox(sprite_top.getX(),sprite_top.getY(),(int)sprite_top.getWidth(),(int)sprite_top.getHeight());
		
		sprite_top2 = new Sprite(texture_top);
		sprite_top2.setSize((int)(texture_top.getWidth()*factor_top),(int)(texture_top.getHeight()*factor_top));
		cb_top2 = new CollisionBox(sprite_top2.getX(),sprite_top2.getY(),(int)sprite_top2.getWidth(),(int)sprite_top2.getHeight());
		
		this.gap = gap;
	}
	
	public void setPos(int x,int y) {
		sprite_bottom.setPosition(x, y);
		setRelativePos();
		updateCollisionBox();
	}
	
	private void setRelativePos() {
		sprite_top.setPosition(sprite_bottom.getX()-sprite_bottom.getWidth()/2, sprite_bottom.getY()+gap+sprite_bottom.getHeight());
		sprite_top2.setPosition(sprite_bottom.getX()-sprite_bottom.getWidth()/2, sprite_top.getY()+sprite_top.getHeight());
	}
	
	public void translateX(int x) {
		sprite_bottom.translateX(x);
		sprite_top.translateX(x);
		sprite_top2.translateX(x);
		updateCollisionBox();
	}
	
	private void updateCollisionBox() {
		cb_bottom.setPos(sprite_bottom.getX(), sprite_bottom.getY());
		cb_top.setPos(sprite_bottom.getX(), sprite_top.getY());//bottom x wegen verschiebung
		cb_top2.setPos(sprite_bottom.getX(), sprite_top2.getY());
	}
	
	public boolean checkCollision(CollisionBox cb) {
		return cb_bottom.checkCollision(cb)||cb_top.checkCollision(cb)||cb_top2.checkCollision(cb);
	}
	
	public void draw(Batch batch) {
		sprite_bottom.draw(batch);
		sprite_top.draw(batch);
		sprite_top2.draw(batch);
	}
	public float getX() {
		return sprite_bottom.getX();
	}
}
