package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
/*
 * Hier ist die Highscore Logik, sprich zählen des Scores und 
 * spiechern und laden des Highscores
 */
public class Scoreboard {
	//benoetigte Variablen
	private Preferences pref;
	private int score;
	//Konstruktor
	public Scoreboard() {
		pref = Gdx.app.getPreferences("FlappyWizard");//wird bei Windows nun unter "/user/.pref/FlappyWizard.xml" gespeichert
		score = 0;
	}
	
	//returns true falls es ein neuer Highscore war
	public Boolean setHighscore(int new_score, String player_name) {
		if(pref.getInteger("Highscore1",0)<new_score) {
			pref.putInteger("Highscore3", pref.getInteger("Highscore2"));
			pref.putString("Name3", pref.getString("Name2"));
			pref.putInteger("Highscore2", pref.getInteger("Highscore1"));
			pref.putString("Name2", pref.getString("Name1"));
			pref.putInteger("Highscore1", new_score);
			pref.putString("Name1", player_name);
			pref.flush();
			return true;
		}
		if(pref.getInteger("Highscore2",0)<new_score) {
			pref.putInteger("Highscore3", pref.getInteger("Highscore2"));
			pref.putString("Name3", pref.getString("Name2"));
			pref.putInteger("Highscore2", new_score);
			pref.putString("Name2", player_name);
			pref.flush();
		}else if(pref.getInteger("Highscore3",0)<new_score) {
			pref.putInteger("Highscore3", new_score);
			pref.putString("Name3", player_name);
			pref.flush();
		}
		return false;
	}
	//gibt Name und Punktzahl des Highscores zurueck
	public String getHighscore() {
		return "1. "+pref.getString("Name1","No Highscore")+" : "+pref.getInteger("Highscore1", 0)
		+"\n"+"2. "+pref.getString("Name2","No Highscore")+" : "+pref.getInteger("Highscore2", 0)
		+"\n"+"3. "+pref.getString("Name3","No Highscore")+" : "+pref.getInteger("Highscore3", 0);
	}
	//gibt Name und Punktzahl des hoechsten Highscores zurueck
	public String getBestHighscore(){
		return pref.getString("Name1","No Highscore")+" : "+pref.getInteger("Highscore1", 0);
	}
	//erhoeht den Score
	public void incrementScore(int factor) {
		score = score +(1*factor);
	}
	//gibt den Score zurueck
	public int getScore() {
		return score;
	}
}
