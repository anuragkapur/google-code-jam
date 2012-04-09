package com.anuragkapur.jam2011.qr.ABotTrust;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author anuragkapur
 * 
 */
public class BotTrust {
	
	static int[] robotOButtonsToPush = new int[100];
	static int[] robotBButtonsToPush = new int[100];
	static String[] robotActionSequence = new String[100];
	static int robotONextButtonToPushIndex;
	static int robotBNextButtonToPushIndex;
	static int robotOCurrentPosition;
	static int robotBCurrentPosition;
	static int currentIndexInRobotActionSequence;
	static boolean doesRobotOHaveControl = true;
	
	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	// methods for algorithm logic here
	
	public void resetVariables(boolean indexOnly) {
		robotONextButtonToPushIndex = 0;
		robotBNextButtonToPushIndex = 0;
		robotOCurrentPosition = 1;
		robotBCurrentPosition = 1;
		currentIndexInRobotActionSequence = 0;
		
		if (!indexOnly) {
			for (int i = 0; i < 100; i++) {
				robotOButtonsToPush[i] = -1;
				robotBButtonsToPush[i] = -1;
				robotActionSequence[i] = "no_action";
			}
		}
	}
	
	public String mainController(String input) {
		resetVariables(false);
		
		// setup control and action arrays
		StringTokenizer tokenizer = new StringTokenizer(input, " ");
		
		if (tokenizer.hasMoreElements()) {
			String totalButtonsToBePressed = (String)tokenizer.nextElement();
		}
		
		int count = -1;
		while (tokenizer.hasMoreElements()) {
			count ++;
			
			String token = (String) tokenizer.nextElement();
			if (count % 2 == 0) {
				System.out.println("Robot action sequence, adding entry :: " + token);
				robotActionSequence[currentIndexInRobotActionSequence] = token;
				currentIndexInRobotActionSequence ++;
			}else {
				String buttonValue = token;
				if (robotActionSequence[currentIndexInRobotActionSequence - 1].equals("B")) {
					System.out.println("Button for B :: " + buttonValue);
					robotBButtonsToPush[robotBNextButtonToPushIndex] = Integer.parseInt(buttonValue);
					robotBNextButtonToPushIndex ++;
				}else if(robotActionSequence[currentIndexInRobotActionSequence - 1].equals("O")) {
					System.out.println("Button for O :: " + buttonValue);
					robotOButtonsToPush[robotONextButtonToPushIndex] = Integer.parseInt(buttonValue);
					robotONextButtonToPushIndex ++;
				}
			}
		}
		
		// reset variables, index only
		resetVariables(true);
		
		// start the action
		int time = 1;
		while(true) {
			
			// determine which robot has control
			if(robotActionSequence[currentIndexInRobotActionSequence].equalsIgnoreCase("B")) {
				doesRobotOHaveControl = false;
			}else {
				doesRobotOHaveControl = true;
			}
			
			// Robot O Actions
			if(robotOCurrentPosition < robotOButtonsToPush[robotONextButtonToPushIndex]) {
				robotOCurrentPosition ++;
			}else if(robotOCurrentPosition > robotOButtonsToPush[robotONextButtonToPushIndex]) {
				robotOCurrentPosition --;
			}else {
				if(doesRobotOHaveControl) {
					// Push button
					robotONextButtonToPushIndex ++;
					currentIndexInRobotActionSequence ++;
				}else {
					// No action, stay where you are wait for control to come to you
				}
			}
			
			// Robot B Actions
			if(robotBCurrentPosition < robotBButtonsToPush[robotBNextButtonToPushIndex]) {
				robotBCurrentPosition ++;
			}else if(robotBCurrentPosition > robotBButtonsToPush[robotBNextButtonToPushIndex]) {
				robotBCurrentPosition --;
			}else {
				if(!doesRobotOHaveControl) {
					// Push button
					robotBNextButtonToPushIndex ++;
					currentIndexInRobotActionSequence ++;
				}else {
					// No action, stay where you are wait for control to come to you
				}
			}

			
			if(currentIndexInRobotActionSequence == 100 || robotActionSequence[currentIndexInRobotActionSequence].equals("no_action"))
				break;
			
			time ++;
		}
		
		return ""+time;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.out.println("Not enough command line arguments specified. Need 2 (Input and output file paths)");
			return;
		}
		
		String inputFilePath = args[0];
		try {
			// String buffer for storing the output
			StringBuffer output = new StringBuffer();
			
			// Instantiate object to use non static methods
			BotTrust botTrust = new BotTrust();
			
			// read and parse input file
			FileInputStream fstream = new FileInputStream(inputFilePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int lineNumber = 0;
			int noOfTestCases = -1;
			int activeTestCaseNumber = 0;
			while ((strLine = br.readLine()) != null) {
				
				if (lineNumber == 0) {
					noOfTestCases = Integer.parseInt(strLine);
				} else {
					noOfTestCases ++;
					activeTestCaseNumber ++;
					// Now that a test case has been parsed, compute output for
					// this test case
					
					// Invoke algorithm here
					String solutionToTestCase = botTrust.mainController(strLine);
					
					// Prepare output string
					System.out.println("Test case # :: " + activeTestCaseNumber);
					System.out.println("Solution to Test case # :: " + solutionToTestCase);
					output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
					output.append("\n");
				}
				lineNumber++;
			}
			
			in.close();
			
			// Pass output string to method to write to file
			botTrust.writeOutputToFile(output.toString(), args[1]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}
