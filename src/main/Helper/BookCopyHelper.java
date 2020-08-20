package main.Helper;

import main.ConsoleHelper;
import main.DatabaseInfo;
import main.Elements.BookCopy;
import main.Elements.Customer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BookCopyHelper {

    public static void searchBook(final BufferedReader bfr) {

        ConsoleHelper.print("Please write a word or phrase which is in the title of the book copy your looking for.\n" +
                "You can also enter the whole title.\n" +
                "Any book copy matching the condition will be given out.\n" +
                "NOTE THE SEARCH IS NOT KEY SENSITIVE. YOU CAN REQUEST A KEY SENSITIVE MODE.");
        String searchString;
        final String returnOption = "--return--";
        while ((searchString = ConsoleHelper.readString(bfr)) == null || searchString.isEmpty()) {
            ConsoleHelper.print(String.format("Please enter a valid input or %s to return", returnOption));
        }

        if (returnOption.equals(searchString)) {
            ConsoleHelper.print("Returning to menu");
            return;
        }

        final TreeMap<String, BookCopy> sortedBookCopies = new TreeMap<>();
        String bookTitle;
        for (final BookCopy bookCopy : DatabaseInfo.getBookCopies().keySet()) {
            bookTitle = bookCopy.getBook().getTitle();
            if (bookTitle.toLowerCase().contains(searchString.toLowerCase())) {
                sortedBookCopies.put(bookTitle, bookCopy);
            }
        }

        if (sortedBookCopies.isEmpty()) {
            ConsoleHelper.print(String.format("There are no book copies containing %s in the title.", searchString));
            return;
        }
        final StringBuilder bookCopyOutput = new StringBuilder();
        bookCopyOutput.append(String.format("The following book copies do contain %s in the title:\n", searchString));
        for (final Map.Entry<String, BookCopy> sortedEntry : sortedBookCopies.entrySet()) {
            bookCopyOutput.append(sortedEntry.getValue().getFormattedData());
        }
        ConsoleHelper.print(bookCopyOutput.toString());
    }

    public static void returnBookCopy() {
        final Customer chosenCustomer = CustomerHelper.getSpecCustomer();
        if (chosenCustomer == null) {
            ConsoleHelper.print("Going back to main menu");
            return;
        }

        final List<BookCopy> bookCopies = chosenCustomer.getBookList();

        if (bookCopies.isEmpty()) {
            ConsoleHelper.print("This user has not lent any books. Returning to main menu");
            return;
        }

        ConsoleHelper.print("Please choose the book copy you want to return.\nOtherwise 0 to exit");

        final HashMap<Integer, BookCopy> correspondingBookCopy = new HashMap<>();
        int chooseId = 1;
        for (final BookCopy bookCopy : bookCopies) {
            ConsoleHelper.print(String.format("(%s) " + bookCopy.getFormattedData(), chooseId));
            correspondingBookCopy.put(chooseId, bookCopy);
            chooseId++;
        }
        while (true) {
            int chosenValue = ConsoleHelper.readInt(new BufferedReader(new InputStreamReader(System.in)));
            if (chosenValue > 0 && chosenValue <= bookCopies.size()) {
                if (chosenCustomer.getBookList().remove(correspondingBookCopy.get(chosenValue))) {
                    chosenCustomer.bookCopyReturned();
                    ConsoleHelper.print(String.format("Returned book copy:\n%s", correspondingBookCopy.get(chosenValue).getFormattedData()));
                } else {
                    ConsoleHelper.print("There was an error with returning the book. Returning to main menu.");
                }
                return;
            }
            if (chosenValue == 0) {
                return;
            }
            if (bookCopies.size() == 0) {
                ConsoleHelper.print("There are no book copies, please enter 0 to go back to main menu");
            }
        }
    }

    public static void borrowBookCopy() {

        final Customer chosenCustomer = CustomerHelper.getSpecCustomer();
        if (chosenCustomer == null) {
            ConsoleHelper.print("Going back to main menu");
            return;
        }

        final List<BookCopy> lentBookCopies = chosenCustomer.getBookList();

        if (lentBookCopies.size() >= 5) {
            ConsoleHelper.print("This user has lent to many books. Returning to main menu");
            return;
        }
        final HashMap<BookCopy, Integer> bookCopies = DatabaseInfo.getBookCopies();
        final HashMap<Integer, BookCopy> correspondingBookCopy = new HashMap<>();
        final StringBuilder lendAbelBookCopies = new StringBuilder();
        int chooseId = 1;
        for (final BookCopy bookCopy : bookCopies.keySet()) {
            int diff = bookCopies.get(bookCopy) - DatabaseInfo.getNumberOfBookCopiesLent(bookCopy);
            if (diff > 0 && !lentBookCopies.contains(bookCopy)) {
                lendAbelBookCopies.append(String.format("(%s) %sCopies left: %s\n\n", chooseId, bookCopy.getFormattedData(), diff));
                correspondingBookCopy.put(chooseId, bookCopy);
                chooseId++;
            }
        }
        if (chooseId == 1) {
            ConsoleHelper.print("There are no books which can be lent.\nReturning to main menu");
            return;
        }
        ConsoleHelper.print(lendAbelBookCopies.toString());

        ConsoleHelper.print("Please type the number fo the corresponding book you want to add.");

        while (true) {
            int chosenValue = ConsoleHelper.readInt(new BufferedReader(new InputStreamReader(System.in)));
            if (chosenValue > 0 && chosenValue <= bookCopies.size()) {
                final BookCopy bookCopy = correspondingBookCopy.get(chosenValue);
                DatabaseInfo.addBookCopyToCustomer(chosenCustomer, bookCopy);
                return;
            }
            if (chosenValue == 0) {
                return;
            }
            if (bookCopies.size() == 0) {
                ConsoleHelper.print("There are no book copies that can be lend, please enter 0 to go back to main menu");
            }

        }
    }

}
