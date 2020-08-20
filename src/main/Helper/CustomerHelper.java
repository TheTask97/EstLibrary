package main.Helper;

import main.ConsoleHelper;
import main.DatabaseInfo;
import main.Elements.Customer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CustomerHelper {

    public static Customer getSpecCustomer() {
        ConsoleHelper.print("You can press 0 to cancel. You will be brought back to the main menu");
        ConsoleHelper.print("Please type in the corresponding number of the User you want to select");
        final HashMap<Customer, Integer> customers = DatabaseInfo.getCustomers();
        final HashMap<Integer, Customer> correspondingCustomer = new HashMap<>();
        int chooseId = 1;

        for (final Customer customer : customers.keySet()) {
            ConsoleHelper.print(String.format("(%s) " + customer.getFormattedData(), chooseId));
            correspondingCustomer.put(chooseId, customer);
            chooseId++;
        }
        while (true) {
            int chosenValue = ConsoleHelper.readInt(new BufferedReader(new InputStreamReader(System.in)));
            if (chosenValue > 0 && chosenValue <= customers.size()) {
                return correspondingCustomer.get(chosenValue);
            }
            if (chosenValue == 0) {
                return null;
            }
            if (customers.size() == 0) {
                ConsoleHelper.print("There are no customers, please enter 0 to go back to main menu");
            }
        }
    }

}
