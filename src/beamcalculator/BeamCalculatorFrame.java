package beamcalculator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class BeamCalculatorFrame extends JFrame implements ActionListener {

	// declare variables
	private JButton woodDesignButton, steelDesignButton, concreteDesignButton;
	private JLabel woodButtonLabel, steelButtonLabel, concreteButtonLabel,
			beamGraphic, instructions1, instructions2;
	private ImageIcon beamPicture;

	// create instances of JFframe
	public static JFrame frame1;
	public static JFrame frame2;
	public static JFrame frame3;
	public static JFrame frame4;
	static boolean showGraph = false;

	public BeamCalculatorFrame() {

		// Set title & window size
		setTitle("Beam Design Software");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int width = 350;
		int height = 350;
		setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		setResizable(false);

		// add Listener to close window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		});

		// Create JPanel for graphic image
		JPanel graphics = new JPanel();
		graphics.setLayout(new FlowLayout(FlowLayout.CENTER));
		beamPicture = new ImageIcon(BeamCalculatorFrame.class.getResource("beam_design.gif"));
		beamGraphic = new JLabel(beamPicture);

		// add image to JPanel
		graphics.add(beamGraphic);

		// Create JPanel for Buttons & Text
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		woodButtonLabel = new JLabel("  Design a Wood Beam / Joist            ");
		woodDesignButton = new JButton("Wood");
		steelButtonLabel = new JLabel(
				"  Design a Steel I-Beam                  ");
		steelDesignButton = new JButton("Steel");
		concreteButtonLabel = new JLabel("  Design a Concrete Beam            ");
		concreteDesignButton = new JButton("Concrete");
		instructions1 = new JLabel(
				"Note: This Software is for the design of simply");
		instructions2 = new JLabel(
				"supported single span beams with a uniform load.");

		// add buttons and labels to JPanel
		displayPanel.add(woodDesignButton);
		displayPanel.add(woodButtonLabel);
		displayPanel.add(steelDesignButton);
		displayPanel.add(steelButtonLabel);
		displayPanel.add(concreteDesignButton);
		displayPanel.add(concreteButtonLabel);
		displayPanel.add(instructions1);
		displayPanel.add(instructions2);

		// add listeners for buttons
		woodDesignButton.addActionListener(this);
		steelDesignButton.addActionListener(this);
		concreteDesignButton.addActionListener(this);

		// create content pane and add JPanels
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(graphics, BorderLayout.NORTH);
		contentPane.add(displayPanel, BorderLayout.CENTER);

	}

	public void actionPerformed(ActionEvent e) {

		// create source object
		Object source = e.getSource();

		// check for source of button click
		if (source == woodDesignButton) {

			frame1.setVisible(false);
			frame1.dispose();
			frame2.setVisible(true);

		}

		if (source == steelDesignButton) {

			frame1.setVisible(false);
			frame1.dispose();
			frame3.setVisible(true);

		}

		if (source == concreteDesignButton) {

			frame1.setVisible(false);
			frame1.dispose();
			frame4.setVisible(true);

		}

	}

	public static void main(String[] args) {

		// initialize all JFrames
		frame1 = new BeamCalculatorFrame();
		frame2 = new WoodBeamCalculatorFrame();
		frame3 = new SteelBeamCalculatorFrame();
		frame4 = new ConcreteBeamCalculatorFrame();

		// make frame1 visible
		frame1.setVisible(true);

	}
}

class WoodBeamCalculatorFrame extends JFrame implements ActionListener {

	// declare and initialize variables
	private JTextField liveLoad, deadLoad, beamLength;
	private JTextArea results;
	private JLabel liveLoadLabel, deadLoadLabel, beamLengthLabel, speciesLabel,
			gradeLabel, resultsLabel, repeatLabel;
	private JButton calculateButton, exitButton, graphButton;
	private JRadioButton twelveButton, sixteenButton, twentyButton,
			twentyfourButton;
	private JComboBox speciesBox, gradeBox, beamRepeatBox;
	private static double shearFv = 0;
	private static double bending = 0;
	private static double bearing = 0;
	private static double emodulus = 0;

