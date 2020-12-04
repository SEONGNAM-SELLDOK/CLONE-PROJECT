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
    private Long data_access_expiration_time;
    private Long expiresIn;
    private String graphDomain;
    private String signedRequest;
    private String userID;
}
