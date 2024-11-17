package com.amalitech.librarymanagementsystem;

import java.sql.Date;

public class AvailableBooks {
    private final String title;
    private final String author;
    private final String bookType;
    private final Date date;

    public AvailableBooks(String title, String author, String bookType, Date date) {
        this.title = title;
        this.author = author;
        this.bookType = bookType;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookType() {
        return bookType;
    }

    public Date getDate() {
        return date;
    }
}
