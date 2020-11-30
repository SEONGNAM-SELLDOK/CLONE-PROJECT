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
public class AuthCallBackBody {
    private String accessToken;
    private String expiresIn;
    private String signedRequest;
    private String userId;
}
