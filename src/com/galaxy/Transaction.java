package com.galaxy;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Lars on 5/2/2016.
 *
 * Set of methods to read given input and  validate and delegate to required types of transactions
 *
 */
public class Transaction {

    public Scanner scan;
    private ConversionLine conversionLine;
    private Error errorMessage;

    //This is the hash map that will contain the value for each identifier
    private HashMap<String, String> constantAssignments;

    //This is the hash map for storing the value of the calculation
    private HashMap<String, String> computedLiterals;

    private ArrayList<String> transactionOutput;

    public Transaction(InputStream source) {
        this.scan = new Scanner(source);
        this.conversionLine = new ConversionLine();
        this.errorMessage = Error.SUCCESS_OK;
        this.constantAssignments = new HashMap<String, String>();
        this.computedLiterals = new HashMap<String, String>();
        this.transactionOutput = new ArrayList<>();
    }

    /*
        This method reads the paragraph from the input console
        The input sequence can be terminated by blank new line
        Each input entered will be processed same time and if contains any formatting
        error will be sho
     */
    public ArrayList<String> readSentenceProcessTransaction() {
        String line;
        int count = 0;
        Error errorCodes = null;

        while(scan.hasNextLine() && (line = scan.nextLine()).length() > 0) {
            errorCodes = processTypeOfLine(line);

            switch (errorCodes) {
                case NO_IDEA: transactionOutput.add(errorCodes.getMessage());
                    break;
                case SUCCESS_OK: break;
                default: errorCodes.printMessage();
            }

            count++;
        }

        switch (count) {
            case 0: Error.NO_INPUT.printMessage();
                break;

            default:
        }

        return transactionOutput;
    }

    //Identify the type of line and process each input of the line
    public Error processTypeOfLine(String line) {

        Error errorCodes = Error.SUCCESS_OK;

        ConversionLine.Type lineType = conversionLine.getLineType(line);

        switch (lineType) {
            case ASSIGNED: processAssignmentConversion(line); break;
            case CREDITS: processCreditsConversion(line); break;
            case QUESTION_HOW_MUCH: processHowMuchQuestion(line); break;
            case QUESTION_HOW_MANY: processManyCreditsQuestion(line); break;

            default: errorCodes = Error.NO_IDEA; break;
        }

        return errorCodes;
    }

    //retrieve the constant roman from the assignment line
    private void processAssignmentConversion(String line) {

        //split the input, separator(spaces)
        String [] metalSymbol = line.trim().split("\\s+");

        try {
            //store the metal and correspondent value
            constantAssignments.put(metalSymbol[0], metalSymbol[2]);

        }catch (ArrayIndexOutOfBoundsException aio) {
            Error.INCORRECT_LINE_TYPE.printMessage();
            System.out.println(aio.getMessage());
        }
    }

    //process the type of "How much" question and calcule the value from the assignments hashMap
    private void processHowMuchQuestion(String line) {

        try {
            //break the question line, separator(is)
            String formatted = line.split("\\sis\\s")[1].trim();

            //delete question mark from the string
            formatted = formatted.replace("?","").trim();

            //break the remain sentence into identifiers
            String keys[] = formatted.split("\\s+");

            String romanResult = "";
            String completeResult = null;
            boolean errorOccurred = false;

            for(String key: keys) {
                //assign identifier a respective value
                String romanValue = constantAssignments.get(key);
                if(romanValue == null) {
                    //user input does not exist in the hash map
                    completeResult = Error.NO_IDEA.getMessage() ;
                    errorOccurred = true;
                    break;
                }
                romanResult += romanValue;
            }

            if(!errorOccurred) {
                //
                romanResult = RomanNumerals.convertRomanArabic(romanResult);
                completeResult = formatted+" is "+romanResult;
            }

            transactionOutput.add(completeResult);

        }catch (Exception e) {
            Error.INCORRECT_LINE_TYPE.printMessage();
            System.out.println(e.getMessage());
        }
    }

    //extracts the type of credit given in the input
    private void processCreditsConversion(String line) {

        try {
            //remove the sentence separator(is) and (credits)
            String formatted = line.replaceAll("(is\\s+)|([c|C]redits)\\s*","").trim();

            //remaining content, split, separator(space)
            String[] keys = formatted.split("\\s");

            //concatenate all keys and obtain the final value
            String toBeComputed = keys[keys.length - 2];
            float value = Float.parseFloat(keys[keys.length - 1]);

            //join remaining strings
            String roman = "";
            for (int i = 0; i < keys.length - 2; i++)
                roman += constantAssignments.get(keys[i]);

            //compute credit rate for given metal
            int romanNumber = Integer.parseInt(RomanNumerals.convertRomanArabic(roman));
            float credit = (float) (value / romanNumber);

            computedLiterals.put(toBeComputed, credit+"");

        }catch (Exception e) {
            Error.INCORRECT_LINE_TYPE.printMessage();
            System.out.println(e.getMessage());
        }
    }

    //calculate the value of credits to answer the "How many" questions
    private void processManyCreditsQuestion(String line) {
        try {
            //remove unnecessary words(is, ?) - break logic
            String [] word = line.split("(\\sis\\s)");
            String formatted = word[1].replace("?","").trim();

            //compute the values of all numerals
            String [] keys = formatted.split("\\s");

            boolean found = false;
            String roman = "";
            String outputResult = null;
            Stack<Float> creditValues = new Stack<Float>();

            for (String key: keys) {
                found = false;

                String romanValue = constantAssignments.get(key);
                if (romanValue != null) {
                    roman += romanValue;
                    found = true;
                }
                String computedValue = computedLiterals.get(key);
                if (!found && computedValue != null) {
                    creditValues.push(Float.parseFloat(computedValue));
                    found = true;
                }
                if (!found) {
                    outputResult = Error.NO_IDEA.getMessage();
                    break;
                }
            }
                if(found) {
                    float res = 1;
                    for(int i=0; i < creditValues.size(); i++)
                        res *= creditValues.get(i);

                    int finalresult = (int) res;
                    if(roman.length() > 0 )
                        finalresult = (int)(Integer.parseInt(RomanNumerals.convertRomanArabic(roman))*res);
                    outputResult = formatted + " is "+finalresult+" Credits";
                }

                transactionOutput.add(outputResult);
        }catch (Exception e) {
            Error.INCORRECT_LINE_TYPE.printMessage();
            System.out.println(e.getMessage());
        }
    }


}
