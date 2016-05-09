package com.galaxy;

import java.util.ArrayList;

/**
 * Created by Lars on 5/2/2016.
 *
 * Starting point of the application
 */
public class MerchantApplication {

    public static void main(String [] args ) {

        System.out.println("<Merchant Galaxy> Welcome, please provide input" +
                " below and a blank new line to end the application ");

        //New Transaction, and define the source of input
        Transaction transaction = new Transaction(System.in);

        //Read the input from console, validate and process
        ArrayList<String> output = transaction.readSentenceProcessTransaction();

        for(int i=0; i<output.size(); i++)
            System.out.println(output.get(i));


    }


}
