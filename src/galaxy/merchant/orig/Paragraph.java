package galaxy.merchant.orig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Lars on 4/30/2016.
 *
 *
 */
public class Paragraph {

    private Scanner scan;
    private ConversationLine conversationLine;
    private ErrorMessage eMessage;

    //This is the hash map that will contain the value for each identifier
    private HashMap<String, String> constantAssigments;
    //This is the hash map for storing the value of the calculation
    private HashMap<String, String> computedLiterals;

    private ArrayList<String> output;

    public Paragraph() {
        this.scan = new Scanner(System.in);
        this.conversationLine = new ConversationLine();
        this.eMessage = new ErrorMessage();
        this.constantAssigments = new HashMap<String, String>();
        this.computedLiterals = new HashMap<String, String>();
        this.output = new ArrayList<>();
    }

//        This method reads the paragraph from the input console
//        The input sequence can be terminated by a blank new line
//        Each input entered will be processed same time and if contains any formatting
//         error message will be shown imidiatelly
    public ArrayList<String> read() {
        String line;
        int count = 0;
        ErrorCodes error = null;

        while(this.scan.hasNextLine() && (line = this.scan.nextLine()).length() > 0) {
            error = validate(line);

            switch (error) {
                case NO_IDEA : this.output.add(this.eMessage.getMessage(error));
                    break;
                default: this.eMessage.printMessage(error);
            }
            count++;
        }

        switch (count) {
            case 0: error = ErrorCodes.NO_INPUT;
                this.eMessage.printMessage(error);
                break;
            default:
        }

        return this.output;
    }

    //Identify the type of line and process each input of the line
    public ErrorCodes validate(String line) {

        ErrorCodes error = ErrorCodes.SUCCESS_OK;

        ConversationLine.Type lineType = this.conversationLine.getLineType(line);

        switch (lineType) {
            case ASSIGNED: processAssignmentLine(line); break;
            case CREDITS:  processCreditsLine(line); break;
            case QUESTION_HOW_MUCH: processHowMuchQuestion(line); break;
            case QUESTION_HOW_MANY: processHowManyCreditsQuestion(line); break;

            default: error = ErrorCodes.NO_IDEA; break;
        }

        return error;
    }

    //retrieve the constant roman from the assignment line
    private void processAssignmentLine(String line) {

        //split the input in
        String[] splited = line.trim().split("\\s+");

        try {
            constantAssigments.put(splited[0], splited[2]);

        }catch (ArrayIndexOutOfBoundsException aio) {
            this.eMessage.printMessage(ErrorCodes.INCORRECT_LINE_TYPE);
            Utility.println(aio.getMessage());
        }
    }

    //process the type of "how_much" question and calculate the value from the assignments hashMap
    private void processHowMuchQuestion(String line) {
        try {
            //break the question line, separator(is)

            String formatted = line.split("\\sis\\s")[1].trim();

            //delete question mark from the string
            formatted = formatted.replace("?","").trim();

            //break the remain sentence into identifiers
            String keys[] =  formatted.split("\\s+");

            String romanResult = "";
            String completeResult = null;
            boolean errorOcurred = false;

            for(String key: keys) {
                //assign identifier a respective value
                String romanValue = constantAssigments.get(key);
                if(romanValue == null) {
                    //user input does not exist in the hash map
                    completeResult = this.eMessage.getMessage(ErrorCodes.NO_IDEA);
                    errorOcurred =  true;
                    break;
                }
                romanResult += romanValue;
            }

            if(!errorOcurred) {
                //Utility.println(romanResult.length()+"");
                romanResult = RomanNumbers.romanToArabic(romanResult);
                completeResult = formatted+" is "+romanResult;
            }

            output.add(completeResult);
        }catch (Exception e ) {
            this.eMessage.printMessage(ErrorCodes.INCORRECT_LINE_TYPE);
            Utility.println(e.getMessage());
        }
    }

    //extracts the type of credit given in the input
    private void processCreditsLine(String line) {
        try {
            String formatted = line.replaceAll("(is\\s+)|([c|C]redits)\\s*","").trim();

            //remaining content, split, separator(space)
            String[] keys = formatted.split("\\s");

            //concatenate all keys and obtain the final value
            String toBeComputed = keys[keys.length-2];
            float value = Float.parseFloat(keys[keys.length-1]);

            //
            String roman = "";
            for(int i=0; i<keys.length-2; i++) {
                roman += constantAssigments.get(keys[i]);
            }

            int romanNumber = Integer.parseInt(RomanNumbers.romanToArabic(roman));
            float credit = (float) (value/romanNumber);

            computedLiterals.put(toBeComputed, credit+"");

        }catch (Exception e) {
            this.eMessage.printMessage(ErrorCodes.INCORRECT_LINE_TYPE);
            Utility.println(e.getMessage());
        }
    }

    //calculate the value of credits to answer the "How many" questions
    private void processHowManyCreditsQuestion(String line) {
        try {

            //remove unecessary words(is, ?)
            String formatted = line.split("(\\sis\\s)")[1];

            formatted = formatted.replace("?","").trim();

            //compute the values of all numerals
            String[] keys = formatted.split("\\s");

            boolean found = false;
            String roman = "";
            String outputResult = null;
            Stack<Float> cvalues = new Stack<Float>();

            for(String key: keys) {
                found = false;

                String romanValue = constantAssigments.get(key);
                if(romanValue!=null) {
                    roman += romanValue;
                    found = true;
                }
                String computedValue = computedLiterals.get(key);
                if(!found && computedValue!=null) {
                    cvalues.push(Float.parseFloat(computedValue));
                    found = true;
                }
                if(!found) {
                    outputResult = this.eMessage.getMessage(ErrorCodes.NO_IDEA);
                    break;
                }

                if(found) {
                    float res = 1;
                    for(int i=0; i<cvalues.size(); i++)
                        res *= cvalues.get(i);

                    int finalres = (int) res;
                    if(roman.length() >0)
                        finalres = (int)(Integer.parseInt(RomanNumbers.romanToArabic(roman))*res);
                    outputResult = formatted + " is "+finalres+" Credits";
                }

                this.output.add(outputResult);
            }
        }catch (Exception e ) {
            this.eMessage.printMessage(ErrorCodes.INCORRECT_LINE_TYPE);
            Utility.println(e.getMessage());
        }
    }
}
