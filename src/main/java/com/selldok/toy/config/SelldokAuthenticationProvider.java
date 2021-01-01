package com.selldok.toy.config;

import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.FaceBookTokenResponse;
import com.selldok.toy.employee.service.AuthService;
import com.selldok.toy.employee.service.EmployeeService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

/**
 * @author Incheol Jung
 */
@Component
@RequiredArgsConstructor
public class SelldokAuthenticationProvider implements AuthenticationProvider{

    private final AuthService authService;

    private final EmployeeService employeeService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        String accessToken = token.getPrincipal().toString();
        FaceBookTokenResponse response = authService.validateToken(accessToken);

        Optional<Employee> optionalEmployee = authService.findUserInfoByEmail(response.getEmail());
        ROLE currentRole = optionalEmployee.isPresent() ? ROLE.REGULAR : ROLE.BASIC;
        final Employee employee = optionalEmployee.orElseGet(() -> employeeService.insert(
                response.getName(),
                response.getEmail(),
                response.getPicture().getData().getUrl()));

        SelldokUserToken selldokUserToken = new SelldokUserToken(employee.getId(),
                                                               accessToken,
                                                               employee.getInfo().getName(),
                                                               employee.getInfo().getEmail(),
                                                               response.getPicture().getData().getUrl(),
                                                               currentRole,
            response.getId());

        SecurityContextHolder.getContext().setAuthentication(selldokUserToken);
        return selldokUserToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
