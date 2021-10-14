package com.vti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String email;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String role;
}
