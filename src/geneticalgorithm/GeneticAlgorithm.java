package geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Generic genetic algorithm.
 * 
 * This class follows the Template design pattern.
 * In order to create a specific genetic algorithm, one has to implement the
 * specific methods of such an algorithm: 
 * 		- Candidate crossover(Candidate, Candidate)
 * 		- Candidate mutate(Candidate)
 * 		- Candidate createRandomCandidate() 
 * 
 * @author Razvan Madalin MATEI <matei.rm94@gmail.com>
 *
 * @param <C> Candidate's type
 */
public abstract class GeneticAlgorithm <C extends Candidate> {
	protected int numOfCandidates;
	protected double crossoverProbability;
	private int maxIterations;
	private ArrayList<C> candidates;
	private Random rand;
	
	/**
	 * Creates a generic genetic algorithm.
	 *  
	 * @param numOfCandidates
	 * 			number of candidates
	 * @param crossoverProbability
	 * 			crossover probability (should be in [0, 1] interval)
	 * @param maxIterations
	 * 			maximum number of iteration before end of algorithm
	 */
	public GeneticAlgorithm(int numOfCandidates, double crossoverProbability,
			int maxIterations) {
		this.numOfCandidates      = numOfCandidates;
		this.crossoverProbability = crossoverProbability;
		this.maxIterations        = maxIterations;
		
		candidates = new ArrayList<C>(numOfCandidates);
		rand = new Random();
	}
	
	/**
	 * Run the genetic algorithm.
	 * 
	 * Steps of genetic algorithm:
	 * 		a - create initial generation of random candidates
	 * 		b - look for possible solution and update the best candidate
	 * 		c - select the better (smaller fitness) half of candidates
	 * 		d - crossover and mutate selected candidates
	 */
	public final void run() {
		C candidate1, candidate2;
		C bestCandidate;
		int candidatesSize;
		double fitness;
		C newCandidate;
		
		// Step a: create initial generation of random candidates
		createInitialGeneration();
		
		// Initialize the best candidate
		bestCandidate = candidates.get(0);
		
		for (int i = 0; i < maxIterations; i++) {
			// Step b: look for possible solution and update the best candidate
			for (C c : candidates) {
				fitness = c.fitness();
				
				// Display solution if found
				if (fitness == 0) {
					displaySolution(i, c);
					return;
				}
				
				// Update best candidate
				if (bestCandidate.fitness() > fitness) {
					bestCandidate = c;
				}
			}
			
			// Step c: select the better (smaller fitness) half of candidates
			selection();
			
			candidatesSize = candidates.size();
			
			// Step d: crossover and mutate selected candidates
			for (int j = 0; j < numOfCandidates - candidatesSize; j++) {
				if (rand.nextDouble() < crossoverProbability) {
					candidate1 = candidates.get(rand.nextInt(candidatesSize));
					candidate2 = candidates.get(rand.nextInt(candidatesSize));
					newCandidate = crossover(candidate1, candidate2);
				} else {
					newCandidate = mutate(
							candidates.get(rand.nextInt(candidates.size())));
				}
				
				candidates.add(newCandidate);
			}
		}
		
		// No solution found; print best candidate
		System.out.println("No exact solution found. Best candidate:");
		bestCandidate.display();
	}
	
	/**
	 * Create the initial generation of random candidates.
	 */
	private void createInitialGeneration() {
		for (int i = 0; i < numOfCandidates; i++) {
			candidates.add(createRandomCandidate());
		}
	}
	
	/**
	 * Display the solution.
	 * 
	 * @param iterationNum
	 * 			iteration where solution was found
	 * @param solutionCandidate
	 * 			candidate that fulfill the requirements
	 */
	private void displaySolution(int iterationNum, C solutionCandidate) {
		System.out.println("Solution found at iteration #" + iterationNum + ":");
		solutionCandidate.display();
	}
	
	/**
	 * Select the half of candidates more fit for the next generation.
	 */
	private void selection() {
		int initNumOfCandidates;;
		
		// Sort candidates by fitness
		Collections.sort(candidates, new Comparator<C>() {
			@Override
			public int compare(C c1, C c2) {
				double fitness1, fitness2;
				
				fitness1 = c1.fitness();
				fitness2 = c2.fitness();
				
				if (fitness1 < fitness2) {
					return -1;
				} else if (fitness1 > fitness2) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		
		// Remove the half of candidates less fit for the next generation
		initNumOfCandidates = candidates.size();
		for (int i = initNumOfCandidates - 1; i > initNumOfCandidates / 2; i--) {
			candidates.remove(i);
		}
	}
	
	/** Genetic algorithm specific methods **/
	public abstract C crossover(C candidate1, C candidate2);
	public abstract C mutate(C candidate);
	public abstract C createRandomCandidate();
}