	// create String arrays for drop down lists
	private static String[] species = { "Hem-Fir", "Southern Pine" };
	private static String[] grade = { "Number 1", "Number 2" };
	private static String[] repeat = { "Yes", "No" };

	// create arraylists to load data
	public static ArrayList<Lumber> lumber = new ArrayList<Lumber>();
	public static ArrayList<WoodType> woodType = new ArrayList<WoodType>();

	// create embedded class for wood design
	public WoodBeamCalculatorFrame() {

		// set title and window size
		setTitle("Wood Beam Calculator");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int width = 300;
		int height = 470;
		setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		setResizable(false);

		// add listener to close window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		});

		// create JPanel for radio buttons
		JPanel radioPanel = new JPanel();
		twelveButton = new JRadioButton("12 O.C.");
		sixteenButton = new JRadioButton("16 O.C.");
		twentyButton = new JRadioButton("20 O.C.");
		twentyfourButton = new JRadioButton("24 O.C.");

		// create button group instance
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(twelveButton);
		radioGroup.add(sixteenButton);
		radioGroup.add(twentyButton);
		radioGroup.add(twentyfourButton);

		// add buttons to JPanel
		radioPanel.add(twelveButton);
		radioPanel.add(sixteenButton);
		radioPanel.add(twentyButton);
		radioPanel.add(twentyfourButton);

		// create JPanel for inputs & labels
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		liveLoadLabel = new JLabel("Live Load (psf)");
		deadLoadLabel = new JLabel("Dead Load (psf)");
		beamLengthLabel = new JLabel("Beam Length (feet)");
		speciesLabel = new JLabel("Wood Type (Species)");
		gradeLabel = new JLabel("Visual Wood Grade");
		resultsLabel = new JLabel("Calculation Results:        ");
		repeatLabel = new JLabel("Beam Repeats (i.e. 3 or more)");
		liveLoad = new JTextField(10);
		deadLoad = new JTextField(10);
		beamLength = new JTextField(10);
		beamRepeatBox = new JComboBox(repeat);
		beamRepeatBox.setMaximumRowCount(2);
		speciesBox = new JComboBox(species);
		speciesBox.setMaximumRowCount(2);
		gradeBox = new JComboBox(grade);
		gradeBox.setMaximumRowCount(2);
		results = new JTextArea(35, 25);

		// add items to JPanel
		displayPanel.add(liveLoadLabel);
		displayPanel.add(liveLoad);
		displayPanel.add(deadLoadLabel);
		displayPanel.add(deadLoad);
		displayPanel.add(beamLengthLabel);
		displayPanel.add(beamLength);
		displayPanel.add(speciesLabel);
		displayPanel.add(speciesBox);
		displayPanel.add(gradeLabel);
		displayPanel.add(gradeBox);
		displayPanel.add(repeatLabel);
		displayPanel.add(beamRepeatBox);
		displayPanel.add(resultsLabel);
		displayPanel.add(results);

		// create JPanel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		calculateButton = new JButton("Calculate");
		exitButton = new JButton("Exit");
		graphButton = new JButton("Graph");

		// add buttons to JPanel
		buttonPanel.add(calculateButton);
		buttonPanel.add(exitButton);
		buttonPanel.add(graphButton);

		// add action listener for buttons
		calculateButton.addActionListener(this);
		exitButton.addActionListener(this);
		graphButton.addActionListener(this);

		// create instance of Container & add JPanels
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(radioPanel, BorderLayout.NORTH);
		contentPane.add(displayPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {

		// create source object
		Object source = e.getSource();

		// check source of button input
		if (source == exitButton) {
			System.exit(0);
		}

		// check to make sure at least one radion button is selected
		if (!twelveButton.isSelected() && !sixteenButton.isSelected()
				&& !twentyButton.isSelected() && !twentyfourButton.isSelected()) {

			JOptionPane.showMessageDialog(this,
					"Beam spacing has not been selected.\n"
							+ "Please check all data and try again.");
		}

		else if (source == calculateButton) {

			try {

				actionCalculateButton();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid data entered.\n"
						+ "Please check all data and try again.");
			}
		}

		else if (source == graphButton) {

			try {

				actionGraphButton();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid data entered.\n"
						+ "Please check all data and try again.");
			}
		}
	}

