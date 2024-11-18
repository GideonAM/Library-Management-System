CREATE TABLE Book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    availableQuantity INT NOT NULL,
    bookType VARCHAR(255) NOT NULL
);

CREATE TABLE Patron (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE Transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patronEmail VARCHAR(255) NOT NULL,
    bookTitle VARCHAR(255) NOT NULL,
    transaction_type ENUM('BORROW', 'RETURN') NOT NULL,
    transaction_date DATETIME NOT NULL,
    FOREIGN KEY (patron_id) REFERENCES Patron(id),
    FOREIGN KEY (book_id) REFERENCES Book(id)
);
