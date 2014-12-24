package pl.bmaraszek.IntegratorTest;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;

import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.forceGenerators.ForceGenerator2D;
import pl.bmaraszek.integrators.Integrator;
import pl.bmaraszek.integrators.VelocityVerletIntegrator;
import pl.bmaraszek.math.Vector2D;

public abstract class IntegratorTest {

	/* time of simulation in seconds */
	protected static final double TIME_OF_SIMULATION = 10;
	/* timeStep dt in seconds <= 1 */
	protected static double TIME_STEP = 0.01;

	protected ForceGenerator2D dummyForceGenerator;
	protected ForceGenerator2D dummySpringForceGenerator;
	protected Vertex v;
	protected Vertex accurate;
	protected List<Double> distances;
	protected Integrator integrator;
	protected double totalError = 0;
	private Integrator velocityVerlet = new VelocityVerletIntegrator();

	@Before
	public void setUp() throws Exception {

		v = new Vertex();
		v.getPosition().set(0.0, 0.0);

		distances = new ArrayList<Double>();

		dummyForceGenerator = new ForceGenerator2D() {
			private Vector2D force = new Vector2D(0, 10);

			@Override
			public Vector2D generateForce(Vertex v, double dt) {
				return force;
			}
		};

		dummySpringForceGenerator = new ForceGenerator2D() {
			private Vector2D force = new Vector2D(0, 0);
			double k = -5.0;

			@Override
			public Vector2D generateForce(Vertex v, double dt) {
				double x = v.getPosition().getX() * k;
				double y = v.getPosition().getY() * k;
				force.set(x, y);
				return force;
			}
		};

	}

	protected abstract void shouldPredictFreeFallDistance();

	protected abstract void computeAvgErrorTermForFreefall();

	protected abstract void computeAvgErrorTermForOscillator();

	protected void shouldPredictFreeFallDistance(Integrator underTest) {
		System.out.println();
		System.out.println(underTest + " -- Freefall - t = 10 [s]; dt = 0,01 [s] --");
		SimulationData data = FreeFall(underTest, 0.01);
		data.print();
		assertAccuracy(data, 5);
	}

	protected void computeAvgErrorTermForFreefall(Integrator underTest) {
		System.out.println();
		System.out.println(underTest + " -- Freefall Error(dt) --");

		for (double dt = 0.01; dt <= 1; dt += 0.01) {
			SimulationData data = FreeFall(underTest, dt);
			System.out.printf("%f\n", data.avgErrorTerm);
		}
	}

	protected void computeAvgErrorTermForOscillator(Integrator underTest) {
		for (double dt = 0.01; dt <= 1.0; dt *= 10) {
			System.out.println();
			System.out.println(underTest + " -- Oscillator - t = 10 [s]; dt = " + dt + " [s] --");

			SimulationData data = Oscillator(underTest, dt);
			for (Double d : data.errorTerms) {
				System.out.printf("%f\n", Math.abs(d));
			}
		}
	}

	/**
	 * 
	 * @param underTest
	 *            <code>Integrator</code> instance under test.
	 * @param dt
	 *            The timestep.
	 * @return <code>SimulationData</code> containing position, error terms and
	 *         average error for every full second of this simulation.
	 */
	protected SimulationData FreeFall(Integrator underTest, double dt) {
		// given
		SimulationData result = new SimulationData();
		v = new Vertex();
		TIME_STEP = dt;
		integrator = underTest;
		// when

		double integrationSteps = TIME_OF_SIMULATION / TIME_STEP;
		int stepsPerSecond = (int) (1 / TIME_STEP);
		/* Perform integration steps (up to time [s]) */
		int j = 0;
		while (j <= integrationSteps) {
			++j;
			v.clearForceAccum();
			v.addForce(dummyForceGenerator.generateForce(v, TIME_STEP));
			integrator.integrate(v, TIME_STEP);
			// For every full second add the Y position to list
			if (j % stepsPerSecond == 0) {
				distances.add(v.getPosition().getY());
				double realDistance = Math.pow(j * TIME_STEP, 2) * 10 / 2;
				double error = 100 * (Math.abs(v.getPosition().getY() - realDistance) / realDistance);
				totalError += error;
				result.yPositions.add(v.getPosition().getY());
				result.errorTerms.add(error);
			}
		}

		result.avgErrorTerm = totalError / distances.size();
		return result;
	}

	protected SimulationData Oscillator(Integrator underTest, double dt) {
		// given
		SimulationData result = new SimulationData();
		v = new Vertex();
		/* Do not forget to set the start distance, or the spring won't work! */
		v.getPosition().set(5, 5);
		accurate = new Vertex();
		accurate.getPosition().set(5, 5);
		TIME_STEP = dt;
		integrator = underTest;
		// when

		double integrationSteps = TIME_OF_SIMULATION / TIME_STEP;
		/* Perform integration steps (up to time [s]) */
		int j = 0;
		while (j <= integrationSteps) {
			++j;
			v.clearForceAccum();
			v.addForce(dummySpringForceGenerator.generateForce(v, TIME_STEP));
			integrator.integrate(v, TIME_STEP);
			accurate.clearForceAccum();
			accurate.addForce(dummySpringForceGenerator.generateForce(accurate, TIME_STEP));
			velocityVerlet.integrate(accurate, TIME_STEP);
			// For every step add the Y position to list
			distances.add(v.getPosition().getY());
			double realDistance = accurate.getPosition().getY();
			double error = 100 * (Math.abs(v.getPosition().getY() - realDistance) / realDistance);
			totalError += error;
			result.yPositions.add(v.getPosition().getY());
			result.errorTerms.add(error);
		}

		result.avgErrorTerm = totalError / distances.size();
		return result;
	}

	/**
	 * <p>
	 * Checks if the data fulfills desired accuracy.
	 * </p>
	 * <p>
	 * Calling FreeFallSimulation() of other simulation methods returns a
	 * SimulationData object. After receiving the SimulationData we can assert a
	 * certain accuracy on its every step. The accuracy is given in percents
	 * [%], so calling <code>assertAccuracy(someData, 5)</code> checks if every
	 * error term of the simulation is less than 5%.
	 * </p>
	 * 
	 * @param data
	 *            A SimulationData containing error terms to check.
	 * @param accuracy
	 *            The desired accuracy in [%]
	 * 
	 */
	protected void assertAccuracy(SimulationData data, double accuracy) {
		for (int i = 0; i < data.errorTerms.size(); ++i) {
			Assert.assertTrue(data.errorTerms.get(i) < accuracy);
		}
	}

}
