package com.vti.entity.authen;

import com.vti.entity.Account;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RefreshToken")
@NoArgsConstructor
public class RefreshToken extends Token {
    public RefreshToken(String token, Account account, long expiredTime) {
        super(token, account, expiredTime);
    }
}