package com.vti.controller;

import com.vti.dto.AccountDto;
import com.vti.dto.authen.TokenRefreshRequest;
import com.vti.dto.authen.TokenRefreshResponse;
import com.vti.entity.Account;
import com.vti.service.IAccountService;
import com.vti.service.IJWTTokenService;
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

    @Autowired
    private IJWTTokenService jwtTokenService;

    /**
     * This method refresh token
     * @param refreshToken
     * @return
     */
    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest refreshToken) {
        if (!jwtTokenService.isValidRefreshToken(refreshToken.getRefreshToken())) {
            return new ResponseEntity<>("Refresh Token is invalid!", HttpStatus.SERVICE_UNAVAILABLE);
        }

        TokenRefreshResponse newTokenDto = jwtTokenService.refreshToken(refreshToken.getRefreshToken());
        return new ResponseEntity<>(newTokenDto, HttpStatus.OK);
    }

    /**
     * This method create User
     * @param dto
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody AccountDto dto) {
        Account account = modelMapper.map(dto, Account.class);
        // create User
        accountService.createAccount(account);

        return new ResponseEntity<>("We have sent 1 email. Please check email to active account!", HttpStatus.OK);
    }

    /**
     * This method is active Account
     * @param token
     * @return
     */
    @GetMapping("/activeAccount")
    public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {

        // active user  // để a vào mail bên này xem nó bị cái nhập kia k no
        accountService.activeUser(token);

        return new ResponseEntity<>("Active success!", HttpStatus.OK);
    }

    /**
     * This method is get All Users
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> getAllUsers(
    ) {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }
}
