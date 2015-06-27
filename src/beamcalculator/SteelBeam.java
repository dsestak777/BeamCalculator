package beamcalculator;

public class SteelBeam {

	//declare instance variables
	private static final double PHI = 0.9;
	private String name;
	private double zModulus;
	private double area;
	private double weightPerFoot;
	private double momentInertia;
	private int grade;
	private double plasticLength;
	private double bf;
	private double maxLength;
	private double hctw;
	private double d;
	private double tw;
	
	//no argument constructor
	public SteelBeam () {
		
	}
	
	//full argument constructor
	public SteelBeam (String name, double zModulus, double area, double weightPerFoot, double momentInertia, int grade, 
						double plasticLength, double bf, double maxLength, double hctw, double d, double tw ) {
		
		this.name = name;
		this.zModulus = zModulus;
		this.area = area;
		this.weightPerFoot = weightPerFoot;
		this.momentInertia = momentInertia;
		this.grade = grade;
		this.plasticLength = plasticLength;
		this.bf = bf;
		this.maxLength = maxLength;
		this.hctw = hctw;
		this.d = d;
		this.tw = tw;
		
		
	}
	
	//calculate moment capacity based upon grade 
	public double calculateMomCap () {
		
		double momentCapacity = 0;
		
		momentCapacity = (PHI * grade * zModulus) / 12;
		
		return momentCapacity;
	}
	
	//toString method
	public String toString () {
		
		return "Size= " + name + " zModulus= " + zModulus + " cross sectional area = " +
				area + " weight per foot= " + weightPerFoot + " moment of inertia = " + momentInertia +
				" grade = " + grade + " plasticLength = " + plasticLength + " bf= " + bf + " maxLength= " 
				+ maxLength + " hctw = " + hctw + " d = " + d + " tw = " + tw;
	}
	
	
	//getters & setters

	public double getHctw() {
		return hctw;
	}

	public void setHctw(double hctw) {
		this.hctw = hctw;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getTw() {
		return tw;
	}

	public void setTw(double tw) {
		this.tw = tw;
	}

	public double getPlasticLength() {
		return plasticLength;
	}

	public void setPlasticLength(double plasticLength) {
		this.plasticLength = plasticLength;
	}

	public double getBf() {
		return bf;
	}

	public void setBf(double bf) {
		this.bf = bf;
	}

	public double getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(double maxLength) {
		this.maxLength = maxLength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getzModulus() {
		return zModulus;
	}

	public void setzModulus(double zModulus) {
		this.zModulus = zModulus;
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

	public double getMomentInertia() {
		return momentInertia;
	}

	public void setMomentInertia(double momentInertia) {
		this.momentInertia = momentInertia;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
}
