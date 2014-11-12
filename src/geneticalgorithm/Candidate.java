package geneticalgorithm;

/**
 * Generic genetic algorithm's candidate
 * 
 * In order to create a specific genetic algorithm candidate, one has to 
 * implement the specific methods of such an candidate: 
 * 		- double fitness()
 * 		- void   display()
 * 
 * @author Razvan Madalin MATEI <matei.rm94@gmail.com>
 */
public abstract class Candidate {
	/**
	 * Computes the fitness of candidate.
	 * A fitness of 0 indicates an exact solution.
	 * 
	 * @return fitness of candidate
	 */
	public abstract double fitness();
	
	/**
	 * Displays the candidate.
	 */
	public abstract void display();
}
