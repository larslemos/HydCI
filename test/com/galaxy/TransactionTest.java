package com.galaxy;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TransactionTest {

    ByteArrayInputStream testBytes = null;
    Transaction transaction = null;


    private void init(String data) {
        testBytes = new ByteArrayInputStream(data.getBytes());
        transaction = new Transaction(testBytes);
    }


    @Test
     public void testReadSentenceProcessTransactionWrongQuestion() throws Exception {

        init("how many Credits is Silver Gold ?");

        ArrayList<String> result = transaction.readSentenceProcessTransaction();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("I have no idea what you are talking about");

        assertEquals(expected, result);
    }

    @Test
    public void testReadSentenceProcessTransactionWrongRoman() throws Exception {

        String data = "prok is I\n" +
                "glob is V\n" +
                "pish is X\n" +
                "how much is prok glob pish ?";

        init(data);


        ArrayList<String> actual = transaction.readSentenceProcessTransaction();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("prok glob pish is Wrong Roman Numeral, it violates roman numeral format");

        assertEquals(expected, actual);
    }

    @Test
    public void testReadSentenceProcessTransactionAssignmentTest() throws Exception {

        String data = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "tegj is L\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "pish pish Iron is 3910 Credits\n" +
                "how much is pish tegj glob glob ?\n" +
                "how many Credits is glob prok Silver ?\n" +
                "how many Credits is glob prok Gold ?\n" +
                "how many Credits is glob prok Iron ?\n" +
                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?";

        init(data);


        ArrayList<String> actual = transaction.readSentenceProcessTransaction();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("pish tegj glob glob is 42");
        expected.add("glob prok Silver is 68 Credits");
        expected.add("glob prok Gold is 57800 Credits");
        expected.add("glob prok Iron is 782 Credits");
        expected.add("I have no idea what you are talking about");

        assertEquals(expected, actual);
    }

    @Test
    public void testReadSentenceProcessTransactionCreditsQuestion() throws Exception {
        String data = "glob is I\n" +
                "prok is V\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "how many Credits is Silver Gold ?";

        init(data);


        ArrayList<String> actual = transaction.readSentenceProcessTransaction();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Silver Gold is 245650 Credits");

        assertEquals(expected, actual);
    }

    @Test
    public void testReadSentenceProcessTransactionMetalValue() throws Exception {
        String data = "prok is I\n" +
                "how much is glob ?\n" +
                "how much is prok ?\n" +
                "glob is V\n" +
                "how much is glob ?";

        init(data);


        ArrayList<String> actual = transaction.readSentenceProcessTransaction();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("I have no idea what you are talking about");
        expected.add("prok is 1");
        expected.add("glob is 5");

        assertEquals(expected, actual);

    }

    @Test
    public void testReadSentenceProcessTransactionMetalValuesAndCredits() throws Exception {
        String data = "glob is I\n" +
                "prok is V\n" +
                "pish is X\n" +
                "how much is pish prok glob ?\n" +
                "tegj is L\n" +
                "how much is pish tegj glob glob ?\n" +
                "glob glob Silver is 34 Credits\n" +
                "glob prok Gold is 57800 Credits\n" +
                "how many Credits is glob prok Gold ?";

        init(data);


        ArrayList<String> actual = transaction.readSentenceProcessTransaction();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("pish prok glob is 16");
        expected.add("pish tegj glob glob is 42");
        expected.add("glob prok Gold is 57800 Credits");

        assertEquals(expected, actual);

    }


}