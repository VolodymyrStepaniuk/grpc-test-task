package com.stepaniuk.bookstore.grpc;

import com.bookstore.grpc.Bookstore;
import com.bookstore.grpc.BookstoreServiceGrpc;
import com.stepaniuk.bookstore.book.BookService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class BookGrpcService extends BookstoreServiceGrpc.BookstoreServiceImplBase {

    private final BookService bookService;

    @Override
    public void createBook(Bookstore.CreateBookRequest request, StreamObserver<Bookstore.BookResponse> responseObserver) {
        var bookResponse = bookService.createBook(request);

        responseObserver.onNext(bookResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getBook(Bookstore.GetBookRequest request, StreamObserver<Bookstore.BookResponse> responseObserver) {
        var bookResponse = bookService.getBookById(request);

        responseObserver.onNext(bookResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateBook(Bookstore.UpdateBookRequest request, StreamObserver<Bookstore.BookResponse> responseObserver) {
        var bookResponse = bookService.updateBook(request);

        responseObserver.onNext(bookResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteBook(Bookstore.DeleteBookRequest request, StreamObserver<Bookstore.DeleteBookResponse> responseObserver) {
        var deleteBookResponse = bookService.deleteBook(request);

        responseObserver.onNext(deleteBookResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllBooks(com.google.protobuf.Empty request,StreamObserver<Bookstore.ListOfBookResponses> responseObserver) {
        var allBooksResponse = bookService.getAllBooks();

        responseObserver.onNext(allBooksResponse);
        responseObserver.onCompleted();
    }
}
