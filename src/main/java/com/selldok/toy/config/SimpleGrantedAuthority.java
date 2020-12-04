package com.selldok.toy.config;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Incheol Jung
 */
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
