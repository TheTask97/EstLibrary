package ElementTest;

import main.DatabaseInfo;
import main.Elements.Book;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookTest {
    static File validFile;
    static File invalidFile;

    static Book harryPotter;
    static Book lordOfTheRings;
    static Book diary;
    static Book robinsonCrusoe;
    static Book donQuixote;
    static Book aliceInWonderland;


    public static void initialize() {

        validFile = new File("leaks.csv");////Initialization of Files
        invalidFile = new File("leaks.pdf");

        harryPotter = new Book(11111111, "Harry Potter and the Philosopher's Stone", "J.K Rowling", Year.of(1997), "Bloomsbury", "London", 1);//Initialization of Books
        lordOfTheRings = new Book(22222222, "Lord of the Rings", "J. R. R. Tolkien", Year.of(1954), "Allen & Unwin", "London", 3);
        diary = new Book(33333333, "Diary of a wimpy kid", "Jeff Kinney", Year.of(2007), "Amulet Books / Puffin Books", "Washington", 1);
        robinsonCrusoe = new Book(44444444, "Robinson Crusoe", "Daniel Defoe", Year.of(1719), "William Taylor", "London", 2);
        donQuixote = new Book(55555555, "Don Quixote", "Miguel de Cervantes", Year.of(1620), "Francisco de Robles", "Madrid", 4);
        aliceInWonderland = new Book(66666666, "Alice's Adventures in Wonderland ", "Lewis Carroll", Year.of(1865), "Macmillan", "London", 3);


    }

    @BeforeAll
    public static void init() {
        initialize();
        DatabaseInfo.addBook(harryPotter);
        DatabaseInfo.addBook(diary);
        DatabaseInfo.addBook(robinsonCrusoe);
        DatabaseInfo.addBook(donQuixote);
        DatabaseInfo.addBook(aliceInWonderland);
    }

    @Test
    public void deleteExistingBook() { //test to determine if it possible to delete the main.Elements.BookCopy

        assertTrue(DatabaseInfo.checkBookIsPresent(harryPotter));
        assertTrue(DatabaseInfo.deleteBook(harryPotter));
        assertFalse(DatabaseInfo.checkBookIsPresent(harryPotter));

    }

    @Test
    public void deleteNonExistingBook() {
        assertFalse(DatabaseInfo.deleteBook(lordOfTheRings));
    }

    @AfterAll
    public static void clearData() {
        DatabaseInfo.clearData();
    }
}
