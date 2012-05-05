/**
 * 
 */
package com.anuragkapur.jam2012.r1a.B;

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
public class KingdomRush {
	
	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	public void resetInputArray(int [][]a) {
		System.out.println("Will reset input array");
		for (int i = 0; i < 1001; i++) {
			for (int j = 0; j < 1001; j++) {
				a[i][j] = -1;
			}
		}
	}
	
	public void printInputArray(int [][]a) {
		System.out.println("Will print input array");
		for (int i = 0; i < 1001; i++) {
			System.out.println(a[i][0] + " "+ a[i][1]);
			if (a[i][0] == -1 && a[i][1] == -1) {
				break;
			}
		}
	}
	
	// methods for algorithm logic here
	public int quickestWin(int totalLevelsInTestCase, int [][]a) {
		int solution = 0;
		int currentStars = 0;
		int levelsCompleted = 0;
		
		while(true) {
			// One iteration logic
			boolean someProgress = false;
			
			for (int i = 0; i <= 1000; i++) {
				
				if (a[i][0] == -1) {
					// No more test cases, exit.
					break;
				}else if(a[i][1] == 9999) {
					// Level already complete. continue
					continue;
				}else {
					if(a[i][1] <= currentStars) {
						if (a[i][0] == 9999) {
							currentStars += 1;
						}else {
							currentStars += 2;
						}
						levelsCompleted ++;
						a[i][1] = 9999;
						solution ++;
						someProgress = true;
					}
				}
				
				if (someProgress) {
					break;
				}
			}
			
			if (!someProgress) {
				for (int j = 0; j <= 1000; j++) {
					if (someProgress) {
						break;
					}
					if (a[j][0] == -1) {
						break;
					}else if(a[j][1] == 9999) {
						continue;
					}else {
						if (a[j][0] == 9999) {
							// Level already completed, move on mate!
							continue;
						}else {
							if (a[j][0] <= currentStars) {
								currentStars += 1;
								a[j][0] = 9999;
								solution ++;
								someProgress = true;
							}	
						}		
					}
				}
			}
			
			if (!someProgress) {
				break;
			}
		}
		
		if (levelsCompleted != totalLevelsInTestCase) {
			solution = 0;
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
			KingdomRush kr = new KingdomRush();
			
			// read and parse input file
			FileInputStream fstream = new FileInputStream(inputFilePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int lineNumber = 0;
			int noOfTestCases = -1;
			int activeTestCaseNumber = 0;
			int noOfLevelsInTestCase = -1;
			int levelNumberInTestCase = -1;
			
			// first test setup
			int a[][] = new int[1001][1001];
			kr.resetInputArray(a);
			
			while ((strLine = br.readLine()) != null) {
				if (lineNumber == 0) {
					noOfTestCases = Integer.parseInt(strLine);
				}else {
					if (strLine.indexOf(" ") < 0) {
						// This is the start of a test case
						levelNumberInTestCase = -1;
						
						// Compute solution to previous test case
						// Now that a test case has been parsed, compute output for
						// this test case
						
						if (activeTestCaseNumber == 0) {
							// Ignore, nothing to compute yet
						}else {
							// Invoke algorithm here
							int solution = kr.quickestWin(noOfLevelsInTestCase, a);
							String solutionToTestCase;
							if (solution == 0) {
								solutionToTestCase = "Too Bad";
							}else {
								solutionToTestCase = "" + solution;
							}
							
							// Prepare output string
							output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
							output.append("\n");
						}
						
						// Setup for computing solution to new test case
						activeTestCaseNumber ++;
						noOfLevelsInTestCase = Integer.parseInt(strLine);
						kr.resetInputArray(a);
					}else {
						levelNumberInTestCase ++;
						StringTokenizer tokenizer = new StringTokenizer(strLine, " ");
						a[levelNumberInTestCase][0] = Integer.parseInt(tokenizer.nextToken());
						a[levelNumberInTestCase][1] = Integer.parseInt(tokenizer.nextToken());
						System.out.println("level in test case :: " + levelNumberInTestCase);
						System.out.println(a[levelNumberInTestCase][0] + " " + a[levelNumberInTestCase][1]);
						kr.printInputArray(a);
					}
				}
				lineNumber++;
			}
			
			// Compute solution to last test case
			// Invoke algorithm here
			int solution = kr.quickestWin(noOfLevelsInTestCase, a);
			String solutionToTestCase = "";
			if (solution == 0) {
				solutionToTestCase = "Too Bad";
			}else {
				solutionToTestCase = "" + solution;
			}
			
			// Prepare output string
			output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
			output.append("\n");
			
			in.close();
			
			// Pass output string to method to write to file
			kr.writeOutputToFile(output.toString(), args[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}