package com.selldok.toy.employee.service;

import com.selldok.toy.employee.dao.EmployeeRepository;
import com.selldok.toy.employee.model.FaceBookTokenResponse;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

/**
 * @author Incheol Jung
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;

    private RestTemplate restTemplate;

    public Boolean checkUserInfo(String email) {
        return employeeRepository.existsByInfoEmail(email);
    }

    public FaceBookTokenResponse validateToken(String token) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000); // 읽기시간초과, 5000ms
        factory.setConnectTimeout(3000); // 연결시간초과, 3000ms
        CloseableHttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(100) // connection pool 적용
                                                          .setMaxConnPerRoute(5) // connection pool 적용
                                                          .build();
        factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
        RestTemplate restTemplate = new RestTemplate(factory);
        String url = "https://graph.facebook.com/me/?access_token=" + token + "&fields=email,id,name,picture.width(720).height(720).as(picture)";
        FaceBookTokenResponse response = restTemplate.getForObject(url, FaceBookTokenResponse.class);

        return response;
    }
}
