/**
 * 
 */
package pl.bmaraszek.forceGenerators;

import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 *
 */
public interface ForceGenerator2D {
	public Vector2D generateForce(Vertex v, double dt);
}
