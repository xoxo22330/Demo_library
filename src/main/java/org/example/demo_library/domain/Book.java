package org.example.demo_library.domain;

import jakarta.persistence.*;
import lombok.Getter; import lombok.NoArgsConstructor; import lombok.Setter;

import java.io.Serializable;

@Entity @Getter @Setter @NoArgsConstructor
public class Book  {
    @Id @Column(length = 20)
    private String isbn;

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String author;
    @Column(columnDefinition = "TEXT") private String introduction;
}
