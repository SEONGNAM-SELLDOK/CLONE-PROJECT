package com.selldok.toy.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private String email;
    private String password;
}
