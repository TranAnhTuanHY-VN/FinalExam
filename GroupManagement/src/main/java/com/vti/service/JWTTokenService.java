package com.vti.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vti.dto.authen.LoginInfoDto;
import com.vti.dto.authen.TokenRefreshResponse;
import com.vti.entity.Account;
import com.vti.entity.authen.RefreshToken;
import com.vti.repository.IRefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Transactional
@Service
public class JWTTokenService implements IJWTTokenService {
    @Value("${jwt.token.expired-time}")
    private long EXPIRATION_TIME;

    @Value("${jwt.token.secret}")
    private String SECRET;

    @Value("${jwt.token.prefix}")
    private String PREFIX_TOKEN;

    @Value("${jwt.token.authorization}")
    private String AUTHORIZATION;

    @Value("${jwt.refresh-token.expired-time}")
    private long REFRESH_EXPIRATION_TIME;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;


    /**
     * This method add jWt from Headers to Body
     *
     * @param response
     * @param username
     * @throws IOException
     */
    @Override
    public void addJWTTokenToHeader(HttpServletResponse response, String username) throws IOException {
        // get account info
        Account account = accountService.getAccountByUsername(username);
        // get jwt code
        String jwt = generateJWTFromUsername(username);
        String refreshToken = createNewRefreshToken(account);
        //convert account to dto
        LoginInfoDto userDto = modelMapper.map(account, LoginInfoDto.class);
        userDto.setToken(jwt);
        userDto.setRefreshToken(refreshToken);
        // convert to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userDto);
        // add to response body
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        //response.addHeader(tokenAuthorization, tokenPrefix + " " + jwt);
    }

    @Override
    public Authentication parseTokenToUserInformation(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        if (token == null) {
            return null;
        }

        token = token.replace(PREFIX_TOKEN, "");

        if (!isValidJwt(token)) {
            return null;
        }

        String username = parseJWTToUsername(token);

        Account account = accountService.getAccountByUsername(username);

        return account != null ?
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        AuthorityUtils.createAuthorityList(account.getRole())) :
                null;
    }

    @Override
    public boolean isValidRefreshToken(String refreshToken) {
        return refreshTokenRepository.existsByTokenAndExpiryDateGreaterThan(refreshToken, new Date());
    }

    @Override
    public String createNewRefreshToken(Account account) {
        //Create new refresh token
        String newToken = UUID.randomUUID().toString();
        RefreshToken token = new RefreshToken(newToken, account, REFRESH_EXPIRATION_TIME);

        //Create new token
        refreshTokenRepository.save(token);

        return newToken;
    }

    @Override
    public TokenRefreshResponse refreshToken(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken);
        Account account = refreshTokenEntity.getAccount();

        //create new token
        String newToken = generateJWTFromUsername(account.getUsername());
        String newRefreshToken = createNewRefreshToken(account);

        //Remove old refresh token if exists
        refreshTokenRepository.deleteByToken(refreshToken);
        return TokenRefreshResponse.builder().token(newToken).refreshToken(newRefreshToken).id(account.getId()).fullName(account.getFullName())
                .role(account.getRole()).build();
    }

    /**
     * Check token valid
     *
     * @param jwt
     * @return
     */
    private boolean isValidJwt(String jwt) {
        try {
            Jwts.parser().setSigningKey(createKeyFromSecretToken()).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Generate JWT from Username
     *
     * @param username
     * @return
     */
    private String generateJWTFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, createKeyFromSecretToken())
                .compact();
    }

    @SuppressWarnings("unused")
    private String generateJWTFromUsername(LoginInfoDto dto) {
        return Jwts.builder()
                .setSubject(dto.getFullName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, createKeyFromSecretToken())
                .claim("user-details", dto)
                .compact();
    }

    private String parseJWTToUsername(String token) {
        return Jwts.parser()
                .setSigningKey(createKeyFromSecretToken())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key createKeyFromSecretToken() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }
}