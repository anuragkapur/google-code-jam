/**
 * 
 */
package com.anuragkapur.jam2012.r1c.A;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author anuragkapur
 * 
 */
public class DiamondInheritance {
	
	public void writeOutputToFile(String output, String filepath)
			throws IOException {
		FileWriter fstream = new FileWriter(filepath);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(output);
		out.flush();
		out.close();
	}
	
	// methods for algorithm logic here
	public String processTestCase(List<String> lines) {
		System.out.println("Test case");
		
		for (int i = 0; i < lines.size(); i++) {
			System.out.println(lines.get(i));
		}
		
		// 
		System.out.println(">>>>");
		
		Set<String> paths = new HashSet<String>();
		
		// create basic paths
		Set<String> basicPaths = new HashSet<String>();
		for (int i = 0; i < lines.size(); i++) {
			StringTokenizer tokenizer = new StringTokenizer(lines.get(i), " ");

			// skip first token
			tokenizer.nextElement();
			
			while (tokenizer.hasMoreElements()) {
				String token = (String) tokenizer.nextElement();
				int j = i + 1;
				basicPaths.add(j+ " " + token);
				System.out.println(j+ " " + token);
			}
		}
		
		// compute full set of paths (including consolidated)
		while(true) {
			Iterator<String> it1 = basicPaths.iterator();
			
			while(it1.hasNext()) {
				String a = it1.next();
				String aEndsWith = a.substring(a.lastIndexOf(" ")+1);
				paths.add(a);
				
				Iterator<String> it2 = basicPaths.iterator();
				while(it2.hasNext()) {
					String b = it2.next();
					String bStartsWith = b.substring(0,b.indexOf(" "));
					if (aEndsWith.equals(bStartsWith)) {
						String c = a + b.substring(b.indexOf(" "));
						System.out.println("New path :: " + c);
						paths.add(c);
					}
				}
			}
			
			if (paths.size() != basicPaths.size()) {
				int sizeBefore = basicPaths.size();
				basicPaths.addAll(paths);
				int sizeAfter = basicPaths.size();
				System.out.println("Will add more to basic paths :: BEFORE :: " + sizeBefore + " AFTER :: " + sizeAfter);
				if (sizeAfter == sizeBefore) {
					break;
				}
			}else {
				break;
			}
		}
		
		String solution = "No";
		
		Iterator<String> iterator = paths.iterator();
		while (iterator.hasNext()) {
			String a = iterator.next();
			String aStartsWith = a.substring(0,a.indexOf(" "));
			String aEndsWith = a.substring(a.lastIndexOf(" ")+1);
			Iterator<String> iterator2 = paths.iterator();
			while (iterator2.hasNext()) {
				String b = (String) iterator2.next();
				if (a.equals(b)) {
					// this is the same string, skip
				}else {
					String bStartsWith = b.substring(0,b.indexOf(" "));
					String bEndsWith = b.substring(b.lastIndexOf(" ")+1);
					if (aStartsWith.equals(bStartsWith) && aEndsWith.equals(bEndsWith)) {
						System.out.println("Match found :: " + a + " and :: " + b);
						solution = "Yes";
						break;
					}
				}
			}
			if (solution.equals("Yes")) {
				break;
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
			DiamondInheritance diamond = new DiamondInheritance();
			
			// read and parse input file
			FileInputStream fstream = new FileInputStream(inputFilePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int lineNumber = 0;
			int noOfTestCases = -1;
			int activeTestCaseNumber = 0;
			
			int linesInTestCase = 0;
			int currentLineInTestCase = 0;
			
			List<String> testCaseLines = new ArrayList<String>();
			
			while ((strLine = br.readLine()) != null) {
				
				if (lineNumber == 0) {
					noOfTestCases = Integer.parseInt(strLine);
				} else {
					if (linesInTestCase == 0) {
						
						// start of a new test case
						noOfTestCases ++;
						activeTestCaseNumber ++;
						currentLineInTestCase = 0;
						linesInTestCase = Integer.parseInt(strLine);
					}else {
						if(currentLineInTestCase < linesInTestCase){
							testCaseLines.add(strLine);
							currentLineInTestCase ++;
						}else {
							// Now that a test case has been parsed, compute output for
							// this test case
							
							// Invoke algorithm here
							String solutionToTestCase = diamond.processTestCase(testCaseLines);
							
							// Prepare output string
							System.out.println(solutionToTestCase);
							output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
							output.append("\n");
							
							// reset variables for next test case
							linesInTestCase = Integer.parseInt(strLine);
							currentLineInTestCase = 0;
							testCaseLines = new ArrayList<String>();
							activeTestCaseNumber ++;
						}
					}
				}
				lineNumber++;
			}
			
			// process last test case
			
			// Invoke algorithm here
			String solutionToTestCase = diamond.processTestCase(testCaseLines);
			
			// Prepare output string
			System.out.println(solutionToTestCase);
			output.append("Case #" + activeTestCaseNumber + ": " + solutionToTestCase);
			output.append("\n");
			
			in.close();
			
			// Pass output string to method to write to file
			diamond.writeOutputToFile(output.toString(), args[1]);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// File read cleanup
		}
	}
}
