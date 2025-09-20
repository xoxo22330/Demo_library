package org.example.demo_library.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo_library.domain.Book;
import org.example.demo_library.repository.BookRepository;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository repo;

    // 分頁 + 關鍵字查詢（q：書名；author：作者）
    // 列出所有書籍()
    @GetMapping
    public Map<String,Object> list(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "") String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String sort
    ) {
        String[] s = sort.split(",", 2);
        Sort sortObj = Sort.by(Sort.Direction.fromString(s.length > 1 ? s[1] : "asc"), s[0]);
        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<Book> result = repo.findByNameContainingIgnoreCaseAndAuthorContainingIgnoreCase(q, author, pageable);
        return Map.of(
                "content", result.getContent(),
                "page", result.getNumber(),
                "size", result.getSize(),
                "totalElements", result.getTotalElements(),
                "totalPages", result.getTotalPages()
        );
    }

    // 取得指定 {isbn} 的書籍資訊
    @GetMapping("/{isbn}")
    public Book get(@PathVariable String isbn) {
        return repo.findById(isbn).orElseThrow(() -> new IllegalArgumentException("Book not found: " + isbn));
    }

    // 這條是給管理員用的，用於創建新的書本
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book b) {
        if (repo.existsById(b.getIsbn())) throw new IllegalStateException("ISBN already exists: " + b.getIsbn());
        return repo.save(b);
    }

    // 這條是給管理員用的，用於更新書籍資訊
    @PutMapping("/{isbn}")
    public Book update(@PathVariable String isbn, @RequestBody Book b) {
        Book exist = repo.findById(isbn).orElseThrow(() -> new IllegalArgumentException("Book not found: " + isbn));
        exist.setName(b.getName());
        exist.setAuthor(b.getAuthor());
        exist.setIntroduction(b.getIntroduction());
        return repo.save(exist);
    }

    // 這條是給管理員用的，用於刪除書籍
    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
        repo.deleteById(isbn); // 若有外鍵使用中會被 DB 擋下，交給 GlobalExceptionHandler 轉成 409
    }
}
