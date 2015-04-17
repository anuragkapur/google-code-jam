package com.anuragkapur.gcj2015.qr;

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
public class Dijkstra {


    private static String inputFileName = "C-small-attempt0.in";
    private static String outputFileName = "src/main/resources/C-small-attempt0 .out";
    private static ClassLoader classLoader;

    static {
        classLoader = Dijkstra.class.getClassLoader();
    }

    private String solution(String line1, String line2) {
        System.out.println(line1);
        System.out.println(line2);
        int noOfChars = Integer.parseInt(line1.split("\\s")[0]);
        int repetitions = Integer.parseInt(line1.split("\\s")[1]);
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<repetitions; i++) {
            builder.append(line2);
        }

        String str = builder.toString();
        char chars[] = str.toCharArray();
        int index = 0;

        boolean i = false;
        boolean j = false;
        boolean k = false;

        if (chars[index] == 'i') {
            i = true;
            index ++;
        } else if(index < chars.length) {
            String result = String.valueOf(chars[index]);
            index ++;
            while (index < chars.length) {
                result = multiply(result, String.valueOf(chars[index]));
                index ++;
                if (result.equals("i")) {
                    i = true;
                    break;
                }
            }
        }

        if (index < chars.length && chars[index] == 'j') {
            j = true;
            index ++;
        } else if(index < chars.length) {
            String result = String.valueOf(chars[index]);
            index ++;
            while (index < chars.length) {
                result = multiply(result, String.valueOf(chars[index]));
                index ++;
                if (result.equals("j")) {
                    j = true;
                    break;
                }
            }
        }

        if (index < chars.length && chars[index] == 'k') {
            k = true;
            index ++;
        } else if(index < chars.length) {
            String result = String.valueOf(chars[index]);
            index ++;
            while (index < chars.length) {
                result = multiply(result, String.valueOf(chars[index]));
                index ++;
                if (result.equals("k")) {
                    k = true;
                    break;
                }
            }
        }

        if (i && j && k && (index == chars.length)) {
            return "YES";
        } else {
            return "NO";
        }
    }

    private String multiply(String str1, String str2) {
        String result = null;

        switch (str1){
            case "1":
                if (str2.equals("1"))
                    result = "1";
                else if(str2.equals("i"))
                    result = "i";
                else if(str2.equals("j"))
                    result = "j";
                else if(str2.equals("k"))
                    result = "k";
                else
                    throw new IllegalArgumentException("bonkers");
                break;
            case "-1":
                if (str2.equals("1"))
                    result = "-1";
                else if(str2.equals("i"))
                    result = "-i";
                else if(str2.equals("j"))
                    result = "-j";
                else if(str2.equals("k"))
                    result = "-k";
                else
                    throw new IllegalArgumentException("bonkers");
                break;
            case "i":
                if (str2.equals("1"))
                    result = "i";
                else if(str2.equals("i"))
                    result = "-1";
                else if(str2.equals("j"))
                    result = "k";
                else if(str2.equals("k"))
                    result = "-j";
                else
                    throw new IllegalArgumentException("bonkers");
                break;
            case "-i":
                if (str2.equals("1"))
                    result = "-i";
                else if(str2.equals("i"))
                    result = "1";
                else if(str2.equals("j"))
                    result = "-k";
                else if(str2.equals("k"))
                    result = "j";
                else
                    throw new IllegalArgumentException("bonkers");
                break;
            case "j":
                if (str2.equals("1"))
                    result = "j";
                else if(str2.equals("i"))
                    result = "-k";
                else if(str2.equals("j"))
                    result = "-1";
                else if(str2.equals("k"))
                    result = "i";
                else
                    throw new IllegalArgumentException("bonkers");
                break;
            case "-j":
                if (str2.equals("1"))
                    result = "-j";
                else if(str2.equals("i"))
                    result = "k";
                else if(str2.equals("j"))
                    result = "1";
                else if(str2.equals("k"))
                    result = "-i";
                else
                    throw new IllegalArgumentException("bonkers");
                break;
            case "k":
                if (str2.equals("1"))
                    result = "k";
                else if(str2.equals("i"))
                    result = "j";
                else if(str2.equals("j"))
                    result = "-i";
                else if(str2.equals("k"))
                    result = "-1";
                else
                    throw new IllegalArgumentException("bonkers");
                break;
            case "-k":
                if (str2.equals("1"))
                    result = "-k";
                else if(str2.equals("i"))
                    result = "-j";
                else if(str2.equals("j"))
                    result = "i";
                else if(str2.equals("k"))
                    result = "1";
                else
                    throw new IllegalArgumentException("bonkers");
                break;
        }

        return result;
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
            Dijkstra dijkstra = new Dijkstra();

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
            String testCaseLine1 = null;
            String testCaseLine2 = null;

            while ((strLine = reader.readLine()) != null) {

                //System.out.println(strLine);

                if (lineNumber == 0) {
                    noOfTestCases = Integer.parseInt(strLine);
                } else {
                    if (lineNumber % 2 != 0) {
                        testCaseLine1 = strLine;
                    } else {
                        noOfTestCases ++;
                        activeTestCaseNumber ++;

                        testCaseLine2 = strLine;
                        // Now that a test case has been parsed, compute output for
                        // this test case

                        // Invoke algorithm here
                        String solutionToTestCase = dijkstra.solution(testCaseLine1, testCaseLine2);
                        System.out.printf(solutionToTestCase);
                        System.out.println("");

                        // Prepare output string
                        output.append("Case #").append(activeTestCaseNumber).append(": ").append(solutionToTestCase);
                        output.append("\n");
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
