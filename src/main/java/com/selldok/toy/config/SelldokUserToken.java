package com.selldok.toy.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
public class SelldokUserToken extends UsernamePasswordAuthenticationToken {

    private String name;
    private String email;
    private String picUrl;

    public SelldokUserToken(String name, String email, String picUrl, ROLE role){
        super(name, email, List.of(new SimpleGrantedAuthority(role)));
        this.name = name;
        this.email = email;
        this.picUrl = picUrl;
    }
}
