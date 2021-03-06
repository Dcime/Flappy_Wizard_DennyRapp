package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dennyrapp.game.FlappyGame;

import helper.Scoreboard;

public class HighscoreScreen implements Screen{
	//variablen
	private Scoreboard scoreboard;
	private final FlappyGame game;
	private Stage stage;
	private OrthographicCamera camera;
	private BitmapFont pixelFont;
	private float posx = 1280/2; 
	private float posy = 720;
	private GlyphLayout pixelLayout;
	//------------------------------
	public HighscoreScreen(FlappyGame game1) {
		this.game = game1;
		scoreboard = new Scoreboard();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
        int row_height = 1280 / 12;
        int col_width = 1280 / 12;
		pixelFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
	    pixelFont.getData().setScale(0.5f);
	    pixelLayout = new GlyphLayout(pixelFont,scoreboard.getHighscore());
	    posx -=pixelLayout.width/2;
		posy -=(pixelLayout.height+10);
		//zurueck Button
		Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		Button goBack = new TextButton("Zurueck",mySkin,"small");
		goBack.setSize(col_width*4,row_height);
		goBack.setPosition(20,20);
		goBack.addListener(new InputListener(){
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	game.setScreen(new MainMenuScreen(game));
		    	dispose();
		    }
		    @Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

		        return true;
		    }
		});
		stage.addActor(goBack);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);	
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.batch.setProjectionMatrix(camera.combined);
		//schreiben des Highscores
		game.batch.begin();
		pixelFont.draw(game.batch,pixelLayout,posx,posy);
		game.batch.end();
		//stage
		stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
