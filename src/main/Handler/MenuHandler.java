package main.Handler;

import main.ConsoleHelper;
import main.Helper.MenuHelper;
import main.MenuPoint;

import java.util.Arrays;
import java.util.HashMap;

public class MenuHandler {
    /**
     * Class is used to create the main menu points
     */

    private static final HashMap<Integer, MenuPoint> menuMap = new HashMap<>();

    public static String getWelcomeMenu() {
        final StringBuilder welcomeMenu = new StringBuilder();
        MenuHelper.addMenuOptions(welcomeMenu, Arrays.asList(MenuPoint.SEARCH_BOOK_COPY, MenuPoint.BORROW_BOOK_COPY
                , MenuPoint.RETURN_BOOK, MenuPoint.DATA, MenuPoint.OPTIONS), menuMap, true);
        return welcomeMenu.toString();
    }

    /**
     *
     * @param menuPoint the chosen menu point
     */
    public static void getCorrespondingMenu(final int menuPoint){
      if(menuPoint > 0 && menuPoint<= menuMap.size()) {
          menuMap.get(menuPoint).demoMenu();
      }else{
          ConsoleHelper.print("Please enter a valid number for the menu.");
      }
    }

}
