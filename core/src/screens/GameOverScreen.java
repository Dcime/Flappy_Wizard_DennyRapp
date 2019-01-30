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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dennyrapp.game.FlappyGame;

import helper.Scoreboard;
/*
 * Screen der beim Game Over aufgerufen wird.
 * Hier wird der Highscore angezeigt und
 * ggf. ein neuer gesetzt.
 * Ausserdem kann man von hier ein neues Spiel
 * starten oder zum Hauptmenue zurueck.
 */
public class GameOverScreen implements Screen{
	
	//Magic ab hier
	private FlappyGame game;
	Scoreboard scoreboard;
	private Stage stage;
	BitmapFont pixelFont;
	GlyphLayout pixelLayout;
	OrthographicCamera camera;
	float posx, posy;
	final int spacing = 10;
	
	public GameOverScreen(FlappyGame game1, Scoreboard scoreboard) {
		this.game = game1;
		this.scoreboard = scoreboard;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		pixelFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
	    pixelFont.getData().setScale(0.5f);
	    pixelLayout = new GlyphLayout(pixelFont,"Your score is: "+ scoreboard.getScore());
	    posx = Gdx.graphics.getWidth()/2-pixelLayout.width/2;
		posy = Gdx.graphics.getHeight()-pixelLayout.height-10;
	    camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		int row_height = Gdx.graphics.getWidth() / 12;
        int col_width = Gdx.graphics.getWidth() / 12;
		Button button = new TextButton("Neustart",mySkin,"small");
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
		/*
		TextField name_textField = new TextField("Namen eingeben", mySkin,"small");
		name_textField.setSize(col_width*4,row_height);
		name_textField.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3-spacing);
		name_textField.addListener(new InputListener(){
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
		stage.addActor(name_textField);
		*/
	}
	
	
	/*	
	private FlappyGame game;
	private Stage stage;
	
	private  TextButton submit;
	private TextButtonStyle tbStyle;
	private BitmapFont font;
	private Label textlabel1,textlabel2;

	OrthographicCamera camera;
	
	public GameOverScreen(FlappyGame game, String score) {
		this.game = game;
		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		
		
		//Button button2 = new TextButton("Text Button",mySkin,"small");
		//button2.setSize(col_width*4,row_height);
		//button2.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);
		//button2.addListener(new InputListener(){
    	//@Override
    	//public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        //outputLabel.setText("Press a Button");
    //}
    //@Override
    //public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
      //  outputLabel.setText("Pressed Text Button");
        //return true;
    //}
//});
//stage.addActor(button2);
		
		
		
		Table rootTable = new Table();
		rootTable.setFillParent(true);
		rootTable.center().center();
		
		
		
		font = new BitmapFont();
		tbStyle = new TextButtonStyle();
		tbStyle.font = font;
		Skin uiSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		submit = new TextButton("Submit",tbStyle);
		textlabel1 = new Label("Game Over",uiSkin);
		textlabel2 = new Label(score,uiSkin);
		
		rootTable.add(textlabel1);
		rootTable.row();
		rootTable.add(textlabel2);
		rootTable.row();
		rootTable.add(submit);
		
		stage.addActor(rootTable);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);	
	}

	@Override
	public void show() {

		
	}

	@Override
	public void render(float delta) {
	    Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.act();
	    stage.draw();
	}
	*/
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
		game.dispose();
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
		game.batch.begin();
		game.batch.draw(game.hermine, 0, 0, (int)(game.hermine.getWidth() * 0.25), (int)(game.hermine.getHeight() * 0.25));
		pixelFont.draw(game.batch,pixelLayout,posx,posy);
		game.batch.end();
		
		stage.act();
        stage.draw();
		
	}

}
