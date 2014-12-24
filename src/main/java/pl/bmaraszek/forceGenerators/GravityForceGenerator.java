package pl.bmaraszek.forceGenerators;

import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 *
 */
public class GravityForceGenerator implements ForceGenerator2D {
	
	private Vector2D force;
	
	public GravityForceGenerator(Vector2D gravityAcceleration){
		force = gravityAcceleration;
	}

	/**
	 * @see pl.bmaraszek.forceGenerators.ForceGenerator2D#generateForce()
	 */
	@Override
	public Vector2D generateForce(Vertex v, double dt) {
		if(v.getInverseMass() == 0){
			return force;
		}
		return new Vector2D(force).divide(v.getInverseMass());
	}

}
