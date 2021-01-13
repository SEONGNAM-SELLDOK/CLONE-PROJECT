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

Scenario: 입사 지원

	# 지원하기에서 오류 발생시키기 위해 일부러 없는 구인광고 식별자(999)를 넣음
	Given path '/employees/1/applyHistories'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
		{
			"employmentBoardId": 999
		}
	"""		
	When method post
	Then status 404
	And assert response.errorCode == 'ERR001'

	# 지원 수정 하기에서 오류 발생시키기 위해 일부러 없는 구직자 식별자(999)를 넣음
	Given path '/employees/999/applyHistories/1'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
		{}
	"""		
	When method put
	Then status 404
	And assert response.errorCode == 'ERR002'
