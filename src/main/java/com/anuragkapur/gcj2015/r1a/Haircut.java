package com.anuragkapur.gcj2015.r1a;

import com.sun.tools.javac.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anuragkapur
 */
public class Haircut {

    private static String inputFileName = "B-small-attempt1.in";
    private static String outputFileName = "src/main/resources/B-small-attempt1.out";
    private static ClassLoader classLoader;
    private static Map<String, Integer> memoized = new HashMap<>();

    static {
        classLoader = Haircut.class.getClassLoader();
    }

    private int compute(String line1, String line2) {
        //System.out.println(line1);
        //System.out.println(line2);
        int b = Integer.parseInt(line1.split("\\s")[0]);
        int n = Integer.parseInt(line1.split("\\s")[1]);

        String tokens[] = line2.split("\\s");
        int barberTimes[] = new int[tokens.length];
        boolean areAllEqual = true;
        for (int i=0; i<barberTimes.length; i++) {
            barberTimes[i] = Integer.parseInt(tokens[i]);
            if (i > 0) {
                if (barberTimes[i] != barberTimes[i-1]) {
                    areAllEqual = false;
                }
            }
        }

        // if all barber times are equal
        if (areAllEqual) {
            int mod = n % b;
            //System.out.println("all equal");
            return b - mod;
        }

        int barbers[][] = new int[b][2];

        int minTime = minDiff(barberTimes);
        int maxTime = max(barberTimes);

        // t=0;
        int queueTracker = 0;
        for (int i=0; i<b; i++) {
            if(i+1 == n) {
                return i+1;
            }
            barbers[i][0] = i;
            barbers[i][1] = 0;
            queueTracker = i+1;
        }

        while(queueTracker < n) {

            // update barber elapsed times with current customer
            for (int i=0; i<b; i++) {
                barbers[i][1] = barbers[i][1] + minTime;
            }

            // check if a barber is finishing with a customer
            for (int i=0; i<b; i++) {
                if (barbers[i][1] == barberTimes[i]) {
                    queueTracker ++;
                    barbers[i][0] = queueTracker;
                    barbers[i][1] = 0;

                    if (queueTracker == n) {
                        return i+1;
                    }
                }
            }
        }

        return -1;
    }

    private int max(int a[]) {
        int max = a[0];
        for (int i=1; i<a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }

    private int minDiff(int a[]) {
        int b[] = Arrays.copyOf(a, a.length);
        Arrays.sort(b);

        if (b.length == 2) {
            return b[1] - b[0];
        }

        int minDif = b[1] - b[0];
        for (int i=2; i<b.length; i++) {
            int localMinDif = b[i] - b[i-1];
            if (localMinDif < minDif) {
                minDif = localMinDif;
            }
        }

        return minDif;
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
            Haircut problem = new Haircut();

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
                        String solutionToTestCase = String.valueOf(problem.compute(line1, strLine));

                        // Prepare output string
                        System.out.println(activeTestCaseNumber + " :: " + solutionToTestCase);
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