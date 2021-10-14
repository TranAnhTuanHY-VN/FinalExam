package com.vti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private short id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private AccountDto creator;

    private int memberNum;

    @Data
    @NoArgsConstructor
    static class AccountDto {
        private int id;

        private String fullName;

        private String firstName;

        private String lastName;

        private String role;
    }
}
