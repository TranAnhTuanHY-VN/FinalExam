package com.vti.service;

import com.vti.entity.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class JWTTokenService {
    // h cai nay chuyen het vao config r thì load ra thôi
    @Value("${jwt.token.expired-time}")
    private long EXPIRATION_TIME;

    @Value("${jwt.token.secret}")
    private String SECRET;

    @Value("${jwt.token.prefix}")   // đáy tự có    a hqua cx fix mãi sm ra nhiều logic
    private String PREFIX_TOKEN;

    @Value("${jwt.token.authorization}")
    private String AUTHORIZATION;

    @Autowired
    private IAccountService accountService; // cái file thì cx đơn giản thôi chủ yếu là đg dẫn là chính

    public void addJWTTokenToHeader(HttpServletResponse response, String username) {
        Account account = accountService.getAccountByUsername(username);
        // dùng hàm thường thôi k mấy cái kia static theo k tiện
        // ở đây xử lý với accountService lấy cái role ra
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        // dùng thêm dto trả ra body request login kia để xem cái role

        response.addHeader(AUTHORIZATION, PREFIX_TOKEN + " " + JWT);
        // cái jwt nó chưa lquan
    }

    public Authentication parseTokenToUserInformation(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        // cái này nó token để parse ra username k đúg nên repo nó k ra role thì nó qua đc nên lỗi
        // role kia null thì là k có list role nó qa đc ấy chắc postman kia chỉnh sai

        if (token == null) {
            return null;
        }
        // test thử account khác
        // parse the token
        String username = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(PREFIX_TOKEN, ""))
                .getBody()
                .getSubject();
        String role = accountService.getAccountByUsername(username).getRole();
        // lại dùng service lấy cái role ra
        return username != null ?   // chưa sửa
                new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.createAuthorityList(role)) :
                null;
    }
}