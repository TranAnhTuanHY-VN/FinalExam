package com.vti.config.security;

import com.vti.entity.Account;
import com.vti.service.IAccountService;
import com.vti.service.IJWTTokenService;
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

// cái này k có annotation vì nó là của servlet (spring là framework, servlet là cái cổ r ấy nên k có)
// mình muốn nhúng code thì phải cấu hình cho cái JWTAuthenticationFilter,thì dùng config before ấy
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IJWTTokenService jwtTokenService;

    public JWTAuthenticationFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        Account account = accountService.getAccountByUsername(username);
        String role = account.getRole();
        String password = request.getParameter("password");
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password,
                        AuthorityUtils.createAuthorityList(role)
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        jwtTokenService.addJWTTokenToHeader(response, authResult.getName());
    }
}
