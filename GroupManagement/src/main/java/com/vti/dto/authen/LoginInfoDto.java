package com.vti.dto.authen;

import com.vti.entity.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class LoginInfoDto {
    private int id;
    private String fullName;
    private String role;
    private String token; // save token
    private String refreshToken;
    private AccountStatus status;
}
