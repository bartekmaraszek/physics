/**
 * 
 */
package pl.bmaraszek.PhysicsBodies;

import pl.bmaraszek.Physics;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 *         <p>
 *         Represents an edge connecting two vertices.
 *         </p>
 */
public class Edge {

	private final Vertex v1;
	private final Vertex v2;

	/**
	 * The distance between the two vertices bounding this Edge.
	 */
	private final double length;

	/**
	 * Used for collision optimization. See Physics::detectCollision()
	 */
	private final boolean boundary;

	/**
	 * The parent Body object that contains this Edge.
	 */
	private final Body body;

	/**
	 * Create the default Edge with boundary = true.
	 * 
	 * @param world
	 *            The physics engine.
	 * @param body
	 *            The parent Body object that contains this Edge.
	 * @param v1
	 *            The first bounding Vertex.
	 * @param v2
	 *            The second bounding Vertex.
	 */
	public Edge(Physics world, Body body, Vertex v1, Vertex v2) {
		this(world, body, v1, v2, true);
	}

	/**
	 * Creates a new Edge.
	 * 
	 * @param world
	 *            The physics engine.
	 * @param body
	 *            The parent Body object that contains this Edge.
	 * @param v1
	 *            The first bounding Vertex.
	 * @param v2
	 *            The second bounding Vertex.
	 * @param boundary
	 *            For collision optimization. When in doubt set this to true.
	 */
	public Edge(Physics world, Body body, Vertex v1, Vertex v2, boolean boundary) {
/*		if (world == null || v1 == null || v2 == null) {
			throw new IllegalArgumentException("Null values in Edge constructor not accepted");
		}
		if (v1 == v2) {
			throw new IllegalArgumentException(
					"Cannot instantiate new Edge between one Vertex. Edge constructor must be passed two different Vertex objects.");
		}*/
		this.v1 = v1;
		this.v2 = v2;
		/*
		 * Calculate the distance between two bounding vertices:
		 */
		length = Math.hypot(v2.getPosition().getX() - v1.getPosition().getX(), v2.getPosition().getY()
				- v1.getPosition().getY());
		this.boundary = boundary;
		this.body = body;
		body.addEdge(this);
		world.addEdge(this);
	}

	public Vector2D getCurrentDistanceBetweenVertices() {
		return new Vector2D(v2.getPosition().getX() - v1.getPosition().getX(), v2.getPosition().getY()
				- v1.getPosition().getY());
	}

	public Body getBody() {
		return body;
	}
	
	public boolean getBoundary(){
		return boundary;
	}

	public double getLength() {
		return length;
	}

	public Vertex getV1() {
		return v1;
	}

	public Vertex getV2() {
		return v2;
	}

	public boolean isBoundary() {
		return boundary;
	}

	@Override
	public String toString() {
		return "Edge [v1=" + v1 + ", v2=" + v2 + ", length=" + length + ", boundary=" + boundary + ", body=" + body
				+ "]";
	}
}
