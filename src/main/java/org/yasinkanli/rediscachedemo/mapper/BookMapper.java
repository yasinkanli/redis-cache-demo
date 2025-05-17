package org.yasinkanli.rediscachedemo.mapper;

import org.springframework.stereotype.Component;
import org.yasinkanli.rediscachedemo.dto.BookDto;
import org.yasinkanli.rediscachedemo.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor());
    }

    public Book toEntity(BookDto dto) {
        return new Book(dto.getTitle(), dto.getAuthor());
    }

    public List<BookDto> toDtoList(List<Book> books) {
        return books.stream().map(this::toDto).collect(Collectors.toList());
    }
}