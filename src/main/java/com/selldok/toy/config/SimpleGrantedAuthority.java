package com.selldok.toy.config;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

/**
 * @author Incheol Jung
 */
@Getter
public class SimpleGrantedAuthority implements GrantedAuthority {

    private ROLE role;

    public SimpleGrantedAuthority(ROLE role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
