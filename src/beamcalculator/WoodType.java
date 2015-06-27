package beamcalculator;

public class WoodType {

	//declare instance variables
	private String name;
	private int grade;
	private double fB; // allowable bending stress
	private double fV; //allowable shear stress
	private double fT; // allowable bearing stress
	private double modulusE;
	
	//no argumennt constructor
	public WoodType () {
		
	}
	
	//full argument constructor
	public WoodType (String name, int grade, double fB, double fV, double fT, double modulusE) {
		
		this.name = name;
		this.grade = grade;
		this.fB = fB;
		this.fV = fV;
		this.fT = fT;
		this.modulusE = modulusE;
		
	}

	//toString method
	public String toString () {
		
		return "Species" + name + "grade =" + grade + "allowable bending stress =" + fB +
				"allowable shear stress =" + fV + "allowable bearing stress =" + fT + 
				"modulus of Elasticity =" + modulusE;
		
	}
	
	//getters & setters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public double getfB() {
		return fB;
	}

	public void setfB(double fB) {
		this.fB = fB;
	}

	public double getfV() {
		return fV;
	}

	public void setfV(double fV) {
		this.fV = fV;
	}

	public double getfT() {
		return fT;
	}

	public void setfT(double fT) {
		this.fT = fT;
	}

	public double getModulusE() {
		return modulusE;
	}

	public void setModulusE(double modulusE) {
		this.modulusE = modulusE;
	}

	
	
}
