package main;

import main.Helper.BookCopyHelper;
import main.Helper.DataMenuHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public enum MenuPoint {
    /**
     * Each enum represents a menu option which can be displayed
     */

    LOGIN("Login"),
    SEARCH_BOOK_COPY("Search for a book copy"),
    BORROW_BOOK_COPY("Borrow a book copy"),
    RETURN_BOOK("Return a book copy"),
    OPTIONS("Options"),
    DATA("Data menu"),
    REPORTS("Reports"),
    REPORT_ALL_BOOKS("Report all books"),
    REPORT_BORROWED_BOOK_COPIES("Report all borrowed book copies"),
    REPORT_NOT_BORROWED_BOOK_COPIES("Report all not borrowed book copies"),
    REPORT_ALL_COSTUMERS("Report all costumers"),
    REPORT_ALL_BORROWED_BOOKS_COSTUMER("Report all books borrowed from one customer"),
    REPORT_ALL_BOOKCOPIES_OF_THE_PUBLISHING_COMPANY("Report all the bookcopies of the publishing companies"),//moi
    IMPORT_BOOK("Import book"),
    DELETE_BOOK("Delete book"),
    IMPORT_CUSTOMER("Import customer"),
    DELETE_CUSTOMER("Delete customer"),
    IMPORT_BOOK_COPY("Import book copies"),
    DELETE_BOOK_COPY("Delete book copies");

    private final String name;

    MenuPoint(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // dummy method for a simply demo purpose
    public void demoMenu() {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String username, pw;
        String admin = "admin";
        switch (this) {
            case LOGIN:
                ConsoleHelper.print("Please Enter your username");
                username = ConsoleHelper.readString(bfr);
                ConsoleHelper.print("Please enter your password.");
                pw = ConsoleHelper.readString(bfr);

                if (admin.equals(username) && admin.equals(pw)) {
                    ConsoleHelper.print("Logged in. Returning to menu");
                } else {
                    ConsoleHelper.print("Login failed. Returning to main menu");
                }
                break;
            case DATA:
                DataMenuHelper.dataMenu(bfr);
                break;
            case SEARCH_BOOK_COPY:
                BookCopyHelper.searchBook(bfr);
                break;
            case BORROW_BOOK_COPY:
                BookCopyHelper.borrowBookCopy();
                break;
            case RETURN_BOOK:
                BookCopyHelper.returnBookCopy();
                break;
            default:
                printOptionNotEnabled();
        }
    }

    private void printOptionNotEnabled() {
        ConsoleHelper.print("This Option is currently not enabled. Please wait for a patch.");
    }
}