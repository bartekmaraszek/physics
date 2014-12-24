/**
 * 
 */
package pl.bmaraszek.PhysicsBodies;

import pl.bmaraszek.Physics;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 *         <p>
 *         The Vertex class represents a single particle in the physics system.
 *         Vertices build Edges and Bodies.
 *         </p>
 * 
 */
public class Vertex {

	/**
	 * Stores a reference to actual physics simulator.
	 */
	private Physics world = null;
	/**
	 * Stores a reference to a Body object that contains this Vertex.
	 */
	private Body body = null;
	/**
	 * Stores the current vertex position as a Vector2D. Position is used by all
	 * integration methods.
	 */
	private Vector2D position = new Vector2D(0, 0);
	/**
	 * Stores the sum of all forces acting upon this Vertex as a Vector2D
	 * object. During each step of the physics simulation:
	 * <ul>
	 * <li />Forces are updated - all forces acting upon a Vertex are added and
	 * stored in the Vertex' force Accum.
	 * <li />The Integrator algorithm is run on the set of all vertices.
	 * <li />The force accumulator is cleared.
	 * </ul>
	 */
	private Vector2D forceAccum = new Vector2D(0, 0);
	/**
	 * Next step forceAccum needed for Velocity Verlet Algorithm.
	 */
	private Vector2D newForceAccum = new Vector2D(0, 0);
	/**
	 * Stores the current velocity value as a Vector2D. Velocity is used by
	 * Euler and RK4 integrators.
	 */
	private Vector2D velocity = new Vector2D(0, 0);
	/**
	 * Stores the old position (from previous step) as a Vector2D object. The
	 * old position is used by Verlet Integrator.
	 */
	private Vector2D oldPosition = new Vector2D(0, 0);
	/**
	 * Stores the current velocity of this Vertex as a Vector2D object.
	 */
	private Vector2D acceleration = new Vector2D(0, 0);
	/**
	 * Stores the inverse mass of this Vertex. There are several reasons for
	 * using inverse mass instead of just mass:
	 * <ul>
	 * <li />It simplifies the division: <b>a = F / m</b> to <b>a = F *
	 * (1/m)</b> (1)
	 * <li />Because of (1) it does not allow the division by 0 error.
	 * <li />It does not allow 0 mass, however it does allow infinite mass when
	 * setting <b>(1/m) = 0</b>, therefore enabling easy creation of immovable
	 * objects.
	 * </ul>
	 */
	private double inverseMass = 1;

	/**
	 * Creates the default Vertex with unit mass.
	 * 
	 * @param world
	 *            Parent Physics object.
	 * @param body
	 *            Parent Body object. Body can be set to null for a particle
	 *            simulation.
	 * @param position
	 *            The starting position of this Vertex.
	 */
	public Vertex(Physics world, Body body, Vector2D position) {
		this(world, body, position, 1.0);
	}

	public Vertex() {
	}

	/**
	 * Creates a new Vertex with given position and mass.
	 * 
	 * @param world
	 *            Parent Physics object.
	 * @param body
	 *            Parent Body object. Body can be set to null for a particle
	 *            simulation.
	 * @param position
	 *            The starting position of this Vertex.
	 * @param inverseMass
	 *            The inverse mass of the Vertex in units. Can be set to 0 for
	 *            immovable objects.
	 */
	public Vertex(Physics world, Body body, Vector2D position, double inverseMass) {
/*		if (world == null || position == null) {
			throw new IllegalArgumentException("Vertex constructor arguments must not be null!");
		}*/
/*		if (inverseMass < 0) {
			throw new IllegalArgumentException("InverseMass must not be less than zero!");
		}*/
		if (Double.isInfinite(inverseMass) || Double.isNaN(inverseMass)) {
			throw new IllegalArgumentException("InverseMass must not be Infinite nor NaN!");
		}
		this.world = world;
		world.addVertex(this);
		this.body = body;
		body.addVertex(this);
		this.position = position;
		this.oldPosition = new Vector2D(position);
		this.inverseMass = inverseMass;
	}

	public Vertex(Vertex v) {
		this(v.world, v.body, new Vector2D(v.position), v.inverseMass);
		this.oldPosition = new Vector2D(v.oldPosition);
		this.velocity = new Vector2D(v.velocity);
		this.acceleration = new Vector2D(v.acceleration);
		this.forceAccum = new Vector2D(v.forceAccum);
		this.newForceAccum = new Vector2D(v.newForceAccum);
	}

	/**
	 * Adds a new force to forceAccum.
	 * 
	 * @see forceAccum
	 * @param force
	 *            The force to add as a Vector2D
	 */
	public void addForce(Vector2D force) {
		forceAccum.add(force);
	}

	/**
	 * Clears the forceAccum of this Vertex.
	 */
	public void clearForceAccum() {
		forceAccum.clear();
	}
	
	public void clearNewForceAccu(){
		newForceAccum.clear();
	}

	public Vector2D getAcceleration() {
		return acceleration;
	}

	public Body getBody() {
		return body;
	}

	public Vector2D getForceAccum() {
		return forceAccum;
	}

	public double getInverseMass() {
		return inverseMass;
	}

	public Vector2D getOldPosition() {
		return oldPosition;
	}

	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public Physics getWorld() {
		return world;
	}

	@Override
	public String toString() {
		return "Vertex [position=" + position + ", forceAccum=" + forceAccum + ", velocity=" + velocity
				+ ", oldPosition=" + oldPosition + ", acceleration=" + acceleration + ", inverseMass=" + inverseMass
				+ "]";
	}
}
