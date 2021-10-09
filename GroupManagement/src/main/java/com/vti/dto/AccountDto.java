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

    private String lastName;    // k cần fullName vì nó k có trong db k có tdung lắm

    private String role;    // thêm cái này nx để set sẵn User cho nó
}
