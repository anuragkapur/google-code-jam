package com.anuragkapur.jam2012.qr.B;

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
public class DancingWithGooglers {
	
	boolean lifelineAvailable = true;
	int noOfSurprisingTripletsAllowed = -1;
	
	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	// methods for algorithm logic here
	public boolean checkIfTotalScoreMeetsCriteria(int score, int requiredBestScore) {
		
		boolean solution = false;
		int perfectTotal = requiredBestScore * 3;
		
		if (score == 0) {
			if (requiredBestScore == 0) {
				solution = true;
			}else {
				solution = false;
			}
		}else if (perfectTotal == score) {
			// Perfect case. No problems here.
			solution = true;
		}else {
			// First try not to use lifeline
			int difference = perfectTotal - score;
			
			if (difference > 2) {
				// Check if this is doable at all
				if (difference > 4) {
					// No way, this is not doable
					solution = false;
				}else {
					// Can't do without lifeline. Check if lifeline is available
					if (noOfSurprisingTripletsAllowed > 0) {
						// Uselifeline and done.
						noOfSurprisingTripletsAllowed --;
						solution = true;
					}else {
						// No lifeline available
						solution = false;
					}
				}
			}else {
				// Can do without lifeline
				solution = true;
			}
		}
		
		return solution;
	}
	
	public int solveTestCase(String input) {
		
		System.out.println("\n\n\n\nWill solve input string :: " + input);
		
		StringTokenizer tokenizer = new StringTokenizer(input, " ");
		
		int noOfGooglers = -1;
		int bestScore = -1;
		int solution = 0;
		
		int tokenCount = 0;
		while (tokenizer.hasMoreElements()) {
			tokenCount ++;
			
			String token = (String) tokenizer.nextElement();
			if (tokenCount == 1) {
				lifelineAvailable = true;
				noOfGooglers = Integer.parseInt(token);
				System.out.println("no of googlers :: " + noOfGooglers);
			}else if (tokenCount == 2) {
				noOfSurprisingTripletsAllowed = Integer.parseInt(token);
				System.out.println("no of surprising triplets allowed :: " + noOfSurprisingTripletsAllowed);
			}else if (tokenCount == 3) {
				bestScore = Integer.parseInt(token);
				System.out.println("required best score :: " + bestScore);
			}else {
				// This is a googlers score. Check if it meets criteria
				int perfectTotal = bestScore * 3;
				int score = Integer.parseInt(token);
				boolean didMeetCriteria = false;
				
				if (perfectTotal == score) {
					didMeetCriteria = true;
				}else if(perfectTotal > score) {
					didMeetCriteria = checkIfTotalScoreMeetsCriteria(Integer.parseInt(token), bestScore);
				}else if (perfectTotal < score) {
					int difference = score - perfectTotal;
					while(difference > 4) {
						perfectTotal += 3;
						difference = score - perfectTotal;
					}
					didMeetCriteria = checkIfTotalScoreMeetsCriteria(Integer.parseInt(token), bestScore);
				}
				
				System.out.println("check if score meets criteria :: " + score);
				
				if (didMeetCriteria) {
					solution ++;
					System.out.println("Criteria met, solution count :: " + solution);
				}else {
					System.out.println("Didnt meet criteria");
				}
			}
		}
		
		return solution;
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
			DancingWithGooglers dance = new DancingWithGooglers();
			
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
					int solutionToTestCase = dance.solveTestCase(strLine);
					
					// Prepare output string
					System.out.println(solutionToTestCase);
					output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
					output.append("\n");
				}
				lineNumber++;
			}
			
			in.close();
			
			// Pass output string to method to write to file
			dance.writeOutputToFile(output.toString(), args[1]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}