package com.vti.service;

import com.vti.dto.authen.TokenRefreshResponse;
import com.vti.entity.Account;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IJWTTokenService {
    void addJWTTokenToHeader(HttpServletResponse response, String username) throws IOException;

    Authentication parseTokenToUserInformation(HttpServletRequest request);

    boolean isValidRefreshToken(String refreshToken);

    String createNewRefreshToken(Account account);

    TokenRefreshResponse refreshToken(String refreshToken);
}
