package cards;

import java.util.Random;

import geneticalgorithm.GeneticAlgorithm;

/**
 * This class represents the genetic algorithm implementation for the
 * GeneticCards problem.
 * 
 * GeneticCards problem:
 * 		- there are <numCards> cards numbered from 1 to <numCards>
 * 		- split the given <numCards> in 2 piles such that
 * 		  the sum of the cards' values in the first pile to be <sumTarget> and 
 * 		  the product of the cards' values in the second pile to be <prodTarget> 
 * 
 * @author Razvan Madalin MATEI <matei.rm94@gmail.com>
 */
public class GeneticCards extends GeneticAlgorithm<CardsCandidate> {
	int numCards;
	int sumTarget;
	int prodTarget;

	/**
	 * Creates a new GeneticCards genetic algorithm.
	 * 
	 * @param numCards
	 * 			number of cards
	 * @param sumTarget
	 * 			sum of the cards in the first pile
	 * @param prodTarget
	 * 			product of the cards in the second pile
	 * @param numOfCandidates
	 * 			initial number of candidates
	 * @param crossoverProbability
	 * 			crossover probability
	 * @param maxIterations
	 * 			maximum number of iterations
	 */
	public GeneticCards(int numCards, int sumTarget, int prodTarget,
			int numOfCandidates, double crossoverProbability, int maxIterations) {
		super(numOfCandidates, crossoverProbability, maxIterations);
		
		this.numCards = numCards;
		this.sumTarget = sumTarget;
		this.prodTarget = prodTarget;
	}

	/**
	 * A new candidate is created from the crossover operation of two candidates
	 * as follows:
	 * 		- if the i-th card is in the sum pile (equals 0) in both candidates
	 * 		  then the new candidate will have the i-th card in the sum pile also
	 * 		-  if the i-th card is in the prod pile (equals 0) in both candidates
	 * 		  then the new candidate will have the i-th card in the prod pile also
	 * 		- if none of the conditions above are satisfied then the i-th card 
	 * 		  has equal chances to be in sum pile or in the prod pile
	 */
	@Override
	public CardsCandidate crossover(CardsCandidate c1, CardsCandidate c2) {
		Random rand;
		int c1Representation;
		int c2Representation;
		int newRepresentation;
		
		rand = new Random();
		newRepresentation = 0;
		c1Representation = c1.getRepresentation();
		c2Representation = c2.getRepresentation();
		
		for (int i = 0 ; i < numCards; i++) {
			if (((c1Representation & (1 << i)) & (c2Representation & (1 << i))) > 0) {
				newRepresentation |= (1 << i);
			} else if (rand.nextDouble() > 0.5) {
				if (((c1Representation & (1 << i)) | (c2Representation & (1 << i))) > 0) {
					newRepresentation |= (1 << i);
				}
			}
		}
		
		return new CardsCandidate(numCards, sumTarget, prodTarget, newRepresentation);
	}

	/**
	 * The mutated candidate will have 50% of the card moved in the opposite pile
	 */
	@Override
	public CardsCandidate mutate(CardsCandidate candidate) {
		int newRepresentation;
		Random rand;
		
		rand = new Random();
		newRepresentation = candidate.getRepresentation();
		for (int i = 0; i < numCards; i++) {
			if (rand.nextDouble() > 0.5) {
				if ((newRepresentation & (1 << i)) > 0) {
					newRepresentation &= ~(1 << i);
				} else {
					newRepresentation |= (1 << i);
				}
			}
		}
		
		return new CardsCandidate(numCards, sumTarget, prodTarget, newRepresentation);
	}

	/**
	 * Returns a new candidate with a random representation
	 */
	@Override
	public CardsCandidate createRandomCandidate() {
		int representation;
		Random rand;
		
		rand = new Random();
		representation = 0;
		for (int i = 0; i < numCards; i++) {
			if (rand.nextDouble() > 0.5) {
				representation = representation | (1 << i);
			}
		}
		
		return new CardsCandidate(numCards, sumTarget, prodTarget, representation);
	}

}
