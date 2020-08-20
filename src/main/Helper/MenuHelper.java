package main.Helper;

import main.MenuPoint;

import java.util.HashMap;
import java.util.List;


public class MenuHelper {

    /**
     *  Class for helping creating menus
     */

    /**
     *
     * @param stringBuilder the stringBuilder which is used to create the output
     * @param menuOptions the options to be displayed
     * @param menuMap the map to be filled
     * @param isMainMenu boolean if it is the main menu
     */
    public static void addMenuOptions(final StringBuilder stringBuilder, final List<MenuPoint> menuOptions, final HashMap<Integer, MenuPoint> menuMap, final boolean isMainMenu){
        int menuPoint = 1;
        for (MenuPoint menuOption : menuOptions) {
            menuMap.put(menuPoint, menuOption);
            stringBuilder.append(String.format("(%s) " + menuOption.getName() + "\n", menuPoint));
            menuPoint++;
        }
        if(isMainMenu) {
            stringBuilder.append("(0) Exit");
        }
    }
}
