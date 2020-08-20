package main;

import main.Elements.Book;
import main.Elements.BookCopy;
import main.Elements.Customer;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.regex.Pattern;

public class DatabaseInfo {

    private static final HashMap<Book, Integer> persistentBooks = new HashMap<>();
    private static final HashMap<Customer, Integer> customers = new HashMap<>();
    private static final HashMap<BookCopy, Integer> persistentBookCopies = new HashMap<>();

    public static HashMap<Book, Integer> getBooks() {
        return persistentBooks;
    }

    public static HashMap<Customer, Integer> getCustomers() {
        return customers;
    }

    public static HashMap<BookCopy, Integer> getBookCopies() {
        return persistentBookCopies;
    }

    public static boolean addBooks(final File bookFile, final String separator) {
        final String csvExtension = "csv";
        final List<String> separatorList = new ArrayList<>(Arrays.asList(",", ";", "|"));
        if (!csvExtension.equals(FilenameUtils.getExtension(bookFile.getName()))) {
            return false;
        }

        if (!separatorList.contains(separator)) {
            ConsoleHelper.print("The separator is invalid.");
            return false;
        }

        final BufferedReader bfr;
        try {
            bfr = new BufferedReader(new FileReader(bookFile));

        } catch (FileNotFoundException e) {
            ConsoleHelper.print("Could not find file.");
            return false;
        }

        try {
            final String[] headline = bfr.readLine().split(Pattern.quote(separator));
            if (!(headline.length == 7 && checkHeadline(headline))) {
                throw new IOException();
            }
        } catch (IOException e) {
            ConsoleHelper.print("Please correct the csv file to match the Following rows in the exact order: \n " +
                    "ID" + separator + "Title" + separator + "Authors" + separator + "Year" + separator + "City" + separator + "Publisher" + separator + "Edition");
        }
        int line = 2;
        try {
            String row;
            String[] newBook;
            while ((row = bfr.readLine()) != null) {
                newBook = row.split(Pattern.quote(separator));
                if (!addBook(newBook)) {
                    throw new IOException();
                }
                line++;
            }
            bfr.close();
        } catch (IOException e) {
            ConsoleHelper.print(String.format("Error at line %s of the csv. Please check if the line is not empty or has invalid values. Entry's before line %s were added", line, line));
            return false;
        }

        return true;
    }

    public static boolean addBookCopies(final File bookCopyFile, final String separator) {
        final String csvExtension = "csv";
        final List<String> separatorList = new ArrayList<>(Arrays.asList(",", ";", "|"));
        if (!csvExtension.equals(FilenameUtils.getExtension(bookCopyFile.getName()))) {
            return false;
        }

        if (!separatorList.contains(separator)) {
            ConsoleHelper.print("The separator is invalid.");
            return false;
        }

        final BufferedReader bfr;
        try {
            bfr = new BufferedReader(new FileReader(bookCopyFile));

        } catch (FileNotFoundException e) {
            ConsoleHelper.print("Could not find file.");
            return false;
        }

        try {
            final String[] headline = bfr.readLine().split(separator);
            if (!(headline.length == 6 && checkHeadlineBookCopy(headline))) {
                throw new IOException();
            }
        } catch (IOException e) {
            ConsoleHelper.print("Please correct the csv file to match the Following rows in the exact order: \n " +
                    "ID" + separator + "Book" + separator + "ShelfLocation" + separator + "AddedToLibrary" + separator + "Lent" + separator + "LentDate");
        }
        int line = 2;
        try {
            String row;
            String[] newBookCopy;
            while ((row = bfr.readLine()) != null) {
                newBookCopy = row.split(separator);
                if (bookCopyExists(newBookCopy)) {
                    line++;
                    continue;
                }
                if (!addBookCopy(newBookCopy)) {
                    throw new IOException();
                }
                line++;
            }
            bfr.close();
        } catch (IOException e) {
            ConsoleHelper.print(String.format("Error at line %s of the csv. Please check if the line is not empty or has invalid values. Entry's before line %s were added", line, line));
            return false;
        }
        return true;
    }


    //checking if the headline of the bookcopy csv matches the required headline
    private static boolean checkHeadlineBookCopy(final String[] headline) {
        final String[] headlineTemplate = {"ID", "Book", "ShelfLocation", "AddedToLibrary", "Lent", "LentDate"};
        return Arrays.equals(headline, headlineTemplate);
    }

