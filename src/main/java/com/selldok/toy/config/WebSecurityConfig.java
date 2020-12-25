package com.selldok.toy.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author Incheol Jung
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SelldokSecurityFailHandler failureHandler;

    private final SelldokAuthenticationProvider authenticationProvider;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // 정적 자원에 대해 Security 적용 하지 않음
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestCache().disable();

        http.csrf()
            .disable()
            .addFilterBefore(new SelldokSecurityFilter("/auth/check", authenticationManager(), failureHandler),
                             UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/auth/**")
            .permitAll()
            .antMatchers("/company/**")
            .permitAll()
            .antMatchers("/board/**")
            .permitAll()
            .antMatchers("/employees/**")
            .permitAll()
            .antMatchers("/employees", HttpMethod.POST.toString())
            .permitAll()
            .antMatchers("/")
            .permitAll()
            .antMatchers("/h2-db/**")
            .permitAll()
            .anyRequest()
            .authenticated() // 추후 로그인 적용 이후에 주석 해제할 예정
//            .permitAll()
            .and().formLogin().loginPage("/auth/view")
            .successHandler((request, response, authentication) -> redirectStrategy.sendRedirect(request, response, "/auth/main"))
//            .successForwardUrl("/auth/main")
//            .defaultSuccessUrl("/auth/main",true)
            .and()
            .logout()
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/")
            .and()
            .headers()
            .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationProvider);
    }
}
