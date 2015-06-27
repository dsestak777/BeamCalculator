package beamcalculator;

import java.util.ArrayList;

public class BeamCalculations {

	//declare and initialize variables 
	private static final double REPETITION_FACTOR = 1.15; 
	private static final double DEAD_LOAD_DURATION_FACTOR = 0.9;
	private static final double LIVE_LOAD_DURATION_FACTOR = 1.25;
	private static final double SHEAR_FACTOR = 1.5;
	private static final double DEAD_LOAD_FACTOR_ONLY = 1.4;
	private static final double DEAD_LOAD_FACTOR = 1.2;
	private static final double LIVE_LOAD_FACTOR = 1.6;
	private static final int FOOT_INCH_CONVERSION = 12;
	private static final int POUNDS_TO_KIPS = 1000;
	private static final double BEAM_WIDTH = 1.5;
	private static final int SQ_IN_CONV = 1728;
	private static final double PHI = 0.9;
	private static final double SHEAR_PHI = 0.85;
	private static final double SHEAR_YIELD_FACTOR = 0.6;
	private static final int STEEL_E_MODULUS = 29000;
	private static final double CONC_COVER = 1.5;
	private static final double STIRRUP_DIAMETER = 0.375;
	private static final double STIRRUP_AREA = 0.22;
	private static final double BALANCED_STRAIN = 0.003;
	private static boolean deadControls;
	private static double unfactoredLoad;
	private static double deadLoad;
	private static double deadLiveLoad;
	private static double woodEndReaction;
	private static double steelEndReaction;
	private static double concEndReaction;
	private static double allowableConcShearStress;
	
	static ArrayList<Double> graph = new ArrayList<Double>();

	//method to calculate required section modulus for wood beam
	public static double calculateRequiredSX (double live, double dead, double length, double Fb, double spacing, double sizeF, double repeatFactor) {
	
		//declare and initialize variables
		deadControls = false;
		double moment = 0;
		double allowBendingStress = 0;
		deadLoad = dead * spacing;
		deadLiveLoad =  ( dead + live ) * spacing;
		
		//check to see which load pattern is greater
		if (deadLoad > deadLiveLoad) {
			moment = ((deadLoad * Math.pow(length, 2)) / 8) * FOOT_INCH_CONVERSION; 
			woodEndReaction = deadLoad * length /2;
			deadControls = true;
			calculateGraphData(deadLoad, length);		
		} else {
			moment = ((deadLiveLoad * Math.pow(length, 2)) /8) * FOOT_INCH_CONVERSION ; 
			woodEndReaction = deadLiveLoad * length/2;
			deadControls = false;
			calculateGraphData(deadLiveLoad, length);		
		}
		
		//calculate allowbending stress 
		if (deadControls) {
			allowBendingStress = Fb * REPETITION_FACTOR * DEAD_LOAD_DURATION_FACTOR * sizeF * repeatFactor;
		} else {
			allowBendingStress = Fb * REPETITION_FACTOR * LIVE_LOAD_DURATION_FACTOR * sizeF * repeatFactor;
		}
		
		//calculate required section modulus
		double sectionModulus = moment / allowBendingStress;
		
		System.out.println(sectionModulus);
		
	
		
		return sectionModulus;
	}
	
	
	public static void calculateGraphData (double loadPerFoot, double length) {
		
		//remove existing data from array
		graph.clear();
		
		
		int numDataPoints = (int) length + 1;
		
		//add a data point to array every 1-foot
		for (int i=0; i<numDataPoints; i++) {
			
			double dataPoint = i * loadPerFoot / 2 * (length - i);
			
			graph.add(dataPoint);
		}
		
		
		
	}
	
