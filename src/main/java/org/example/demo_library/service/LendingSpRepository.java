package org.example.demo_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository @RequiredArgsConstructor
public class LendingSpRepository {
    private final JdbcTemplate jdbc;
    public void borrow(long userId, long invId) { jdbc.update("CALL sp_borrow_book(?, ?)", userId, invId); }
    public void returnBook(long userId, long invId) { jdbc.update("CALL sp_return_book(?, ?)", userId, invId); }
}
