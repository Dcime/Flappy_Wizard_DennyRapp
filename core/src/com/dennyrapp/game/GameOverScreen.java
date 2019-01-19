package com.dennyrapp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen{
	
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
		
		/*
			Button button2 = new TextButton("Text Button",mySkin,"small");
button2.setSize(col_width*4,row_height);
button2.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);
button2.addListener(new InputListener(){
    @Override
    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        outputLabel.setText("Press a Button");
    }
    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        outputLabel.setText("Pressed Text Button");
        return true;
    }
});
stage.addActor(button2);
		 */
		
		
		
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
