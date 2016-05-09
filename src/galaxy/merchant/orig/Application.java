package galaxy.merchant.orig;



import java.util.ArrayList;

/**
 * Created by Lars on 4/30/2016.
 *
 * Starting point of the application
 */
public class Application {

    public static void main(String [] args ){

        Utility.println("Welcome, please provide input below and a blank new line to stop the application");

        //New paragraph
        Paragraph paragraph = new Paragraph();

        //Read the input from console, validate and process
        ArrayList<String> output = paragraph.read();

        for(int i=0; i<output.size(); i++)
            Utility.println(output.get(i));

    }
}
