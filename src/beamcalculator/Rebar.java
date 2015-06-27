package beamcalculator;

public class Rebar {

	//declare instance variables
	private String barSize;
	private double diameter;
	private double area;
	
	//no argument constructor
	public Rebar () {
		
	}
	
	//full argument constructor
	public Rebar (String barSize, double diameter, double area) {
		
		this.barSize = barSize;
		this.diameter = diameter;
		this.area = area;
		
	}
	
	//toString methods
	public String toString () {
		
		return " bar size = " + barSize + " diameter = " + diameter + " area = " + area;
		
	}
	
	//getters & setters
	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}
	
	
	
}
