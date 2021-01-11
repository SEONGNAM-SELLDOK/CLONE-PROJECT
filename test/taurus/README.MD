# 부하 테스트 툴 선정 기준

1. 쉬운 설정
1. 가벼운 실행환경
1. 확장성, 다양한 플러그인

# Taurus 의 특징

1. 온라인 기반 테스팅 플랫폼 BlazeMeter에서 공개한 오픈소스 프로젝트. 그래서 BlazeMeter와 통합이 되어 있음. 하지만 독립적으로도 사용가능
1. python3 기반 터미널 cli
1. 쉬운 설정(Jmeter에 비해)
1. 다양한 테스트 툴 연동 지원(기본 Executor로 Jmeter를 사용). 지원 Executor목록 👉 https://gettaurus.org/docs/ExecutionSettings/#Executor-Types

# Taurus 사용

제일 간편한 방법은 Docker를 이용하는 것입니다(Docker image : https://hub.docker.com/r/blazemeter/taurus).

혹은 아래를 참고하여 설치를 진행해주세요

## 구동 환경

1. python3(추가로 pip 필요)
2. Java8 이상

윈도우 환경에서는 WSL(Windows Subsystem for Linux)로 Ubuntu를 설치하여 사용하는 것으로 가정하겠습니다.
(윈도우에서 파이썬 설치 후 사용하려면 'Microsoft Visual C++'이 필요한데 설치에 2GB 정도 추가 공간이 필요합니다. 그래서 WSL + Ubuntu로 사용하시는 것을 추천드립니다)

## 설치

아래 명령들로 파이썬과 필요 dependency 그리고 taurus를 설치합니다.

	sudo apt update
	sudo apt install python3 default-jre-headless python3-tk python3-pip python3-dev \
	  libxml2-dev libxslt-dev zlib1g-dev net-tools
	sudo python3 -m pip install bzt

## 설치 확인

아래 명령을 실행하여 blazedemo.com 에 대해서 부하테스트를 진행합니다. 진행 상황은 터미널 혹은 자동으로 뜨는 브라우저에서 확인 가능합니다. 아무 옵션 없이 실행하게 되면 기본 설정 값들로 테스트를 진행합니다.
(blazedemo.com 은 부하테스트용으로 blazemeter에서 만든 사이트입니다. 맘껏 테스트 해보셔도 됩니다)

	bzt http://blazedemo.com/ -report

## CLONE-PROJECT 에서 사용

### 설정 파일 샘플

아래 설정파일을 복사하여 test.yml 이라는 이름으로 저장합니다.
(설정 파일은 JSON 포맷도 지원합니다 👉 https://gettaurus.org/docs/ConfigSyntax/#YAML-JSON-Format-for-Config-Files)

	---
	execution:
	# 시나리오별로 프로파일을 다르게 설정할 수 있습니다.
	- scenario: webViewTests
	  # 동시 사용자 수
	  concurrency: 50
	  #ramp-up: 1m
	  #동시 사용자 수 수치까지 증가하는데 소요될 시간
	  ramp-up: 10s
	  #hold-for: 1m30s
	  # 동시 사용자 수 유지 시간
	  hold-for: 10s  
	- scenario: apiTests
	  concurrency: 50
	  #동시 사용자 수 수치까지 증가하는데 소요될 시간
	  #ramp-up: 1m
	  ramp-up: 10s
	  #hold-for: 1m30s
	  # 동시 사용자 수 유지 시간
	  hold-for: 10s
	
	scenarios:
	  # 화면 테스트 시나리오
	  webViewTests:
	    #요청 사이의 시간간격
	    think-time: 0.75
	    #브라우저 캐시 흉내내기. 기본값은 true
	    store-cache: true
	    #브라우저 쿠키 저장기능 흉내내기 . 기본값은 true
	    store-cookie: true
	    default-address: http://localhost:9090
	    #화면에 삽입된 리소스를 모두 로드 할 것인지 여부. true로 했을 때는 외부 리소스의 로딩시간에 영향을 받습니다.
	    retrieve-resources: true
	    requests:
	      - url: '/'
	      - url: '/board/list'
	      - url: '/event/search'
	  # api 테스트 시나리오
	  apiTests:
	    timeout: 5s
	    default-address: http://localhost:9090
	    #해당 시나리오 전체에 적용될 헤더
	    headers:
	      User-Agent: 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36'
	      Accept-Encoding: 'gzip, deflate, sdch'
	    requests:
	       # 신규 구인 목록
	      - url: '/newHire'
	       # 탐색
	      - url: '/board/boards'
	       # 신규 사용자 등록
	      - url: '/employees'
	        method: POST
	        #해당 리퀘스트에 적용될 헤더
	        headers:
	          Content-Type: application/json
	        body:
	          name: 'userName'
	          email: '1@2.com'
	          phoneNumber: '010-1234-5678'
	
(**_시나리오나 리퀘스트가 순차적으로 진행되지 않고 동시에 진행됩니다. 즉, 시나리오기반 기능테스트 용도로는 부적합합니다._**)

터미널에서 다음 명령을 실행합니다.

	bzt test.yml -report

자동으로 보고서 웹화면이 뜹니다

![](https://i.ibb.co/Fq9QzhJ/Execution.png)

(**_이 화면은 7일 후 자동 삭제 됩니다. 유지하시려면 BlazeMeter에 회원가입을 하셔야 합니다._**)

테스트 중 터미널 화면

<img src="https://i.ibb.co/Z1PgnD6/Screenshot-from-2021-01-09-14-45-10.png">

완료 후  터미널 화면

![](https://i.ibb.co/Rcz659m/Screenshot-from-2021-01-09-14-45-58.png)


