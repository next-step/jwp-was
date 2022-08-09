# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 구현할 기능 목록

### 1단계

#### RequestLine 파싱 하기

* GET 요청 파싱
* POST 요청 파싱
* Query String 파싱

### 2단계
* http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
* http://localhost:8080/user/form.html 로 이동후 GET 방식으로 회원가입이 가능하게 한다.
```
GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```
* http://localhost:8080/user/form.html 로 이동후 POST 방식으로 회원가입이 가능하게 한다.
```
POST /user/create HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 59
Content-Type: application/x-www-form-urlencoded
Accept: */*

userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```
* 회원가입 후 성공/실패 유무에 따라 특정 페이지로 리다이렉트 할 수 있어야 한다.
* 회원가입 후 로그인 할 수 있어야 한다. 로그인 후 쿠키를 통해 로그인 유지를 할 수 있어야 한다. 
* http://localhost:8080/user/list 에 접속시 로그인 유무에 따라 다른 처리를 할 수 있어야 한다.
* stylesheet 파일을 지원할 수 있어야 한다.

### 3단계

>각각의 역할을 분리해 재사용 가능하도록 개선한다.
>즉, WAS 기능, HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다.


### 4단계 세션 구현
* 세션 API 구현
  * getId()
  * setAttribute()
  * getAttribute()
  * removeAttribute()
  * invalidate()
* 여러 세션들을 관리할 세션 컨테이너 구현
* 요청에서 세션 쿠키 확인, 없다면 쿠키 만들어주기
* 로그인 부분을 세션으로 변경해보기

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)
