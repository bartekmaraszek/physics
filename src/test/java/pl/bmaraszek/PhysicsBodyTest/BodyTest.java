package pl.bmaraszek.PhysicsBodyTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.geom.Point2D;
import java.util.Arrays;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import pl.bmaraszek.Physics;
import pl.bmaraszek.PhysicsBodies.Body;
import pl.bmaraszek.PhysicsBodies.Edge;
import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.MinMax;
import pl.bmaraszek.math.Vector2D;

public class BodyTest {

	private Body rectangle, triangle;
	private Vertex v0;
	private Edge e0;
	private Vector2D xAxis, yAxis;

	/**
	 * Creates a new Body with Vertices at given points. Doesn't create Edges.
	 * 
	 * @param points
	 *            Points indicating Vertices position.
	 * @return new Body object with Vertices at given points and no Edges.
	 */
	private Body setPolygon(Point2D.Double... points) {

		Physics physics = mock(Physics.class);
		when(physics.getMaxVertices()).thenReturn(1024);
		Body polygon = new Body(physics);
		Vector2D tempVector;
		Vertex tempVertex;
		/*
		 * Repeat for every Point given: - create a mock for Vector2D - make the
		 * mock return coordinates of given Point - create a mock for Vertex -
		 * set Vertex position to mock Vector2D - add the Vertex to newly
		 * created Body
		 */
		for (Point2D.Double p : points) {
			tempVector = mock(Vector2D.class);
			when(tempVector.getX()).thenReturn(p.x);
			when(tempVector.getY()).thenReturn(p.y);
			tempVertex = mock(Vertex.class);
			when(tempVertex.getPosition()).thenReturn(tempVector);
			polygon.addVertex(tempVertex);
		}

		return polygon;
	}

	@Before
	public void setUp() throws Exception {

		v0 = mock(Vertex.class);
		e0 = mock(Edge.class);

		rectangle = setPolygon(new Point2D.Double(1.0, 1.0), new Point2D.Double(1.0, 4.0),
				new Point2D.Double(5.0, 4.0), new Point2D.Double(5.0, 1.0));

		triangle = setPolygon(new Point2D.Double(1.0, 1.0), new Point2D.Double(2.0, 3.0), new Point2D.Double(-2.0, 3.0));

		/*
		 * TODO: change to mocks.
		 */
		xAxis = new Vector2D(1.0, 0.0);
		yAxis = new Vector2D(0.0, 1.0);
	}

	@Test
	public void shouldAddVertex() {
		rectangle.addVertex(v0);
		Assert.assertTrue(Arrays.asList(rectangle.getVertices()).contains(v0));
	}

	@Test
	public void shouldAddEdge() {
		rectangle.addEdge(e0);
		Assert.assertTrue(Arrays.asList(rectangle.getEdges()).contains(e0));
	}

	@Test
	public void shouldProjectToAxis() {

		/*
		 * Try with a rectangle.
		 */
		MinMax projection = rectangle.projectToAxis(xAxis);
		Assert.assertTrue(projection.getMin() == 1 && projection.getMax() == 5);

		projection = rectangle.projectToAxis(yAxis);
		Assert.assertTrue(projection.getMin() == 1 && projection.getMax() == 4);

		/*
		 * Try with a triangle.
		 */
		projection = triangle.projectToAxis(xAxis);
		Assert.assertTrue(projection.getMin() == -2.0 && projection.getMax() == 2.0);

		projection = triangle.projectToAxis(yAxis);
		Assert.assertTrue(projection.getMin() == 1.0 && projection.getMax() == 3.0);

	}

	@Test
	/**
	 * This test in fact depends on shouldAddVertex. Change this!
	 */
	public void shouldCalculateCenterOfMass() {

		rectangle.calculateCenterOfMass();
		Assert.assertTrue(rectangle.getCenterOfMass().getX() == 3.0 && rectangle.getCenterOfMass().getY() == 2.5);
	}
}
