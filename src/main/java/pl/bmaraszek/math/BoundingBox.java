package pl.bmaraszek.math;

/**
 * @author Bartek Maraszek
 */
public class BoundingBox {
	private double minX;
	private double minY;
	private double maxY;
	private double maxX;
	
	public BoundingBox(double minX, double minY, double maxX, double maxY) {
		set(minX, minY, maxX, maxY);
	}
	
	public void set(double minX, double minY, double maxX, double maxY){
		//Number.checkIfLegal(minX, minY, maxX, maxY);
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		
	}
	
	public double getMaxX() {
		return maxX;
	}
	
	public double getMaxY() {
		return maxY;
	}
	
	public double getMinX() {
		return minX;
	}
	
	public double getMinY() {
		return minY;
	}
	
	public void setMaxX(double maxX) {
		//Number.checkIfLegal(maxX);
		this.maxX = maxX;
	}
	
	public void setMaxY(double maxY) {
		//Number.checkIfLegal(maxY);
		this.maxY = maxY;
	}
	
	public void setMinX(double minX) {
		//Number.checkIfLegal(minX);
		this.minX = minX;
	}
	
	public void setMinY(double minY) {
		//Number.checkIfLegal(minY);
		this.minY = minY;
	}
	
	@Override
	public String toString() {
		return "BoundingBox [minX=" + minX + ", minY=" + minY + ", maxY=" + maxY + ", maxX=" + maxX + "]";
	}
}
