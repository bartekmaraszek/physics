package pl.bmaraszek.forceGenerators;

import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 */
public class SpringForceGenerator implements ForceGenerator2D {
	
	private Vector2D force = new Vector2D();
	private double k = 0;
	
	public SpringForceGenerator(double springCoefficient){
		k = springCoefficient;
	}

	@Override
	public Vector2D generateForce(Vertex v, double dt) {
		double x = v.getPosition().getX() * k;
		double y = v.getPosition().getY() * k;
		force.set(x, y);
		return force;
	}

}
