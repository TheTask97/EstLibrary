package main;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleHelper {
    /**
     * Class to handle simple Output and reading from the console
     *
     */

    private static final String typedIllegal = "Seams you typed something illegal";
    public static void print(final String printString) {
        System.out.println(printString);
    }

    public static String readString(final BufferedReader bfr) {
        try {
            return bfr.readLine();
        } catch (IOException e) {
            ConsoleHelper.print(typedIllegal);
            return null;
        }
    }

    public static int readInt(final BufferedReader bfr) {
        try {
            return Integer.parseInt(bfr.readLine());
        } catch (NumberFormatException | IOException e) {
            ConsoleHelper.print(typedIllegal);
            return -1; // return -1 since it wont be ever a option. it will force to run the default case in switch cases
        }
    }
}
