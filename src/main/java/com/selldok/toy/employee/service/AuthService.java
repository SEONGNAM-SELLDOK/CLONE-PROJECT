package com.selldok.toy.employee.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.selldok.toy.config.SelldokUserToken;
import com.selldok.toy.repository.EmployeeRepository;
import com.selldok.toy.employee.entity.Employee;
import com.selldok.toy.employee.model.FaceBookFriend;
import com.selldok.toy.employee.model.FaceBookFriendResult;
import com.selldok.toy.employee.model.FaceBookTokenResponse;
import lombok.RequiredArgsConstructor;

/**
 * @author Incheol Jung
 */
@Service
@RequiredArgsConstructor
public class AuthService {

	private final EmployeeRepository employeeRepository;
	private final RestTemplate restTemplate;

	public Optional<Employee> findUserInfoByEmail(String email) {
		return employeeRepository.findByInfoEmail(email);
	}

	public FaceBookTokenResponse validateToken(String token) {
		String url = "/me/?access_token=" + token + "&fields=email,id,name,picture.width(720).height(720).as(picture)";
		FaceBookTokenResponse response = restTemplate.getForObject(url, FaceBookTokenResponse.class);

		return response;
	}

	public List<FaceBookFriend> findFriends() {
		List<FaceBookFriend> friends = Collections.emptyList();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof SelldokUserToken) {
			SelldokUserToken selldokUserToken = (SelldokUserToken)authentication;

			String url = "https://graph.facebook.com/" + selldokUserToken.getAuthId() + "/friends?access_token="
				+ selldokUserToken.getAccessToken();
			FaceBookFriendResult response = restTemplate.getForObject(url, FaceBookFriendResult.class);
			friends = response.getData();
		}
		return friends.size() > 3 ? friends.subList(0, 3) : friends;
	}
}
