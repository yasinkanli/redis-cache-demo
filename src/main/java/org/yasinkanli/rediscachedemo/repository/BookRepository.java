package org.yasinkanli.rediscachedemo.repository;

import org.yasinkanli.rediscachedemo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
