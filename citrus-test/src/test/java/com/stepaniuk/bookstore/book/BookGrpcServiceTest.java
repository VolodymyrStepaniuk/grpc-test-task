package com.stepaniuk.bookstore.book;


import com.bookstore.grpc.Bookstore;
import com.bookstore.grpc.BookstoreServiceGrpc;
import com.consol.citrus.GherkinTestActionRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.config.CitrusSpringConfig;
import com.consol.citrus.junit.jupiter.spring.CitrusSpringSupport;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@CitrusSpringSupport
@ContextConfiguration(classes = {CitrusSpringConfig.class})
class BookGrpcServiceTest {
    private final Channel channel = ManagedChannelBuilder
            .forAddress("localhost", 8080)
            .usePlaintext()
            .build();

    private final BookstoreServiceGrpc.BookstoreServiceBlockingStub blockingStub = BookstoreServiceGrpc.newBlockingStub(channel);

    @Test
    @CitrusTest
    void shouldReturnBookResponseWhenCreatingBook(@CitrusResource GherkinTestActionRunner runner) {
        var bookId = UUID.randomUUID();
        var createRequest = Bookstore.CreateBookRequest.newBuilder()
                .setTitle("Title")
                .setAuthor("Author")
                .setIsbn("ISBN")
                .setQuantity(1)
                .setDescription("Description")
                .setPrice(10)
                .build();


        runner.given((context) -> {
            context.setVariable("expectedResponse",
                    getNewBookResponseWithAllFields(bookId, createRequest.getTitle(), createRequest.getAuthor())
            );
        });

        runner.when((context) -> {
            var response = blockingStub.createBook(createRequest);
            context.setVariable("response", response);
        });

        runner.then((context) -> {
            var response = context.getVariable("response", Bookstore.BookResponse.class);
            var expectedResponse = context.getVariable("expectedResponse", Bookstore.BookResponse.class);
            float delta = 0.0001f;

            assertNotNull(response);
            assertEquals(expectedResponse.getTitle(), response.getTitle());
            assertEquals(expectedResponse.getAuthor(), response.getAuthor());
            assertEquals(expectedResponse.getIsbn(), response.getIsbn());
            assertEquals(expectedResponse.getQuantity(), response.getQuantity());
            assertEquals(expectedResponse.getDescription(), response.getDescription());
            assertEquals(expectedResponse.getPrice(), response.getPrice(), delta);
        });
    }

    @Test
    @CitrusTest
    void shouldReturnBookResponseWhenGettingBookById(@CitrusResource GherkinTestActionRunner runner) {

       var bookId = UUID.fromString("b7f0ef30-e861-4c3f-9858-2bc59e3aee75");
       var title = "Title";
       var author = "Author";

        runner.given((context) -> {
            context.setVariable("expectedResponse",
                    getNewBookResponseWithAllFields(bookId, title, author)
            );

            context.setVariable("bookId", bookId.toString());
        });

        runner.when((context) -> {
            var getBookRequest = Bookstore.GetBookRequest.newBuilder().setId(context.getVariable("bookId")).build();

            var response = blockingStub.getBook(getBookRequest);
            context.setVariable("response", response);
        });

        runner.then((context) -> {
            var response = context.getVariable("response", Bookstore.BookResponse.class);
            var expectedResponse = context.getVariable("expectedResponse", Bookstore.BookResponse.class);
            float delta = 0.0001f;

            assertNotNull(response);
            assertEquals(expectedResponse.getTitle(), response.getTitle());
            assertEquals(expectedResponse.getAuthor(), response.getAuthor());
            assertEquals(expectedResponse.getIsbn(), response.getIsbn());
            assertEquals(expectedResponse.getQuantity(), response.getQuantity());
            assertEquals(expectedResponse.getDescription(), response.getDescription());
            assertEquals(expectedResponse.getPrice(), response.getPrice(), delta);
        });
    }

    @Test
    @CitrusTest
    void shouldReturnBookResponseWhenUpdatingBookById(@CitrusResource GherkinTestActionRunner runner) {
        var bookId = UUID.fromString("b7f0ef30-e861-4c3f-9858-2bc59e3aee75");
        var author = "Author";

        runner.given((context) -> {
            context.setVariable("bookId", bookId.toString());
        });

        runner.when((context) -> {
            var updateRequest = Bookstore.UpdateBookRequest.newBuilder()
                    .setId(context.getVariable("bookId"))
                    .setTitle("New Title")
                    .build();

            var response = blockingStub.updateBook(updateRequest);
            context.setVariable("response", response);
            context.setVariable("expectedResponse",
                    getNewBookResponseWithAllFields(bookId, updateRequest.getTitle(), author)
            );
        });

        runner.then((context) -> {
            var response = context.getVariable("response", Bookstore.BookResponse.class);
            var expectedResponse = context.getVariable("expectedResponse", Bookstore.BookResponse.class);
            float delta = 0.0001f;

            assertNotNull(response);
            assertEquals(expectedResponse.getTitle(), response.getTitle());
            assertEquals(expectedResponse.getAuthor(), response.getAuthor());
            assertEquals(expectedResponse.getIsbn(), response.getIsbn());
            assertEquals(expectedResponse.getQuantity(), response.getQuantity());
            assertEquals(expectedResponse.getDescription(), response.getDescription());
            assertEquals(expectedResponse.getPrice(), response.getPrice(), delta);
        });
    }

    @Test
    @CitrusTest
    void shouldReturnDeleteBookResponseWhenDeletingBookById(@CitrusResource GherkinTestActionRunner runner) {
        var bookId = UUID.fromString("b7f0ef30-e861-4c3f-9858-2bc59e3aee75");

        runner.given((context) ->
            context.setVariable("bookId", bookId.toString())
        );

        runner.when((context) -> {
            var deleteRequest = Bookstore.DeleteBookRequest.newBuilder().setId(context.getVariable("bookId")).build();

            var response = blockingStub.deleteBook(deleteRequest);
            context.setVariable("response", response);
        });

        runner.then((context) -> {
            var response = context.getVariable("response", Bookstore.DeleteBookResponse.class);

            assertNotNull(response);
            assertTrue(response.getIsDeleted());
        });
    }

    @Test
    @CitrusTest
    void shouldReturnAllBooksResponseWhenGettingAllBooks(@CitrusResource GherkinTestActionRunner runner) {

        runner.when((context) -> {
            var responses = blockingStub.getAllBooks(com.google.protobuf.Empty.getDefaultInstance());
            context.setVariable("responses", responses);
        });

        runner.then((context) -> {
            var responses = context.getVariable("responses", Bookstore.ListOfBookResponses.class);

            assertNotNull(responses);
            assertFalse(responses.getBooksList().isEmpty());
        });
    }

    private static Bookstore.BookResponse getNewBookResponseWithAllFields(UUID bookId, String title, String author) {
        return Bookstore.BookResponse.newBuilder()
                .setId(bookId.toString())
                .setTitle(title)
                .setAuthor(author)
                .setIsbn("ISBN")
                .setQuantity(1)
                .setDescription("Description")
                .setPrice(10)
                .build();
    }
}
