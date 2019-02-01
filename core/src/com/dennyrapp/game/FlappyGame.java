
package com.dennyrapp.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MainMenuScreen;

public class FlappyGame extends Game {
	
	public SpriteBatch batch;
	
	public Texture hermine;
	public Texture harry, luna, malfoy, cedrig;
	public Texture item_blau, item_gruen, item_rot, item_silber;
	public Texture dementor, turm_gryffindor, turm_huffelpuff, turm_ravenclaw, turm_slytherin;
	public Texture cloud,hilfe_texture,hilfe2_texture;
	public Texture texture_arr_tower[];
	public BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		texture_arr_tower = new Texture[4];

		hermine = new Texture("png/hermine.png");
		
		harry = new Texture("png/harry.png");
		luna = new Texture("png/luna.png");
		malfoy = new Texture("png/malfoy.png");
		cedrig = new Texture("png/cedrig.png");
		
		item_blau = new Texture("png/item_blau.png");
		item_gruen = new Texture("png/item_gruen.png");
		item_rot = new Texture("png/item_rot.png");
		item_silber = new Texture("png/item_silber.png");

		cloud = new Texture("png/cloud.png");
		dementor = new Texture("png/dementor.png");
		
		turm_gryffindor = new Texture("png/turm_gryffindor.png");
		texture_arr_tower[0] = turm_gryffindor;
		turm_huffelpuff = new Texture("png/turm_huffelpuff.png");
		texture_arr_tower[1] = turm_huffelpuff;
		turm_ravenclaw = new Texture("png/turm_ravenclaw.png");
		texture_arr_tower[2] = turm_ravenclaw;
		turm_slytherin = new Texture("png/turm_slytherin.png");
		texture_arr_tower[3] = turm_slytherin;
		
		hilfe_texture = new Texture("Hilfe.jpeg");
		hilfe2_texture = new Texture("Hilfe2.jpeg");
		
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		
		hermine.dispose();
		
		harry.dispose();
		luna.dispose();
		malfoy.dispose();
		cedrig.dispose();
		
		item_blau.dispose();
		item_gruen.dispose();
		item_rot.dispose();
		item_silber.dispose();
		
		dementor.dispose();
		turm_gryffindor.dispose();
		turm_huffelpuff.dispose();
		turm_ravenclaw.dispose();
		turm_slytherin.dispose();
	}
}
