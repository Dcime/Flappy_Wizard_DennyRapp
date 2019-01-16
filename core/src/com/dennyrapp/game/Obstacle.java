package com.dennyrapp.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Obstacle {
	/**
	 * 
	 * @author Denny
	 * das hinderniss besteht aus 3 Sprites, unten einen turm, dann kommt eine 
	 * luecke auf der wiederum 2 Dementoren schweben
	 */
	
	// Die Kollisionsboxen der 3 Sprites
	CollisionBox cb_bottom, cb_top, cb_top2;
	// Die 3 Sprites
	Sprite sprite_bottom, sprite_top,sprite_top2;
	//die groesse der luecke
	//diese wird spaeter durch die hoehe des Spieler + gap berechnet
	//man braucht die gap weil es sonst unmoeglich wird fuer einen 
	//menschlichen Spieler durch die luecke zu kommen
	int gap;
	
	//Konstruktor in dem die 3 Sprites festgelegt werden
	//mit groessen Faktor und Kollisionsboxen
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
	//die Position des erstellten Objekts wird verändert
	public void setPos(int x,int y) {
		sprite_bottom.setPosition(x, y);
		//es wird nur das untere Sprite gesetzt, weswegen man die anderen Sprites auch relativ dazu setzen
		setRelativePos();
		//Die Kollisionsbox wird nachgezogen
		updateCollisionBox();
	}
	//die Position der anderen Sprites werden gesetzt
	private void setRelativePos() {
		sprite_top.setPosition(sprite_bottom.getX()-sprite_bottom.getWidth()/2, sprite_bottom.getY()+gap+sprite_bottom.getHeight());
		sprite_top2.setPosition(sprite_bottom.getX()-sprite_bottom.getWidth()/2, sprite_top.getY()+sprite_top.getHeight());
	}
	//das Objekt wird an der X-Achse bewegt
	public void translateX(int x) {
		sprite_bottom.translateX(x);
		sprite_top.translateX(x);
		sprite_top2.translateX(x);
		updateCollisionBox();
	}
	//Die Kollisionsboxen werden gesetzt
	private void updateCollisionBox() {
		cb_bottom.setPos(sprite_bottom.getX(), sprite_bottom.getY());
		cb_top.setPos(sprite_bottom.getX(), sprite_top.getY());//bottom x wegen verschiebung
		cb_top2.setPos(sprite_bottom.getX(), sprite_top2.getY());
	}
	//es wird geprueft ob der Spieler mit einem der Sprites kollidiert
	public boolean checkCollision(CollisionBox cb) {
		return cb_bottom.checkCollision(cb)||cb_top.checkCollision(cb)||cb_top2.checkCollision(cb);
	}
	//Das Objekt wird gezeichnet
	public void draw(Batch batch) {
		sprite_bottom.draw(batch);
		sprite_top.draw(batch);
		sprite_top2.draw(batch);
	}
	//Die X-Position von dem Objekt wird zurueck gegeben
	public float getX() {
		return sprite_bottom.getX();
	}
}
