CREATE TABLE books (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    description TEXT NOT NULL,
    price FLOAT NOT NULL
);

INSERT INTO books(id, title, author, isbn, quantity, description, price) VALUES
('b7f0ef30-e861-4c3f-9858-2bc59e3aee75', 'Title', 'Author', 'ISBN', 1, 'Description', 10);