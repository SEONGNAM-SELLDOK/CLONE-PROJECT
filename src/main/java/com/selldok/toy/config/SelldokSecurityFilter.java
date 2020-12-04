package com.selldok.toy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selldok.toy.employee.model.AuthCallBackRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Incheol Jung
 */
public class SelldokSecurityFilter extends AbstractAuthenticationProcessingFilter {

    public SelldokSecurityFilter(String loginUrl, AuthenticationManager authenticationManager,
                                 AuthenticationFailureHandler failureHandler) {
        super(new AntPathRequestMatcher(loginUrl, "POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
        AuthenticationException,
        IOException {
        AuthCallBackRequest authCallBackRequest = new ObjectMapper().readValue(request.getInputStream(), AuthCallBackRequest.class);


        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(authCallBackRequest.getAuthResponse().getAccessToken(),
                                                                                                  authCallBackRequest.getAuthResponse().getUserID());
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication){
        try {
            OutputStream ostr = response.getOutputStream();
            ObjectMapper om = new ObjectMapper();
            String returnStr = om.writeValueAsString(authentication);
            ostr.write(returnStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}

