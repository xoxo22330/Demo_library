package org.example.demo_library.repository;

import org.example.demo_library.domain.Inventory;
import org.example.demo_library.domain.InventoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 可借清單：把關聯 book 一起載入，避免 Lazy 代理被序列化
    @EntityGraph(attributePaths = "book")
    List<Inventory> findByStatus(InventoryStatus status);

    // 獲取所有庫存：把關聯 book 一起載入，避免 Lazy 代理被序列化
    @Override
    @EntityGraph(attributePaths = "book")
    List<Inventory> findAll();

    // 分頁查詢所有庫存：把關聯 book 一起載入，避免 Lazy 代理被序列化
    @EntityGraph(attributePaths = "book")
    Page<Inventory> findAll(Pageable pageable);

    long countByStatus(InventoryStatus status);
}