package com.selldok.toy.employee.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
@ToString
public class AuthCallBackRequest {
    private String status;
    private String email;
    private AuthCallBackBody authResponse;
}