	//calculate shear stress in wood beam
	public static boolean calculateShearStress(double Fv, double Area ) {
		
		boolean shearOK = false;
		
		//calculate shear stress 
		double shearStress = (SHEAR_FACTOR * woodEndReaction )/Area;
		
		//check to see if maximum shear is less than allowable
		if (shearStress <=Fv) {
			
			shearOK= true;
			
		} else {
			
			shearOK=false;
		}
		
		//if shear is OK return true
		return shearOK;
	}
	
	
	public static double calculateTotalDeflection (double modE, double length, double inertia) {
		
		//calculate maximum wood beam deflection
		double totalDeflection = (5 * deadLiveLoad * Math.pow(length,4) * SQ_IN_CONV) / (384 * modE * inertia)  ;
		
		return totalDeflection;
	}
	
	
	
	public static double calculateBearing (double fT) {
		
		//calculate required end bearing for wood beams
		double reqArea =  woodEndReaction / fT;
		
		double bearing = reqArea/BEAM_WIDTH;
		
			if (bearing < 1.5){
			
				bearing = 1.5;
			}
		
		return bearing;
		
		
	}
	
	public static double calculateRequiredMomCapacity (double live, double dead, double length, double unbracedLength, double tribWidth) {
		
		double reqMomCapacity = 0;
		
		//calculate load without load factors
		unfactoredLoad = ((dead + live) * tribWidth) / POUNDS_TO_KIPS;
		
		// add selfweight???
		
		//calculate uniform load per foot dead load only
		double deadOnlyLoad = (dead * tribWidth * DEAD_LOAD_FACTOR_ONLY) / POUNDS_TO_KIPS;    
		
		//calculate uniform load per foot live and dead 
		double liveDeadLoad = (((dead * DEAD_LOAD_FACTOR) + (live * LIVE_LOAD_FACTOR))* tribWidth) / POUNDS_TO_KIPS;
		
		//calculate required moment capacity for both cases
		double reqMomCapacity1 = ((deadOnlyLoad * Math.pow(length, 2)) /8);
		double reqMomCapacity2 = ((liveDeadLoad * Math.pow(length, 2)) /8); 
		
		reqMomCapacity = reqMomCapacity1;
		steelEndReaction = (deadOnlyLoad * length) / 2;
		calculateGraphData(deadOnlyLoad, length);		
		
		//determine which case is greater
		if (reqMomCapacity < reqMomCapacity2) {
		
			reqMomCapacity = reqMomCapacity2;
			steelEndReaction = (liveDeadLoad * length) / 2;
			calculateGraphData(liveDeadLoad, length);	
			
		} else {//do nothing
			
		}
		
		return reqMomCapacity;
		
	}
	
	public static double calculateMaxMomCapacity (double unbracedLength,  double momCapacity, double plasticLength, double bf) {
		
		double maxMomCap = 0;
	
		//calculate moment capacity 
		if (unbracedLength <= plasticLength) {
			
			maxMomCap = momCapacity;
			
		} else {
			//if length exceeds plastic length 
			maxMomCap = momCapacity - bf * (unbracedLength - plasticLength);
		}
		
		
		return maxMomCap;
	}
	
	public static boolean calculateSteelShearStress(double hctw, double d, double tw, int grade  ) {
		
		boolean shearOK = false;
		
		double allowableShear = 0;
		
		//calculate are of beam web
		double webArea = d * tw;
		
		double webWidthThicknessRatio = hctw;
		
		//calculate stable web case
		double webStable = 418 / Math.sqrt(grade);
		
		//calculate inelastic web case
		double webInelastic = 523 / Math.sqrt(grade);
		
		//elastic web case
		final double WEB_ELASTIC = 260;
		
		//determine which web case controls and calculate allowable shear
		if (webWidthThicknessRatio <= webStable ) {
			
			allowableShear = PHI * SHEAR_YIELD_FACTOR * webArea * grade;
			
		} else if (webWidthThicknessRatio > webStable && webWidthThicknessRatio <= webInelastic) {
			
			allowableShear = PHI * SHEAR_YIELD_FACTOR * webArea * grade * ( webStable / webWidthThicknessRatio); 
		
		} else if (webWidthThicknessRatio < WEB_ELASTIC) {
			
			allowableShear = 132000 * webArea / (Math.pow(webWidthThicknessRatio, 2));
		}	
		
		if (allowableShear >= steelEndReaction) {
			
			shearOK = true;
		
		} else {
			
			shearOK=false;
		}
		
		
		return shearOK;
	}
	
