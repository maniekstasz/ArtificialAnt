package ant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Szymon Konicki
 *
 */
public class GeneticService {
	private Board board = ApplicationContext.board;
	private Ant ant = ApplicationContext.ant;
	private Program currentProgram;
	private List<Program> programs = new ArrayList<Program>();
	private List<Program> programHistory = new ArrayList<Program>();
	
	private int programsCount = 500;
	private int programDepth = 5;
	private double tournamentFraction = 0.2;
	private int generationsLimit = 100;
	
	private int mutationLikelihood = 1;
	private int crossoverLikelihood = 5;
	private int reproductionLikelihood = 5;
	
	private int defaultSimilarCount = 5;
	private int defaultMutationLikelihood = 1;
	private int defaultCrossoverLikelihood = 5;
	private int defaultReproductionLikelihood = 5;

	public void onStep() {
		Coordinates coordinates = ant.getCoordinates();
		boolean hasFood = board.hasFood(coordinates);
		currentProgram.onStep();
		if (hasFood) {
			currentProgram.incrementRawFitness();
			board.removeFood(coordinates);
		}
	}

	private void createInitialPopulation() {
		programs = new ArrayList<Program>();
		for (int i = 0; i < programsCount; i++) {
			Node root = generateFull(NodeFactory.r.nextInt(programDepth));
			root.updateSubNodesCount();
			programs.add(new Program(root));
		}
	}

	private Node generateFull(int depth) {
		Node root;
		if (depth > 1) {
			root = NodeFactory.getFunction();
		} else {
			root = NodeFactory.getTerminal();
		}
		int start = 0;
		if (root.getAction() == Action.IF_FOOD) {
			root.setNode(0, new Node(Action.MOVE));
			start = 1;
		}
		for (int i = start; i < root.getNodes().length; i++) {
			root.setNode(i, generateFull(depth - 1));
		}
		return root;
	}

	private Program assessPopulation() {
		Program result = null;
		double sum = 0;
		for (Program p : programs) {
			sum += p.getRawFitness();

		}
		for (Program p : programs) {
			p.setFitness(p.getRawFitness() / sum);
			if (result == null) {
				result = p;
			} else if (result.getFitness() < p.getFitness()) {
				result = p;
			}
		}
		return result;
	}

	private Program selectProgramOnFitness() {
		double decision = Math.random();
		double sum = 0;
		for (int i = programs.size() - 1; i >= 0; i--) {
			sum += programs.get(i).getFitness();
			if (decision <= sum) {
				return programs.get(i);
			}
		}
		return null;
	}

	private Program tournamentSelect() {
		int size = (int) (programsCount * tournamentFraction);
		Program tournament[] = new Program[size];
		for (int i = 0; i < size; i++) {
			int index = NodeFactory.r.nextInt(programsCount);
			tournament[i] = programs.get(index);
		}
		Program best = tournament[0];
		for (int i = 1; i < size; i++) {
			if (best.getRawFitness() < tournament[i].getRawFitness()) {
				best = tournament[i];
			}
		}
		return best;
	}

	private Program getRandom() {
		return programs.get(NodeFactory.r.nextInt(programs.size()));
	}

	private void executePrograms() {
		for (Program program : programs) {
			currentProgram = program;
			while (!currentProgram.isStop()) {
				currentProgram.execute();
			}
			ApplicationContext.ant.reset();
			board.reset();
		}
	}

	private Program crossover(Program father, Program mother) {
		Node nodeToAdd = mother.getRandomNode();
		return createProgramFromNodes(father, nodeToAdd);
	}

	private Program mutation(Program father) {
		Node nodeToAdd = generateFull(NodeFactory.r.nextInt(programDepth));
		return createProgramFromNodes(father, nodeToAdd);

	}

	private Program createProgramFromNodes(Program program, Node nodeToAdd) {
		int depth = nodeToAdd.getDepth();
		int diff = programDepth - depth;
		Node child = program.getRandomNode(diff);
		if (child.getParent() != null) {
			child.getParent().setNode(child.getParentIndex(), nodeToAdd);
			program.removeObject(child, child.getDepth() - 1);
			program.addNodesToList(nodeToAdd, diff);
			return program;
		} else {
			return new Program(nodeToAdd);
		}

	}

	public List<Program> getPrograms() {
		return programs;
	}

	public Program getCurrentProgram() {
		return currentProgram;
	}

	private Program reproduction(Program program) {
		return new Program(program);
	}

