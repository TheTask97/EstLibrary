package MainTest;

import main.DatabaseInfo;
import main.Elements.Book;
import main.Elements.Customer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Year;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DatabaseInfoTest {

    @Test
    public void getBooksTest() {
        DatabaseInfo.addBook(new Book(11111111, "Harry Potter and the Philosopher's Stone", "J.K Rowling", Year.of(1997), "Bloomsbury", "London", 1));
        assertEquals(1, DatabaseInfo.getBooks().size());
    }

    @Test
    public void getCustomersTest() {
        DatabaseInfo.addCustomer(new Customer(6, "Uzi Vert", "Lil", 1, new ArrayList<>(), true));
        assertEquals(1, DatabaseInfo.getCustomers().size());
    }

    @Test
    public void addBooksTest() {
        assertFalse(DatabaseInfo.addBooks(new File("failTest.pdf"), ","));
        assertFalse(DatabaseInfo.addBooks(new File("validTest.csv"), "l"));
    }

    @Test
    public void addBookTest(){

    }

    @AfterAll
    public static void clearData() {
        DatabaseInfo.clearData();
    }
}
