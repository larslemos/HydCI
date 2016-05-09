package com.galaxy;

/**
 * Created by Lars on 5/2/2016.
 *  Set of possible errors that can occur in application
 *   Used by the ErrorClass
 *   Associate the error code to a given message to display to the user
 */
public enum  Error {
    //no error
    SUCCESS_OK(" "),
    //input provided is empty
    NO_INPUT("No input was specified! Program will exit"),
    //conversion line does not match possible types found in enum
    INVALID("Input format is wrong! input discarded"),
    //roman number has illegal characters
    INVALID_ROMAN_CHARACTER("Illegal character specified in roman number, Sorry Input will be discarded!"),
    //roman has invalid format
    INVALID_ROMAN_STRING("Wrong Roman Numeral, it violates roman numeral format"),
    //identified line differs from actual type
    INCORRECT_LINE_TYPE("Exception caused during processing due to incorrect line type supplied"),
    //
    NO_IDEA("I have no idea what you are talking about");
    //obtain the given error message and print

    private String message;

    Error(String value) {
        message = value;
    }

    public String getMessage() {
        return message;
    }

    //obtain the given error message and print
    public void printMessage() {
        System.out.print(this.message);
    }


}
