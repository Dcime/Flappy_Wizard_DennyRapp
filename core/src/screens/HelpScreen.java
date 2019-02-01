package screens;

import java.util.concurrent.ThreadLocalRandom;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dennyrapp.game.FlappyGame;


public class HelpScreen implements Screen{
	//variablen
	private FlappyGame game;
	private Stage stage;
	private OrthographicCamera camera;
	private int fun = 0;
	//---------------------------------------
	public HelpScreen(FlappyGame game1) {
		game = game1;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);	
		Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
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
		//random funktion um spaeter zu entscheiden welcher hintergrund genutzt wird
		fun = ThreadLocalRandom.current().nextInt(0,99);
		
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		if(fun >50) {
			game.batch.draw(game.hilfe_texture, 0, 0);
		}else {
			game.batch.draw(game.hilfe2_texture, 0, 0);
		}
		
		game.batch.end();
		
		stage.act();
        stage.draw();
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
