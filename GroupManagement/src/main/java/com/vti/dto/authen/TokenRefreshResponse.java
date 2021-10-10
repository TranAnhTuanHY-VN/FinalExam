package com.vti.dto.authen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class TokenRefreshResponse {
    private int id;
    private String fullName;
    private String role;
    private String token;
    private String refreshToken;
}
