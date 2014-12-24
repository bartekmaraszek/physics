package pl.bmaraszek.integrators;

import pl.bmaraszek.PhysicsBodies.Vertex;

/**
 * @author Bartek Maraszek
 */
public class EulerIntegrator implements Integrator {

	@Override
	public void integrate(Vertex vertex, double dt) {
		
		vertex.getPosition().addScaledVector(vertex.getVelocity(), dt);
		vertex.getVelocity().addScaledVector(vertex.getAcceleration(), dt);
		vertex.getAcceleration().clear();
		vertex.getAcceleration().addScaledVector(vertex.getForceAccum(), vertex.getInverseMass());
		
	}

	@Override
	public String toString() {
		return "EulerIntegrator []";
	}
	
	

}
