package com.vti.service;

import com.vti.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IAccountService extends UserDetailsService {
    Account getAccountByUsername(String username);

    Account findAccountByEmail(String email);

    void activeUser(String token);

    List<Account> getAllAccounts();

    void createAccount(Account account);
}
