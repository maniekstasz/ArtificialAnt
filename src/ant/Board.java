package ant;

import ant.Ant.Direction;

/**
 * @author Szymon Konicki
 *
 */
public class Board {

	public int width = 32;
	public int height = 32;
	public int foodCount = 89;
	private Cell[][] board = new Cell[width][height];

	public Board(){
	reset();
	}
	
	public void reset(){
		for(int i=0;i<width; i++){
			for(int j=0; j< height;j++){
				board[j][i]=new Cell();
			}
		}
		
		board[1][0].setWithFood(true);
		board[2][0].setWithFood(true);
		board[3][0].setWithFood(true);
		board[3][1].setWithFood(true);
		board[3][2].setWithFood(true);
		board[3][3].setWithFood(true);
		board[3][4].setWithFood(true);
		board[3][5].setWithFood(true);
		board[4][5].setWithFood(true);
		board[5][5].setWithFood(true);
		board[6][5].setWithFood(true);
		board[8][5].setWithFood(true);
		board[9][5].setWithFood(true);
		board[10][5].setWithFood(true);
		board[11][5].setWithFood(true);
		board[12][5].setWithFood(true);
		board[12][6].setWithFood(true);
		board[12][7].setWithFood(true);
		board[12][8].setWithFood(true);
		board[12][9].setWithFood(true);
		board[12][11].setWithFood(true);
		board[12][12].setWithFood(true);
		board[12][13].setWithFood(true);
		board[12][14].setWithFood(true);
		board[12][17].setWithFood(true);
		board[12][18].setWithFood(true);
		board[12][19].setWithFood(true);
		board[12][20].setWithFood(true);
		board[12][21].setWithFood(true);
		board[12][22].setWithFood(true);
		board[12][23].setWithFood(true);
		board[11][24].setWithFood(true);
		board[10][24].setWithFood(true);
		board[9][24].setWithFood(true);
		board[8][24].setWithFood(true);
		board[7][24].setWithFood(true);
		board[4][24].setWithFood(true);
		board[3][24].setWithFood(true);
		board[1][25].setWithFood(true);
		board[1][26].setWithFood(true);
		board[1][27].setWithFood(true);
		board[1][28].setWithFood(true);
		board[2][30].setWithFood(true);
		board[3][30].setWithFood(true);
		board[4][30].setWithFood(true);
		board[5][30].setWithFood(true);
		board[7][29].setWithFood(true);
		board[7][28].setWithFood(true);
		board[8][27].setWithFood(true);
		board[9][27].setWithFood(true);
		board[10][27].setWithFood(true);
		board[11][27].setWithFood(true);
		board[12][27].setWithFood(true);
		board[13][27].setWithFood(true);
		board[14][27].setWithFood(true);
		board[16][26].setWithFood(true);
		board[16][25].setWithFood(true);
		board[16][24].setWithFood(true);
		board[16][21].setWithFood(true);
		board[16][20].setWithFood(true);
		board[16][19].setWithFood(true);
		board[16][18].setWithFood(true);
		board[17][15].setWithFood(true);
		board[20][14].setWithFood(true);
		board[20][13].setWithFood(true);
		board[20][10].setWithFood(true);
		board[20][9].setWithFood(true);
		board[20][8].setWithFood(true);
		board[20][7].setWithFood(true);
		board[21][5].setWithFood(true);
		board[22][5].setWithFood(true);
		board[24][4].setWithFood(true);
		board[24][3].setWithFood(true);
		board[25][2].setWithFood(true);
		board[26][2].setWithFood(true);
		board[27][2].setWithFood(true);
		board[29][3].setWithFood(true);
		board[29][4].setWithFood(true);
		board[29][6].setWithFood(true);
		board[29][9].setWithFood(true);
		board[29][12].setWithFood(true);
		board[28][14].setWithFood(true);
		board[27][14].setWithFood(true);
		board[26][14].setWithFood(true);
		board[23][15].setWithFood(true);
		board[24][18].setWithFood(true);
		board[27][19].setWithFood(true);
		board[26][22].setWithFood(true);
		board[23][23].setWithFood(true);
	}
	
	public Cell getCell(Coordinates coordinates){
		return board[coordinates.y][coordinates.x];
	}
	
	public Coordinates getTileForward(Coordinates coordinates,
			Direction facingDirection) {
		Coordinates newCoordinates;
		switch (facingDirection) {
		case LEFT:
			newCoordinates = new Coordinates( coordinates.y,coordinates.x - 1);
			break;
		case UP:
			newCoordinates = new Coordinates( coordinates.y - 1, coordinates.x);
			break;
		case RIGHT:
			newCoordinates = new Coordinates( coordinates.y,coordinates.x + 1);
			break;
		case DOWN:
			newCoordinates = new Coordinates( coordinates.y + 1,coordinates.x);
			break;
		default:
			newCoordinates = new Coordinates( coordinates.y, coordinates.x);
		}
		if (newCoordinates.x < 0) {
			newCoordinates.x = width - 1;
		}
		if (newCoordinates.x == width) {
			newCoordinates.x = 0;
		}
		if (newCoordinates.y < 0) {
			newCoordinates.y = height - 1;
		}
		if (newCoordinates.y == height) {
			newCoordinates.y = 0;
		}
		return newCoordinates;
	}
	
	public boolean hasFood(Coordinates coordinates){
		Cell cell =  getCell(coordinates);
		return cell.isWithFood();
	}
	
	public void removeFood(Coordinates coordinates){
		Cell cell = getCell(coordinates);
		cell.setWithFood(false);
	}
}
