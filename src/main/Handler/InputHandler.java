package main.Handler;

import main.ConsoleHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {
    /**
     * class for handling the main input
     */
    private boolean running = true; // variable to indicate if program is running

    public InputHandler() {

    }

    // starting the program / entering main menu
    public void run() {
        ConsoleHelper.print("System is loading....");

        final BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        int consoleInputParsed = Integer.MAX_VALUE;
        ConsoleHelper.print("Welcome. Please choose a menu point with typing the corresponding number and hitting enter.\n" +
                "Please note that there is still demo data in the project. The demo data is wrong about lent status and lent date sometimes." +
                "Thank you for staying with us and our software!");
        ConsoleHelper.print(MenuHandler.getWelcomeMenu());

        while (running) {

            try {
                consoleInputParsed = Integer.parseInt(bfr.readLine());
            } catch (NumberFormatException | IOException e) {
                ConsoleHelper.print("It seams like you didn't typed a valid integer number");
            }
            if (!(Integer.MAX_VALUE == consoleInputParsed)) {
                if (consoleInputParsed == 0) {
                    ConsoleHelper.print("System is shutting down...");
                    this.running = false;
                } else {
                    MenuHandler.getCorrespondingMenu(consoleInputParsed);
                    ConsoleHelper.print(MenuHandler.getWelcomeMenu());
                }

            }
        }

    }


}
