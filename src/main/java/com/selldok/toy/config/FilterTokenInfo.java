package com.selldok.toy.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
@AllArgsConstructor
public class FilterTokenInfo {
    private String accessToken;
    private String userId;
}
