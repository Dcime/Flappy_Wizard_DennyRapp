package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
/*
 * Hier ist die Highscore Logik, sprich zählen des Scores und 
 * spiechern und laden des Highscores
 */
public class Scoreboard {
	//benoetigte Variablen
	Preferences pref;
	int score;
	//Konstruktor
	public Scoreboard() {
		pref = Gdx.app.getPreferences("FlappyWizard");//wird bei Windows nun unter "/user/.pref/FlappyWizard.xml" gespeichert
		score = 0;
	}
	
	//returns true falls es ein neuer Highscore war
	public Boolean setHighscore(int new_score, String player_name) {
		if(pref.getInteger("Highscore",0)<new_score) {
			pref.putInteger("Highscore", new_score);
			pref.putString("Name", player_name);
			pref.flush();
			return true;
		}
		return false;
	}
	//gibt Name und Punktzahl des Highscores zurueck
	public String getHighscore() {
		return pref.getString("Name","No Highscore")+" : "+pref.getInteger("Highscore", 0);
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
