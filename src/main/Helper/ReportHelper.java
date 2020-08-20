package main.Helper;

import main.ConsoleHelper;
import main.DatabaseInfo;
import main.Elements.Book;
import main.Elements.BookCopy;
import main.Elements.Customer;
import main.MenuPoint;

import java.io.BufferedReader;
import java.util.*;

public class ReportHelper {
    /**
     * Class for the reporting menu functionality
     *
     */

    /**
     * @param bfr the bufferedReader for console interaction
     */
    public static void reportMenu(final BufferedReader bfr) {
        final HashMap<Integer, MenuPoint> menuMap = new HashMap<>();
        final StringBuilder reportMenu = new StringBuilder();
        boolean failed = true;

        MenuHelper.addMenuOptions(reportMenu, Arrays.asList(MenuPoint.REPORT_ALL_BOOKS, MenuPoint.REPORT_BORROWED_BOOK_COPIES,
                MenuPoint.REPORT_NOT_BORROWED_BOOK_COPIES, MenuPoint.REPORT_ALL_COSTUMERS, MenuPoint.REPORT_ALL_BORROWED_BOOKS_COSTUMER,
                MenuPoint.REPORT_ALL_BOOKCOPIES_OF_THE_PUBLISHING_COMPANY), menuMap, false);
        ConsoleHelper.print(reportMenu.toString());

        do {
            switch (ConsoleHelper.readInt(bfr)) {
                case 1:
                    printAllBooks();
                    failed = false;
                    break;
                case 2:
                    printAllBorrowedBookCopies();
                    failed = false;
                    break;
                case 3:
                    printAllNotBorrowedBooks();
                    failed = false;
                    break;
                case 4:
                    printAllCustomers();
                    failed = false;
                    break;
                case 5:
                    printAllBorrowedBookCopiesFromCustomer();
                    failed = false;
                    break;
                case 6:
                    printAllBookCopiesFromThePublishingCompanies();
                    failed = false;
                    break;
                default:
                    ConsoleHelper.print("There is no option for that number. Please enter a valid number");
                    break;
            }
        } while (failed);

    }

