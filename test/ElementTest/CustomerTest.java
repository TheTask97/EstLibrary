package ElementTest;

import main.DatabaseInfo;
import main.Elements.Book;
import main.Elements.BookCopy;
import main.Elements.Customer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {
    static File validFile;
    static File invalidFile;

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

    static List<BookCopy> bookList;
    static List<BookCopy> bookList2;
    static List<BookCopy> bookList3;
    static List<BookCopy> bookList4;
    static List<BookCopy> bookList5;
    static List<BookCopy> bookList6;

    static Customer andy;
    static Customer peter;
    static Customer jeffrey;
    static Customer woods;
    static Customer pluto;
    static Customer kodak;

    @BeforeAll
    public static void initialize() {

        validFile = new File("leaks.csv");////Initialization of Files
        invalidFile = new File("leaks.pdf");

        harryPotter = new Book(11111111, "Harry Potter and the Philosopher's Stone", "J.K Rowling", Year.of(1997), "Bloomsbury", "London", 1);//Initialization of Books
        lordOfTheRings = new Book(22222222, "Lord of the Rings", "J. R. R. Tolkien", Year.of(1954), "Allen & Unwin", "London", 3);
        diary = new Book(33333333, "Diary of a wimpy kid", "Jeff Kinney", Year.of(2007), "Amulet Books / Puffin Books", "Washington", 1);
        robinsonCrusoe = new Book(44444444, "Robinson Crusoe", "Daniel Defoe", Year.of(1719), "William Taylor", "London", 2);
        donQuixote = new Book(55555555, "Don Quixote", "Miguel de Cervantes", Year.of(1620), "Francisco de Robles", "Madrid", 4);
        aliceInWonderland = new Book(66666666, "Alice's Adventures in Wonderland ", "Lewis Carroll", Year.of(1865), "Macmillan", "London", 3);

        harryPotterC = new BookCopy(11111112, harryPotter, "Fantasy", "07.07.2007", true, "17.05.2020");//Initialization of Bookcopies
        lordOfTheRingsC = new BookCopy(22222223, lordOfTheRings, "Fantasy", "05.05.2005", true, "16.05.2020");
        diaryC = new BookCopy(33333334, diary, "Slice of Life", "03.03.2003", true, "15.05.2020");
        robinsonCrusoeC = new BookCopy(44444445, robinsonCrusoe, "Adventure", "01.01.2001", true, "14.05.2020");
        donQuixoteC = new BookCopy(55555556, donQuixote, "Adventure", "02.02.2002", false, "");
        aliceInWonderlandC = new BookCopy(66666667, aliceInWonderland, "Fantasy", "04.04.2004", true, "13.05.2020");

        harryPotterC2 = new BookCopy(11111113, harryPotter, "Fantasy", "07.07.2007", true, "12.05.2020");
        lordOfTheRingsC2 = new BookCopy(22222224, lordOfTheRings, "Fantasy", "05.05.2005", true, "11.05.2020");
        diaryC2 = new BookCopy(33333335, diary, "Slice of Life", "03.03.2003", true, "10.05.2020");
        robinsonCrusoeC2 = new BookCopy(44444446, robinsonCrusoe, "Adventure", "01.01.2001", true, "09.11.2019");
        donQuixoteC2 = new BookCopy(55555557, donQuixote, "Adventure", "02.02.2002", true, "08.05.2020");
        aliceInWonderlandC2 = new BookCopy(66666668, aliceInWonderland, "Fantasy", "04.04.2004", true, "20.01.2020");

        harryPotterC3 = new BookCopy(11111114, harryPotter, "Fantasy", "07.07.2007", false, "");
        aliceInWonderlandC3 = new BookCopy(66666669, aliceInWonderland, "Fantasy", "04.04.2004", false, "");

        bookList = new ArrayList<>();
        bookList.add(harryPotterC);
        bookList.add(lordOfTheRingsC);
        bookList.add(diaryC);
        bookList.add(robinsonCrusoeC);
        bookList.add(aliceInWonderlandC);

        bookList2 = new ArrayList<>();
        bookList.add(harryPotterC2);
        bookList.add(lordOfTheRingsC2);
        bookList.add(diaryC2);

        bookList3 = new ArrayList<>();
        bookList4 = new ArrayList<>();
        bookList5 = new ArrayList<>();
        bookList.add(donQuixoteC2);

        bookList6 = new ArrayList<>();
        bookList.add(robinsonCrusoeC2);

        andy = new Customer(1, "Andy", "Ahmed", 5, bookList, false);//Initialization of main.Elements.Customer
        peter = new Customer(2, "Maffay", "Peter", 3, bookList2, false);
        jeffrey = new Customer(3, "Lamar", "Jeffrey", 0, bookList3, false);
        kodak = new Customer(4, "Kapri", "Kill K.", 0, bookList4, false);
        pluto = new Customer(5, "Willburn", "Navyadius", 1, bookList5, false);
        woods = new Customer(6, "Uzi Vert", "Lil", 1, bookList6, true);

        final List<Customer> customers = Arrays.asList(andy, peter, jeffrey, kodak, pluto, woods);

        for (final Customer customer : customers) {
            DatabaseInfo.addCustomer(customer);
        }
    }

    @Test
    public void deleteExistingCustomerTest() {//test to determine if it is possible to delete the customer

        assertTrue(DatabaseInfo.checkCustomerPresent(kodak));
        assertTrue(DatabaseInfo.deleteCostumer(kodak));
        assertFalse(DatabaseInfo.checkCustomerPresent(kodak));

    }

    @Test
    public void deleteNotExistingCustomerTest() {//test to determine if it is possible to delete the customer

        assertFalse(DatabaseInfo.deleteCostumer(new Customer(6, "Uzi Vert", "Lil", 1, bookList6, true)));

    }

    @AfterAll
    public static void clearData() {
        DatabaseInfo.clearData();
    }
}
