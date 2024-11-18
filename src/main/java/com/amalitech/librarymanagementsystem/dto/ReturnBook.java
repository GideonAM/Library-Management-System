package com.amalitech.librarymanagementsystem.dto;

import java.sql.Date;

public class ReturnBook {
    private final String title;
    private final String author;
    private final Date date;

    public ReturnBook(String title, String author, Date date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }
}
