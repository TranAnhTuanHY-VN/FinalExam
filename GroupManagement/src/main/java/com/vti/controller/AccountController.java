package com.vti.controller;

import com.vti.dto.AccountDto;
import com.vti.dto.GroupDto;
import com.vti.entity.Account;
import com.vti.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.function.Function;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/accounts")
@Validated
public class AccountController {

	@Autowired
	private IAccountService accountService;

//	@PostMapping()
//	public ResponseEntity<?> createUser(@Valid @RequestBody AccountDto dto) {
//
//		// create User
//		accountService.createAccount(dto.toEntity());
//
//		return new ResponseEntity<>("We have sent 1 email. Please check email to active account!", HttpStatus.OK);
//	}

	@GetMapping("/activeUser")
	public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {

		// active user
		accountService.activeUser(token);

		return new ResponseEntity<>("Active success!", HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<?> getAllUsers(
	) {
		return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
	}

}
