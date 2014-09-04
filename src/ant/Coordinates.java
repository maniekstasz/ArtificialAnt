package ant;

/**
 * @author Szymon Konicki
 *
 */
public class Coordinates {
	public int x;
	public int y;
	public Coordinates(int y, int x) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "y: " + this.y + " x: " +this.x;
	}
}
