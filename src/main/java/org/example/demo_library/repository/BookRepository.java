package org.example.demo_library.repository;

import org.example.demo_library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
    Page<Book> findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(
            String name, String author, Pageable pageable);
}
