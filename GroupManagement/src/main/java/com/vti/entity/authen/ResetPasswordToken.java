package com.vti.entity.authen;

import com.vti.entity.Account;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ResetPasswordToken")
@NoArgsConstructor
public class ResetPasswordToken extends Token {
    public ResetPasswordToken(String token, Account account, long expiryTime) {
        super(token, account, expiryTime);
    }
}
