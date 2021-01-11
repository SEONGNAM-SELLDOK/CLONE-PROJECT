Feature: 회사

Background:
	* def conf = read('karate-config.js')
	* url baseUrl
	* def name = '회사이름';
	* def address = '회사 주소';

Scenario: 회사 crud

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
	
	# 회사 검색
	Given path '/company/companies'
	Given param size = '10'
	Given param companyName = '검색하고자하는 키워드'
	When method get	
	Then status 200
	And assert response.content.size() > 0

