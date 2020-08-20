package ElementTest;

import main.DatabaseInfo;
import main.Elements.Book;
import main.Elements.BookCopy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookCopyTest {

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

    public static void initialize() {

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
    }

    @BeforeAll
    public static void init() {
        initialize();
        DatabaseInfo.addBook(harryPotter);
        DatabaseInfo.addBook(diary);
        DatabaseInfo.addBook(robinsonCrusoe);
        DatabaseInfo.addBook(donQuixote);
        DatabaseInfo.addBook(aliceInWonderland);

        final List<BookCopy> bookCopies = Arrays.asList(harryPotterC, lordOfTheRingsC, diaryC, robinsonCrusoeC, donQuixoteC, aliceInWonderlandC,
                harryPotterC2, lordOfTheRingsC2, diaryC2, robinsonCrusoeC2, donQuixoteC2, aliceInWonderlandC2);

        for (final BookCopy bookCopy : bookCopies) {
            DatabaseInfo.addBookCopy(bookCopy);
        }
    }

    @Test
    public void deleteExistingBookCopy() { //test to determine if it possible to delete the main.Elements.BookCopy


        assertTrue(DatabaseInfo.checkBookCopyIsPresent(donQuixoteC));
        final int numberOfCopies = DatabaseInfo.getBookCopies().get(donQuixoteC);
        assertTrue(DatabaseInfo.deleteBookCopy(donQuixoteC));
        final int newNumberOfCopies = DatabaseInfo.getBookCopies().get(donQuixoteC);
        assertTrue(numberOfCopies > newNumberOfCopies);
    }

    @Test
    public void deleteNonExistingBook() {
        assertFalse(DatabaseInfo.deleteBookCopy(new BookCopy(66666669, aliceInWonderland, "Fantasy", "04.04.2004", false, "")));
    }

    @AfterAll
    public static void clearData() {
        DatabaseInfo.clearData();
    }

}
