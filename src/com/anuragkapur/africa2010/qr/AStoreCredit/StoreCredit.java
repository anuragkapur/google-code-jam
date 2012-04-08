package com.anuragkapur.africa2010.qr.AStoreCredit;

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
public class StoreCredit {

	public void writeOutputToFile(String output, String filepath) throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	public String computeBestFitItems(int testCaseNumber, int credit,
			int[] prices) {
		int positionOfSolutionItem1 = -1;
		int positionOfSolutionItem2 = -1;
		boolean solutionFound = false;
		
		for (int i = 0; i < prices.length; i++) {
			int price1 = prices[i];
			positionOfSolutionItem1 = i + 1;
			for (int j = 0; j < prices.length; j++) {
				if (j == i) {
					// ignore this pass. you are comparing the same item, which
					// is not allowed
					continue;
				}
				int price2 = prices[j];
				if (price1 + price2 == credit) {
					positionOfSolutionItem2 = j + 1;
					solutionFound = true;
					break;
				}
			}
			if (solutionFound) {
				break;
			}
		}
		
		StringBuffer solutionStringBufferForSet = new StringBuffer();

		solutionStringBufferForSet = solutionStringBufferForSet.append("Case #" + testCaseNumber + ": ");
		if (positionOfSolutionItem1 < positionOfSolutionItem2) {
			solutionStringBufferForSet = solutionStringBufferForSet.append(positionOfSolutionItem1);
			solutionStringBufferForSet = solutionStringBufferForSet.append(" ");
			solutionStringBufferForSet = solutionStringBufferForSet.append(positionOfSolutionItem2);
		}else {
			solutionStringBufferForSet = solutionStringBufferForSet.append(positionOfSolutionItem2);
			solutionStringBufferForSet = solutionStringBufferForSet.append(" ");
			solutionStringBufferForSet = solutionStringBufferForSet.append(positionOfSolutionItem1);
		}
		
		return solutionStringBufferForSet.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.out.println("Not enough command line arguments specified. Need 2 (Input and output file paths)");
			return;
		}
		
		/**
		 * <pre>
		 * Input 
		 * 3
		 * 100
		 * 3
		 * 5 75 25
		 * 200
		 * 7
		 * 150 24 79 50 88 345 3
		 * 8
		 * 8
		 * 2 1 9 4 4 56 90 3
		 * </pre>
		 */

		String inputFilePath = args[0];
		try {
			// String buffer for storing the output
			StringBuffer output = new StringBuffer();
			
			// Instantiate object to use non static methods
			StoreCredit storeCredit = new StoreCredit();
			
			// read and parse input file
			FileInputStream fstream = new FileInputStream(inputFilePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int lineNumber = 0;
			int noOfTestCases = -1;
			int activeTestCaseNumber = 0;
			int creditForATestCase = -1;
			int noOfItemsForATestCase = -1;
			int[] pricesForATestCase;

			while ((strLine = br.readLine()) != null) {
				if (lineNumber == 0) {
					noOfTestCases = Integer.parseInt(strLine);
				} else if ((lineNumber % 3) == 1) {
					activeTestCaseNumber++;
					creditForATestCase = Integer.parseInt(strLine);
				} else if ((lineNumber % 3) == 2) {
					noOfItemsForATestCase = Integer.parseInt(strLine);
				} else if ((lineNumber % 3) == 0) {
					pricesForATestCase = new int[noOfItemsForATestCase];
					StringTokenizer tokenizer = new StringTokenizer(strLine,
							" ");
					int itemCount = 0;
					while (tokenizer.hasMoreElements()) {
						pricesForATestCase[itemCount] = Integer
								.parseInt((String) tokenizer.nextElement());
						itemCount++;
					}
					
					// Now that a test case has been parsed, compute output for
					// this test case
					String solutionToTestCase = storeCredit.computeBestFitItems(
							activeTestCaseNumber, creditForATestCase,
							pricesForATestCase);
					System.out.println(solutionToTestCase);
					output.append(solutionToTestCase);
					output.append("\n");
				}

				lineNumber++;
			}
			
			in.close();
			
			// Pass output string to method to write to file
			storeCredit.writeOutputToFile(output.toString(), args[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}
