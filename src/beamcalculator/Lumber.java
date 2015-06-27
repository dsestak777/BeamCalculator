package beamcalculator;

public class Lumber {
	
	//declare instance variables
	private String name;
	private double sectionModulus;
	private double area;
	private double weightPerFoot;
	private double sizeFactor;
	private double momentInertia;
	
	//no argument constructor
	public Lumber () {
		
		
	}
	
	//full argument constructor
	public Lumber (String name, double sectionModulus, double area, double weightPerFoot, double cF, double momI) {
		
		this.name = name;
		this.sectionModulus = sectionModulus;
		this.area = area;
		this.weightPerFoot = weightPerFoot;
		this.sizeFactor = cF;
		this.momentInertia = momI;
	}
	
	//toString method
	public String toString () {
		
		return "Size= " + name + " sectionModulus= " + sectionModulus + " cross sectional area = " +
					area + " weight per foot= " + weightPerFoot+ " size factor = " + sizeFactor +
					" moment of inertia = " + momentInertia;
		
	}

	//getters & setters
	
	public double getMomentInertia() {
		return momentInertia;
	}

	public void setMomentInertia(double momentInertia) {
		this.momentInertia = momentInertia;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSectionModulus() {
		return sectionModulus;
	}

	public void setSectionModulus(double sectionModulus) {
		this.sectionModulus = sectionModulus;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getWeightPerFoot() {
		return weightPerFoot;
	}

	public void setWeightPerFoot(double weightPerFoot) {
		this.weightPerFoot = weightPerFoot;
	}

	public double getSizeFactor() {
		return sizeFactor;
	}

	public void setSizeFactor(double sizeFactor) {
		this.sizeFactor = sizeFactor;
	}
	
	
	
}
