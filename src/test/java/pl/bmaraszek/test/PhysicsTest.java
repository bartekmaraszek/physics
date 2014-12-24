package pl.bmaraszek.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.bmaraszek.Physics;

/**
 * @author bama
 *
 */
public class PhysicsTest {
	
	private int WIDTH = 600;
	private int HEIGHT = 450;
	private Physics world;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		world = new Physics(600, 450, 12);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldUpdateForcesForEveryParticle() {
		//world.updateForces();
		
	}
	
	@Test
	public void shouldInvokeIntegratorForEveryParticle() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldEnforceConstraintsForEveryEdge() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldDetectCollisions() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldProcessCollisions() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldRenderTheScene() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldAddNewBodyIfLimitNotReached() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldNotAddNewBodyIfLimitReached() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldAddNewEdgeIfLimitNotReached() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldNotAddNewEdgeIfLimitReached() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldAddNewVertexIfLimitNotReached() {
		fail("Not yet implemented");
	}
	
	@Test
	public void shouldNotAddNewVertexIfLimitReached() {
		fail("Not yet implemented");
	}

}
