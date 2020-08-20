package main;

import main.Handler.DemoInitDataHandler;
import main.Handler.InputHandler;

public class CommandLine {

    /**
     * This class is only used to start the main program
     *
     * @param args can be used in future to enable navigation through numb-pad
     */
    public static void main(String[] args){
        DemoInitDataHandler.initDemoData();
        InputHandler inputHandler = new InputHandler();
        inputHandler.run();
    }

}
