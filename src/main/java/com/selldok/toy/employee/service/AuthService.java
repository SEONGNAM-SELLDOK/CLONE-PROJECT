package com.selldok.toy.employee.service;

import com.selldok.toy.employee.dao.EmployeeRepository;

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

    public Boolean checkUserInfo(String name) {
        return employeeRepository.existsByInfoName(name);
    }

    public Boolean validateToken(String token) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000); // 읽기시간초과, ms
        factory.setConnectTimeout(3000); // 연결시간초과, ms
        CloseableHttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(100) // connection pool 적용
                                                          .setMaxConnPerRoute(5) // connection pool 적용
                                                          .build();
        factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
        RestTemplate restTemplate = new RestTemplate(factory);
        String url = "https://graph.facebook.com/me/?access_token=" + token; // 예제니까 애초에 때려박음..
        Object obj = restTemplate.getForObject(url, Object.class);
        System.out.println(obj);

        return true;
    }
}
