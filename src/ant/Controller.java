package ant;

/**
 * @author Szymon Konicki
 *
 */
public class Controller {

	private GeneticService geneticService = ApplicationContext.geneticService;
	
	public enum Turn{LEFT, RIGHT}
	
	
	private boolean graphics = false;
	public boolean isGraphics() {
		return graphics;
	}
	public void setGraphics(boolean graphics) {
		this.graphics = graphics;
	}
	
	
	
	// �eby dosta� si� do tych funkcji nale�y ustawi� wczesniej graphics na true
	
	public void turn(Turn turn){

	}
	
	
	public void move(Coordinates c){
	}
	
}
