package org.yasinkanli.rediscachedemo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.yasinkanli.rediscachedemo.dto.BookDto;
import org.yasinkanli.rediscachedemo.service.CacheUtilityService;

@Service
public class CacheUtilityServiceImpl implements CacheUtilityService {

    private static final Logger log = LoggerFactory.getLogger(CacheUtilityService.class);

    @Autowired
    private CacheManager cacheManager;

    public void printBookFromCache(Long id) {
        Cache cache = cacheManager.getCache("book");
        if (cache != null) {
            Object value = cache.get(id, Object.class);
            if (value != null) {
                log.info("[CACHE HIT] Book with ID {} found in cache: {}", id, value);
            } else {
                log.warn("[CACHE MISS] Book with ID {} not found in cache.", id);
            }
        } else {
            log.error("[CACHE ERROR] 'book' cache does not exist.");
        }
    }

    @Override
    public void putBookIntoCache(Long id, BookDto bookDto) {
        Cache cache = cacheManager.getCache("book");
        if (cache != null) {
            cache.put(id, bookDto);
            log.info("[CACHE PUT] Book manually added to cache with ID {} → {}", id, bookDto);
        } else {
            log.warn("[CACHE PUT FAILED] 'book' cache not found.");
        }
    }

    public void evictBookFromCache(Long id) {
        Cache cache = cacheManager.getCache("book");
        if (cache != null) {
            cache.evict(id);
            log.info("[CACHE EVICT] Book with ID {} manually evicted from cache.", id);
        }
    }

    public void clearAllCaches() {
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
                log.warn("[CACHE CLEARED] Cache '{}' has been manually cleared.", cacheName);
            }
        }
    }
}
