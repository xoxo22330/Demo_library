package org.example.demo_library.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class TokenResponse {
    private String token;
    private long   expiresIn; // 秒
}
