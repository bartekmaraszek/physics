package pl.bmaraszek.math;

/**
 * @author Bartek Maraszek
 *         <p>
 *         MinMax structure contains two double values which cannot be Infinite
 *         or NaN. It has no setters and therefore is immutable.
 *         </p>
 */
public class MinMax {
	private double min;
	private double max;
	
	public MinMax(){
		this(0.0, 0.0);
	}

	public MinMax(double min, double max) {
		
		//Number.checkIfLegal(min, max);
		
		this.min = min;
		this.max = max;
	}
	
	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}

	public void setMax(double max){
		//Number.checkIfLegal(max);
		this.max = max;
	}

	public void setMin(double min){
		//Number.checkIfLegal(min);
		this.min = min;
	}

	@Override
	public String toString() {
		return "MinMax [min=" + min + ", max=" + max + "]";
	}
}
