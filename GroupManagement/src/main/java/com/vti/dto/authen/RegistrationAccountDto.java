package com.vti.dto.authen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAccountDto {
    private String userName;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String role;
}
