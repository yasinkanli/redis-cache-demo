package org.yasinkanli.rediscachedemo.service;

import org.yasinkanli.rediscachedemo.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(Long id);
    BookDto addBook(BookDto dto);
    BookDto updateBook(Long id, BookDto dto);
    void clearCache();
}