package com.vti.config.security;

import com.vti.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

// mình dùng thằng này vì nó có @Component spring nó đọc đc cấu hình trong class
// muốn có nhiều thawg do spring quản lý mà mình custom thì dùng @Bean
// Bean nó như cấu hình cho method còn cái Component , service là cả class
// mình mượn thằng này cấu hình hộ
@Component
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;    // như này nó hiểu đc

    @Autowired
    private IAccountService service;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder);
        // a duy new chỗ này là nó đang bị phụ thuộc ấy
        // như kiểu cái encode này nhé nếu mình đki tkhoan nó phải mã hóa đúg k
        // thì k phải mỗi chỗ này dùng mà service cx dùng
        // mình cấu hình thành Bean để các thành phần spring nó hiểu để dùng lại đc
        // class k thì nó k hiểu ất
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/v1/accounts/**").permitAll()
                .antMatchers("/api/v1/files/**").permitAll()
                .antMatchers("/api/v1/groups/**").hasAnyAuthority("Admin","Manager")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .addFilterBefore(
                        JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                        JWTAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    // cấu hình tí nó vào đây nó đọc đc thì cái service bên kia mới hiểu là của spring để nhúng code vào
    @Bean    // chỉ dùng cho method thôi
    public JWTAuthenticationFilter JWTAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter("/api/v1/login", authenticationManager());
        // cài này nó lquan sercurity thooii vứt lại bên kia
    }

    @Bean
    public JWTAuthorizationFilter JWTAuthorizationFilter() {
        return new JWTAuthorizationFilter();
    }
}