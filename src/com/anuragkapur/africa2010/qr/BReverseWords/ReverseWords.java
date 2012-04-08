package com.anuragkapur.africa2010.qr.BReverseWords;

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
public class ReverseWords {

	public void writeOutputToFile(String output, String filepath) throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	public String reverseWordsInString(String input, int testCaseNumber) {
		StringTokenizer tokenizer = new StringTokenizer(input, " ");
		int wordCount = tokenizer.countTokens();
		String[] reversedWords = new String[wordCount];
		
		int elementCount = 0;
		while (tokenizer.hasMoreElements()) {
			elementCount ++;
			String word = (String) tokenizer.nextElement();
			reversedWords[wordCount - elementCount] = word;
		}
		
		StringBuffer solutionStringBuffer = new StringBuffer();
		solutionStringBuffer.append("Case #" + testCaseNumber + ": ");
		
		for (int i = 0; i < reversedWords.length; i++) {
			solutionStringBuffer.append(reversedWords[i]);
			solutionStringBuffer.append(" ");
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
			ReverseWords reverseWords = new ReverseWords();
			
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
					String solutionToTestCase = reverseWords.reverseWordsInString(stringForTestCase, activeTestCaseNumber);
					System.out.println(solutionToTestCase);
					output.append(solutionToTestCase);
					output.append("\n");
				}

				lineNumber++;
			}
			
			in.close();
			
			// Pass output string to method to write to file
			reverseWords.writeOutputToFile(output.toString(), args[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}
