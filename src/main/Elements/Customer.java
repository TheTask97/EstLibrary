package main.Elements;

import java.util.List;

public class Customer {

    final private int id;
    final private String name;
    final private String firstName;
    private int numberOfBooksBorrowed;
    final private List<BookCopy> bookList;
    final private boolean overdone;


    public Customer(int id, String name, String firstName, int numberOfBooksBorrowed, List<BookCopy> bookList, boolean overdone) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.numberOfBooksBorrowed = numberOfBooksBorrowed;
        this.bookList = bookList;
        this.overdone = overdone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getNumberOfBooksBorrowed() {
        return numberOfBooksBorrowed;
    }

    public List<BookCopy> getBookList() {
        return bookList;
    }

    public boolean isOverdone() {
        return overdone;
    }

    public String getFormattedData() {
        final StringBuilder dataBuilder = new StringBuilder();
        final String newLine = "\n";
        dataBuilder.append("ID: " + this.id + newLine)
                .append("Name: " + this.name + newLine)
                .append("First name: " + this.firstName + newLine)
                .append("Payment status: " + this.overdone + newLine)
                .append("Number of lent book copies: " + this.numberOfBooksBorrowed + newLine);
        return dataBuilder.toString();
    }

    public void bookCopyReturned() {
        this.numberOfBooksBorrowed--;
    }

    public void bookCopyLent() {
        this.numberOfBooksBorrowed++;
    }

}
