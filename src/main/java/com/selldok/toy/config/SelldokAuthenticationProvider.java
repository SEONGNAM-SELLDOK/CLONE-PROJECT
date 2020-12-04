package com.selldok.toy.config;

import com.selldok.toy.employee.model.FaceBookTokenResponse;
import com.selldok.toy.employee.service.AuthService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author Incheol Jung
 */
@Component
@RequiredArgsConstructor
public class SelldokAuthenticationProvider implements AuthenticationProvider {

    private final AuthService authService;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        String accessToken = token.getPrincipal().toString();
        FaceBookTokenResponse response = authService.validateToken(accessToken);
        boolean isExistMember = authService.checkUserInfo(response.getEmail());
        ROLE currentRole = isExistMember ? ROLE.REGULAR : ROLE.BASIC;
        Authentication newToken = new UsernamePasswordAuthenticationToken(response.getEmail(),
                                                                          response.getId(),
                                                                          List.of(new SimpleGrantedAuthority(currentRole)));
        newToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(newToken);
        return newToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
