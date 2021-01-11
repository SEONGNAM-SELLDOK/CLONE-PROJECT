# 소개

Karate는 API 자동화 테스트, Mock Servlet, 부하 테스트, UI 테스트를 지원하는 통합 테스트 프레임워크입니다. 

Cucumber의 Gherkin BDD(Behaviour-Driven Development https://cucumber.io/docs/gherkin/reference )문법을 이용해서 시나리오를 작성할 수 있어 비개발자도 쉽게 이용할 수 있습니다(라고 하지만 실제로 비개발자가 사용하기에는 쉽지 않을 듯  -.-).

assertion을 작성할 때 JSON 혹은 XML을 그대로 사용할 수 있으며 속도를 향상 시키기 위해 병렬 실행도 가능합니다.

* 홈페이지 : https://intuit.github.io/karate/

# 구동

1. Junit test suite : Maven, Gradle 을 이용하여 의존성을 추가한 후 junit 테스트를 작성하여 task형태로 실행. 혹은 Eclipse, IntelliJ 같은 IDE의 플러그인을 이용해서 실행
1. stand-alone : jar 다운로드 후 터미널에서 실행(이 방식을 기준으로 설명을 진행하겠습니다)

# 설치

1. 최신버전 https://dl.bintray.com/ptrthomas/karate/karate-0.9.6.zip 을 다운로드 받습니다(현재 최신 버전을 확인하시려면 여기를 방문해주셔요 
 https://dl.bintray.com/ptrthomas/karate )
1. 압축을 해제한 후 터미널(혹은 도스창)에서 해당 경로로 이동합니다. 아래의 모든 파일들은 이 경로에 저장합니다.

# 샘플

## 설정 파일 작성 : karate-config.js

```
function fn() {
	var config = {
		"여기에 공통 환경 변수를 넣으세요":""
		,"baseUrl": "http://localhost:9090"
	};
	return config;
}
```

## feature 파일 작성(구직자용, 회사용)

하나의 feature 파일에는 여러 개의 feature를 넣을 수 있으며 feature에는 여러 개의 시나리오를 넣을 수 있습니다.
시나리오는 step들로 구성되어 있습니다.

* employee.feature : 구직자용 피처 파일

```
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

```
* company.feature : 회사용 피처 파일
```
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

```

## 실행

터미널(혹은 도스창)에서 실행합니다.

* 리눅스 환경에서 실행 : `./karate ./employee.feature ./company.feature`
* 윈도우 환경에서 실행 : `karate.bat employee.feature company.feature`

## 결과 확인

### 터미널에서 확인
실행 후 터미널에서 결과가 간략하게 출력됩니다.
```
Karate version: 0.9.6
======================================================
elapsed:   1.23 | threads:    1 | thread time: 0.49 
features:     2 | ignored:    0 | efficiency: 0.40
scenarios:    2 | passed:     2 | failed: 0
======================================================
```

### 결과 리포트를 브라우저에서 확인
테스트가 끝나면 결과가 html로 생성됩니다. 이 파일을 브라우저로 열어서 결과를 확인합니다.

* cucumber : {압축해제 경로}/target/cucumber-html-reports/overview-features.html

![](https://i.ibb.co/Bf6Dwpc/Cucumber-Reports-Features-Overview.png)

* surefire : {압축해제 경로}/target/surefire-reports/karate-summary.html

![](https://i.ibb.co/cyR6F39/company.png)

위의 2개 리포트는 내용은 동일하고 형식만 틀립니다