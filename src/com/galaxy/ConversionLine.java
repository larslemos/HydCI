package com.galaxy;

/**
 * Created by Lars on 5/2/2016.
 *
 * Set of regular expressions to check whether that input belongs to our assumption or not
 */
public class ConversionLine {

    private LineFilter[] linefilter;

    //Enumeration that hold the different type of possible line types of notation
    public static enum Type {
        //line of Assigment type
        ASSIGNED("^([A-Za-z]+) is ([I|V|X|L|C|D|M])$"),
        //line of Credits type
        CREDITS("^([A-Za-z]+)([A-Za-z\\s]*) is ([0-9]+) ([c|C]redits)$"),
        //line of question of type for quantity price (How much)
        QUESTION_HOW_MUCH("^how much is (([A-Za-z\\s])+)\\?$"),
        //line of question of type for cost of credits (How many)
        QUESTION_HOW_MANY("^how many [c|C]redits is (([A-Za-z\\s])+)\\?$"),
        //line that represents no match type
        NOMATCH("");

        Type(String value) { this.pattern = value; }

        private String pattern;

        public String getPattern() {    return this.pattern; }

    }

    //initialize the set of possible galactic transactions
    public ConversionLine() {
        //amount of filters
        this.linefilter = new LineFilter[4];
        this.linefilter[0] = new LineFilter(Type.ASSIGNED, Type.ASSIGNED.getPattern());
        this.linefilter[1] = new LineFilter(Type.CREDITS, Type.CREDITS.getPattern());
        this.linefilter[2] = new LineFilter(Type.QUESTION_HOW_MUCH, Type.QUESTION_HOW_MUCH.getPattern());
        this.linefilter[3] = new LineFilter(Type.QUESTION_HOW_MANY, Type.QUESTION_HOW_MANY.getPattern());
    }

    //retrieves the line type for the particular line
    public ConversionLine.Type getLineType(String line) {
        line = line.trim();
        ConversionLine.Type result = Type.NOMATCH;

        boolean matchFound = false;

        for(int i=0; i<linefilter.length && !matchFound; i++) {
            if(line.matches(linefilter[i].getPattern())) {
                matchFound = true;
                result = linefilter[i].getType();
            }
        }

        return  result;
    }



}
