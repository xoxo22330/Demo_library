package org.example.demo_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class LendingService {
    private final LendingSpRepository sp;
    @Transactional public void borrow(long userId, long invId) { sp.borrow(userId, invId); }
    @Transactional public void returnBook(long userId, long invId) { sp.returnBook(userId, invId); }
}
