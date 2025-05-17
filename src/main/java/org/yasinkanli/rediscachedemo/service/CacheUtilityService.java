package org.yasinkanli.rediscachedemo.service;


import org.springframework.stereotype.Service;
import org.yasinkanli.rediscachedemo.dto.BookDto;

@Service
public interface CacheUtilityService {

    void printBookFromCache(Long id);
    void putBookIntoCache(Long id, BookDto bookDto);
    void evictBookFromCache(Long id);
    void clearAllCaches();


}
