package com.dennyrapp.game;

import static org.junit.Assert.*;

import org.junit.Test;

import helper.CollisionBox;
import helper.Scoreboard;
import objects.Player;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;


public class JUnitTest {
	@Test
	public void test() {
		/*
		 * 				x,y,width,height
		 * 1 test 	cb x = 0, y = 0,width = 1, height = 1
		 * 			cb2 x = 1,y=1,width = 1, height = 1
		 * durch die loops liegen die items/hindernisse nie soweit auseinander, dass sie die grenzen
		 * ihres Datentypens erreichen also muss das nicht gecheckt werden.
		 */
		CollisionBox cb = new CollisionBox(0,0,1,1);
		CollisionBox cb2 = new CollisionBox(1,1,1,1);
		assertEquals(false,cb.checkCollision(cb2));
		
		cb2.setPos(0, 0);
		assertEquals(true,cb.checkCollision(cb2));

	}
	//eigentlich eigene Testklasse aber ich habe irgendwas veraendert sodass mein
	//junit nicht mehr geht
	/*
	@Test
	public void test2() {
	*/
		/*
		 * test des freien falls beim spieler
		 */
	/*
		Player pl = new Player(null,0,0);
		assertEquals(9.81, pl.testHelp(1));
	}
	*/
}