	private void actionGraphButton() {

		//start graph JPanel class
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GraphPanel.createAndShowGui();
			}
		});

	}

	private void actionCalculateButton() {

		// read data from files
		lumber = FileMethods.readLumberFile();
		woodType = FileMethods.readWoodFile();

		// create instance of decimal formatter
		DecimalFormat df = new DecimalFormat("#.##");

		// declare and initialize variables
		double spacing = 0;
		double sizeFactor = 1.0;
		double repeatFactor = 0;
		boolean beamFound = false;

		// parse input data from window
		double live = Double.parseDouble(liveLoad.getText());
		double dead = Double.parseDouble(deadLoad.getText());
		double length = Double.parseDouble(beamLength.getText());
		String repeat = beamRepeatBox.getSelectedItem().toString();
		String type = speciesBox.getSelectedItem().toString();
		String output = "";
		int grade = (gradeBox.getSelectedIndex()) + 1;

		// check if beams are repeating
		if (repeat.equalsIgnoreCase("Yes")) {

			repeatFactor = 1.15;

		} else {

			repeatFactor = 1.0;
		}

		// check which spacing button was selected
		if (twelveButton.isSelected()) {
			spacing = 1.0;
		} else if (sixteenButton.isSelected()) {
			spacing = 1.34;
		} else if (twentyButton.isSelected()) {
			spacing = 1.67;
		} else if (twentyfourButton.isSelected()) {
			spacing = 2.0;
		}

		// use type & grade to get parameters
		for (int i = 0; i < woodType.size(); i++) {

			// get species information from arraylist
			String species = woodType.get(i).getName();
			int woodGrade = woodType.get(i).getGrade();

			if (species.equalsIgnoreCase(type) && woodGrade == grade) {

				// get design data from arraylist
				bending = woodType.get(i).getfB();
				shearFv = woodType.get(i).getfV();
				bearing = woodType.get(i).getfT();
				emodulus = woodType.get(i).getModulusE();

			}
		}

		// iterate over lumber to check if it is acceptable
		for (int i = 0; i < lumber.size(); i++) {

			sizeFactor = lumber.get(i).getSizeFactor();

			// calculate the required section modulus for the beam
			double reqSectionMod = BeamCalculations.calculateRequiredSX(live,
					dead, length, bending, spacing, sizeFactor, repeatFactor);

			// get section modulus from arraylist for each beam type
			double secMod = lumber.get(i).getSectionModulus();

			// check to see if section modulus of beam is greater or equal to
			// requirement
			if (secMod >= reqSectionMod) {

				double area = lumber.get(i).getArea();
				double inertia = lumber.get(i).getMomentInertia();

				// check to make sure shear stress is not exceeded in beam
				boolean shearOK = BeamCalculations.calculateShearStress(
						shearFv, area);

				// if shear is OK check deflection and print out results
				if (shearOK) {

					double deflection = BeamCalculations
							.calculateTotalDeflection(emodulus, length, inertia);

					String beamSize = "beam size = " + lumber.get(i).getName()
							+ " is acceptable\n"
							+ " maximum total deflection = "
							+ df.format(deflection) + " inches\n";

					beamFound = true;

					output = output + beamSize;

				}

				// calculate the minimum required end bearing for the beam
				double reqBearing = BeamCalculations.calculateBearing(bearing);

				String bearingOutput = " the minmum bearing is = "
						+ df.format(reqBearing) + " inches\n";

				output = output + bearingOutput;

			}

			results.setText(output);
		}

		// no acceptable beam was found display message
		if (beamFound == false) {

			output = "No Beam Sizes in our files are Acceptable";

			results.setText(output);
		}

	}
}

class SteelBeamCalculatorFrame extends JFrame implements ActionListener {

	// declare and initialize variables
	private JTextField liveLoad, deadLoad, beamLength, beamUnbracedLength,
			tributaryWidth;
	private JTextArea results;
	private JLabel liveLoadLabel, deadLoadLabel, beamLengthLabel, resultsLabel,
			unbracedLengthLabel, tributaryWidthLabel;
	private JButton calculateButton, exitButton, graphButton;
	private JRadioButton a36GradeButton, a50GradeButton;
	private static int grade = 0;

