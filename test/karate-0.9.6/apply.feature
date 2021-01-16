Feature: 입사지원

Background:
	* def conf = read('karate-config.js')
	# 김동석 개발환경에서 ssl쓰지 않으면 로그인 안되는 문제 발생하여 https 세팅함
	* url "https://localhost:8443"
	* def name = '회사이름';
	* def address = '회사 주소';

Scenario: 지원할 회사와 공고 생성 & 입사지원 후 지원 내용 수정

	# 사용자 신규 등록 & 세션 생성
	Given path '/auth/check'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
	{
	"authResponse": {
		"accessToken": "EAADCsvIBG8oBAFLTCgrTSbu7D4EGkKVjPSfd41QwEaKmpJXeHewPLLbOKGBzg5IIk9ZCFYuJRtywSQQplVYW9AiPM4ZBX2iqXtTaJLkEZCsBLwY8iFwSbkq310GFuy8dlkrgMZA7Kh1I9T8ByXLk6ZCAOC47ZBgyJvQcqqqB0lpVYVT1LZAbKOtBQPZAzL0OSqTrTa6SaXqSiRZCXgdXgQ0y0",
		"userID": "100754358568341",
		"expiresIn": 5759,
		"signedRequest": "Rx2Fpt3uES-pYU5xL7rDoTX_ZxsJ317pPsgIT78Gytk.eyJ1c2VyX2lkIjoiMTAwNzU0MzU4NTY4MzQxIiwiY29kZSI6IkFRRHhzY2VMTTl4TkJ4b0VIRGkyVDlQUTBpYXNYRWROVXZ3ZU5JQzg5eXpwdFJSRHN5d000dm0yWUFfenE1SnpZXzdKS2RzMGVaZGRuVjdKVVYyV19QaDB2aGtMTVVwSmlYUGdJYlBCTHhRQnp4WWgyd2JjeEE0ZEhSdDIwSXJkTUNiczNiVzV1WXliVFNUcGhmRjVHdTA2U0Vmemg3bGg5b2JrSmNwWWg2bWdEd3ZCY3dCaDZzc29yWnpJek9Ub3gwQkFNSmlzMGpicEdGWWRpVE9aX3dWalVYalVIeG9JWWRudTZkVVNZdG1xczc2eUpZSTNDSTh1M2lNZ29XR0lzdTlMVVpScTJSZHFBeTg5MXo0d2hjTkpIWGZCeWFQZmU5cU5MSjFLSFRuSE81dlhQZ0I0WmVGT2RmWjNwTUYyS0dLRDN0NlN3cG9fMG0tbVM2RFJtTUVkIiwiYWxnb3JpdGhtIjoiSE1BQy1TSEEyNTYiLCJpc3N1ZWRfYXQiOjE2MTA3NzQ2NDF9",
		"graphDomain": "facebook",
		"data_access_expiration_time": 1618550641
	},
	"status": "connected"
	}
	"""
	When method post
	Then status 200

	# 회사 등록
	Given path '/company'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
	{
		"name" : "#(name)"
		,"address" : "#(address)"
		,"businessNum" : "businessNum"
		,"totalSales" : "totalSales"
		,"country" : "country"
		,"city" : "city"
		,"street" : "street"
		,"employees" : 99
		,"info" : "info"
		,"email" : "1@2.3"
		,"since" : "since"
		,"phone" : "phone"
		,"homepage" : "homepage"
		,"terms" : true

	}
	"""		
	When method post
	Then status 200
	And assert response.company_id >= 1
	* def company_id = response.company_id
	
	# 회사 조회
	Given path '/company/' + company_id
	When method get	
	Then status 200
	And assert response.id == company_id
	* def applicantId = response.representative.id;
	* def representativeId = response.representative.id;
	
	# 구인 공고 추가
	Given path '/board/add'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
	{
		"title" : "제목"
		,"content" : "내용"
		,"image" : "썸네일 이미지"
		,"endDate" : "2022-01-16"
		,"companyId" : "#(company_id)"
	}
	"""	
	When method post
	Then status 201
	And assert response.board_id > 0
	* def board_id = response.board_id

	### 입사지원 하기
	Given path '/employees/' + applicantId + '/applyHistories'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
	{
		"name" : "지원자명",
		"email" : "applicant@naver.com",
		"phoneNumber" : "010-1111-2222",  
		"status": "APPLCN_COMPT",
		"employmentBoardId": #(board_id)
	}
	}
	"""
	When method post
	Then status 200
	* def applyHistory_id = response

	# 회사의 지원 목록에 지원 이력 id가 있는가?
	Given path '/company/' + company_id + '/applyHistories'
	When method get	
	Then status 202
	#And match response[*].id contains applyHistory_id
	And assert response[0].id == applyHistory_id

	# 관리자의 회사의 지원 목록에 지원 이력 id가 있는가?
	Given path 'employees/' + representativeId + '/company/applyHistories'
	When method get	
	Then status 202
	#And match response[*].id contains applyHistory_id
	And assert response[0].id == applyHistory_id

	### 입사지원 수정하기
	Given path '/employees/' + applicantId + '/applyHistories/' + applyHistory_id
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
	{
	"name" : "지원자명 수정",
	"email" : "applicant@naver.com 수정",
	"phoneNumber" : "010-1111-2222 수정",  
	"status": "APPLCN_COMPT",
	"employmentBoardId": #(board_id)
	}
	}
	"""
	When method put
	Then status 202