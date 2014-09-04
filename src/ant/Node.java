package ant;

import ant.Controller.Turn;

/**
 * @author Szymon Konicki
 *
 */
public class Node {

	private static final Board board = ApplicationContext.board;
	private static final Ant ant = ApplicationContext.ant;
	private static final GeneticService service = ApplicationContext.geneticService;

	private Node nodes[];
	private Node parent;
	private int parentIndex;

	private int subNodesCount;

	public Node(Action action) {
		this.action = action;
		if (action == Action.PROGN_2) {
			nodes = new Node[2];
			subNodesCount = 2;
		} else if (action == Action.PROGN_3) {
			nodes = new Node[3];
			subNodesCount = 3;
		} else if (action == Action.IF_FOOD) {
			nodes = new Node[2];
			subNodesCount = 2;
		} else {
			nodes = new Node[0];
			subNodesCount = 0;
		}
	}

	private Node() {

	}
	public Node(Node node) {
		this.action = node.action;
		if (action == Action.PROGN_2) {
			nodes = new Node[2];
			subNodesCount = 2;
		} else if (action == Action.PROGN_3) {
			nodes = new Node[3];
			subNodesCount = 3;
		} else if (action == Action.IF_FOOD) {
			nodes = new Node[2];
			subNodesCount = 2;
		} else {
			nodes = new Node[0];
			subNodesCount = 0;
		}
		this.nodes = new Node[node.nodes.length];
		// this.subNodesCount = node.subNodesCount;
		for (int i = 0; i < node.getNodes().length; i++) {
			this.setNode(i, new Node(node.getNodes()[i]));
		}
	}

	public void setNode(int index, Node node) {
		nodes[index] = node;
//		subNodesCount += node.getSubNodesCount();
		node.setParent(this);
		node.setParentIndex(index);
	}

	public Node[] getNodes() {
		return nodes;
	}

	public void setNodes(Node[] nodes) {
		this.nodes = nodes;

	}

	public void run() {
		if(service.isStopped()){
			return;
		}
		switch (action) {
		case IF_FOOD:
			Coordinates forwardC = board.getTileForward(ant.getCoordinates(),
					ant.getFacingDirection());
			boolean hasFood = board.hasFood(forwardC);
			if (hasFood) {
				nodes[0].run();
			} else {
				nodes[1].run();
			}
			break;
		case PROGN_2:
			nodes[0].run();
			nodes[1].run();
			break;
		case PROGN_3:
			nodes[0].run();
			nodes[1].run();
			nodes[2].run();
			break;
		case TURN_LEFT:
			ant.turnLeft();
			service.onStep();
			if(ApplicationContext.controller.isGraphics())
				ApplicationContext.controller.turn(Turn.LEFT);
			break;
		case TURN_RIGHT:
			ant.turnRight();
			service.onStep();
			if(ApplicationContext.controller.isGraphics())
				ApplicationContext.controller.turn(Turn.RIGHT);
			break;
		case MOVE:
			ant.move();
			service.onStep();
			if(ApplicationContext.controller.isGraphics())
				ApplicationContext.controller.move(ant.getCoordinates());
			break;
		}
	}

//	public int getSubNodesCount() {
//		return getSubNodesCount(service.programDepth);
//	}
//
//	public int getSubNodesCount(int depth){
//		if(depth == 1){
//			return 0;
//		}
//		int sum = nodes.length;
//		for (int i = 0; i < nodes.length; i++) {
//			sum+= nodes[i].getSubNodesCount(depth-1);
//		}
//		return sum;
//	}
//	
	private Action action;

	public Action getAction() {
		return action;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getParentIndex() {
		return parentIndex;
	}

	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}

	public void setSubNodesCount(int subNodesCount) {
		this.subNodesCount = subNodesCount;
	}

	@Override
	public String toString() {
		String result = action.toString() + ": {\n";

		for (int i = 0; i < nodes.length; i++) {
			result += "\t";
			result += nodes[i].toString();
		}
		result += "}";
		result += "\n";
		return result;
	}
	
	public int updateSubNodesCount(){
		int sum = nodes.length;
		for(int i =0; i< nodes.length; i++){
			sum += nodes[i].updateSubNodesCount();
		}
		return sum;
	}
	
	public int getDepth(){
		int sum = 1;
		int max = 0;
		for(int i=0;i<nodes.length;i++){
			int temp = nodes[i].getDepth();
			if(temp > max){
			     max =temp;
			}
		}
		return sum+=max;
	}

	
	public int getLevel(){
		int level = 0;
		if(getParent() !=null)
			level += getParent().getLevel();
		return level;
	}
}
