package pl.bmaraszek.integrators;

import pl.bmaraszek.PhysicsBodies.Vertex;

/**
 * @author Bartek Maraszek
 */
public class VelocityVerletIntegrator implements Integrator {

	@Override
	public void integrate(Vertex v, double dt) {
		
		v.getAcceleration().clear();
		v.getAcceleration().addScaledVector(v.getForceAccum(), v.getInverseMass());
		
		v.getVelocity().addScaledVector(v.getAcceleration(), dt);
		
		v.getPosition().addScaledVector(v.getVelocity(), dt);
		
	}
	
	@Override
	public String toString(){
		return "VelocityVerletIntegrator []";
	}

}
