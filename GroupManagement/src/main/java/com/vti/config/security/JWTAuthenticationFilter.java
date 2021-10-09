package com.vti.config.security;

import com.vti.entity.Account;
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

// cái này k có annotation vì nó là của servlet (spring là framework, servlet là cái cổ r ấy nê k có)
// mình muốn nhúng code thì phải cấu hình cho cái JWTAuthenticationFilter đúg k thì mình dùng config before ấy
// cái này cx thế thôi
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private IAccountService accountService;

    @Autowired  // tự thêm interface nhé
    private JWTTokenService jwtTokenService;

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
        Account account = accountService.getAccountByUsername(username);
        String role = account.getRole();
        String password = request.getParameter("password");
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password,
                        AuthorityUtils.createAuthorityList(role)
                        //Collections.emptyList()
                        // h cần trả thêm data cho nó mỗi cái token kia thì chưa đủ
                )
        );
    }

    @Override
    protected void successfulAuthentication(    // hàm trên nó thành user r nhưng mình ghép thêm thông tin thì override hàm này
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        jwtTokenService.addJWTTokenToHeader(response, authResult.getName());    // mình cx nhúng vào
    }
}
