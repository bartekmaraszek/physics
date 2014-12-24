package pl.bmaraszek.PhysicsBodies;

import pl.bmaraszek.Physics;
import pl.bmaraszek.math.BoundingBox;
import pl.bmaraszek.math.MinMax;
import pl.bmaraszek.math.Vector2D;

/**
 * @author Bartek Maraszek
 */
public class Body {

	private BoundingBox boundingBox = new BoundingBox(0, 0, 0, 0);
	private int vertexCount = 0;
	private int edgeCount = 0;
	private Edge[] edges;
	private Vertex[] vertices;

	public int getVertexCount() {
		return vertexCount;
	}

	/*
	 * TODO: Implement this.
	 */
	public double getMass() {
		return 1.0;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public void setEdgeCount(int edgeCount) {
		this.edgeCount = edgeCount;
	}

	public void setCenterOfMass(Vector2D centerOfMass) {
		this.centerOfMass = centerOfMass;
	}

	public int getEdgeCount() {
		return edgeCount;
	}

	public BoundingBox getBox() {
		return boundingBox;
	}

	public Body(Physics world) {
		world.addBody(this);
		vertices = new Vertex[world.getMaxVertices()];
		edges = new Edge[world.getMaxVertices() / 2];
	}

	private Vector2D centerOfMass = new Vector2D();

	public void addEdge(Edge edge) {
		edges[edgeCount++] = edge;
	}

	public void addVertex(Vertex vertex) {
		vertices[vertexCount++] = vertex;
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public Edge[] getEdges() {
		return edges;
	}

	/**
	 * Recalculates the center of mass for this object. As for now it doesn't
	 * support different vertex weights. TODO: Support different vertex weights.
	 */
	public void calculateCenterOfMass() {
		centerOfMass.clear();

		double arbitrarilyLargeValue = 100000.0;

		boundingBox.set(arbitrarilyLargeValue, arbitrarilyLargeValue, -arbitrarilyLargeValue, -arbitrarilyLargeValue);

		Vector2D temp;
		for (int i = 0; i < vertexCount; ++i) {
			centerOfMass.add(vertices[i].getPosition());

			temp = vertices[i].getPosition();

			boundingBox.set(Math.min(boundingBox.getMinX(), temp.getX()), Math.min(boundingBox.getMinY(), temp.getY()),
					Math.max(boundingBox.getMaxX(), temp.getX()), Math.max(boundingBox.getMaxY(), temp.getY()));

		}

		centerOfMass.divide(vertexCount);
	}

	public Vector2D getCenterOfMass() {
		return centerOfMass;
	}

	/**
	 * <p>
	 * Projects the Body onto given axis. Requires a <b>normalized</b> Vector2D
	 * as argument.
	 * </p>
	 * Given:
	 * <ul>
	 * <li />Vector representing Vertex position
	 * <li />Normalized vector representing axis
	 * </ul>
	 * Performing:
	 * <ul>
	 * <li />position.dot(axis)
	 * </ul>
	 * Results in:
	 * <ul>
	 * <li />A double being the projection of the vertex onto the axis.
	 * </ul>
	 * 
	 * @param axis
	 *            A <b>normalized</b> Vector2D indicating the axis.
	 * @return a MinMax object corresponding to the projection of Body onto
	 *         given axis.
	 */
	public MinMax projectToAxis(Vector2D axis) {

		double dotP = axis.dot(vertices[0].getPosition());

		MinMax data = new MinMax(dotP, dotP);

		for (int i = 0; i < vertexCount; ++i) {
			/*
			 * Project the rest of the vertices onto the axis and extend the
			 * interval to the left/right if necessary.
			 */
			dotP = axis.dot(vertices[i].getPosition());
			data.setMin(Math.min(dotP, data.getMin()));
			data.setMax(Math.max(dotP, data.getMax()));
		}

		return data;
	}

}
