package org.example.demo_library.domain;

import jakarta.persistence.*;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;
import java.time.LocalDateTime;

@Entity @Getter @Setter @NoArgsConstructor
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    private Book book;

    @Column(nullable = false)
    private LocalDateTime storeTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private InventoryStatus status = InventoryStatus.AVAILABLE;
}
