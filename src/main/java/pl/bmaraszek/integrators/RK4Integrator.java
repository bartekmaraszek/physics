package pl.bmaraszek.integrators;

import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 */
public class RK4Integrator implements Integrator {
	
	private Vertex evaluate(Vertex initial, double dt, Vertex derivative){
		Vertex state = new Vertex();
		state.getPosition().add(initial.getPosition());
		state.getPosition().addScaledVector(derivative.getVelocity(), dt);
		state.getVelocity().add(initial.getVelocity());
		state.getVelocity().addScaledVector(derivative.getAcceleration(), dt);
		
		Vertex output = new Vertex();
		
		output.getVelocity().add(state.getVelocity());
		initial.getAcceleration().clear().add(initial.getForceAccum().multiply(initial.getInverseMass()));
		output.getAcceleration().add(initial.getAcceleration());
		
		return output;
	}

	@Override
	public void integrate(Vertex state, double dt) {
		
		Vertex a = evaluate(state, 0.0, new Vertex());
		Vertex b = evaluate(state, 0.5 * dt, a);
		Vertex c = evaluate(state, 0.5 * dt, b);
		Vertex d = evaluate(state, dt, c);
		
		final Vector2D dxdt = b.getVelocity().add(c.getVelocity()).multiply(2.0).add(a.getVelocity())
				.add(d.getVelocity()).multiply(1.0 / 6.0);
		final Vector2D dvdt = b.getAcceleration().add(c.getAcceleration()).multiply(2.0).add(a.getAcceleration())
				.add(d.getAcceleration()).multiply(1.0 / 6.0);
		
		state.getPosition().addScaledVector(dxdt, dt);
		state.getVelocity().addScaledVector(dvdt, dt);
		
	}
	
	@Override
	public String toString() {
		return "RK4Integrator []";
	}

}
