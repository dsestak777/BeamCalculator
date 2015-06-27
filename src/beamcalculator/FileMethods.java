package beamcalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileMethods {

    public static ArrayList<WoodType> readWoodFile() {

        //create arraylist to hold file data
        ArrayList<WoodType> wood = new ArrayList<WoodType>();

        //create new file 
        InputStream is = FileMethods.class.getResourceAsStream("woodtype.txt");
        InputStreamReader file = new InputStreamReader(is);

             //   File file = new File ("files/woodtype.txt");
        //create new Buffered Reader instance
        BufferedReader in = null;

        //start try catch
        try {

            //start load file into buffered reader 
            in = new BufferedReader(file);
           // in = new BufferedReader(new FileReader(file));

            //read a line 
            String line = in.readLine();

            //loop while line are still lines in file
            while (line != null) {
                //split apart line based upon tokens
                StringTokenizer t = new StringTokenizer(line, "|");

                //parse variables from file data
                String name = t.nextToken().trim();
                int grade = Integer.parseInt(t.nextToken().trim());
                double fB = Double.parseDouble(t.nextToken().trim());
                double fV = Double.parseDouble(t.nextToken().trim());
                double fT = Double.parseDouble(t.nextToken().trim());
                double eMod = Double.parseDouble(t.nextToken().trim());

                //create a new woodtype object
                WoodType wt = new WoodType(name, grade, fB, fV, fT, eMod);

                //add object to arraylist
                wood.add(wt);

                System.out.println(wt.toString());

                //read in the next line
                line = in.readLine();
            }

        } catch (IOException io) {

            System.out.println("An IO Exception Occured");
            io.printStackTrace();
        } finally {
            try {
                //close the file
                in.close();
            } catch (Exception e) {
            }

        }

        //return the data in arraylist format
        return wood;
    }

    public static ArrayList<Lumber> readLumberFile() {

        //create arraylidt to store lumber data
        ArrayList<Lumber> lumber = new ArrayList<Lumber>();

		//create a new file
        InputStream is = FileMethods.class.getResourceAsStream("lumber.txt");
        InputStreamReader file = new InputStreamReader(is);
                //BufferedReader br = new BufferedReader(isr);

		//File file = new File("files/lumber.txt");
        BufferedReader in = null;

        try {
			//start reading in the file
            //in = new BufferedReader(new FileReader(file));
            in = new BufferedReader(file);

            //read a line in as a string
            String line = in.readLine();

            //loop until end of file
            while (line != null) {
                //break up line based upon tokens
                StringTokenizer t = new StringTokenizer(line, "|");

                //parse data from file
                String name = t.nextToken().trim();
                double secMod = Double.parseDouble(t.nextToken().trim());
                double area = Double.parseDouble(t.nextToken().trim());
                double weight = Double.parseDouble(t.nextToken().trim());
                double sizeFac = Double.parseDouble(t.nextToken().trim());
                double momInertia = Double.parseDouble(t.nextToken().trim());

                //create a new lumber object
                Lumber l = new Lumber(name, secMod, area, weight, sizeFac, momInertia);

                //add object to arraylist
                lumber.add(l);

                System.out.println(l.toString());

                //read a new line in	
                line = in.readLine();
            }
        } catch (IOException io) {
            System.out.println("An IO Exception occured");
            io.printStackTrace();

        } finally {
            try {
                //close file
                in.close();
            } catch (Exception e) {
            }
        }
        return lumber;
    }

    public static ArrayList<SteelBeam> readSteelFile(String fileName) {

        //create arraylist to store file data
        ArrayList<SteelBeam> steelBeam = new ArrayList<SteelBeam>();

        //create a new file
        InputStream is = FileMethods.class.getResourceAsStream(fileName);
        InputStreamReader file = new InputStreamReader(is);
        //File file = new File(fileName);
        
        BufferedReader in = null;

        try {
            //start reading in the file
              in = new BufferedReader(file);
           // in = new BufferedReader(new FileReader(file));

            //read a line in as a string
            String line = in.readLine();

            //loop until end of file
            while (line != null) {
                //break line up based upon tokens
                StringTokenizer t = new StringTokenizer(line, "|");

                //parse data from file
                String name = t.nextToken().trim();
                double area = Double.parseDouble(t.nextToken().trim());
                double zMod = Double.parseDouble(t.nextToken().trim());
                double weight = Double.parseDouble(t.nextToken().trim());
                double momInertia = Double.parseDouble(t.nextToken().trim());
                int grade = Integer.parseInt(t.nextToken().trim());
                double plasticLength = Double.parseDouble(t.nextToken().trim());
                double bf = Double.parseDouble(t.nextToken().trim());
                double maxLength = Double.parseDouble(t.nextToken().trim());
                double hctw = Double.parseDouble(t.nextToken().trim());
                double d = Double.parseDouble(t.nextToken().trim());
                double tw = Double.parseDouble(t.nextToken().trim());

                //create a new steel beam object
                SteelBeam s = new SteelBeam(name, weight, area, zMod, momInertia, grade, plasticLength, bf, maxLength, hctw, d, tw);

                //add object to arraylist
                steelBeam.add(s);

                System.out.println(s.toString());

                //read in next line
                line = in.readLine();
            }
        } catch (IOException io) {
            System.out.println("An IO Exception occured");
            io.printStackTrace();

        } finally {
            try {
                //clos file
                in.close();
            } catch (Exception e) {
            }
        }
        return steelBeam;
    }

    public static ArrayList<Rebar> readRebarFile() {

        //create arraytlist to hold file data
        ArrayList<Rebar> rebar = new ArrayList<Rebar>();

        //create a new file
        InputStream is = FileMethods.class.getResourceAsStream("rebar.txt");
        InputStreamReader file = new InputStreamReader(is);
        
        //File file = new File("files/rebar.txt");
        BufferedReader in = null;

        try {
            //start reading in file
              in = new BufferedReader(file);
           // in = new BufferedReader(new FileReader(file));

            //assign line as to a string
            String line = in.readLine();

            //loop until end of the file
            while (line != null) {
                //break line apart based upon tokens
                StringTokenizer t = new StringTokenizer(line, "|");

                //parse data from file
                String name = t.nextToken().trim();
                double area = Double.parseDouble(t.nextToken().trim());
                double diameter = Double.parseDouble(t.nextToken().trim());

                //create a new rebar object
                Rebar r = new Rebar(name, area, diameter);

                //add object to arraylist
                rebar.add(r);

                System.out.println(r.toString());

                //read in the next line
                line = in.readLine();
            }
        } catch (IOException io) {
            System.out.println("An IO Exception occured");
            io.printStackTrace();

        } finally {
            try {
                //close file
                in.close();
            } catch (Exception e) {
            }
        }
        return rebar;
    }

}