	// create arraylists to store data
	public static ArrayList<SteelBeam> steelBeam = new ArrayList<SteelBeam>();
	public static ArrayList<SteelBeam> a36SteelBeam = new ArrayList<SteelBeam>();
	public static ArrayList<SteelBeam> a572SteelBeam = new ArrayList<SteelBeam>();

	public SteelBeamCalculatorFrame() {

		// set window title and size
		setTitle("Steel Beam Calculator");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int width = 300;
		int height = 470;
		setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		setResizable(false);

		// add listener to close window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		});

		// create JPanel for radion buttons
		JPanel radioPanel = new JPanel();
		a36GradeButton = new JRadioButton("36 ksi Steel");
		a50GradeButton = new JRadioButton("50 ksi Steel");

		// create radio group instance and add buttons
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(a36GradeButton);
		radioGroup.add(a50GradeButton);

		// add buttons to JPanel
		radioPanel.add(a36GradeButton);
		radioPanel.add(a50GradeButton);

		// create JPanel for labels and text inputs
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		liveLoadLabel = new JLabel("Live Load (psf)");
		deadLoadLabel = new JLabel("Dead Load (psf)");
		beamLengthLabel = new JLabel("Beam Length (feet)");
		unbracedLengthLabel = new JLabel("Unbraced Length (feet)");
		tributaryWidthLabel = new JLabel("Tributary Width (feet)");
		resultsLabel = new JLabel("Calculation Results:        ");
		liveLoad = new JTextField(10);
		deadLoad = new JTextField(10);
		beamLength = new JTextField(10);
		beamUnbracedLength = new JTextField(10);
		tributaryWidth = new JTextField(10);
		results = new JTextArea(35, 25);

		// add labels and textfields to JPanel
		displayPanel.add(liveLoadLabel);
		displayPanel.add(liveLoad);
		displayPanel.add(deadLoadLabel);
		displayPanel.add(deadLoad);
		displayPanel.add(beamLengthLabel);
		displayPanel.add(beamLength);
		displayPanel.add(unbracedLengthLabel);
		displayPanel.add(beamUnbracedLength);
		displayPanel.add(tributaryWidthLabel);
		displayPanel.add(tributaryWidth);
		displayPanel.add(resultsLabel);
		displayPanel.add(results);

		// create JPanel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		calculateButton = new JButton("Calculate");
		exitButton = new JButton("Exit");
		graphButton = new JButton("Graph");

		// add buttons to JPanel
		buttonPanel.add(calculateButton);
		buttonPanel.add(exitButton);
		buttonPanel.add(graphButton);

		// add action listeners for buttons
		calculateButton.addActionListener(this);
		exitButton.addActionListener(this);
		graphButton.addActionListener(this);

		// create container instance and add JPanels
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(radioPanel, BorderLayout.NORTH);
		contentPane.add(displayPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {

		// create source object
		Object source = e.getSource();

		// check source of button click
		if (source == exitButton) {
			System.exit(0);
		}

		// check to make sure at least one radio button has been selected
		if (!a36GradeButton.isSelected() && !a50GradeButton.isSelected()) {

			JOptionPane.showMessageDialog(this,
					"Beam steel grade has not been selected.\n"
							+ "Please check all data and try again.");
		}

		else if (source == calculateButton) {

			try {

				actionCalculateButton();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid data entered.\n"
						+ "Please check all data and try again.");
			}
		}

		else if (source == graphButton) {

			try {

				actionGraphButton();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid data entered.\n"
						+ "Please check all data and try again.");
			}
		}

	}

