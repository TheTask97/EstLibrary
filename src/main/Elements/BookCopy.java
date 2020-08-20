package main.Elements;

public class BookCopy implements Cloneable {
    final private int id;
    final private Book book;
    final private String shelfLocation;
    final private String addedToLibrary;
    private boolean lent;
    private String lentDate;

    public BookCopy(int id, Book book, String shelfLocation, String addedToLibrary, boolean lent, String lentDate) {
        this.id = id;
        this.book = book;
        this.shelfLocation = shelfLocation;
        this.addedToLibrary = addedToLibrary;
        this.lent = lent;
        this.lentDate = lentDate;
    }


    public int getId() {
        return id;
    }

    public void setLentDate(final String date) {
        this.lentDate = date;
    }

    public Book getBook() {
        return book;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public String getAddedToLibrary() {
        return addedToLibrary;
    }

    public boolean isLent() {
        return lent;
    }

    public void setLent(final boolean lent) {
        this.lent = lent;
    }

    public String getLentDate() {
        return lentDate;
    }

    public String getFormattedData() {
        final StringBuilder dataBuilder = new StringBuilder();
        final String newLine = "\n";
        dataBuilder.append("Title: " + this.book.getTitle() + newLine)
                .append("ISBN: " + this.book.getId() + newLine)
                .append("Authors: " + this.book.getAuthors() + newLine)
                .append("ID: " + this.id + newLine)
                .append("ShelfLocation: " + this.shelfLocation + newLine)
                .append("Is lent: " + this.lent + newLine)
                .append("Lent date: " + this.lentDate + newLine);
        return dataBuilder.toString();

    }

    public BookCopy clone() throws CloneNotSupportedException {
        return (BookCopy) super.clone();
    }
}