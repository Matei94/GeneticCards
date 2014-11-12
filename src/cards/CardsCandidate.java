package cards;

import geneticalgorithm.Candidate;

/**
 * Candidate for the GeneticCards problem.
 * 
 * @author Razvan Madalin MATEI <matei.rm94@gmail.com>
 */
public class CardsCandidate extends Candidate {
	private int numCards;
	private int sumTarget;
	private int prodTarget;
	
	/**
	 * The representation of a GeneticCards candidate is an int where only the
	 * first <numCards> represent useful data.
	 * 
	 * If the i-th bit of <represenation> is 0, then the i-th card belongs in
	 * the sum pile. Otherwise it belongs in the prod pile.
	 */
	private int representation;
	
	/**
	 * Creates a new GeneticCards candidate
	 * @param numCards
	 * 			number of cards
	 * @param sumTarget
	 * 			sum of cards in the sum pile
	 * @param prodTarget
	 * 			product of cards in the prod pile 
	 * @param representation
	 * 			representation of cards
	 */
	public CardsCandidate(int numCards, int sumTarget, int prodTarget,
			int representation) {
		this.numCards   = numCards;
		this.sumTarget  = sumTarget;
		this.prodTarget = prodTarget;
		this.representation = representation;
	}
	
	/**
	 * Returns a copy of the representation of the candidate.
	 * 
	 * A copy and not the actual representation is return in order to avoid
	 * privacy leaks and possible unnoticed modification of the representation. 
	 */
	public int getRepresentation() {
		int representationCopy;
		representationCopy = representation;
		return representationCopy;
	}
	
	/**
	 * Sets the representation of candidate
	 * 
	 * @param representation
	 * 			the new representation
	 */
	public void setRepresentation(int representation) {
		this.representation = representation;
	}
	
	/**
	 * Computes the fitness of candidate.
	 * 
	 * A fitness of 0 means an exact solution.
	 * 
	 * The fitness of an GeneticCards candidate represent the sum of the
	 * standard deviations of actual sum and prod of the 2 piles from the
	 * targets.
	 */
	@Override
	public double fitness() {
		double sum;
		double prod;
		double sumError;
		double prodError;
		
		sum  = 0;
		prod = 1;
		
		for (int i = 0; i < numCards; i++) {
			if (((representation >> i) & 1) == 0) {
				sum += (1 + i);
			} else {
				prod *= (1 + i);
			}
		}
		
		sumError  = Math.abs((sum - sumTarget)   / sumTarget);
		prodError = Math.abs((prod - prodTarget) / prodTarget);
		
		return sumError + prodError;
	}

	/**
	 * Displays the candidate
	 */
	@Override
	public void display() {
		System.out.print("Sum cards:  " );
		for (int i = 0; i < numCards; i++) {
			if (((representation >> i) & 1) == 0) {
				System.out.print((i + 1) + " ");
			}
		}
		System.out.println();
		
		System.out.print("Prod cards: ");
		for (int i = 0; i < numCards; i++) {
			if (((representation >> i) & 1) == 1) {
				System.out.print((i + 1) + " ");
			}
		}
		System.out.println();
	}

}
