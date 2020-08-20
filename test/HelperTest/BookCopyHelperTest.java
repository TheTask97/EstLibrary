package HelperTest;

import main.ConsoleHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookCopyHelperTest {

    private final static InputStream sysInBackup = System.in;
    private final static PrintStream sysOutBackup = System.out;
    private static String testString;

    @Test
    public void readStringTest() {

        testString = "test String";

        final ByteArrayInputStream testByte = new ByteArrayInputStream(testString.getBytes());
        System.setIn(testByte);
        assertEquals(testString, ConsoleHelper.readString(new BufferedReader(new InputStreamReader(System.in))));

    }

    @Test
    public void readIntSuccessTest() {

        testString = "1";

        final ByteArrayInputStream testByte = new ByteArrayInputStream(testString.getBytes());
        System.setIn(testByte);
        assertEquals(Integer.parseInt(testString), ConsoleHelper.readInt(new BufferedReader(new InputStreamReader(System.in))));
    }

    @Test
    public void readIntFailTest() {

        testString = "s";

        final ByteArrayInputStream testByte = new ByteArrayInputStream(testString.getBytes());
        System.setIn(testByte);
        assertEquals(-1, ConsoleHelper.readInt(new BufferedReader(new InputStreamReader(System.in))));
    }

    @Test
    public void printTest() {

        testString = "Test";
        final ByteArrayOutputStream outputTest = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputTest));
        ConsoleHelper.print(testString);
        assertEquals(testString, outputTest.toString().replaceAll("(\\r|\\n)", ""));

    }


    @BeforeAll
    public static void doThat() {
        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);
    }
}
