package com.amalitech.librarymanagementsystem.entity;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private Integer availableQuantity;

    public Book(Integer id, String title, String author, Integer availableQuantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.availableQuantity = availableQuantity;
    }
}
