package com.selldok.toy.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

/**
 * @author Incheol Jung
 */
@Component
public class SelldokAuthenticationProvider implements AuthenticationProvider {

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        LoginInfo loginInfo = new LoginInfo(token.getPrincipal().toString(), token.getCredentials().toString());
        if(!loginInfo.getEmail().equals("incheol") || !loginInfo.getPassword().equals("12345")){
            throw new BadCredentialsException("InvalidLoginInfo");
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
