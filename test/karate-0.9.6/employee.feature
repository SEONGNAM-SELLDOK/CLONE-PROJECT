Feature: 구직자

Background:
	* def conf = read('karate-config.js')
	* url baseUrl
	* def name = '구직자이름';
	* def email = 'user@domain.com';
	* def phoneNumber = '010-1111-2222';

Scenario: 구직자 crud

	# 구직자 등록
	Given path '/employees'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
		{
		  "name" : "#(name)"
		  ,"email" : "#(email)"
		  ,"phoneNumber" : "#(phoneNumber)"
		}
	"""		
	When method post
	Then status 202
