# CLONE-PROJECT

## 프로젝트 목적
토이 프로젝트 용도로 특정 서비스를 클론코딩 하였습니다. 다양한 스프링 프레임워크 기술이나 인프라 환경을 경험하기 위한 목적입니다. 

### 타겟 서비스
- 원티드(https://www.wanted.co.kr)

## 개발환경
- jdk 11
- spring boot 2.4
- spring 5.x
- JPA
- hibernate
- h2 db

## 프로젝트 범위
### 기업 관련(기성)
- 기업 정보 등록
- 구직 정보 등록

### 구직자 관련(인철)
- 로그인 (SNS 연동)
- 이력서 등록

### 페이지(준혁)
- 구직 정보 보기(리스트)
- 구직 정보 상세 보기
- 이벤트(이희준)

### 이벤트 등록(희준)
- 등록된 이벤트 리스트
- 이벤트 상세 페이지

## 실행 방법

### 애플리케이션 실행

```
gradlew bootrun
```

## 확인 방법
### http Plugin 활용
```
### 구직자 조회
GET http://localhost:8080/employees

> {%
client.test("Request executed successfully", function() {
    client.log("response.body" + response.body)
    client.assert(response.status === 200, "Response status is not 200");
});
%}
```

```
### 결과
GET http://localhost:8080/employees

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 20 Nov 2020 23:54:11 GMT
Keep-Alive: timeout=60
Connection: keep-alive

[
  {
    "id": 1,
    "name": "incheol"
  }
]
```

### 데이터베이스 콘솔 활용(localhost:8080/h2-db)
![image](https://user-images.githubusercontent.com/2491418/99860795-7b358480-2bd7-11eb-916b-ffbd9b665ce8.png)


