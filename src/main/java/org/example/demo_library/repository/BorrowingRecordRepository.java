package org.example.demo_library.repository;

import org.example.demo_library.domain.dto.BorrowingRecordDto;
import org.example.demo_library.domain.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query("""
     select new org.example.demo_library.domain.dto.BorrowingRecordDto(
         r.id, inv.inventoryId, b.isbn, b.name, r.borrowingTime, r.returnTime
     )
     from BorrowingRecord r
     join Inventory  inv on r.inventory.inventoryId = inv.inventoryId
     join Book b on b.isbn = inv.book.isbn
     where r.user.userId= :userId
       and (:onlyOpen = false or r.returnTime is null)
     order by r.borrowingTime desc
     """)
    List<BorrowingRecordDto> findDtosByUserId(
            @Param("userId") Long userId,
            @Param("onlyOpen") boolean onlyOpen);
}
