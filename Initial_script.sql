CREATE DATABASE IF NOT EXISTS library;
USE library;
-- Create author table
CREATE TABLE author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

-- Create genre table
CREATE TABLE genre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100)
);

-- Create book table with foreign key constraints
CREATE TABLE book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    author_id INT,
    genre_id INT,
    price DECIMAL,
    quantity INT,
    FOREIGN KEY (author_id) REFERENCES author(id),
    FOREIGN KEY (genre_id) REFERENCES genre(id)
);

-- Insert data into author table
INSERT INTO author (name) VALUES
    ('Author 1'),
    ('Author 2'),
    ('Author 3'),
    ('Author 4'),
    ('Author 5');

-- Insert data into genre table
INSERT INTO genre (title) VALUES
    ('Genre 1'),
    ('Genre 2'),
    ('Genre 3'),
    ('Genre 4'),
    ('Genre 5');

-- Insert data into book table
INSERT INTO book (title, author_id, genre_id, price, quantity) VALUES
    ('Book 1', 1, 1, 20.99, 100),
    ('Book 2', 2, 2, 15.50, 75),
    ('Book 3', 3, 3, 30.75, 50),
    ('Book 4', 4, 4, 18.25, 120),
    ('Book 5', 5, 5, 25.00, 80),
    ('Book 6', 1, 1, 22.50, 90),
    ('Book 7', 2, 2, 27.99, 110),
    ('Book 8', 3, 3, 19.75, 60),
    ('Book 9', 4, 4, 24.50, 70),
    ('Book 10', 5, 5, 16.80, 85);