package com.stepaniuk.bookstore.book.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Exception thrown when no book with given id exists.
 *
 * @see RuntimeException
 */
@Getter
public class NoSuchBookByIdException extends RuntimeException{
    private final UUID id;

    public NoSuchBookByIdException(UUID id) {
        super("No such book with id: " + id);
        this.id = id;
    }
}
