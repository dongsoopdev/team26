package com.edutech.team26.config;

import com.edutech.team26.biz.CustomUserDetailsService;
import com.edutech.team26.biz.MemberService;
import com.edutech.team26.component.LoginFailHandler;
import com.edutech.team26.component.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.lang.reflect.Field;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    private final DataSource dataSource;

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    //.requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/admin/**", "/lecture/**","/teacher/**").permitAll() //예진 테스트중
                    .requestMatchers("/", "/**","/login","/join_frm", "join_frm_u", "join_frm_t").permitAll()
                    .anyRequest().authenticated());

        http
            .formLogin((formLogin) ->
                formLogin
                    .loginPage("/login")
                    .failureUrl("/login/error")
                    .defaultSuccessUrl("/loginPro")
            );

        http
            .csrf((csrf) ->
                csrf.disable()
            );

        http
            .logout((logout) ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
            );

        http
            .exceptionHandling((exceptionHandling) ->
                exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations());
    }

}