package pl.bmaraszek.IntegratorTest;

import java.util.LinkedList;
import java.util.List;

public class SimulationData {
	protected List<Double> yPositions = new LinkedList<Double>();
	protected List<Double> errorTerms = new LinkedList<Double>();
	protected double avgErrorTerm;

	public void print() {
		for (int i = 0; i < yPositions.size(); ++i) {
			System.out.printf("%f error: %f%%\n", yPositions.get(i), errorTerms.get(i));
		}
		System.out.println();
		System.out.printf("Avarege error: %f%%\n", avgErrorTerm);
	}
}
