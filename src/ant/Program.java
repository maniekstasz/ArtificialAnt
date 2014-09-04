package ant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Szymon Konicki
 *
 */
public class Program  {
	private int rawFitness = 0;
	private double fitness;
	private boolean stop = false;
	private int steps = 0;
	private Node root;

	private List<List<Node>> nodeLevels = new ArrayList<List<Node>>();

	
	public void reset(){
		stop = false;
		steps = 0;
		rawFitness = 0;
	}
	public Program(Node root) {
		this.root = root;
		for (int i = 0; i < ApplicationContext.geneticService.getProgramDepth(); i++) {
			nodeLevels.add(new ArrayList<Node>());
		}
		addNodesToList(root, 0);
	}

	public Program(Program program) {
		this.root = new Node(program.root);
		for (int i = 0; i < ApplicationContext.geneticService.getProgramDepth(); i++) {
			nodeLevels.add(new ArrayList<Node>());
		}
		addNodesToList(root, 0);
	}

	public void addNodesToList(Node node, int level) {
		List<Node> nodes = nodeLevels.get(level);

		nodes.add(node);
		for (int i = 0; i < node.getNodes().length; i++) {
			addNodesToList(node.getNodes()[i], level + 1);
		}
	}

	public void removeObject(Node node, int level) {
		nodeLevels.get(level).remove(node);
		for (int i = 0; i < node.getNodes().length; i++) {
			removeObject(node.getNodes()[i], level + 1);
		}
	}

	public void incrementRawFitness() {
		rawFitness++;
	}

	public void execute() {
		root.run();
	}

	public Node getRoot() {
		return root;
	}

	public int getRawFitness() {
		return rawFitness;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int onStep() {
		steps++;
		if (steps == 600 || rawFitness == 89) {
			stop = true;
		}
		return steps;
	}

	public int getSteps() {
		return steps;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

//	public class ProgramComparator implements Comparator<Program> {
//
//		@Override
//		public int compare(Program o1, Program o2) {
//			if (o2.rawFitness > o1.rawFitness)
//				return -1;
//			if (o2.rawFitness == o1.rawFitness)
//				return 0;
//			return 1;
//		}
//
//	}

//	@Override
//	public int compareTo(Object o) {
//		Program object = (Program) o;
//		if (object.rawFitness > rawFitness)
//			return -1;
//		if (object.rawFitness == rawFitness)
//			return 0;
//		return 1;
//	}

	public Node getRandomNode(int maxLevel) {
		if (maxLevel == 0) {
			return root;
		}
		int level = 0;
		do {
			level = NodeFactory.r.nextInt(maxLevel);
		} while (nodeLevels.get(level).size() == 0);
		int index = NodeFactory.r.nextInt(nodeLevels.get(level).size());
		return nodeLevels.get(level).get(index);
	}

	public Node getRandomNode() {
		int level = 0;
		do {
			level = NodeFactory.r.nextInt(nodeLevels.size());
		} while (nodeLevels.get(level).size() == 0);
		int index = NodeFactory.r.nextInt(nodeLevels.get(level).size());
		return nodeLevels.get(level).get(index);
	}
}
