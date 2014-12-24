/**
 * Package pl.bmaraszek.math contains Vectors and other utilities used for math calculations.
 */
package pl.bmaraszek.math;

/**
 * @author Bartek Maraszek
 *         <p>
 *         Vector2D represents a 2-dimensional vector.
 *         </p>
 */
public class Vector2D {

	private double x;
	private double y;

	/**
	 * Default constructor. Constructs new Vector2D object with (0, 0)
	 * coordinates.
	 */
	public Vector2D() {
		this(0, 0);
	}

	/**
	 * Constructs new Vector2D object. The Vector2D components can be neither
	 * NaN nor Infinite. This constructor throws IllegalArgumentException if any
	 * of its arguments is NaN or Infinite.
	 * 
	 * @param x
	 *            the x component of this Vector2D.
	 * @param y
	 *            the y component of this Vector2D.
	 */
	public Vector2D(final double x, final double y) {
		this.set(x, y);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param vector
	 *            Vector2D to copy.
	 */
	public Vector2D(final Vector2D vector) {
		this(vector.getX(), vector.getY());
	}

	/**
	 * Adds another Vector2D to this Vector2D.
	 * 
	 * @param vector
	 *            Vector2D to add.
	 * @return Reference to the modified instance of Vector2D.
	 */
	public Vector2D add(final Vector2D vector) {
		this.x += vector.getX();
		this.y += vector.getY();
		return this;
	}

	/**
	 * Adds a scaled vector to this vector. Does not change its arguments.
	 * 
	 * @param vector
	 *            Vector2D to add.
	 * @param scale
	 *            Scale factor.
	 * @return Reference to the modified instance of Vector2D.
	 */
	public Vector2D addScaledVector(final Vector2D vector, final double scale) {
		this.x += vector.getX() * scale;
		this.y += vector.getY() * scale;
		return this;
	}

	/**
	 * Sets this Vector coordinates to 0.
	 * 
	 * @return reference to this object.
	 */
	public Vector2D clear() {
		this.x = 0;
		this.y = 0;
		return this;
	}

	/**
	 * Component product of Vector2D(a, b) and Vector2D(c, d) is new
	 * Vector2D(a*c, b*d).
	 * 
	 * @param vector
	 *            Vector2D to multiply according to component product rule.
	 * @return new Vector2D object being the result of component multiplication.
	 */
	public Vector2D componentProduct(final Vector2D vector) {
		return new Vector2D(x * vector.getX(), y * vector.getY());
	}

	/**
	 * Calculates and returns the dot product (scalar product) of this vector
	 * with the given vector. Mind that a.dot(b) == a.length() * b.lenght() *
	 * cosine(D), where D is the angle between a and b. Also: D = arccosine(
	 * a.normalize().dot(b.normalize()) ); so D = arccosine( a.dot(b) /
	 * (a.lenght() * b.length()) ); If a.dot(b) == 0 then a and b are
	 * perpendicular.
	 * 
	 * @param vector
	 *            A Vector2D object to multiply using the dot product rule.
	 * @return A double being the dot multiplication product.
	 */
	public double dot(final Vector2D vector) {
		return (this.x * vector.getX() + this.y * vector.getY());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	/**
	 * Public accessor method.
	 * 
	 * @return the x component of this Vector2D.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Public accessor method.
	 * 
	 * @return the y component of this Vector2D.
	 */
	public double getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Inverses the vector.
	 * 
	 * @return Reference to the modified instance of Vector2D which coordinates are [-x, -y].
	 */
	public Vector2D invert() {
		this.x = -x;
		this.y = -y;
		return this;
	}

	/**
	 * Returns the vector's magnitude (length).
	 * 
	 * @return the vector magnitude (length).
	 */
	public double magnitude() {
		return Math.hypot(x, y);
	}

	/**
	 * Multiplies vector by number.
	 * 
	 * @param number
	 *            Number to multiply.
	 * @return Reference to the modified instance of Vector2D.
	 */
	public Vector2D multiply(final double number) {
		this.x *= number;
		this.y *= number;
		return this;
	}

	/**
	 * Divises the Vector2D by scalar.
	 * 
	 * @param number
	 *            The divisor.
	 * @return Reference to the modified instance of Vector2D.
	 */
	public Vector2D divide(final double number) {
		this.x /= number;
		this.y /= number;
		return this;
	}

	/**
	 * Normalizes the vector.
	 */
	public Vector2D normalize() {
		double l = 1.0 / Math.hypot(x, y);
		x *= l;
		y *= l;
		return this;
	}

	public Vector2D set(final double x, final double y) {
		return setX(x).setY(y);
	}

	public Vector2D set(final Vector2D v) {
		return set(v.getX(), v.getY());
	}

	public Vector2D setX(final double x) {
		// Number.checkIfLegal(x);
		this.x = x;
		return this;
	}

	public Vector2D setY(final double y) {
		// Number.checkIfLegal(y);
		this.y = y;
		return this;
	}

	/**
	 * Returns the vector's magnitude (length) squared. The same as
	 * Math.pow(magnitude(), 2);
	 * 
	 * @return the vector's magnitude squared.
	 */
	public double squareMagnitude() {
		return Math.pow(x, 2) + Math.pow(y, 2);
	}

	/**
	 * Substracts another vector from this vector.
	 * 
	 * @param vector
	 *            Vector2D to substract.
	 * @return Reference to the modified instance of Vector2D.
	 */
	public Vector2D substract(final Vector2D vector) {
		this.x -= vector.getX();
		this.y -= vector.getY();
		return this;
	}

	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}
}
