package org.example.demo_library.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Pattern(regexp = "^09\\d{8}$", message = "手機需為 09 開頭共 10 碼")
    private String phoneNumber;

    @NotBlank
    @Size(min = 8, max = 64, message = "密碼長度需 8~64")
    private String password;

    @NotBlank
    private String userName;
}