    private static void printAllBookCopiesFromThePublishingCompanies() {

        final TreeMap<String, Integer> numberOfCopies = new TreeMap<>();
        for (final BookCopy bookCopy : DatabaseInfo.getBookCopies().keySet()) {
            String publisher = bookCopy.getBook().getPublisher();
            if (numberOfCopies.containsKey(bookCopy.getBook().getPublisher())) {
                numberOfCopies.put(publisher, numberOfCopies.get(publisher) + 1);
            } else {
                numberOfCopies.put(publisher, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : numberOfCopies.entrySet()) {
            ConsoleHelper.print(entry.getKey()+ ": " + entry.getValue()  + " Bookcopy/ies " +"(" + (((double) entry.getValue()) / ((double) numberOfCopies.size())) * 100 + ")" +"%");
        }
        ConsoleHelper.print("\n");
    }


    // dummy method for demo purpose
    private static void printOptionNotEnabled() {
        ConsoleHelper.print("This Option is currently not enabled. Please wait for a patch.");
    }

    private static void printAllBooks() {

        final TreeMap<String, Book> sortedBooks = new TreeMap<>();

        for (final Book book : DatabaseInfo.getBooks().keySet()) {
            sortedBooks.put(book.getTitle(), book);
        }

        for (Map.Entry<String, Book> entry : sortedBooks.entrySet()) {
            ConsoleHelper.print(entry.getValue().getFormattedData());
        }

    }

    private static void printAllCustomers() {

        final TreeMap<String, Customer> sortedCustomers = new TreeMap<>();

        for (final Customer customer : DatabaseInfo.getCustomers().keySet()) {
            sortedCustomers.put(customer.getName() + " " + customer.getFirstName(), customer);
        }

        for (Map.Entry<String, Customer> entry : sortedCustomers.entrySet()) {
            ConsoleHelper.print(entry.getValue().getFormattedData());
        }
    }

    private static void printAllBorrowedBookCopies() {
        final TreeMap<String, BookCopy> sortedBookCopies = new TreeMap<>();
        final HashMap<BookCopy, String> mappedNames = new HashMap<>();
        for (final Customer customer : DatabaseInfo.getCustomers().keySet()) {
            int count = 1;
            for (final BookCopy lentBook : customer.getBookList()) {
                if (!sortedBookCopies.containsKey(lentBook.getBook().getTitle())) {
                    sortedBookCopies.put(lentBook.getBook().getTitle(), lentBook);
                } else {
                    sortedBookCopies.put(lentBook.getBook().getTitle() + count, lentBook);
                    count++;
                }
                mappedNames.put(lentBook, customer.getName() + " " + customer.getFirstName());
            }
        }
        for (Map.Entry<String, BookCopy> entry : sortedBookCopies.entrySet()) {
            ConsoleHelper.print(entry.getValue().getFormattedData() + String.format("\nLent from: %s\n", mappedNames.get(entry.getValue())));
        }
    }

    private static void printAllNotBorrowedBooks() {
        final TreeMap<String, BookCopy> sortedBookCopies = new TreeMap<>();
        final HashMap<BookCopy, Integer> numberOfCopies = new HashMap<>();
        for (final Customer customer : DatabaseInfo.getCustomers().keySet()) {
            for (final BookCopy lentBook : customer.getBookList()) {
                final BookCopy originalBookCopy = DatabaseInfo.getOriginalBookCopy(lentBook.getBook().getId());
                if (!sortedBookCopies.containsValue(originalBookCopy)) {
                    sortedBookCopies.put(lentBook.getBook().getTitle(), originalBookCopy);
                    numberOfCopies.put(originalBookCopy, 1);
                } else {
                    numberOfCopies.put(originalBookCopy, numberOfCopies.get(originalBookCopy));
                }
            }
        }

        final HashMap<BookCopy, Integer> bookCopies = DatabaseInfo.getBookCopies();
        final TreeMap<String, BookCopy> sortedNotLentCopies = new TreeMap<>();
        final HashMap<BookCopy, Integer> numberOfNotLentCopies = new HashMap<>();
        int numberBookCopiesLeft;
        for (final BookCopy bookCopy : bookCopies.keySet()) {
            if (sortedBookCopies.containsValue(bookCopy)) {
                numberBookCopiesLeft = bookCopies.get(bookCopy) - DatabaseInfo.getNumberOfBookCopiesLent(bookCopy);
                if (numberBookCopiesLeft > 0) {
                    sortedNotLentCopies.put(bookCopy.getBook().getTitle(), bookCopy);
                    numberOfNotLentCopies.put(bookCopy, numberBookCopiesLeft);
                }
            } else {
                sortedNotLentCopies.put(bookCopy.getBook().getTitle(), bookCopy);
                numberOfNotLentCopies.put(bookCopy, bookCopies.get(bookCopy));
            }
        }

        for (Map.Entry<String, BookCopy> entry : sortedNotLentCopies.entrySet()) {
            ConsoleHelper.print(entry.getValue().getFormattedData() + String.format("\nQuantity left: %s\n", numberOfNotLentCopies.get(entry.getValue())));
        }

    }

    private static void printAllBorrowedBookCopiesFromCustomer() {
        final Customer customer = CustomerHelper.getSpecCustomer();
        if (customer == null) {
            ConsoleHelper.print("Going back to main menu");
            return;
        }

        final TreeMap<String, BookCopy> sortedBooks = new TreeMap<>();

        for (final BookCopy bookCopy : customer.getBookList()) {
            sortedBooks.put(bookCopy.getBook().getTitle(), bookCopy);
        }
        final String userString = "The user: " + customer.getName() + " " + customer.getFirstName();
        if (!sortedBooks.isEmpty()) {
            ConsoleHelper.print(userString + " borrowed the following book copies:\n");
            for (Map.Entry<String, BookCopy> entry : sortedBooks.entrySet()) {
                ConsoleHelper.print(entry.getValue().getFormattedData());
            }
            return;
        }
        ConsoleHelper.print(userString + " hasn't borrowed any books yet.");


    }

}
