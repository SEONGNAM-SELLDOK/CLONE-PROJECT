Feature: 입사지원

Background:
	* def conf = read('karate-config.js')
	* url baseUrl
	# url을 임시로 변경하고자 할 때 사용
	#* url "https://localhost:8443"
	* def name = '회사이름';
	* def address = '회사 주소';

Scenario: 지원할 회사와 공고 생성 & 입사지원 후 지원 내용 수정

	# 사용자 신규 등록 & 세션 생성
	Given path '/auth/check'
	And header Content-Type = 'application/json; charset=utf-8'
	And request 
	"""
	{"authResponse":{"accessToken":"EAADCsvIBG8oBACOFKSxCJkZAcZBZBSMniP0UnADLNZAUZAr0OFhtB0ZCyJes3xEcsShX6JhMuZBE58UKsLh2hZArtj15DyJLaJJDX8cHcU4EpZBEVNlY8W16zZBlCprAsLWxNV7OO2rnuKVg5yvbpI3QD1uJTSiiJ3otMruFzRCOZCZAuaZAAswOoPZBFqot9hHoTKsgE5HxUbGanpZBWePu17SZBgsD","userID":"100754358568341","expiresIn":6191,"signedRequest":"MIaZ4wWNyVF5rIUwF6kSrGrHJJCAIgsxv1LPsXVNpgE.eyJ1c2VyX2lkIjoiMTAwNzU0MzU4NTY4MzQxIiwiY29kZSI6IkFRQjBqdEJkT3JqUEZVbXdJWnU3VU1hVUhLR0ZnMURHYmN6Y0RiSWVnUDVGZnE4MTNkdDNfUzFhVEx3VThHRG1IWTZIaGJxNTd2RUhvanFGRlQ1aGc2REVmUUVxdFR2eE91OGp5SzZhQ091QTZTc1R0WURnaElCUFFOWFVLRlAwX1AtN2tzcld6OUpUemViR1JQSUZvR2phSDBmekVPckpLM3JKcmk4UWk5TXVlMjlJRE1FeFpqYzNpZ2Y5alBVNElQZEJISDJQSmNNWkhFUXVXYkwySGdYTGNSdU5nUHlmWkhpRXZRRDJwNjdRYTJhYmFnZThqUDNRdE9yRFFkYWZSN1Z5Q21IWmhrT1M1R0daNWhMSzV5RXZPN0pWV1BKanFEWS04ZjFsUFl0RW9tRFFEQXhTOW5xWVdUczJNRloxQnZWamhRR2sxNFVEdzl0UzV0aVI0Mm1PIiwiYWxnb3JpdGhtIjoiSE1BQy1TSEEyNTYiLCJpc3N1ZWRfYXQiOjE2MTA5NzU4MDl9","graphDomain":"facebook","data_access_expiration_time":1618751809},"status":"connected"}
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