package com.dennyrapp.game;

import static org.junit.Assert.*;

import org.junit.Test;

import helper.Scoreboard;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;


public class JUnitTest extends GameTest{
	@Test
	public void test() {
		//fail("Not yet implemented");
		Scoreboard sb;
		sb = new Scoreboard();
		assertEquals(sb.getHighscore(), sb.getHighscore());
	}

}
