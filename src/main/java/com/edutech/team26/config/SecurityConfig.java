package com.edutech.team26.config;

import com.edutech.team26.biz.CustomUserDetailsService;
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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;
import java.util.stream.Stream;

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

    /*private static final String[] PERMIT_ALL_PATTERNS = new String[] {
            "/js/**",
            "/fonts/**",
            "/css/**",
            "/assets/**",
            "/clEditor/**",
            "/images/**",
    };*/

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/js/**"))
                .requestMatchers(new AntPathRequestMatcher( "/fonts/**"))
                .requestMatchers(new AntPathRequestMatcher( "/css/**"))
                .requestMatchers(new AntPathRequestMatcher( "/assets/**"))
                .requestMatchers(new AntPathRequestMatcher( "/clEditor/**"))
                .requestMatchers(new AntPathRequestMatcher( "/images/**"));
        /*return (web) -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations());*/
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        http
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers(new MvcRequestMatcher(introspector,"/")).permitAll()
                    /*.requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/teacher/**").hasAnyRole("TEACHER")
                    .requestMatchers("/student/**").hasAnyRole("STUDENT")
                    .requestMatchers("/", "/**","/login","/join_frm", "join_frm_u", "join_frm_t").permitAll()*/
                    .requestMatchers(new AntPathRequestMatcher("classpath:/static/js/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("classpath:/static/fonts/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("classpath:/static/css/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("classpath:/static/assets/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("classpath:/static/clEditor/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("classpath:/static/images/**")).permitAll()
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

}