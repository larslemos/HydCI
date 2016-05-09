package galaxy.merchant.orig;

/**
* Created by Lars on 4/29/2016.
*/
public class RomanNumbers {
    public static final ErrorMessage eMessage = new ErrorMessage();

    public static String romanNumberValidator =
            "^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    enum Roman {
        I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

        private int value;

        Roman(int value) { this.value = value; }

        public int getValue()  { return this.value; }
    }

    private static int getValueFromRomanChar(char romanChar) {
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

    public static String romanToArabic(String roman) {
        String result = "";

        switch (validateRomanNumber(roman)) {
            case 1 : result = convert(roman);
                break;
            default: result =
                    RomanNumbers.eMessage.getMessage(ErrorCodes.INVALID_ROMAN_STRING);
        }

        return result;
    }

    //validation of a romanNumber input: 1 for valid , 0 for invalid
    public static int validateRomanNumber(String roman) {
        int result = 0;
        if(roman.matches(romanNumberValidator)) {
            result = 1;
        }
        return result;
    }

    //convertion from roman number to arabic
    public static String convert(String roman) {
        int decimal = 0;
        int lastNumber = 0;

        for(int i= roman.length()-1; i>=0; i--) {
            char ch = roman.charAt(i);
            decimal = CheckRoman(getValueFromRomanChar(ch), lastNumber, decimal );
            lastNumber = getValueFromRomanChar(ch);
        }

        return decimal+"";
    }

    private static int CheckRoman(int totalDecimal, int lastRomanLetter, int lastDecimal ) {
        if(lastRomanLetter > totalDecimal) {
            return lastDecimal - totalDecimal;
        } else {
            return lastDecimal + totalDecimal;
        }
    }

}