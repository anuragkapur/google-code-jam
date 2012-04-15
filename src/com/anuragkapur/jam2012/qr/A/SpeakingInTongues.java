package com.anuragkapur.jam2012.qr.A;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author anuragkapur
 * 
 */
public class SpeakingInTongues {

	char[] googlerese = { 'y', 'n', 'f', 'i', 'c', 'w', 'l', 'b', 'k', 'u',
			'o', 'm', 'x', 's', 'e', 'v', 'z', 'p', 'd', 'r', 'j', 'g', 't',
			'h', 'a', 'q' };

	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}

	// methods for algorithm logic here
	public String computeSolutionString(String input) {
		StringBuffer solutionBuffer = new StringBuffer();
		
		char[] characters = input.toCharArray();
		for (char c : characters) {
			// find index in googlerese char array
			int charIndex = -1;
			boolean lowerCase = false;
			
			for (int i = 0; i < googlerese.length; i++) {
				if (c == ' ') {
					break;
				}
				
				// Find if it is caps or num
				if (c >= 97 && c <= 122) {
					// lower case
					lowerCase = true;
				}
				
				// Find index
				if (googlerese[i] == c) {
					charIndex = i;
					break;
				}
			}
			
			// Compute english equivalent
			char englishEquivalent;
			if (charIndex == -1) {
				englishEquivalent = ' ';
			}else if (lowerCase) {
				englishEquivalent = (char) (97 + charIndex);
			}else {
				englishEquivalent = (char) (65 + charIndex);
			}
			
			solutionBuffer = solutionBuffer.append(englishEquivalent);
		}
		
		return solutionBuffer.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < 2) {
			System.out
					.println("Not enough command line arguments specified. Need 2 (Input and output file paths)");
			return;
		}

		String inputFilePath = args[0];
		try {
			// String buffer for storing the output
			StringBuffer output = new StringBuffer();

			// Instantiate object to use non static methods
			SpeakingInTongues speaking = new SpeakingInTongues();
			
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
					noOfTestCases++;
					activeTestCaseNumber++;
					// Now that a test case has been parsed, compute output for
					// this test case

					// Invoke algorithm here
					String solutionToTestCase = speaking.computeSolutionString(strLine);

					// Prepare output string
					System.out.println(solutionToTestCase);
					output.append("Case #" + activeTestCaseNumber + ": "
							+ solutionToTestCase);
					output.append("\n");
				}
				lineNumber++;
			}

			in.close();

			// Pass output string to method to write to file
			speaking.writeOutputToFile(output.toString(), args[1]);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}