	private void actionGraphButton() {

		//start graph JPanel class
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GraphPanel.createAndShowGui();
			}
		});

	}

	private void actionCalculateButton() {

		// load file data in arraylists
		a36SteelBeam = FileMethods.readSteelFile("a36steelbeam.txt");
		a572SteelBeam = FileMethods.readSteelFile("a572steelbeam.txt");

		// create instance of decimal formatter
		DecimalFormat df = new DecimalFormat("##.##");

		// create and initialize variables
		double live = Double.parseDouble(liveLoad.getText());
		double dead = Double.parseDouble(deadLoad.getText());
		double length = Double.parseDouble(beamLength.getText());
		double unbracedLength = Double
				.parseDouble(beamUnbracedLength.getText());
		double tribWidth = Double.parseDouble(tributaryWidth.getText());
		String output = "";
		double beamWeight = 100000;
		boolean beamFound = false;

		// check to see which radio button was selected
		if (a36GradeButton.isSelected()) {
			grade = 36;
			steelBeam = a36SteelBeam;
		} else if (a50GradeButton.isSelected()) {
			grade = 50;
			steelBeam = a572SteelBeam;
		}

		// calculate required moment capacity for beam
		double reqMomentCapacity = BeamCalculations
				.calculateRequiredMomCapacity(live, dead, length,
						unbracedLength, tribWidth);

		// iterate over beam arraylist to check for acceptable match
		for (int i = 0; i < steelBeam.size(); i++) {

			// get beam data from arraylist
			double momCapacity = steelBeam.get(i).calculateMomCap();
			double plasticLength = steelBeam.get(i).getPlasticLength();
			double bf = steelBeam.get(i).getBf();
			double maxLength = steelBeam.get(i).getMaxLength();

			// calculate moment capacity of beam
			double maxMomCapacity = BeamCalculations.calculateMaxMomCapacity(
					unbracedLength, momCapacity, plasticLength, bf);

			// check to see if moment capacity meets or exceeds requirements
			if (maxMomCapacity >= reqMomentCapacity && length <= maxLength) {

				double hctw = steelBeam.get(i).getHctw();
				double d = steelBeam.get(i).getD();
				double tw = steelBeam.get(i).getTw();

				// check shear strees to ensure that it is less than allowable
				boolean shearOK = BeamCalculations.calculateSteelShearStress(
						hctw, d, tw, grade);

				// if shear stress is OK check deflection
				if (shearOK) {

					double inertia = steelBeam.get(i).getMomentInertia();

					double weight = steelBeam.get(i).getWeightPerFoot();

					double deflection = BeamCalculations
							.calculateSteelDeflection(length, inertia);

					// sort through beams to ensure lowest weight beam is
					// selected
					if (weight <= beamWeight) {

						output = "beam size = " + steelBeam.get(i).getName()
								+ " is acceptable\n" + " maximum moment = "
								+ df.format(reqMomentCapacity) + " ft-kips\n"
								+ " maximum total deflection = "
								+ df.format(deflection) + " inches\n";

						beamWeight = weight;

						beamFound = true;

					}
				}

			}
		}

		// if no acceptable beam size is found print message
		if (!beamFound) {

			output = "No Beam Sizes in our files are Acceptable";

		}

		results.setText(output);
	}

}

class ConcreteBeamCalculatorFrame extends JFrame implements ActionListener {

	// declare and initialize variables
	private JTextArea results;
	private JButton calculateButton, exitButton, graphButton;
	private JRadioButton fiftyKsiButton, sixtyKsiButton;
	private JComboBox concreteStrength, rebarSize;
	private JTextField liveLoad, deadLoad, beamWidth, beamHeight, beamLength,
			tributaryWidth, concreteDensity;
	private JLabel liveLoadLabel, deadLoadLabel, beamWidthLabel,
			beamHeightLabel, beamLengthLabel, tributaryWidthLabel,
			concreteStrengthLabel, concDensityLabel, rebarSizeLabel,
			resultsLabel;
	private String output;

	// create String arrays for drop down lists
	private static String[] strength = { "3000psi", "4000psi", "5000psi" };
	private static String[] size = { "#3", "#4", "#5", "#6", "#7", "#8", "#9" };

	// create arraylist to store file data
	ArrayList<Rebar> rebar = new ArrayList<Rebar>();

