package main.Elements;

import java.time.Year;

public class Book {
    final private int id;
    final private String title;
    final private String authors;
    final private Year year;
    final private String city;
    final private String publisher;
    final private int edition;

    public Book(int id, String title, String authors, Year year, String city, String publisher, int edition) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.city = city;
        this.publisher = publisher;
        this.edition = edition;

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public Year getYear() {
        return year;
    }

    public String getCity() {
        return city;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getEdition() {
        return edition;
    }


    public String getFormattedData() {
        final StringBuilder dataBuilder = new StringBuilder();
        final String newLine = "\n";
        dataBuilder.append("ID: " + this.id + newLine)
                .append("Title: " + this.title + newLine)
                .append("Authors: " + this.authors + newLine)
                .append("Year: " + this.year + newLine)
                .append("City: " + this.city + newLine)
                .append("Publisher: " + this.publisher + newLine)
                .append("Edition: " + this.edition + newLine);
        return dataBuilder.toString();
    }
}
