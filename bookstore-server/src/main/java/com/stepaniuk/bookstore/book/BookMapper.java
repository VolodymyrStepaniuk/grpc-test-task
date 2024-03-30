package com.stepaniuk.bookstore.book;

import com.bookstore.grpc.Bookstore;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookMapper {
    Bookstore.BookResponse toResponse(Book book);
}
