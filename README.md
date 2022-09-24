# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# 2단계 - HTTP 웹 서버 구현
## 기능 요구사항1
> http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

```text
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```
- [X] Header의 첫 번째 라인에서 요청 URL 추출.

## 기능 요구사항2
> “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.

```text
// 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```

> HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
#### HTTP Request Header 예
```text
GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```
- [X] 추출한 요청 URL에서 `접근 경로와 이름=값`을 추출하여 User 클래스에 담는다.
- [X] 단위 테스트를 진행하며 개발한다.

## 기능 요구사항3
> http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

#### HTTP Request Header 예
```text
POST /user/create HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 59
Content-Type: application/x-www-form-urlencoded
Accept: */*

userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```
- [X] form.html 파일의 form 태그 method를 get에서 post로 수정한다.
- [X] 회원가입 기능이 정상적으로 동작하도록 구현한다.

## 기능 요구사항4
> “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다.  
> 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다.  
> 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다.  
> 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

- [X] 회원가입 완료 시 302 Found StatusCode를 이용하여 index.html로 redirect 한다.

## 기능 요구사항5
> “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다.  
> 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.  
> 
> 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다.  
> 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.  
> 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.  

#### HTTP Request Header 예
```text
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
Cookie: logined=true
```

#### HTTP Response Header 예
```text
HTTP/1.1 200 OK
Content-Type: text/html
Set-Cookie: logined=true; Path=/
```

- [X] 회원가입, 로그인 기능을 분리한다.
- [X] Cookie 기능을 구현한다.
- [X] 요청, 응답 책임을 추상화한다.

## 기능 요구사항6
> 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우  
> http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다.  
> 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.

> 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

- [ ] index.html 에서 사용자 목록 페이지에 접근할 수 있도록 한다.
- [ ] Handlebars.java template engine을 활용한다.
- [ ] 사용자가 로그인 한 상태일 경우 사용자 목록 조회 기능을 구현한다.
- [ ] 사용자가 로그인 하지않은 상태일 경우 로그인 페이지로 이동한다.

## 기능 요구사항7
> 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다.  
> Stylesheet 파일을 지원하도록 구현하도록 한다.  

#### HTTP Request Header 예
```text
GET ./css/style.css HTTP/1.1
Host: localhost:8080
Accept: text/css,*/*;q=0.1
Connection: keep-alive
```
