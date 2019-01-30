package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Scoreboard {
	Preferences pref;
	int score;
	public Scoreboard() {
		pref = Gdx.app.getPreferences("FlappyWizard");
		score = 0;
	}
	
	//returns true if new highscore
	public Boolean setHighscore(int new_score, String player_name) {
		if(pref.getInteger("Highscore",0)<new_score) {
			pref.putInteger("Highscore", new_score);
			pref.putString("Name", player_name);
			pref.flush();
			return true;
		}
		return false;
	}
	public String getHighscore() {
		return pref.getString("Name","No Highscore")+" : "+pref.getInteger("Highscore", 0);
	}
	public void incrementScore(int factor) {
		score = score +(1*factor);
	}
	public int getScore() {
		return score;
	}
}
