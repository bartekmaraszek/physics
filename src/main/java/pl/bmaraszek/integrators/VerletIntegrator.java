package pl.bmaraszek.integrators;

import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 */
public class VerletIntegrator implements Integrator {

	@Override
	public void integrate(Vertex v, double dt) {

		Vector2D curPos = new Vector2D(v.getPosition());
		
		v.getAcceleration().clear().addScaledVector(v.getForceAccum(), v.getInverseMass());

		v.getPosition().multiply(2).substract(v.getOldPosition())
				.addScaledVector(v.getAcceleration(), Math.pow(dt, 2));

		v.getOldPosition().set(curPos);
	}
	
	@Override
	public String toString() {
		return "VerletIntegrator []";
	}

}
