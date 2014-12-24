package pl.bmaraszek.integrators;

import pl.bmaraszek.PhysicsBodies.Vertex;

/**
 * @author Bartek Maraszek
 */
public interface Integrator {
	void integrate(Vertex v, double dt);
}
