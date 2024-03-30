package com.stepaniuk.bookstore.book;

import com.bookstore.grpc.Bookstore;
import com.stepaniuk.bookstore.book.exception.NoSuchBookByIdException;
import com.stepaniuk.bookstore.testspecific.ServiceLevelUnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.answer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ServiceLevelUnitTest
@ContextConfiguration(classes = {BookService.class, BookMapperImpl.class})
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private static Book getNewBookWithAllFields(UUID id){
        return new Book(id,"title", "author", "isbn", 1, "description", BigDecimal.valueOf(10.0));
    }

    @Test
    void shouldReturnBookResponseWhenCreatingBook() {
        // given
        var bookId = UUID.randomUUID();

        var request = Bookstore.CreateBookRequest.newBuilder()
                .setTitle("title")
                .setAuthor("author")
                .setIsbn("isbn")
                .setQuantity(1)
                .setDescription("description")
                .setPrice((float)10.0)
                .build();

        // when
        when(bookRepository.save(any())).thenAnswer(answer(getFakeSave(bookId)));

        var response = bookService.createBook(request);

        // then
        assertNotNull(response);
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getAuthor(), response.getAuthor());
        assertEquals(request.getIsbn(), response.getIsbn());
        assertEquals(request.getQuantity(), response.getQuantity());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(request.getPrice(), response.getPrice());
    }

    @Test
    void shouldReturnBookResponseWhenGettingBookById(){
        var bookId = UUID.randomUUID();
        var book = getNewBookWithAllFields(bookId);

        when(bookRepository.findById(eq(bookId))).thenReturn(java.util.Optional.of(book));

        var bookResponse = bookService.getBookById(Bookstore.GetBookRequest.newBuilder().setId(bookId.toString()).build());

        assertNotNull(bookResponse);
        assertEquals(book.getTitle(), bookResponse.getTitle());
        assertEquals(book.getAuthor(), bookResponse.getAuthor());
        assertEquals(book.getIsbn(), bookResponse.getIsbn());
        assertEquals(book.getQuantity(), bookResponse.getQuantity());
        assertEquals(book.getDescription(), bookResponse.getDescription());
        assertEquals(book.getPrice().floatValue(), bookResponse.getPrice());
    }

    @Test
    void shouldThrowNoSuchBookByIdExceptionWhenGetByNonExistingId(){
        var bookId = UUID.randomUUID();

        when(bookRepository.findById(eq(bookId))).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchBookByIdException.class, () -> bookService.getBookById(Bookstore.GetBookRequest.newBuilder().setId(bookId.toString()).build()));
    }

    @Test
    void shouldReturnListOfBooksWhenGettingAllBooks(){
        var bookId = UUID.randomUUID();
        var book = getNewBookWithAllFields(bookId);

        when(bookRepository.findAll()).thenReturn(java.util.List.of(book));

        var bookResponses = bookService.getAllBooks();
        var bookResponse = bookResponses.getBooksList().get(0);

        assertNotNull(bookResponses);
        assertEquals(1, bookResponses.getBooksCount());

        assertNotNull(bookResponse);
        assertEquals(book.getTitle(), bookResponse.getTitle());
        assertEquals(book.getAuthor(), bookResponse.getAuthor());
        assertEquals(book.getIsbn(), bookResponse.getIsbn());
        assertEquals(book.getQuantity(), bookResponse.getQuantity());
        assertEquals(book.getDescription(), bookResponse.getDescription());
        assertEquals(book.getPrice().floatValue(), bookResponse.getPrice());
    }

    @Test
    void shouldReturnVoidWhenDeleteBook(){
        //given
        var bookId = UUID.randomUUID();
        var feedback = getNewBookWithAllFields(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(feedback));
        //when
        bookService.deleteBook(Bookstore.DeleteBookRequest.newBuilder().setId(bookId.toString()).build());
        //then
        verify(bookRepository, times(1)).delete(feedback);
    }

    @Test
    void shouldReturnNoSuchBookByIdExceptionWhenDeleteBookByNonExistingId(){
        // given
        var bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // when && then
        assertThrows(NoSuchBookByIdException.class, () -> bookService.deleteBook(Bookstore.DeleteBookRequest.newBuilder().setId(bookId.toString()).build()));
    }

    @Test
    void shouldUpdateAndReturnBookResponseWhenChangingDescription(){
        var bookId = UUID.randomUUID();

        var book = getNewBookWithAllFields(bookId);

        var request = Bookstore.UpdateBookRequest.newBuilder()
                .setId(bookId.toString())
                .setDescription("new description")
                .build();

        when(bookRepository.findById(eq(bookId))).thenReturn(java.util.Optional.of(book));
        when(bookRepository.save(any())).thenAnswer(answer(getFakeSave(bookId)));

        var response = bookService.updateBook(request);

        assertNotNull(response);
        assertEquals(book.getId(), UUID.fromString(response.getId()));
        assertEquals(book.getTitle(), response.getTitle());
        assertEquals(book.getAuthor(), response.getAuthor());
        assertEquals(book.getIsbn(), response.getIsbn());
        assertEquals(book.getQuantity(), response.getQuantity());
        assertEquals(request.getDescription(), response.getDescription());
        assertEquals(book.getPrice().floatValue(), response.getPrice());
    }
    private Answer1<Book, Book> getFakeSave(UUID id) {
        return book -> {
            book.setId(id);
            return book;
        };
    }
}
