package pl.bmaraszek.PhysicsBodyTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.bmaraszek.Physics;
import pl.bmaraszek.PhysicsBodies.Body;
import pl.bmaraszek.PhysicsBodies.Vertex;
import pl.bmaraszek.math.Vector2D;

public class VertexTest {

	Vector2D testPosition0;
	Vector2D testPosition1;
	Vector2D testPosition2;
	Physics testWorld;
	Body testBody;
	Vertex v;

	@Before
	public void setUp() throws Exception {

		testPosition0 = mock(Vector2D.class);
		when(testPosition0.getX()).thenReturn(0.0);
		when(testPosition0.getY()).thenReturn(0.0);

		testPosition1 = mock(Vector2D.class);
		when(testPosition1.getX()).thenReturn(2.5);
		when(testPosition1.getY()).thenReturn(-3.0);

		testPosition2 = mock(Vector2D.class);
		when(testPosition2.getX()).thenReturn(-3.5);
		when(testPosition2.getY()).thenReturn(5.5);

		testWorld = mock(Physics.class);
		testBody = mock(Body.class);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldAddForceToAccumulator() {
		v = new Vertex(testWorld, testBody, testPosition0);
		Assert.assertTrue(v.getForceAccum().equals(new Vector2D(0, 0)));
		v.addForce(testPosition1);
		v.addForce(testPosition2);
		Assert.assertTrue(v.getForceAccum().equals(new Vector2D(-1.0, 2.5)));
	}

	@Test
	public void shouldClearForceAccum() {
		v = new Vertex(testWorld, testBody, testPosition1);
		v.clearForceAccum();
		Assert.assertTrue(v.getForceAccum().getX() == 0 && v.getForceAccum().getY() == 0);
	}
}
