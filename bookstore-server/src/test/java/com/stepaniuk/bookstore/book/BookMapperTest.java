package com.stepaniuk.bookstore.book;

import com.bookstore.grpc.Bookstore;
import com.stepaniuk.bookstore.testspecific.MapperLevelUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MapperLevelUnitTest
@ContextConfiguration(classes = {BookMapperImpl.class})
class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    void shouldMapBookToBookResponse() {
        // given
        Book bookToMap = new Book(UUID.randomUUID(), "title", "author","0134012",10, "description", BigDecimal.valueOf(10.0));

        // when
        Bookstore.BookResponse bookResponse = bookMapper.toResponse(bookToMap);

        // then
        assertNotNull(bookResponse);
        assertEquals(bookToMap.getId(), UUID.fromString(bookResponse.getId()));
        assertEquals(bookToMap.getTitle(), bookResponse.getTitle());
        assertEquals(bookToMap.getAuthor(), bookResponse.getAuthor());
        assertEquals(bookToMap.getIsbn(), bookResponse.getIsbn());
        assertEquals(bookToMap.getQuantity(), Integer.valueOf(bookResponse.getQuantity()));
        assertEquals(bookToMap.getDescription(), bookResponse.getDescription());
        assertEquals(bookToMap.getPrice(), BigDecimal.valueOf(bookResponse.getPrice()));
    }
}
