package com.anuragkapur.gcj2015.r1c;

import com.sun.tools.javac.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author anuragkapur
 */
public class ProblemB {

    private static String inputFileName = "template.in";
    private static String outputFileName = "src/main/resources/template.out";
    private static ClassLoader classLoader;

    static {
        classLoader = ProblemB.class.getClassLoader();
    }

    private String compute(String line1, String line2) {
        return "";
    }

    private static void writeOutputToFile(String str) {
        Path file = Paths.get(outputFileName);
        try {
            Files.write(file, str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Assert.checkNonNull(inputFileName, "InputFileName cannot be null");

        try {
            // String buffer for storing the output
            StringBuilder output = new StringBuilder();

            // Instantiate object to use non static methods
            ProblemB problem1 = new ProblemB();

            // read and parse input file
            URL fileUrl = classLoader.getResource(inputFileName);
            if (fileUrl == null) {
                System.out.println("File URL null. EXITING!");
                return;
            }
            String filePath = fileUrl.getPath();
            BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
            String strLine;

            int lineNumber = 0;
            int noOfTestCases = -1;
            int activeTestCaseNumber = 0;
            String line1 = null;
            while ((strLine = reader.readLine()) != null) {

                //System.out.println(strLine);

                if (lineNumber == 0) {
                    noOfTestCases = Integer.parseInt(strLine);
                } else {
                    if (lineNumber % 2 == 0) {
                        noOfTestCases ++;
                        activeTestCaseNumber ++;
                        // Now that a test case has been parsed, compute output for
                        // this test case

                        // Invoke algorithm here
                        String solutionToTestCase = problem1.compute(line1, strLine);

                        // Prepare output string
                        //System.out.println(solutionToTestCase);
                        output.append("Case #").append(activeTestCaseNumber).append(": ").append(solutionToTestCase);
                        output.append("\n");
                    } else {
                        line1 = strLine;
                    }

                }
                lineNumber++;
            }

            // Pass output string to method to write to file
            writeOutputToFile(output.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
