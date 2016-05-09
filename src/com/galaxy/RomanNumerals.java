package com.galaxy;

/**
 * Created by Lars on 5/2/2016.
 *
 * Set of methods to convert and validate roman numerals to arabic numerals
 */
public class RomanNumerals {
    public static final Error errorMessage = null;


    public static String romanNumberValidator =
            "^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    enum Roman {
        I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

        private int value;

        Roman(int value) { this.value = value; }

        public int getValue() { return  value; }
    }

    public static int getValueFromRomanSymbol(char romanChar){
        int value = -1;

        switch (romanChar) {
            case 'I': value = Roman.I.getValue(); break ;
            case 'V': value = Roman.V.getValue(); break ;
            case 'X': value = Roman.X.getValue(); break ;
            case 'L': value = Roman.L.getValue(); break ;
            case 'C': value = Roman.C.getValue(); break ;
            case 'D': value = Roman.D.getValue(); break;
            case 'M': value = Roman.M.getValue(); break;
        }
        return value;
    }

    //check the valid romanNumeral and convert or print an error message
    public static String convertRomanArabic(String roman) {
        String result = "";

        switch (validateRomanNumeral(roman)) {
            case 1: result = obtainRomanArabicDecimalValue(roman);
                break;
            default: result = Error.INVALID_ROMAN_STRING.getMessage();
        }
        return  result;
    }

    //validation of a romanNumeral input: 1 for validate, 0 for invalid
    private static int validateRomanNumeral(String roman) {
        int result = 0;
        if(roman.matches(romanNumberValidator))
            result = 1;

        return result;
    }

    private static String obtainRomanArabicDecimalValue(String roman) {
        int decimalResult = 0;
        int lastRomanSymbol = 0;

        for(int i=roman.length()-1; i>=0; i--) {
            char romanSymbol = roman.charAt(i);
            int romanSybolDecimalValue = getValueFromRomanSymbol(romanSymbol);
            decimalResult = obtainCombinedRomanNumeralValue(romanSybolDecimalValue, lastRomanSymbol, decimalResult);
            lastRomanSymbol = getValueFromRomanSymbol(romanSymbol);
        }
        return  decimalResult+"";
    }

    private static int obtainCombinedRomanNumeralValue(int totalDecimal, int lastRomanLetter, int lastDecimal) {
        if(lastRomanLetter > totalDecimal) {
            return lastDecimal - totalDecimal;
        } else {
            return lastDecimal + totalDecimal;
        }
    }
}
