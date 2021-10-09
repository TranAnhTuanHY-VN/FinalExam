package com.vti.login;

import com.vti.entity.Account;
import com.vti.service.AccountService;
import com.vti.service.IAccountService;
import com.vti.service.JWTTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private IAccountService accountService;

    public JWTAuthenticationFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        // author ma co cái empty list sao có role đc, phải dùng thêm service lấy cái role ra
        // tham khảo bài gitlab hôm a duy chỉ ấy
        //oke anh..để em xem lại ạ
        String username = request.getParameter("username");
        //Account account = accountService.getAccountByUsername(username);
        //String role = account.getRole();
        String password = request.getParameter("password");
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password,
                        //AuthorityUtils.createAuthorityList(role)
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        JWTTokenService.addJWTTokenToHeader(response, authResult.getName());
    }
}
