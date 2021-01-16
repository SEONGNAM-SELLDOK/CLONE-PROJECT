Feature: 입사지원

Background:
	* def conf = read('karate-config.js')
	# url을 임시로 변경하고자 할 때 사용
	#* url "https://localhost:8443"
	#* url "http://localhost:9090"
	* def name = '회사이름';
	* def address = '회사 주소';

Scenario: 지원할 회사와 공고 생성 & 입사지원 후 지원 내용 수정

	# 사용자 신규 등록 & 세션 생성
	Given path '/auth/check'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
	{"authResponse":{"accessToken":"EAADCsvIBG8oBAAnFzdmZAurFjm0lB9Wr54Mze95kT3mB9lNucqM8uO3l5N0OSf8xxHmRu1iGsB2TkhscV4iTnQCOnguvf3xx7TwVohjhmdPvjkno6GroQ6MvJOGIlFwIWGBYVubSKPW3VaTmHR0HCEzVJi8NUkc6fIM2ZBCEbSrntU3tUkSnIuK0xVxKbIRV6cdWgjxAmxCr19JNLT","userID":"100754358568341","expiresIn":7050,"signedRequest":"uCL4rQs_XfnLa1A3sok-1zsjt7H31iaSziEF1kzsqiM.eyJ1c2VyX2lkIjoiMTAwNzU0MzU4NTY4MzQxIiwiY29kZSI6IkFRRHFJMEN6VXZ5REl2MGFlaEhBa2FJWXZOWTFUemhEZ09fSWE3empqeENTSlhQTDFocUZCZW01SlU5ckNMOTRHSGVWSUpKYTFpX0o2aHlrTk9oakUyMG1Bd25pSlR3OVJfZk1HclQwUWtGY21RUGNXd2NFX09aV2NpT0pRcHBoLXdJQmVzZktnR3J0Tml2N0o2U3pham9WTF9NZUlSeTdPM2UydGsxOEFpakNLZ3RxTmJBMXJRVVc0d1FDVTV1OU1samVDdDFtbEdkSVp0eEVMRTluMFhyYnVOczE2cnFwNzBYWURLT3lvbnYwcnUyNV80aUhXcU9Bd2FJcU9paUNFdmN1dmlTQV90cklwNE1adlBWeUlfUWlxOHR3SnpQMjJvNXVWalhxekhPSXVFSDY3Y1h6NWlhR0l4SEUtT294UmtPMjdCaHFPV1pGbEM5dFdfenNtN0JWIiwiYWxnb3JpdGhtIjoiSE1BQy1TSEEyNTYiLCJpc3N1ZWRfYXQiOjE2MTA3OTEzNTB9","graphDomain":"facebook","data_access_expiration_time":1618567350},"status":"connected"}
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
	#Given path '/company/' + company_id
	#When method get	
	#Then status 200
	#And assert response.id == company_id
	#* def applicantId = response.representative.id;
	#* def representativeId = response.representative.id;

	# 깨끗한 db에서 테스트 한다고 가정한다. 그래서 신규 등록된 구직자와 대표자의id 는 1이다. 혹은 방금 등록한 회사를 조회하면 가져올 수 있다.
	* def applicantId = 1;
	* def representativeId = applicantId;

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