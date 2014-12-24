package pl.bmaraszek.integrators;

/**
 * @author Bartek Maraszek
 * 
 */
public class IntegratorFactory {

	public enum IntegratorType {
		EULER, VERLET, VELOCITY_VERLET, RK4;
	}

	public static Integrator create(IntegratorType type) {

		switch (type) {
		case EULER:
			return new EulerIntegrator();
		case VERLET:
			return new VerletIntegrator();
		case VELOCITY_VERLET:
			return new VelocityVerletIntegrator();
		case RK4:
			return new RK4Integrator();
		default:
			return new VerletIntegrator();
		}

	}
}
