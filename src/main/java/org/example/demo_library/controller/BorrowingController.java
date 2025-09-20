package org.example.demo_library.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo_library.domain.dto.BorrowingRecordDto;
import org.example.demo_library.repository.BorrowingRecordRepository;
import org.example.demo_library.service.LendingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrowing")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final LendingService lendingService;

    private Long currentUserId() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return (Long) a.getPrincipal();
    }

    // 借書
    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@RequestBody Map<String, Object> req) {
        Long inventoryId = Long.valueOf(req.get("inventoryId").toString());
        lendingService.borrow(currentUserId(), inventoryId);
        return ResponseEntity.ok(Map.of("message","BORROWED","inventoryId", inventoryId));
    }

    // 還書
    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody Map<String, Object> req) {
        Long inventoryId = Long.valueOf(req.get("inventoryId").toString());
        lendingService.returnBook(currentUserId(), inventoryId);
        return ResponseEntity.ok(Map.of("message","RETURNED","inventoryId", inventoryId));
    }

    // 查看借閱記錄
    @GetMapping("/records")
    public List<BorrowingRecordDto> records(@RequestParam Long userId,
                                            @RequestParam(defaultValue = "true") boolean onlyOpen) {
        return borrowingRecordRepository.findDtosByUserId(userId, onlyOpen);
    }
}
