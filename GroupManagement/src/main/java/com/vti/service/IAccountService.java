package com.vti.service;

import com.vti.entity.Account;
import com.vti.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAccountService extends UserDetailsService {
    Account getAccountByUsername(String username);

    Account findAccountByEmail(String email);

    void activeUser(String token);

    List<Account> getAllAccounts();
}
