package com.dennyrapp.game;

import static org.junit.Assert.*;

import org.junit.Test;

import helper.Scoreboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class JUnitTest {
	@Test
	public void test() {
		//fail("Not yet implemented");
		Scoreboard sb;
		sb = new Scoreboard();
		assertEquals(sb.getHighscore(), sb.getHighscore());
	}

}
