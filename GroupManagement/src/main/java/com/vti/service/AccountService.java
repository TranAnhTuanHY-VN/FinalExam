package com.vti.service;

import com.vti.entity.Account;
import com.vti.entity.AccountStatus;
import com.vti.entity.authen.RegistrationAccountToken;
import com.vti.config.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.vti.repository.IAccountRepository;
import com.vti.repository.RegistrationAccountTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

// như kiểu cái này nhé nó có @Service đúg kk là nó sẽ đưa vào spring iOc
// kiểu nó quản lý hộ mình ấy thì khi dùng thằng khác có @autowired thì bản thân cx phải do spring quản lý
//
@Transactional
@Service    // k có cái này thì cái repo autowired nó k hiểu đây có phải class của spring k để nhúng code vào
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository repository;
    // sang ben jwt trc
    @Autowired
    private RegistrationAccountTokenRepository registrationAccountTokenRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${registration.token.expired-time}")    // gần giống cái này nhưng cái này nó chỉ lấy theo cả 1 key thôi
    private long registrationPasswordTokenExpiredTime;


    @Override
    public List<Account> getAllAccounts() {
        return repository.findAll();
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

        // xong nó xóa luôn đây này tại vì mình cx k cần dùng lại
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

    @Override
    public void createAccount(Account account) {
        // create user
        account.setPassword(passwordEncoder.encode(account.getPassword())); // cái password nãy
        repository.save(account);

        // create new user registration token
        createNewRegistrationUserToken(account);

        // send email to confirm
        sendConfirmUserRegistrationViaEmail(account.getEmail());
    }

    private void createNewRegistrationUserToken(Account account) {

        // create new token for confirm Registration
        final String newToken = UUID.randomUUID().toString();
        RegistrationAccountToken token = new RegistrationAccountToken(newToken, account, registrationPasswordTokenExpiredTime);

        registrationAccountTokenRepository.save(token);
    }

    private void sendConfirmUserRegistrationViaEmail(String email) {
        eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
    }
}
