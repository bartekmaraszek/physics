package pl.bmaraszek.math;

/**
 * @author Bartek Maraszsek
 *         <p>
 *         A helper class designed to check function arguments validity.
 *         </p>
 * 
 */
public class Number {
	/**
	 * Throws exception if at least one of given numbers is Infinite or NaN.
	 * 
	 * @param numbers
	 *            Numbers to check
	 */
	public static void checkIfLegal(double... numbers) {

		for (double d : numbers) {
			if (Double.isInfinite(d)) {
				throw new IllegalArgumentException("The double argument must not be infinite!");
			}

			if (Double.isNaN(d)) {
				throw new IllegalArgumentException("The double argument must not be NaN!");
			}
		}

	}

}