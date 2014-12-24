package pl.bmaraszek.MathTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.bmaraszek.math.Vector2D;

public class Vector2DTest {

	private Vector2D v1;
	private Vector2D v2;
	private Vector2D v3;

	@Before
	public void setUp() throws Exception {
		v1 = new Vector2D(1.5, 2);
		v2 = new Vector2D(2, 2.5);
		v3 = new Vector2D(v2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfArgumentIsNaN() {
		v1 = new Vector2D(Double.NaN, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfArgumentIsPositiveInfinity() {
		v1 = new Vector2D(Double.POSITIVE_INFINITY, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfArgumentIsNegativeInfinity() {
		v1 = new Vector2D(Double.NEGATIVE_INFINITY, 3);
	}

	@Test
	public void testInvert() {
		v2.invert();
		Assert.assertEquals("Vector2D inversion failed", new Vector2D(-2, -2.5), v2);
	}

	@Test
	public void testMagnitude() {
		Double m = v1.magnitude();
		Assert.assertTrue("Magnitude error", Math.abs(new Double(2.5) - m) < 0.0001);

	}

	@Test
	public void testSquareMagnitude() {
		Double m = v1.squareMagnitude();
		Assert.assertEquals(new Double(6.25), m);
	}

	@Test
	public void testClear() {
		v1.clear();
		Assert.assertEquals(new Vector2D(0, 0), v1);
	}

	@Test
	public void testNormalize() {
		v1.normalize();
		Assert.assertTrue(v1.magnitude() == 1);
		Assert.assertTrue(Math.abs((1.5 * 1 / 2.5) - v1.getX()) < 0.0001);
	}

	@Test
	public void testAdd() {
		v1.add(v2);
		Assert.assertTrue(v1.equals(new Vector2D(3.5, 4.5)));
	}

	@Test
	public void testSubstract() {
		v1.substract(v2);
		Assert.assertTrue(v1.equals(new Vector2D(-0.5, -0.5)));
	}

	@Test
	public void testMultiply() {
		v1.multiply(3);
		Assert.assertTrue(v1.equals(new Vector2D(4.5, 6.0)));
	}

	@Test
	public void testComponentProduct() {
		v3 = v1.componentProduct(v2);
		Assert.assertTrue(v3.equals(new Vector2D(3.0, 5.0)));
	}

	@Test
	public void testDot() {
		Double v3 = v1.dot(v2);
		Assert.assertTrue(new Double(8).equals(v3));
	}

}
