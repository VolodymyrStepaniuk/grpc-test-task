package com.stepaniuk.bookstore.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.stepaniuk.bookstore.book.exception.NoSuchBookByIdException;
import com.stepaniuk.bookstore.testspecific.JpaLevelTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JpaLevelTest
@Sql(scripts = "classpath:sql/book.sql")
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldSaveBook() {
        // given
        Book book = new Book(null,"title", "author", "0134012", 10, "description", BigDecimal.valueOf(10.0));

        // when
        Book savedBook = bookRepository.save(book);

        // then
        assertNotNull(savedBook.getId());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());
        assertEquals(book.getIsbn(), savedBook.getIsbn());
        assertEquals(book.getQuantity(), savedBook.getQuantity());
        assertEquals(book.getDescription(), savedBook.getDescription());
        assertEquals(book.getPrice(), savedBook.getPrice());
    }

    @Test
    void shouldReturnBookWhenFindById(){
        Optional<Book> optionalBook = bookRepository.findById(UUID.fromString("b7f0ef30-e861-4c3f-9858-2bc59e3aee75"));

        assertTrue(optionalBook.isPresent());

        Book foundedBook = optionalBook.get();

        assertNotNull(foundedBook);
        assertEquals(UUID.fromString("b7f0ef30-e861-4c3f-9858-2bc59e3aee75"), foundedBook.getId());
        assertEquals("The Great Gatsby", foundedBook.getTitle());
        assertEquals("F. Scott Fitzgerald", foundedBook.getAuthor());
        assertEquals("9780743273565", foundedBook.getIsbn());
        assertEquals(10, foundedBook.getQuantity());
        assertEquals("The Great Gatsby, F. Scott Fitzgerald's third book, stands as the supreme achievement of his career.", foundedBook.getDescription());
        assertEquals(BigDecimal.valueOf(9.99), foundedBook.getPrice());
    }

    @Test
    void shouldUpdateBookWhenChangingQuantity(){
        Book book = bookRepository.findById(UUID.fromString("72f02364-2590-42e0-9488-39b6f9f1c331")).orElseThrow();

        book.setQuantity(5);

        Book updatedBook = bookRepository.save(book);

        assertNotNull(updatedBook);
        assertEquals(UUID.fromString("72f02364-2590-42e0-9488-39b6f9f1c331"), updatedBook.getId());
        assertEquals(5, updatedBook.getQuantity());
    }

    @Test
    void shouldDeleteBookWhenDeletingByExistingBook(){
        Book book = bookRepository.findById(UUID.fromString("897f1e44-6f67-427b-a4a7-932ac67709f9")).orElseThrow();

        bookRepository.delete(book);

        Optional<Book> optionalBook = bookRepository.findById(UUID.fromString("897f1e44-6f67-427b-a4a7-932ac67709f9"));
        assertTrue(optionalBook.isEmpty());
    }

    @Test
    void shouldDeleteBookWhenDeletingByExistingBookId(){
        bookRepository.deleteById(UUID.fromString("b7f0ef30-e861-4c3f-9858-2bc59e3aee75"));

        Optional<Book> optionalBook = bookRepository.findById(UUID.fromString("b7f0ef30-e861-4c3f-9858-2bc59e3aee75"));
        assertTrue(optionalBook.isEmpty());
    }

    @Test
    void shouldReturnTrueWhenBookExistsById(){
        boolean exists = bookRepository.existsById(UUID.fromString("b7f0ef30-e861-4c3f-9858-2bc59e3aee75"));

        assertTrue(exists);
    }

    @Test
    void shouldReturnNonEmptyListWhenFindAll(){
        List<Book> books = bookRepository.findAll();

        assertNotNull(books);
        assertFalse(books.isEmpty());
    }
}
