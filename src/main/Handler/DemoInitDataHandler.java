package main.Handler;

import main.DatabaseInfo;
import main.Elements.Book;
import main.Elements.BookCopy;
import main.Elements.Customer;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoInitDataHandler {

    static Book harryPotter;
    static Book lordOfTheRings;
    static Book diary;
    static Book robinsonCrusoe;
    static Book donQuixote;
    static Book aliceInWonderland;

    static BookCopy harryPotterC;
    static BookCopy lordOfTheRingsC;
    static BookCopy diaryC;
    static BookCopy robinsonCrusoeC;
    static BookCopy donQuixoteC;
    static BookCopy aliceInWonderlandC;
    static BookCopy harryPotterC2;
    static BookCopy lordOfTheRingsC2;
    static BookCopy diaryC2;
    static BookCopy robinsonCrusoeC2;
    static BookCopy donQuixoteC2;
    static BookCopy aliceInWonderlandC2;
    static BookCopy harryPotterC3;
    static BookCopy aliceInWonderlandC3;

    static ArrayList<BookCopy> bookList;
    static ArrayList<BookCopy> bookList2;
    static ArrayList<BookCopy> bookList3;
    static ArrayList<BookCopy> bookList4;
    static ArrayList<BookCopy> bookList5;
    static ArrayList<BookCopy> bookList6;

    static Customer andy;
    static Customer peter;
    static Customer jeffrey;
    static Customer woods;
    static Customer pluto;
    static Customer kodak;

    public static void initDemoData() {
        // can be turned on but we prefer to use the csv
        harryPotter = new Book(11111111, "Harry Potter and the Philosopher's Stone", "J.K Rowling", Year.of(1997), "London","Bloomsbury",  1);//Initialization of Books
        lordOfTheRings = new Book(22222222, "Lord of the Rings", "J. R. R. Tolkien", Year.of(1954), "London","Allen & Unwin", 3);
        diary = new Book(33333333, "Diary of a wimpy kid", "Jeff Kinney", Year.of(2007),  "Washington","Amulet Books/Puffin Books", 1);
        robinsonCrusoe = new Book(44444444, "Robinson Crusoe", "Daniel Defoe", Year.of(1719),"London", "William Taylor",  2);
        donQuixote = new Book(55555555, "Don Quixote", "Miguel de Cervantes", Year.of(1620),"Madrid", "Francisco de Robles",  4);
        aliceInWonderland = new Book(66666666, "Alice's Adventures in Wonderland ", "Lewis Carroll", Year.of(1865), "London","Macmillan",  3);

        harryPotterC = new BookCopy(11111112, harryPotter, "Fantasy", "07.07.2007", false, "none");//Initialization of Bookcopies
        lordOfTheRingsC = new BookCopy(22222223, lordOfTheRings, "Fantasy", "05.05.2005", false, "none");
        diaryC = new BookCopy(33333334, diary, "Slice of Life", "03.03.2003", false, "none");
        robinsonCrusoeC = new BookCopy(44444445, robinsonCrusoe, "Adventure", "01.01.2001", false, "none");
        donQuixoteC = new BookCopy(55555556, donQuixote, "Adventure", "02.02.2002", false, "");
        aliceInWonderlandC = new BookCopy(66666667, aliceInWonderland, "Fantasy", "04.04.2004", false, "none");

        bookList = new ArrayList<>();

        bookList2 = new ArrayList<>();
        bookList3 = new ArrayList<>();
        bookList4 = new ArrayList<>();
        bookList5 = new ArrayList<>();

        bookList6 = new ArrayList<>();
        andy = new Customer(1, "Andy", "Ahmed", bookList.size(), bookList, false);//Initialization of Customer
        peter = new Customer(2, "Maffay", "Peter", bookList2.size(), bookList2, false);
        jeffrey = new Customer(3, "Lamar", "Jeffrey", bookList3.size(), bookList3, false);
        kodak = new Customer(4, "Kapri", "Kill K.", bookList4.size(), bookList4, false);
        pluto = new Customer(5, "Willburn", "Navyadius", bookList5.size(), bookList5, false);
        woods = new Customer(6, "Uzi Vert", "Lil", bookList5.size(), bookList6, true);

        final List<Customer> customers = Arrays.asList(andy, peter, jeffrey, kodak, pluto, woods);
        final List<Book> books = Arrays.asList(harryPotter, lordOfTheRings, diary, robinsonCrusoe, donQuixote, aliceInWonderland);
        final List<BookCopy> bookCopies = Arrays.asList(harryPotterC, lordOfTheRingsC, diaryC, robinsonCrusoeC, donQuixoteC, aliceInWonderlandC);

        for (final Customer customer : customers) {
            DatabaseInfo.addCustomer(customer);
        }

        for (final Book book : books) {
            DatabaseInfo.addBook(book);
        }
        for (int i = 0; i < 4; i++) {
            for (final BookCopy bookCopy : bookCopies) {
                DatabaseInfo.addBookCopy(bookCopy);
            }
        }

        DatabaseInfo.addBookCopyToCustomer(andy, harryPotterC, "12.03.2012");
        DatabaseInfo.addBookCopyToCustomer(peter, harryPotterC, "12.03.2014");
    }
}
