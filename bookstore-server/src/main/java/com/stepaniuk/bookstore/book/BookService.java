package com.stepaniuk.bookstore.book;

import com.bookstore.grpc.Bookstore;
import com.stepaniuk.bookstore.book.exception.NoSuchBookByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Bookstore.BookResponse createBook(Bookstore.CreateBookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setQuantity(request.getQuantity());
        book.setDescription(request.getDescription());
        book.setPrice(BigDecimal.valueOf(request.getPrice()));

        var savedBook = bookRepository.save(book);

        return bookMapper.toResponse(savedBook);
    }

    public Bookstore.BookResponse getBookById(Bookstore.GetBookRequest request) {
        var bookId = UUID.fromString(request.getId());

        var book = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchBookByIdException(bookId));

        return bookMapper.toResponse(book);
    }

    public Bookstore.BookResponse updateBook(Bookstore.UpdateBookRequest request) {
        var bookId = UUID.fromString(request.getId());

        var book = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchBookByIdException(bookId));

        if (!request.getTitle().isEmpty())
            book.setTitle(request.getTitle());
        if (!request.getAuthor().isEmpty())
            book.setAuthor(request.getAuthor());
        if (!request.getIsbn().isEmpty())
            book.setIsbn(request.getIsbn());
        if (request.getQuantity() > 0)
            book.setQuantity(request.getQuantity());
        if (!request.getDescription().isEmpty())
            book.setDescription(request.getDescription());
        if (request.getPrice() > 0)
            book.setPrice(BigDecimal.valueOf(request.getPrice()));

        var updatedBook = bookRepository.save(book);

        return bookMapper.toResponse(updatedBook);
    }

    public Bookstore.DeleteBookResponse deleteBook(Bookstore.DeleteBookRequest request) {
        var book = bookRepository.findById(UUID.fromString(request.getId()))
                .orElseThrow(() -> new NoSuchBookByIdException(UUID.fromString(request.getId())));

        bookRepository.delete(book);

        return Bookstore.DeleteBookResponse.newBuilder()
                .setIsDeleted(true)
                .build();
    }

    public Bookstore.ListOfBookResponses getAllBooks() {
        List<Bookstore.BookResponse> bookResponses = bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .toList();

        return Bookstore.ListOfBookResponses.newBuilder()
                .addAllBooks(bookResponses)
                .setSize(bookResponses.size())
                .build();
    }
}
