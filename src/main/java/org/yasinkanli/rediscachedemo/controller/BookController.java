package org.yasinkanli.rediscachedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yasinkanli.rediscachedemo.dto.BookDto;
import org.yasinkanli.rediscachedemo.service.BookService;
import org.yasinkanli.rediscachedemo.service.CacheUtilityService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CacheUtilityService cacheUtilityService;


    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDto addBook(@RequestBody BookDto dto) {
        return bookService.addBook(dto);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto dto) {
        return bookService.updateBook(id, dto);
    }

    @DeleteMapping("/clear-cache")
    public String clearCache() {
        bookService.clearCache();
        return "Cache cleared!";
    }

}
