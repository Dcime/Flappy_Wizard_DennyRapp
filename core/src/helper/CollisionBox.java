package helper;
/**
 * 
 * @author Denny Rapp
 *	In dieser Klasse wird ein Rechteck um das bestimmte Sprite gezogen.
 *	Hier kann auch geprüft werden ob 2 Boxen kollidieren.
 */
public class CollisionBox {
	/**
	 * x,y beschreibt die Position des Rechtecks
	 * width,height beschreiben die Breite und Hoehe des Rechtecks
	 */
	public float x,y;
	public int width,height;
	
	//normaler Konstruktor
	public CollisionBox(float x,float y,int width, int height) {
		this.x =x;
		this.y=y;
		this.width = width;
		this.height = height;
	}
	//aendern der Position des Rechtecks
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}
	//Kollisionspruefung
	//Checkt ob 2 Rechtecke ueberlappen
	public boolean checkCollision(CollisionBox cb) {
		return this.x < cb.x + cb.width && this.y < cb.y + cb.height && this.x + this.width > cb.x && this.y + this.height > cb.y;
	}
	//verschieben auf der x-Achse
	public void translateX(float x) {
		this.x = this.x+x;
	}
	//verschieben auf der y-Achse
	public void translateY(float y) {
		this.y = this.y+y;
	}
}
