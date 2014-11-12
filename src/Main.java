import cards.GeneticCards;

/**
 * Shows usage of an genetic algorithm to solve the GeneticCards problem. 
 * 
 * @author Razvan Madalin MATEI <matei.rm94@gmail.com>
 */
public class Main {
	private static final int    numCards             = 10;
	private static final int    sumTarget            = 36;
	private static final int    prodTaget            = 360;
	private static final int    numCandidates        = 50;
	private static final double crossoverProbability = 0.9;
	private static final int    maxIterations        = 500;
	
	public static void main(String[] args) {
		GeneticCards gc = new GeneticCards(
				numCards, 
				sumTarget, 
				prodTaget,
				numCandidates,
				crossoverProbability,
				maxIterations);
		gc.run();
	}
}