	public ConcreteBeamCalculatorFrame() {

		// set window title and size
		setTitle("Concrete Beam Design");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int width = 300;
		int height = 450;
		setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		setResizable(false);

		// add listener to close window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0);
			}
		});

		// create JPanel for radio buttons
		JPanel radioPanel = new JPanel();
		fiftyKsiButton = new JRadioButton("50 Ksi rebar");
		sixtyKsiButton = new JRadioButton("60 Ksi reabar");

		// create instance of button group
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(fiftyKsiButton);
		radioGroup.add(sixtyKsiButton);

		// add buttons to JPanel
		radioPanel.add(fiftyKsiButton);
		radioPanel.add(sixtyKsiButton);

		// create JPanel for labels and text fields
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		liveLoadLabel = new JLabel("Live Load (psf)");
		deadLoadLabel = new JLabel("Dead Load (psf)");
		beamWidthLabel = new JLabel("Beam Width (inches)");
		beamHeightLabel = new JLabel("Beam Height (inches)");
		beamLengthLabel = new JLabel("Beam Length (feet)");
		concreteStrengthLabel = new JLabel("Concrete Strength");
		tributaryWidthLabel = new JLabel("Tributary Width (feet)");
		concDensityLabel = new JLabel("Concrete Density (pcf)");
		rebarSizeLabel = new JLabel("Reinforcing Bar Size");
		resultsLabel = new JLabel("Results");
		concreteStrength = new JComboBox(strength);
		concreteStrength.setMaximumRowCount(3);
		rebarSize = new JComboBox(size);
		rebarSize.setMaximumRowCount(6);
		liveLoad = new JTextField(10);
		deadLoad = new JTextField(10);
		beamWidth = new JTextField(10);
		beamHeight = new JTextField(10);
		beamLength = new JTextField(10);
		tributaryWidth = new JTextField(10);
		concreteDensity = new JTextField(10);
		results = new JTextArea(35, 25);

		// add labels and text fields to the JPanel
		displayPanel.add(liveLoadLabel);
		displayPanel.add(liveLoad);
		displayPanel.add(deadLoadLabel);
		displayPanel.add(deadLoad);
		displayPanel.add(beamWidthLabel);
		displayPanel.add(beamWidth);
		displayPanel.add(beamHeightLabel);
		displayPanel.add(beamHeight);
		displayPanel.add(beamLengthLabel);
		displayPanel.add(beamLength);
		displayPanel.add(tributaryWidthLabel);
		displayPanel.add(tributaryWidth);
		displayPanel.add(concreteStrengthLabel);
		displayPanel.add(concreteStrength);
		displayPanel.add(rebarSizeLabel);
		displayPanel.add(rebarSize);
		displayPanel.add(concDensityLabel);
		displayPanel.add(concreteDensity);
		displayPanel.add(resultsLabel);
		displayPanel.add(results);

		// create JPanel for buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		calculateButton = new JButton("Calculate");
		exitButton = new JButton("Exit");
		graphButton = new JButton("Graph");

		// add buttons to the JPanel
		buttonPanel.add(calculateButton);
		buttonPanel.add(exitButton);
		buttonPanel.add(graphButton);

		// add action listeners for the buttons
		calculateButton.addActionListener(this);
		exitButton.addActionListener(this);
		graphButton.addActionListener(this);

		// create container instance and add JPanels
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(radioPanel, BorderLayout.NORTH);
		contentPane.add(displayPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {

		// create source object
		Object source = e.getSource();

		// check source of input
		if (source == exitButton) {
			System.exit(0);
		}

		// check to make sure at least one radio button has been selected
		if (!fiftyKsiButton.isSelected() && !sixtyKsiButton.isSelected()) {

			JOptionPane.showMessageDialog(this,
					"Beam spacing has not been selected.\n"
							+ "Please check all data and try again.");
		}

		else if (source == calculateButton) {

			try {

				actionCalculateButton();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid data entered.\n"
						+ "Please check all data and try again.");
			}
		}

		else if (source == graphButton) {

			try {

				actionGraphButton();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "Invalid data entered.\n"
						+ "Please check all data and try again.");
			}
		}

	}

	private void actionGraphButton() {

		//start graph JPanel class
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GraphPanel.createAndShowGui();
			}
		});

	}

	public void actionCalculateButton() {

		// read rebar data from File
		rebar = FileMethods.readRebarFile();

		// declare and initialize variables
		int yieldStrength = 0;
		double concStrength = 0;

		// create instance of the decimal formatter
		DecimalFormat df = new DecimalFormat("#.##");

		// get data from text fields and combo boxes
		String concStr = concreteStrength.getSelectedItem().toString();
		double live = Double.parseDouble(liveLoad.getText());
		double dead = Double.parseDouble(deadLoad.getText());
		double width = Double.parseDouble(beamWidth.getText());
		double height = Double.parseDouble(beamHeight.getText());
		double length = Double.parseDouble(beamLength.getText());
		double tribWidth = Double.parseDouble(tributaryWidth.getText());
		double concDensity = Double.parseDouble(concreteDensity.getText());
		int barSize = rebarSize.getSelectedIndex();

		// check to see which radio button has been selected
		if (fiftyKsiButton.isSelected()) {
			yieldStrength = 50;
		} else if (sixtyKsiButton.isSelected()) {
			yieldStrength = 60;
		}

		// convert concrete strength from string to double
		if (concStr.equalsIgnoreCase("3000psi"))
			concStrength = 3000;
		if (concStr.equalsIgnoreCase("4000psi"))
			concStrength = 4000;
		if (concStr.equalsIgnoreCase("5000psi"))
			concStrength = 5000;

		// calculate the maximum moment in the beam
		double maxMoment = BeamCalculations.calculateMaxConcMoment(width,
				height, live, dead, length, tribWidth, concDensity);

		// get rebar information from arraylist
		double diameter = rebar.get(barSize).getDiameter();
		double area = rebar.get(barSize).getArea();

		// calculate the required steel reinforcing
		double reqSteelArea = BeamCalculations.calculateRebarArea(maxMoment,
				width, height, yieldStrength, concStrength, diameter);

		// check to make sure steel ratio is not too high
		boolean checkSteelRatio = BeamCalculations.checkSteelRatio(diameter,
				width, height, yieldStrength, concStrength, reqSteelArea);

		// check shear stress
		boolean shearOK = BeamCalculations.calculateConcShearStress(tribWidth,
				height, concStrength, diameter);

		double shearStirrups = 0;

		// calculate shear stirrups if shear stress is higher than allowable
		if (!shearOK) {

			shearStirrups = BeamCalculations.calculateShearStirrups(tribWidth,
					height, diameter, concStrength, yieldStrength);

		}

		// caculate number of bars required
		int numBars = BeamCalculations.calculateNumBars(reqSteelArea, area);

		// check to make sure the bars fit in the beam
		boolean barFit = BeamCalculations.checkBarFit(diameter, numBars, width);

		barSize = barSize + 3;

		// output design if all checks are OK
		if (barFit && checkSteelRatio && shearOK) {

			output = "Area of Steel Required =" + df.format(reqSteelArea)
					+ " in^2" + "\nMaximum Moment = " + df.format(maxMoment)
					+ " ft-kips" + "\nMaximum Shear Stress = "
					+ df.format(BeamCalculations.getConcEndReaction())
					+ " kips" + "\nNumber of #" + barSize + " bars required = "
					+ numBars;

			// output design if shear is higher than allowable
		} else if (barFit && checkSteelRatio && !shearOK) {

			output = "Area of Steel Required =" + df.format(reqSteelArea)
					+ " in^2" + "\nMaximum Moment = " + df.format(maxMoment)
					+ " ft-kips" + "\nMaximum Shear Stress = "
					+ df.format(BeamCalculations.getConcEndReaction())
					+ " kips" + "\nNumber of #" + barSize + " bars required = "
					+ numBars + "\n#3 Stirrups required at "
					+ df.format(shearStirrups) + "inches O.C.";

			// output message if beam size is too small
		} else {

			output = "Area of Steel Required =" + df.format(reqSteelArea)
					+ "in^2" + "\nNumber of #" + barSize + " bars required = "
					+ numBars + "\nBEAM WIDTH AND/OR DEPTH IS TOO SMALL "
					+ "\nTO FIT REQUIRED REINFORCING BARS !!";

		}
		// output design data to Text field
		results.setText(output);
	}

}
