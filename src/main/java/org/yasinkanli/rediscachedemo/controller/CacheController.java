package org.yasinkanli.rediscachedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yasinkanli.rediscachedemo.dto.BookDto;
import org.yasinkanli.rediscachedemo.service.impl.CacheUtilityServiceImpl;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheUtilityServiceImpl cacheUtilityService;

    @GetMapping("/print/{id}")
    public void printBookFromCache(@PathVariable Long id) {
        cacheUtilityService.printBookFromCache(id);
    }

    @PutMapping("/put/{id}")
    public void putBookIntoCache(@PathVariable Long id, @RequestBody BookDto bookDto) {
        cacheUtilityService.putBookIntoCache(id, bookDto);
    }

    @DeleteMapping("/evict/{id}")
    public void evictBookFromCache(@PathVariable Long id) {
        cacheUtilityService.evictBookFromCache(id);
    }

    @DeleteMapping("/clear-all")
    public void clearAllCaches() {
        cacheUtilityService.clearAllCaches();
    }
}