	public static double calculateSteelDeflection (double length, double inertia) {
		
		length = length * FOOT_INCH_CONVERSION;
		
		//calculate deflection based upon total load
		double totalDeflection = ((5 * unfactoredLoad / FOOT_INCH_CONVERSION) * (Math.pow(length,4))) / (384 * STEEL_E_MODULUS * inertia)  ;
		
		return totalDeflection;
	}
	
	
	public static double calculateMaxConcMoment (double width, double height, double live, double dead, double length, double tribWidth, double concDensity) {
		
		double reqMomCapacity = 0;
		
		//add selfweight of beam
		dead = dead + (width/FOOT_INCH_CONVERSION * height/FOOT_INCH_CONVERSION * concDensity);
		
		//calculate total load without load factors
		unfactoredLoad = ((dead + live) * tribWidth) / POUNDS_TO_KIPS;
		
		
		//calculate uniform dead load
		double deadOnlyLoad = (dead * tribWidth * DEAD_LOAD_FACTOR_ONLY) / POUNDS_TO_KIPS;    
		
		//calculate uniform dead and live load
		double liveDeadLoad = (((dead * DEAD_LOAD_FACTOR) + (live * LIVE_LOAD_FACTOR))* tribWidth) / POUNDS_TO_KIPS;
		
		//determine required moment capacity for both cases
		double reqMomCapacity1 = ((deadOnlyLoad * Math.pow(length, 2)) /8);
		double reqMomCapacity2 = ((liveDeadLoad * Math.pow(length, 2)) /8); 
		
		reqMomCapacity = reqMomCapacity1;
		setConcEndReaction((deadOnlyLoad * length) / 2);
		calculateGraphData(deadOnlyLoad, length);	
		
		//check to see which case controls
		if (reqMomCapacity < reqMomCapacity2) {
		
			reqMomCapacity = reqMomCapacity2;
			setConcEndReaction((liveDeadLoad * length) / 2);
			calculateGraphData(liveDeadLoad, length);	
		
		} else {//do nothing
			
		}
		
		
		
		return reqMomCapacity;
		
	}
	
	
	public static double calculateRebarArea(double maxMoment, double width, double height, int rebarGrade, double concStrength, double barDiameter) {
		
		double area = 0;
		
		//calculate steel/conc ratio
		double m = rebarGrade / (SHEAR_PHI * concStrength / 1000);

		//calculate depth to reinforcing
		double d = calculateD(height, barDiameter);
		
		//calculate Rn
		double Rn = (maxMoment * 12000) / (PHI * width * Math.pow(d, 2)); 
		
		//caculate required reinforcing ratio
		double b = 1 - ((2 * m * Rn) / (rebarGrade * 1000));
		double row = 1/m * (1 - Math.sqrt(b));
		
		//calculate required steel area
		area = row * width * d;
		
		return area; 
		
	}
	
	public static boolean checkSteelRatio (double barDiameter, double width, double height, double yieldStrength, double concStrength, double reqSteelArea ) {
		
		boolean steelRatioOK = false ;
		
		//calculate depth to reinforcing
		double d = calculateD(height, barDiameter);
		
		int conc = (int)concStrength / POUNDS_TO_KIPS;
	
		double b1 = 0;
		
		//assign b1 based upon concrete strength
		switch (conc) {
		case 3: b1 = 0.85; break;
		case 4: b1 = 0.85; break;
		case 5: b1 = 0.8; break;
		}
		
		//determine maximum allowable reinforcing ratio
		double rowMaximum = 0.75 * ((SHEAR_PHI * b1 * conc / yieldStrength) * (87 / (87 + yieldStrength))) ;
		
		//calculate actual reinforcing ratio
		double rowActual = reqSteelArea / (width * d);
		
		//check to reinforcing ratio versus max allowed
		if (rowActual <= rowMaximum) {
			
			steelRatioOK = true;
		}
		
		return steelRatioOK;
	}
	
