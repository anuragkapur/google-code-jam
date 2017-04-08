package com.anuragkapur.gcj2017.qr;

import com.sun.tools.javac.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Anurag Kapur
 */
public class TidyNumbers {

    private static String inputFileName = "gcj2017/qr/B-small-attempt0.in";
    private static String outputFileName = "src/main/resources/gcj2017/qr/B-small-attempt0.out";
    private static ClassLoader classLoader;

    static {
        classLoader = TidyNumbers.class.getClassLoader();
    }

    private static void writeOutputToFile(String str) {
        Path file = Paths.get(outputFileName);
        try {
            Files.write(file, str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int findLargestTidy(int n) {

        for (int i=n; i>=0; i--) {
            if (isTidy(i))
                return i;
        }

        return -1;
    }

    private static boolean isTidy(int n) {
        String num = Integer.toString(n);
        String[] digits = num.split("");
        boolean tidy = true;
        for (int i = 0; i < digits.length-1; i++) {
            if (Integer.parseInt(digits[i]) > Integer.parseInt(digits[i+1])) {
                tidy = false;
                break;
            }
        }
        return tidy;
    }

    public static void main(String[] args) {

        Assert.checkNonNull(inputFileName, "InputFileName cannot be null");

        try {
            // String buffer for storing the output
            StringBuilder output = new StringBuilder();

            // Instantiate object to use non static methods


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
            while ((strLine = reader.readLine()) != null) {

                //System.out.println(strLine);

                if (lineNumber == 0) {
                    noOfTestCases = Integer.parseInt(strLine);
                } else {
                    noOfTestCases ++;
                    activeTestCaseNumber ++;
                    // Now that a test case has been parsed, compute output for
                    // this test case

                    // Invoke algorithm here
                    String solutionToTestCase = Integer.toString(findLargestTidy(Integer.parseInt(strLine)));

                    // Prepare output string
                    System.out.println(solutionToTestCase);
                    output.append("Case #").append(activeTestCaseNumber).append(": ").append(solutionToTestCase);
                    output.append("\n");
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
