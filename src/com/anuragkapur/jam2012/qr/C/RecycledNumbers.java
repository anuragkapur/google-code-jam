package com.anuragkapur.jam2012.qr.C;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author anuragkapur
 * 
 */
public class RecycledNumbers {
	
	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	// methods for algorithm logic here
	public int computeCountOfRecycledPairs(String input) {
		StringTokenizer tokenizer = new StringTokenizer(input, " ");
		Set<String> recycledPairs = new HashSet<String>();
		
		int a = -1;
		int b = -1;
		
		int tokenCount = 0;
		while (tokenizer.hasMoreElements()) {
			tokenCount ++;
			String token = (String) tokenizer.nextElement();
			if (tokenCount == 1) {
				a = Integer.parseInt(token);
			}else {
				b = Integer.parseInt(token);
			}
		}
		
		int originalA = a;
		int originalB = b;
		
		int noOfRecycledPairs = 0;
		if (a == b) {
			return 0;
		}
		
		
		while (a <= b) {
			
			String aStr = "" + a;
			String bStr = "" + b;
			int noOfDigits = aStr.length();
			
			if (noOfDigits < 2) {
				break;
			}else {
				int noOfDigitsToMove = 1;
				while(noOfDigitsToMove < noOfDigits) {
					String movedDigitsString = aStr.substring(noOfDigits - noOfDigitsToMove);
					String unmovedDigitsString = aStr.substring(0, noOfDigits - noOfDigitsToMove);
					int movedDigits = Integer.parseInt(movedDigitsString);
					int unmovedDigits = Integer.parseInt(unmovedDigitsString);
					
					//Check if number at first index is 0. If yes, this is not a valid case
					int firstDigitOFMovedDigits = Integer.parseInt(movedDigitsString.substring(0,1));
					if (firstDigitOFMovedDigits == 0) {
						noOfDigitsToMove ++;
					}else {
						String newNumberString = movedDigitsString + unmovedDigitsString;
						int newNumber = Integer.parseInt(newNumberString);
						//System.out.println("old number " + a + " new number :: " + newNumber);
						if (newNumberString.length() == aStr.length()) {
							if(newNumber > a && newNumber <= originalB && a >= originalA) {
								//System.out.println("recycled pair found :: " + a + " and " + newNumber);
								// Add pair found to a SET so that duplicates get ignored automatically
								recycledPairs.add(a + "," + newNumberString);
							}
						}
						noOfDigitsToMove ++;
					}
				}
			}
			a ++;
		}
		
		noOfRecycledPairs = recycledPairs.size();
		return noOfRecycledPairs;
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
			RecycledNumbers recycled = new RecycledNumbers();
			
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
					int solutionToTestCase = recycled.computeCountOfRecycledPairs(strLine);
					
					// Prepare output string
					//System.out.println(solutionToTestCase);
					output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
					output.append("\n");
				}
				lineNumber++;
			}
			
			in.close();
			
			// Pass output string to method to write to file
			recycled.writeOutputToFile(output.toString(), args[1]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}