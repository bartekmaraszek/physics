package pl.bmaraszek.forceGenerators;

import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 * 
 */
public class ForceGeneratorFactory {

	public enum ForceGeneratorType {
		GRAVITY, SPRING
	}

	public static ForceGenerator2D createGravity(Vector2D gravityAcceleration) {
		return new GravityForceGenerator(gravityAcceleration);
	}

	public ForceGenerator2D createSpringForce(double springCoefficient) {
		return new SpringForceGenerator(springCoefficient);
	}

}
