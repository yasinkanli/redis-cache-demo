package org.yasinkanli.rediscachedemo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.yasinkanli.rediscachedemo.dto.BookDto;
import org.yasinkanli.rediscachedemo.entity.Book;
import org.yasinkanli.rediscachedemo.mapper.BookMapper;
import org.yasinkanli.rediscachedemo.repository.BookRepository;
import org.yasinkanli.rediscachedemo.service.BookService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Override
    @Cacheable(value = "books")
    public List<BookDto> getAllBooks() {
        log.info("[DB ACCESS] Fetching all books from the database.");
        List<Book> books = bookRepository.findAll();
        return bookMapper.toDtoList(books);
    }

    @Override
    @Cacheable(value = "book", key = "#id")
    public BookDto getBookById(Long id) {
        log.info("[DB ACCESS] Fetching book with ID {} from the database.", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    @CachePut(value = "book", key = "#id")
    @CacheEvict(value = "books", allEntries = true)
    public BookDto updateBook(Long id, BookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + id));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());

        Book updated = bookRepository.save(book);
        log.info("[BOOK UPDATED] ID: {}, Title: '{}', Author: '{}'", id, updated.getTitle(), updated.getAuthor());
        return bookMapper.toDto(updated);
    }

    @Override
    @CacheEvict(value = {"books", "book"}, allEntries = true)
    public BookDto addBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        book = bookRepository.save(book);
        log.info("[BOOK ADDED] Title: '{}', Author: '{}'", book.getTitle(), book.getAuthor());
        return bookMapper.toDto(book);
    }

    @Override
    @CacheEvict(value = {"books", "book"}, allEntries = true)
    public void clearCache() {
        log.warn("[CACHE CLEARED] All entries in 'books' and 'book' caches have been evicted.");
    }
}
