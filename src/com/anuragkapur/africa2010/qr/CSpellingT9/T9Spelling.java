/**
 * 
 */
package com.anuragkapur.africa2010.qr.CSpellingT9;

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
public class T9Spelling {

	String[] characterToKeyMap = { "2", "22", "222", "3", "33", "333", "4",
			"44", "444", "5", "55", "555", "6", "66", "666", "7", "77", "777",
			"7777", "8", "88", "888", "9", "99", "999", "9999" };

	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}

	public String computeT9String(String input, int testCaseNumber) {
		char[] characters = input.toCharArray();
		char previous = 7777;
		char charOfPreviousKeyString = 9999;
		char charOfCurrentKeyString = 8888;
		
		StringBuffer solutionStringBuffer = new StringBuffer();
		
		for (int i = 0; i < characters.length; i++) {
			int charIntValue = characters[i];
			int indexInCharMap = charIntValue - 97;
			char current = characters[i];
			
			if (charIntValue == 32) {
				charOfCurrentKeyString = 0;
				if (i != 0) {
					if (current == previous) {
						solutionStringBuffer.append(" ");
					}else if (charOfPreviousKeyString == charOfCurrentKeyString) {
						solutionStringBuffer.append(" ");
					}
				}
				solutionStringBuffer.append("0");
				charOfPreviousKeyString = 0;
			}else {
				String keyStringForChar = characterToKeyMap[indexInCharMap];
				charOfCurrentKeyString = keyStringForChar.charAt(0);
				
				if (i != 0) {
					if (current == previous) {
						solutionStringBuffer.append(" ");
					}else if (charOfPreviousKeyString == charOfCurrentKeyString) {
						solutionStringBuffer.append(" ");
					}
				}
				
				solutionStringBuffer.append(characterToKeyMap[indexInCharMap]);				
				charOfPreviousKeyString = keyStringForChar.charAt(0);
			}
			previous = current;
		}
		
		return solutionStringBuffer.toString();
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
			T9Spelling t9Spelling = new T9Spelling();
			
			// read and parse input file
			FileInputStream fstream = new FileInputStream(inputFilePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int lineNumber = 0;
			int noOfTestCases = -1;
			int activeTestCaseNumber = 0;
			String stringForTestCase = "";

			while ((strLine = br.readLine()) != null) {
				if (lineNumber == 0) {
					noOfTestCases = Integer.parseInt(strLine);
				} else {
					activeTestCaseNumber ++;
					stringForTestCase = strLine;
					// Now that a test case has been parsed, compute output for
					// this test case
					String solutionToTestCase = t9Spelling.computeT9String(stringForTestCase, activeTestCaseNumber);
					System.out.println(solutionToTestCase);
					output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
					output.append("\n");
				}

				lineNumber++;
			}
			
			in.close();
			
			// Pass output string to method to write to file
			t9Spelling.writeOutputToFile(output.toString(), args[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}
