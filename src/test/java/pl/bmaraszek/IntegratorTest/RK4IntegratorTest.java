package pl.bmaraszek.IntegratorTest;

import org.junit.Test;

import pl.bmaraszek.integrators.Integrator;
import pl.bmaraszek.integrators.RK4Integrator;

public class RK4IntegratorTest extends IntegratorTest {

	public static final Integrator UNDER_TEST = new RK4Integrator();

	@Test
	@Override
	public void shouldPredictFreeFallDistance() {
		super.shouldPredictFreeFallDistance(UNDER_TEST);
	}

	@Test
	@Override
	public void computeAvgErrorTermForFreefall() {
		super.computeAvgErrorTermForFreefall(UNDER_TEST);
	}

	@Test
	@Override
	public void computeAvgErrorTermForOscillator() {
		super.computeAvgErrorTermForOscillator(UNDER_TEST);
	}

}