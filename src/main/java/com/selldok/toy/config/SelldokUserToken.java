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

    private Long id;
    private String accessToken;
    private String name;
    private String email;
    private String picUrl;
    private String authId;

    public SelldokUserToken(Long id, String accessToken, String name, String email, String picUrl, ROLE role, String authId){
        super(name, email, List.of(new SimpleGrantedAuthority(role)));
        this.id = id;
        this.accessToken = accessToken;
        this.name = name;
        this.email = email;
        this.picUrl = picUrl;
        this.authId = authId;
    }
}
