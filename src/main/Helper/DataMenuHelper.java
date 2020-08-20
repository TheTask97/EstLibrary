package main.Helper;

import main.ConsoleHelper;
import main.DatabaseInfo;
import main.Elements.Book;
import main.Elements.BookCopy;
import main.Elements.Customer;
import main.MenuPoint;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class DataMenuHelper {
    /**
     * Class for the reporting menu functionality
     *
     */

    /**
     * @param bfr the bufferedReader for console interaction
     */
    public static void dataMenu(final BufferedReader bfr) {
        final HashMap<Integer, MenuPoint> menuMap = new HashMap<>();
        final StringBuilder dataMenu = new StringBuilder();
        Pair<File, String> csvToImport;
        boolean failed = true;
        MenuHelper.addMenuOptions(dataMenu, Arrays.asList(MenuPoint.IMPORT_BOOK, MenuPoint.DELETE_BOOK, MenuPoint.IMPORT_BOOK_COPY, MenuPoint.DELETE_BOOK_COPY, MenuPoint.IMPORT_CUSTOMER, MenuPoint.DELETE_CUSTOMER, MenuPoint.REPORTS), menuMap, false);
        ConsoleHelper.print(dataMenu.toString());

        do {
            switch (ConsoleHelper.readInt(bfr)) {
                case 1:
                    if ((csvToImport = getFileAndSep(bfr)) == null) {
                        ConsoleHelper.print("The given file does not exist returning to main menu.");
                    } else {
                        DatabaseInfo.addBooks(csvToImport.getLeft(), csvToImport.getRight());
                        ConsoleHelper.print("Successfully imported");
                    }
                    failed = false;
                    break;
                case 3:
                    if ((csvToImport = getFileAndSep(bfr)) == null) {
                        ConsoleHelper.print("The given file does not exist returning to main menu.");
                    } else {
                        DatabaseInfo.addBookCopies(csvToImport.getLeft(), csvToImport.getRight());
                        ConsoleHelper.print("Successfully imported");
                    }
                    failed = false;
                    break;
                case 5:
                    if ((csvToImport = getFileAndSep(bfr)) == null) {
                        ConsoleHelper.print("The given file does not exist returning to main menu.");
                    } else {
                        DatabaseInfo.addCustomers(csvToImport.getLeft(), csvToImport.getRight());
                        ConsoleHelper.print("Successfully imported");
                    }
                    failed = false;
                    break;
                case 2:
                    ConsoleHelper.print("Please type in the number of the corresponding book you want to delete or 0 to go back.");
                    while (true) {
                        final Book chosenBook = chooseBook();
                        if (chosenBook == null) {
                            ConsoleHelper.print("Going back to main menu");
                            break;
                        }
                        if (DatabaseInfo.deleteBook(chosenBook)) {
                            ConsoleHelper.print("main.Elements.Book deleted");
                            break;
                        } else {
                            ConsoleHelper.print("The book is lent and cant be deleted. Please choose another book. \n" +
                                    "Press any key to continue. The list of the books will show up again");
                            ConsoleHelper.readString(bfr);
                        }
                    }
                    failed = false;
                    break;
                case 4:
                    ConsoleHelper.print("Please type in the number of the corresponding book copy you want to delete or 0 to go back.");
                    while (true) {
                        final BookCopy chosenBookCopy = chooseBookCopy();
                        if (chosenBookCopy == null) {
                            ConsoleHelper.print("Going back to main menu");
                            break;
                        }
                        if (DatabaseInfo.deleteBookCopy(chosenBookCopy)) {
                            ConsoleHelper.print("One book copy deleted");
                            break;
                        } else {
                            ConsoleHelper.print("The book copy is lent and cant be deleted. Please choose another book copy. \n" +
                                    "Press any key to continue. The list of the book copies will show up again");
                            ConsoleHelper.readString(bfr);
                        }
                    }
                    failed = false;
                    break;
                case 6:
                    while (true) {
                        final Customer chosenCustomer = chooseCustomer();
                        if (chosenCustomer == null) {
                            ConsoleHelper.print("Going back to main menu");
                            break;
                        }
                        if (DatabaseInfo.deleteCostumer(chosenCustomer)) {
                            ConsoleHelper.print("The customer was deleted");
                            break;
                        } else {
                            ConsoleHelper.print("The customer cant be deleted, because he still has books lent. Please choose another customer. \n" +
                                    "Press any key to continue. The list of the customers will show up again");
                            ConsoleHelper.readString(bfr);
                        }
                    }
                    failed = false;
                    break;


                case 7:
                    ReportHelper.reportMenu(bfr);
                    failed = false;
                    break;
                default:
                    ConsoleHelper.print("There is no option for that number. Please enter a valid number");
                    break;
            }
        } while (failed);
    }

    private static Pair<File, String> getFileAndSep(final BufferedReader bfr) {
        ConsoleHelper.print("Please enter the path of the file you want to import the data from.");
        String path;
        final File file;
        while ((path = ConsoleHelper.readString(bfr)) == null) {
            ConsoleHelper.print("Please enter something to continue");
        }

        file = new File(path);

        if (!file.exists()) {
            return null;
        }

        ConsoleHelper.print("Please enter the separator character");
        final String separator = ConsoleHelper.readString(bfr);

        return Pair.of(file, separator);
    }

    private static Book chooseBook() {

        final HashMap<Book, Integer> books = DatabaseInfo.getBooks();
        final HashMap<Integer, Book> correspondingBook = new HashMap<>();
        int chooseId = 1;
        for (final Book book : books.keySet()) {
            ConsoleHelper.print(String.format("(%s) " + book.getFormattedData(), chooseId));
            correspondingBook.put(chooseId, book);
            chooseId++;
        }
        while (true) {
            int chosenValue = ConsoleHelper.readInt(new BufferedReader(new InputStreamReader(System.in)));
            if (chosenValue > 0 && chosenValue <= books.size()) {
                return correspondingBook.get(chosenValue);
            }
            if (chosenValue == 0) {
                return null;
            }

            if (books.size() == 0) {
                ConsoleHelper.print("There are no books, please enter 0 to go back to main menu");
            }
        }


    }

    private static Customer chooseCustomer() {
        return CustomerHelper.getSpecCustomer();
    }

    private static BookCopy chooseBookCopy() {

        final HashMap<BookCopy, Integer> bookCopies = DatabaseInfo.getBookCopies();
        final HashMap<Integer, BookCopy> correspondingBookCopy = new HashMap<>();
        int chooseId = 1;
        for (final BookCopy bookCopy : bookCopies.keySet()) {
            ConsoleHelper.print(String.format("(%s) " + bookCopy.getFormattedData(), chooseId));
            correspondingBookCopy.put(chooseId, bookCopy);
            chooseId++;
        }
        while (true) {
            int chosenValue = ConsoleHelper.readInt(new BufferedReader(new InputStreamReader(System.in)));
            if (chosenValue > 0 && chosenValue <= bookCopies.size()) {
                return correspondingBookCopy.get(chosenValue);
            }
            if (chosenValue == 0) {
                return null;
            }
            if (bookCopies.size() == 0) {
                ConsoleHelper.print("There are no book copies, please enter 0 to go back to main menu");
            }

        }
    }

}