package org.example.demo_library.service;

import lombok.RequiredArgsConstructor;
import org.example.demo_library.domain.*;
import org.example.demo_library.repository.BookRepository;
import org.example.demo_library.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private final BookRepository bookRepo;

    @Transactional
    public Inventory addCopy(String isbn) {
        Book book = bookRepo.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + isbn));
        Inventory inv = new Inventory();
        inv.setBook(book);
        inv.setStatus(InventoryStatus.AVAILABLE);
        return inventoryRepo.save(inv);
    }

    @Transactional
    public Inventory changeStatus(Long inventoryId, InventoryStatus newStatus) {
        Inventory inv = inventoryRepo.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found: " + inventoryId));
        inv.setStatus(newStatus);
        return inventoryRepo.save(inv);
    }
}
