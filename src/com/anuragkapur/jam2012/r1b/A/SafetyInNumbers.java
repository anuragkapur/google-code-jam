/**
 * 
 */
package com.anuragkapur.jam2012.r1b.A;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author anuragkapur
 * 
 */
public class SafetyInNumbers {
	
	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	// methods for algorithm logic here
	public String computeSolution(String testcase) {
		
		StringTokenizer tokenizer = new StringTokenizer(testcase, " ");
		int noOfContestants = -1;
		float sumOfScores = 0;
		List<Float> judgedScores = new ArrayList<Float>();
		List<Float> sortedJudgedScores = new ArrayList<Float>();
		
		// Create list of scores
		int count = -1;
		while (tokenizer.hasMoreElements()) {
			count ++;
			String token = (String) tokenizer.nextElement();
			if (count == 0) {
				noOfContestants = Integer.parseInt(token);
			}else {
				float judgedScore = Float.parseFloat(token);
				sumOfScores += judgedScore;
				judgedScores.add(judgedScore);
			}
		}
		
		// create another list of sorted scores
		sortedJudgedScores.addAll(judgedScores);
		Collections.sort(sortedJudgedScores);
		
		// compute no of contestants sharing lowest score
		int noOfContestantsWithSharedLowest = 2;
		float sumOfJudgedLowScores = sortedJudgedScores.get(0) + sortedJudgedScores.get(1);
		
		float sharedLowest;
		
		while(true) {
			// compute shared lowest score
			sharedLowest = (sumOfScores + sumOfJudgedLowScores)/noOfContestantsWithSharedLowest;
			System.out.println("sharedLowest :: " + sharedLowest + "sumOfJudgedLowScores :: " + sumOfJudgedLowScores);
			if (noOfContestantsWithSharedLowest<noOfContestants &&sortedJudgedScores.get(noOfContestantsWithSharedLowest) < sharedLowest) {
				// repeat
				sumOfJudgedLowScores += sortedJudgedScores.get(noOfContestantsWithSharedLowest);
				noOfContestantsWithSharedLowest ++;
				continue;
			}else {
				// valid combination found
				break;
			}
		}
		
		// compute percentages
		StringBuffer percentagesBuffer = new StringBuffer();
		
		for (int i = 0; i < judgedScores.size(); i++) {
			float percentage = 0;
			float score = judgedScores.get(i);
			int index = Collections.binarySearch(sortedJudgedScores, score);
			if (index > noOfContestantsWithSharedLowest) {
				percentage = 0;
			}else {
				percentage = (((sharedLowest - score) * 100) / sumOfScores);
				if (percentage < 0) {
					percentage = 0;
				}
			}
			percentagesBuffer.append(percentage + " ");
		}
		
		return percentagesBuffer.toString();
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
			SafetyInNumbers numbers = new SafetyInNumbers();
			
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
					String solutionToTestCase = numbers.computeSolution(strLine);
					
					// Prepare output string
					System.out.println(solutionToTestCase);
					output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
					output.append("\n");
				}
				lineNumber++;
			}
			
			in.close();
			
			// Pass output string to method to write to file
			numbers.writeOutputToFile(output.toString(), args[1]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}