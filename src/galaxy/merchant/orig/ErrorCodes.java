package galaxy.merchant.orig;

/**
 * Created by Lars on 4/29/2016.
 * Set of possible errors that can occur in applicaiton
 * Used by the ErrorClass
 */
public enum ErrorCodes {
    //no error
    SUCCESS_OK,
    //input provided is empty
    NO_INPUT,
    //conversation line does not match possible types found in enum
    INVALID,
    //roman number has illegal characters
    INVALID_ROMAN_CHARACTER,
    //roman number has invalid format
    INVALID_ROMAN_STRING,
    //identified line differs from actual type
    INCORRECT_LINE_TYPE,

    NO_IDEA
}
