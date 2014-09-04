package ant;

/**
 * @author Szymon Konicki
 *
 */
public class Cell {
	private boolean withFood;
	private boolean onTrail;
	
	
	
	public Cell() {
	}
	public boolean isWithFood() {
		return withFood;
	}
	public void setWithFood(boolean withFood) {
		this.withFood = withFood;
	}
	public boolean isOnTrail() {
		return onTrail;
	}
	public void setOnTrail(boolean onTrail) {
		this.onTrail = onTrail;
	}
}
