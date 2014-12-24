/**
 * 
 */
package pl.bmaraszek.integrators;

import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.Vector2D;

/**
 * @author bama
 *
 */
public class MidpointIntegrator implements Integrator {
	
	private Vector2D getAcceleration(){
		return new Vector2D(0.0, 10.0);
	}
	
	private Vertex evaluate(Vertex initial, double dt, Vertex derivative){
		Vertex state = new Vertex();
		state.getPosition().add(initial.getPosition());
		state.getPosition().addScaledVector(derivative.getVelocity(), dt);
		state.getVelocity().add(initial.getVelocity());
		state.getVelocity().addScaledVector(derivative.getAcceleration(), dt);
		
		Vertex output = new Vertex();
		
		output.getVelocity().add(state.getVelocity());
		output.getAcceleration().add(getAcceleration());
		
		return output;
	}

	@Override
	public void integrate(Vertex state, double dt) {
		
		Vertex a = evaluate(state, 0.0, new Vertex());
		Vertex b = evaluate(state, 0.5 * dt, a);
		
		final Vector2D dxdt = b.getVelocity().multiply(2.0);
		final Vector2D dvdt = b.getAcceleration().multiply(2.0);
		
		state.getPosition().addScaledVector(dxdt, dt);
		state.getVelocity().addScaledVector(dvdt, dt);

	}

}
