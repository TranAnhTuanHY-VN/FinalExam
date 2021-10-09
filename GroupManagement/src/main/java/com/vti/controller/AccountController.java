package com.vti.controller;

import com.vti.dto.AccountDto;
import com.vti.entity.Account;
import com.vti.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/accounts")
@Validated
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody AccountDto dto) {    // lạ thật
        Account account = modelMapper.map(dto, Account.class);
        // create User
        accountService.createAccount(account);

        return new ResponseEntity<>("We have sent 1 email. Please check email to active account!", HttpStatus.OK);
    }

    @GetMapping("/activeAccount")
    public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {

        // active user  // để a vào mail bên này xem nó bị cái nhập kia k no
        accountService.activeUser(token);

        return new ResponseEntity<>("Active success!", HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers(
    ) {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

}
