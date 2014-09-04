package ant;

/**
 * @author Szymon Konicki
 *
 */
public class Ant {
	public enum Direction {
		LEFT, UP, RIGHT, DOWN
	};

	private Direction facingDirection = Direction.DOWN;
	private Coordinates coordinates = new Coordinates(0, 0);

	private Board board = ApplicationContext.board;
	private Controller controller = ApplicationContext.controller;

	public void turnLeft() {
		int currIndex = facingDirection.ordinal();
		if (currIndex == 0) {
			facingDirection = Direction.DOWN;
		} else {
			facingDirection = Direction.values()[currIndex - 1];
		}
	}

	public void turnRight() {
		int currIndex = facingDirection.ordinal();
		if (currIndex == 3) {
			facingDirection = Direction.LEFT;
		} else {
			facingDirection = Direction.values()[currIndex + 1];
		}
	}

	public void move() {
		this.coordinates = board.getTileForward(coordinates, facingDirection);
	}

	public Direction getFacingDirection() {
		return facingDirection;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void reset() {
		facingDirection = Direction.DOWN;
		coordinates = new Coordinates(0, 0);
	}
}
