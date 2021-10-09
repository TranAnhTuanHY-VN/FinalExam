package com.vti.entity.authen;

import com.vti.entity.Account;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("RegistrationAccountToken")    // cái này là phân biệt này lát nó lưu vào db
@NoArgsConstructor
public class RegistrationAccountToken extends Token {

    public RegistrationAccountToken(String token, Account account, long expiryTime) {
        super(token, account, expiryTime);
    }
}