    //Adding the main.Elements.BookCopy to the Database
    private static boolean addBookCopy(final String[] newBookCopy) {

        Book originalBook = null;
        try {
            originalBook = getOriginalBook(Integer.parseInt(newBookCopy[1]));
        } catch (NumberFormatException e) {
            ConsoleHelper.print("The given ISBN is not valid please consider checking the csv data.");
        }
        if (originalBook == null) {//checking if the book is in the database
            ConsoleHelper.print("The given book is not in the database. Please consider checking the csv data.");
            return false;
        } else {
            try {
                persistentBookCopies.put(new BookCopy(
                        Integer.parseInt(newBookCopy[0]),
                        originalBook,
                        newBookCopy[2],
                        newBookCopy[3],
                        Boolean.parseBoolean(newBookCopy[4]),
                        newBookCopy[5]
                ), 1);
                return true;
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
    }

    //method to get the Customers-Data from the Customers csv-file
    public static boolean addCustomers(final File customerFile, final String separator) {
        final String csvExtension = "csv";
        final List<String> separatorList = new ArrayList<>(Arrays.asList(",", ";", "|"));
        if (!csvExtension.equals(FilenameUtils.getExtension(customerFile.getName()))) {
            return false;
        }

        if (!separatorList.contains(separator)) {
            ConsoleHelper.print("The separator is invalid.");
            return false;
        }

        final BufferedReader bfr;
        try {
            bfr = new BufferedReader(new FileReader(customerFile));

        } catch (FileNotFoundException e) {
            ConsoleHelper.print("Could not find file.");
            return false;
        }

        try {
            final String[] headline = bfr.readLine().split(Pattern.quote(separator));
            if (!(headline.length == 6 && checkHeadlineCustomer(headline))) {
                throw new IOException();
            }
        } catch (IOException e) {
            ConsoleHelper.print("Please correct the csv file to match the Following rows in the exact order: \n " +
                    "ID" + separator + "Name" + separator + "FirstName" + separator + "NumberOfBooksBorrowed" + separator + "BookList" + separator + "Overdone");
            ConsoleHelper.print("The BookList values have to either be empty or the ISBN of the original book separated with a '-' .");
        }
        int line = 2;
        try {
            String row;
            String[] newCustomer;
            while ((row = bfr.readLine()) != null) {
                newCustomer = row.split(Pattern.quote(separator));
                if (!addCustomer(newCustomer)) {
                    throw new IOException();
                }
            }
            line++;
            bfr.close();
        } catch (IOException e) {
            ConsoleHelper.print(String.format("Error at line %s of the csv. Please check if the line is not empty or has invalid values. Entry's before line %s were added", line, line));//nop
            return false;
        }
        return true;
    }

    //checking if the headline of the  customers csv matches the required headline
    private static boolean checkHeadlineCustomer(final String[] headline) {
        final String[] headlineTemplate = {"ID", "Name", "FirstName", "NumberOfBooksBorrowed", "BookList", "Overdone"};
        return Arrays.equals(headline, headlineTemplate);
    }

    private static boolean addCustomer(final String[] newCustomer) {
        try {
            customers.put(new Customer(
                    Integer.parseInt(newCustomer[0]),
                    newCustomer[1],
                    newCustomer[2],
                    Integer.parseInt(newCustomer[3]),
                    getBookCopiesListForCustomer(newCustomer[4], newCustomer[1], newCustomer[2]),
                    Boolean.parseBoolean(newCustomer[5])
            ), customers.size() + 1);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean deleteCostumer(final Customer customer) {
        if (!checkCustomerPresent(customer)) {
            return false;
        }
        final int keyToDelete = customers.get(customer);
        return customers.remove(customer, keyToDelete);
    }

    public static boolean deleteBook(final Book book) {
        if (!checkNoBookCopyLent(book) || !checkBookIsPresent(book)) {
            return false;
        }
        final Book originalBookToDelete = getOriginalBook(book);
        final int keyToDelete = persistentBooks.get(originalBookToDelete);
        return persistentBooks.remove(originalBookToDelete, keyToDelete);
    }

    public static boolean deleteBookCopy(final BookCopy bookCopy) {

        if (!checkBookCopyIsPresent(bookCopy)) {
            return false;
        }

        if (getNumberOfBookCopiesLent(bookCopy) == persistentBookCopies.get(bookCopy)) {
            return false;
        }

        final int numberOfBookCopies = persistentBookCopies.get(bookCopy);
        if (numberOfBookCopies > 1) {
            persistentBookCopies.put(bookCopy, numberOfBookCopies - 1);
        } else {
            persistentBookCopies.remove(bookCopy);
        }
        return true;
    }

    private static boolean checkHeadline(final String[] headline) {
        final String[] headlineTemplate = {"ID", "Title", "Authors", "Year", "City", "Publisher", "Edition"};
        return Arrays.equals(headline, headlineTemplate);
    }

    private static boolean addBook(final String[] newBook) {

        try {
            persistentBooks.put(new Book(
                    Integer.parseInt(newBook[0]),
                    newBook[1],
                    newBook[2],
                    Year.of(Integer.parseInt(newBook[3])),
                    newBook[4],
                    newBook[5],
                    Integer.parseInt(newBook[6])
            ), persistentBooks.size() + 1);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }

    }

    public static int getNumberOfBookCopiesLent(final BookCopy bookCopy) {
        int numberOfLendBooks = 0;
        final Set<Customer> customerList = customers.keySet();
        for (final Customer customer : customerList) {
            final List<BookCopy> borrowedBooks = customer.getBookList();
            for (final BookCopy lentBook : borrowedBooks) {
                if (lentBook.getId() == bookCopy.getId()) {
                    numberOfLendBooks++;
                }
            }
        }

        return numberOfLendBooks;
    }

    private static boolean checkNoBookCopyLent(final Book book) {
        final Set<BookCopy> bookCopySet = persistentBookCopies.keySet();
        for (final BookCopy bookCopy : bookCopySet) {
            final Book bookFromCopy = bookCopy.getBook();
            if (bookFromCopy.getTitle().equals(book.getTitle()) &&
                    bookFromCopy.getAuthors().equals(book.getAuthors()) &&
                    bookFromCopy.getPublisher().equals(book.getPublisher())) {
                return getNumberOfBookCopiesLent(bookCopy) == persistentBookCopies.get(bookCopy);
            }
        }
        return true;
    }

    private static Book getOriginalBook(final Book book) {
        if (persistentBooks.containsKey(book)) {
            return book;
        } else {
            for (final Book persistentBook : persistentBooks.keySet()) {
                if (persistentBook.getTitle().equals(book.getTitle()) &&
                        persistentBook.getAuthors().equals(book.getAuthors()) &&
                        persistentBook.getPublisher().equals(book.getPublisher())) {
                    return persistentBook;
                }
            }
        }
        return null;
    }

    private static Book getOriginalBook(final int bookIsbn) {
        for (final Book persistentBook : persistentBooks.keySet()) {
            if (persistentBook.getId() == bookIsbn) {
                return persistentBook;
            }
        }
        return null;
    }

    public static BookCopy getOriginalBookCopy(final int bookIsbn) {
        for (final BookCopy persistentBookCopy : persistentBookCopies.keySet()) {
            if (persistentBookCopy.getBook().getId() == bookIsbn) {
                return persistentBookCopy;
            }
        }
        return null;
    }


    private static List<BookCopy> getBookCopiesListForCustomer(final String bookCopiesLent, final String name, final String firstName) {
        final ArrayList<BookCopy> lentCopies = new ArrayList<>();
        for (final String bookCopyData : bookCopiesLent.split(Pattern.quote("-").trim())) {
            final String[] bookCopyDataArray = bookCopyData.split(Pattern.quote(":").trim());

            if (bookCopyDataArray.length != 2) {
                ConsoleHelper.print(String.format("The BookCopy data for one book is not valid for customer %s %s.\n" +
                        "Continue with other BookCopies.\n" +
                        "Please check that the BookCopy data for the BookCopy is provided as followed:\n" +
                        "BookCopyId:LentDate", name, firstName));
                continue;
            }

            for (final BookCopy bookCopy : persistentBookCopies.keySet()) {
                try {
                    if (bookCopy.getId() == Integer.parseInt(bookCopyDataArray[0])) {
                        if (persistentBookCopies.get(bookCopy) > getNumberOfBookCopiesLent(bookCopy)) {
                            final BookCopy bookCopyToAdd = bookCopy.clone();
                            bookCopyToAdd.setLent(true);
                            bookCopyToAdd.setLentDate(bookCopyDataArray[1]);
                            lentCopies.add(bookCopyToAdd);
                        } else {
                            ConsoleHelper.print(String.format("There are not enough book copies left for the Book: %s", bookCopy.getBook().getTitle()));
                        }
                    }
                } catch (Exception e) {
                    ConsoleHelper.print(String.format("Failed to import one BookCopy for customer %s %s." +
                                    "\nPlease consider to check the CSV." +
                                    "\nProvided BookCopyData:" +
                                    "\n %s %s",
                            name, firstName, bookCopyDataArray[0], bookCopyDataArray[1]));
                }
            }
        }
        return lentCopies;
    }

    private static boolean bookCopyExists(final String[] potentialNewBookCopy) {
        final Book originalBook = getOriginalBook(Integer.parseInt(potentialNewBookCopy[1]));
        if (!persistentBooks.containsKey(originalBook)) {
            return false;
        }
        for (final Map.Entry<BookCopy, Integer> bookCopy : persistentBookCopies.entrySet()) {
            if (bookCopy.getKey().getBook().equals(originalBook)) {
                persistentBookCopies.put(bookCopy.getKey(), bookCopy.getValue() + 1);
                return true;
            }
        }
        return false;
    }


    /**
     * This methods are only used for test purpose
     */
    public static void addBook(final Book book) {

        String[] bookStringArray = {
                Integer.toString(book.getId()),
                book.getTitle(),
                book.getAuthors(),
                book.getYear().toString(),
                book.getCity(),
                book.getPublisher(),
                Integer.toString(book.getEdition())
        };
        addBook(bookStringArray);
    }

    public static void addBookCopy(final BookCopy bookCopy) {
        for (final Map.Entry<BookCopy, Integer> bookCopyEntry : persistentBookCopies.entrySet()) {
            if (bookCopy.getBook().getId() == bookCopyEntry.getKey().getBook().getId()) {
                persistentBookCopies.put(bookCopyEntry.getKey(), bookCopyEntry.getValue() + 1);
                return;
            }

        }
        persistentBookCopies.put(bookCopy, 1);
    }

    public static void addCustomer(final Customer customer) {
        customers.put(customer, customers.size());
    }

    public static boolean checkBookIsPresent(final Book book) {
        if (persistentBooks.containsKey(book)) {
            return true;
        } else {
            for (final Book persistentBook : persistentBooks.keySet()) {
                if (persistentBook.getTitle().equals(book.getTitle()) &&
                        persistentBook.getAuthors().equals(book.getAuthors()) &&
                        persistentBook.getPublisher().equals(book.getPublisher())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkBookCopyIsPresent(final BookCopy bookCopy) {
        return persistentBookCopies.containsKey(bookCopy);
    }

    public static boolean checkCustomerPresent(final Customer customer) {
        return customers.containsKey(customer);
    }

    public static void clearData() {
        customers.clear();
        persistentBookCopies.clear();
        persistentBooks.clear();
    }

    public static void addBookCopyToCustomer(final Customer customer, final BookCopy bookCopy) {
        try {
            final BookCopy addingBookCopy = bookCopy.clone();
            addingBookCopy.setLent(true);
            addingBookCopy.setLentDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            customer.getBookList().add(addingBookCopy);
            customer.bookCopyLent();
        } catch (CloneNotSupportedException e) {
            ConsoleHelper.print("There was an error lending the book.");
        }
    }

    public static void addBookCopyToCustomer(final Customer customer, final BookCopy bookCopy, final String date) {
        try {
            final BookCopy addingBookCopy = bookCopy.clone();
            addingBookCopy.setLent(true);
            addingBookCopy.setLentDate(date);
            customer.getBookList().add(addingBookCopy);
            customer.bookCopyLent();
        } catch (CloneNotSupportedException e) {
            ConsoleHelper.print("There was an error lending the book.");
        }
    }

}