	public static int calculateNumBars (double reqArea, double areaBar ) {
		
		//calculate number of bars required
		double numBars = Math.ceil(reqArea / areaBar);
		
		int num = (int)numBars;
		
		return num;
	}
	
	public static boolean checkBarFit(double barDiameter, double numBars, double width) {
		
		boolean barFitOK = false;
		
		//calculate required width of beam to fit bars
		double reqWidth = CONC_COVER * 2 + STIRRUP_DIAMETER * 4 + numBars * barDiameter + numBars * 1; 
		
		//check to see if bars will fit 
		if (width >= reqWidth) barFitOK = true; 
		
		return barFitOK;
	}
	
	public static boolean calculateConcShearStress (double width, double height, double concStrength, double barDiameter){
		
		boolean shearOK = false;
		
		//calculate depth to reinforcing
		double d = calculateD(height, barDiameter);
		
		//calculate allowable shear stress in concrete
		allowableConcShearStress = (SHEAR_PHI * 2 * Math.sqrt(concStrength) * width * d) / POUNDS_TO_KIPS;
		
		//check if maximum shear is less than 50% of allowable shear
		if (getConcEndReaction() < 0.5*allowableConcShearStress ) shearOK = true;
		
		return shearOK;
	}


	public static double calculateShearStirrups (double width, double height, double barDiameter, double concStrength, int grade) {
		
		double spacing = 0;
		
		//calculate depth to reinforcing
		double d = calculateD(height, barDiameter);
		
		//determine amount of additional shear strength is required
		double reqShearReinf = concEndReaction - allowableConcShearStress ;
		
		//calculate shear category2
		double category2 = (SHEAR_PHI * 4 * Math.sqrt(concStrength) * width * d) / POUNDS_TO_KIPS;
		
		//calculate shear category3
		double category3 = (SHEAR_PHI * 8 * Math.sqrt(concStrength) * width * d) / POUNDS_TO_KIPS;		
		
		//check to see which category applies to beam
		if (getConcEndReaction() >= category3) {
			
			//calculate required shear stirrup spacing
			spacing = SHEAR_PHI * STIRRUP_AREA * grade * d / reqShearReinf;
			
			//check versus minimum requirements
			if (spacing >= 12) spacing = 12;
			if (spacing >= d/4 ) spacing = d/4;
			
		} else if (getConcEndReaction() >= category2) {
			
			//calculate required shear stirrup spacing
			spacing = SHEAR_PHI * STIRRUP_AREA * grade * d / reqShearReinf;
			
			//check versus minimum requirements
			if (spacing >= 24) spacing = 24;
			if (spacing >= d/2 ) spacing = d/2;
			
		} else if (getConcEndReaction() >= 0.5*allowableConcShearStress ) {
			
			//calculate required shear stirrup spacing
			spacing = STIRRUP_AREA * (grade * 1000) / 50 * width; 
			
			//check versus minimum requirements
			if (spacing >= 24) spacing = 24;
			if (spacing >= d/2 ) spacing = d/2;
			
		}
		
		return spacing; 
		
	}


	public static double calculateD (double height, double barDiameter) {
		
		//calculate depth to reinforcing
		double d = height - CONC_COVER - STIRRUP_DIAMETER - (barDiameter/2);
		
		return d;
	}
	
	//getter and setter 
	public static double getConcEndReaction() {
		return concEndReaction;
	}

	public static void setConcEndReaction(double concEndReaction) {
		BeamCalculations.concEndReaction = concEndReaction;
	}
	
	public static ArrayList<Double> getGraphData() {
		
		return graph;
	}
}
