package objects;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Cloud {
	private Sprite cloud_Sprite;
	private int startX,startY;
	public Cloud(Texture text) {
		startX = ThreadLocalRandom.current().nextInt(0,1280);
		startY = ThreadLocalRandom.current().nextInt(0,720);
		cloud_Sprite = new Sprite(text);
		cloud_Sprite.setSize((int)(text.getWidth()*0.5), (int)(text.getHeight()*0.5));
		cloud_Sprite.setPosition(startX,startY);
	}
	public void draw(Batch batch) {
		cloud_Sprite.draw(batch);
	}
	public void loop(int speed) {
		if(cloud_Sprite.getX()< 0-cloud_Sprite.getWidth()) {
			cloud_Sprite.setX(startX+1280);
		}else {
			cloud_Sprite.translateX(speed);
		}
	}
}
