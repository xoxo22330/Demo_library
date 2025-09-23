package org.example.demo_library.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo_library.domain.InventoryStatus;
import org.example.demo_library.domain.dto.InventoryView;
import org.example.demo_library.repository.InventoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository repo;

    @GetMapping("/available")
    public List<InventoryView> available() {
        return repo.findByStatus(InventoryStatus.AVAILABLE)
                .stream()
                .map(inv -> new InventoryView(
                        inv.getInventoryId(),
                        inv.getBook().getIsbn(),
                        inv.getBook().getName(),
                        inv.getStatus().name()
                ))
                .toList();
    }
}

