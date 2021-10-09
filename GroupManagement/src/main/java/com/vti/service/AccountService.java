package com.vti.service;

import com.vti.entity.Account;
import com.vti.entity.AccountStatus;
import com.vti.entity.Group;
import com.vti.entity.RegistrationAccountToken;
import com.vti.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.vti.form.*;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IGroupRepository;
import com.vti.repository.RegistrationAccountTokenRepository;
import com.vti.specification.AccountSpecification;
import com.vti.specification.GroupSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Transactional
@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository repository;

    @Autowired
    private RegistrationAccountTokenRepository registrationAccountTokenRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    private void createNewRegistrationUserToken(Account account) {

        // create new token for confirm Registration
        final String newToken = UUID.randomUUID().toString();
        RegistrationAccountToken token = new RegistrationAccountToken(newToken, account);

        registrationAccountTokenRepository.save(token);
    }

    private void sendConfirmUserRegistrationViaEmail(String email) {
        eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
    }

    @Override
    public Account findAccountByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void activeUser(String token) {
        RegistrationAccountToken registrationAccountToken = registrationAccountTokenRepository.findByToken(token);

        Account account = registrationAccountToken.getAccount();
        account.setStatus(AccountStatus.ACTIVE);

        repository.save(account);

        // remove Registration User Token
        registrationAccountTokenRepository.deleteById(registrationAccountToken.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(account.getUsername(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole()));
    }

    @Override
    public Account getAccountByUsername(String username) {
        Account account = repository.findByUsername(username); // cái này nó trả null

        return account;
    }


}
