package HelperTest;

import main.Helper.MenuHelper;
import main.MenuPoint;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuHelperTest {

    private static StringBuilder stringBuilder;

    public static void init() {
        stringBuilder = new StringBuilder();
    }

    @Test
    public void addMainMenuTest() {
        init();
        MenuHelper.addMenuOptions(stringBuilder, Collections.emptyList(), new HashMap<>(), true);
        assertEquals("(0) Exit", stringBuilder.toString());
    }

    @Test
    public void addNotMainMenuTest() {
        init();
        MenuHelper.addMenuOptions(stringBuilder, Arrays.asList(MenuPoint.DATA), new HashMap<>(), false);
        assertEquals("(1) Data menu\n", stringBuilder.toString());

    }
}
