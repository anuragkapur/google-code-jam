/**
 * 
 */
package com.anuragkapur.jam2012.r1b.D;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anuragkapur
 * 
 */
public class EqualSums {
	
	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	// methods for algorithm logic here
	
	
	public void printAllSets() {
		int a[] = {1,2,3,4,5};
		
		List<String> sets = new ArrayList<String>();
		
		// sets 
		for (int i = 0; i < a.length; i++) {
			sets.add(a[i]+"");
			for (int j = i+1; j < a.length; j++) {
				sets.add(a[i]+" "+a[j]);
				for (int j2 = j+1; j2 < a.length; j2++) {
					sets.add(a[i]+" "+a[j]+" "+a[j2]);
					for (int k = j2+1; k < a.length; k++) {
						sets.add(a[i]+" "+a[j]+" "+a[j2]+" "+a[k]);
						for (int k2 = k+1; k2 < a.length; k2++) {
							sets.add(a[i]+" "+a[j]+" "+a[j2]+" "+a[k]+" "+a[k2]);
						}
					}
				}
			}
		}
		
		for (int i = 0; i < sets.size(); i++) {
			System.out.println(sets.get(i));
		}
		
		System.out.println("no of sets :: " + sets.size());
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
			EqualSums sums = new EqualSums();
			
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
					sums.printAllSets();
					String solutionToTestCase = "";
					
					// Prepare output string
					System.out.println(solutionToTestCase);
					output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
					output.append("\n");
				}
				lineNumber++;
			}
			
			in.close();
			
			// Pass output string to method to write to file
			//<class object>.writeOutputToFile(output.toString(), args[1]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}