	private void performGeneticOperation() {
		List<Program> offsprings = new ArrayList<Program>(programsCount);

		for (int i = 0; i < programsCount; i++) {
			double operation = NodeFactory.r.nextInt(mutationLikelihood+crossoverLikelihood+reproductionLikelihood);
			Program father = new Program(tournamentSelect());
			Program mother = new Program(tournamentSelect());
			if (operation <= mutationLikelihood) {
				offsprings.add(mutation(father));
			} else if (operation <= crossoverLikelihood + mutationLikelihood) {
				offsprings.add(crossover(father, mother));
			} else {
				offsprings.add(reproduction(father));
			}
		}
		programs = offsprings;
	}

	public Program loop() {
		boolean stop = false;

		Program best = null;
		Program last = null;
		while (true) {
			createInitialPopulation();
			int similarCount = 0;
			for (int i = 0; i < generationsLimit; i++) {
				executePrograms();
				best = assessPopulation();
				archivize();
				if(last != null && best.getRawFitness() == last.getRawFitness()){
					similarCount++;
				}else{
					last = best;
					similarCount = 0;
					resetLikelihood();
				}
				if(similarCount == defaultSimilarCount){
					increaseMutationLikelihood();
				}
				if (best.getRawFitness() >= 89) {
					return best;
				}
				performGeneticOperation();
			}
			resetLikelihood();
		}
	}

	public void increaseMutationLikelihood() {
		mutationLikelihood = 8;
		crossoverLikelihood = 3;
		reproductionLikelihood = 3;
	}

	public void resetLikelihood() {
		mutationLikelihood = defaultMutationLikelihood;
		crossoverLikelihood = defaultCrossoverLikelihood;
		reproductionLikelihood = defaultReproductionLikelihood;
	}
	
	public void archivize(){
		programHistory.addAll(programs);
	}

	public void stopCurrentProgram(boolean stop) {
		currentProgram.setStop(stop);
	}

	public boolean isStopped() {
		return currentProgram.isStop();
	}

	public void setCurrentProgram(Program currentProgram) {
		this.currentProgram = currentProgram;
	}
	
	
	
	
	// Po zakonczeniu wszystkich obliczeñ tutaj znajduj¹ sie wszystkie programy
	
	public List<Program> getProgramHistory() {
		return programHistory;
	}

	public void setProgramHistory(List<Program> programHistory) {
		this.programHistory = programHistory;
	}

	// ile programów jest na jedn¹ generacje
	
	public int getProgramsCount() {
		return programsCount;
	}

	public void setProgramsCount(int programsCount) {
		this.programsCount = programsCount;
	}

	
	// g³êbokoœæ programu
	
	public int getProgramDepth() {
		return programDepth;
	}

	public void setProgramDepth(int programDepth) {
		this.programDepth = programDepth;
	}

	// ile programów losujemy przy nowej generacji 
	
	public double getTournamentFraction() {
		return tournamentFraction;
	}

	public void setTournamentFraction(double tournamentFraction) {
		this.tournamentFraction = tournamentFraction;
	}

	// po ilu generacjach losujemy ca³oœæ od nowa, tak jakbyœmy uruchomili program od nowa
	
	public int getGenerationsLimit() {
		return generationsLimit;
	}

	public void setGenerationsLimit(int generationsLimit) {
		this.generationsLimit = generationsLimit;
	}

	// Tutaj ustawiamy pradwopodobienstwo mutacji, krzy¿ówki i reprodukcji
	
	public int getDefaultMutationLikelihood() {
		return defaultMutationLikelihood;
	}

	public void setDefaultMutationLikelihood(int defaultMutationLikelihood) {
		this.defaultMutationLikelihood = defaultMutationLikelihood;
	}

	public int getDefaultCrossoverLikelihood() {
		return defaultCrossoverLikelihood;
	}

	public void setDefaultCrossoverLikelihood(int defaultCrossoverLikelihood) {
		this.defaultCrossoverLikelihood = defaultCrossoverLikelihood;
	}

	public int getDefaultReproductionLikelihood() {
		return defaultReproductionLikelihood;
	}

	public void setDefaultReproductionLikelihood(
			int defaultReproductionLikelihood) {
		this.defaultReproductionLikelihood = defaultReproductionLikelihood;
	}
	
	
	// po tylu operacjach zwiekszamy prawdopodobieñstwo mutacji
	
	public int getDefautlSimilarCount(){
		return defaultSimilarCount;
	}
	
	public void setDefaultSimilarCount(int defaultSimilarCount){
		this.defaultSimilarCount = defaultSimilarCount;
	}

}
