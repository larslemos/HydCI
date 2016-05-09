package galaxy.merchant.orig;

/**
 * Created by Lars on 4/30/2016.
 */
public class ErrorMessage {

    public ErrorMessage() {}

    //
    public void printMessage(ErrorCodes error) {
        String message = getMessage(error);

        if(message != null)
            Utility.println(message);
    }

    public String getMessage(ErrorCodes error) {

        String message = null;

        switch (error) {
            case NO_INPUT : message = "No input was specified! Program will exit"; break;
            case INVALID : message = "Input format is wrong! input discarded"; break;
            case INVALID_ROMAN_CHARACTER : message = "Illegal character specified in roman number, " +
                    "\n Sorry Input will be discarded ! "; break;
            case INVALID_ROMAN_STRING : message = "Wrong Roman number, it violates roman number format"; break;
            case INCORRECT_LINE_TYPE : message = "Exception caused during processing due to incorrect line type supplied "; break;
            case NO_IDEA : message = "I have no idea what you are talking about"; break;

            default: break;
        }

        return message;
    }
}
