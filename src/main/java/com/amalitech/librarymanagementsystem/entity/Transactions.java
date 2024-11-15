package com.amalitech.librarymanagementsystem.entity;

import com.amalitech.librarymanagementsystem.database.TransactionType;

import java.time.LocalDateTime;

public class Transactions {
    private Integer id;
    private TransactionType transactionType;
    private LocalDateTime dateOfTransaction;
    private Patron patron;
    private Book book;
}
