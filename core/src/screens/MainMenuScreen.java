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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dennyrapp.game.FlappyGame;

public class MainMenuScreen implements Screen {

	private final FlappyGame game;
	private Stage stage;
	private OrthographicCamera camera;
	private final int spacing = 10;
	private BitmapFont pixelFont;
	private float posx = 1280/2; 
	private float posy = 720;
	private GlyphLayout pixelLayout;
	
	public MainMenuScreen(FlappyGame game1) {
		this.game = game1;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
        int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
        pixelFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
    	pixelFont.getData().setScale(0.5f);
		Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		
		Button button = new TextButton("Spiel starten",mySkin,"small");
		button.setSize(col_width*4,row_height);
		button.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);
		button.addListener(new InputListener(){
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	game.setScreen(new GameScreen(game));
		    	dispose();
		    }
		    @Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

		        return true;
		    }
		});
		stage.addActor(button);
		
		Button button2 = new TextButton("Highscore",mySkin,"small");
		button2.setSize(col_width*4,row_height);
		button2.setPosition(col_width*7,button.getY()-button.getHeight()-spacing);
		button2.addListener(new InputListener(){
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	game.setScreen(new HighscoreScreen(game));
		    	dispose();
		    }
		    @Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

		        return true;
		    }
		});
		stage.addActor(button2);
		
		Button button3 = new TextButton("Hilfe",mySkin,"small");
		button3.setSize(col_width*4,row_height);
		button3.setPosition(col_width*7,button2.getY()-button2.getHeight()-spacing);
		button3.addListener(new InputListener(){
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	game.setScreen(new HelpScreen(game));
		    	dispose();
		    }
		    @Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

		        return true;
		    }
		});
		stage.addActor(button3);
		
		
		pixelLayout = new GlyphLayout(pixelFont,"Welcome to Flappy Wizard!!!");
        posx -= pixelLayout.width/2;
        posy -= (pixelLayout.height +10);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);	
	}
	
	

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(game.hermine, 0, 0, (int)(game.hermine.getWidth() * 0.25), (int)(game.hermine.getHeight() * 0.25));
		pixelFont.draw(game.batch,pixelLayout,posx,posy);
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
		stage.dispose();
	}

}
