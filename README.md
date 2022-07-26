# 웹 애플리케이션 서버

## 진행 방법

* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단계 - TDD 실습

### 요구사항 1 - GET 요청

- HTTP GET 요청에 대한 RequestLine을 파싱

> GET /users HTTP/1.1  
> method는 GET  
> path는 /users  
> protocol은 HTTP  
> version은 1.1


### 요구사항 2 - POST 요청

HTTP POST 요청에 대한 RequestLine을 파싱

> POST /users HTTP/1.1  
> method는 POST  
> path는 /users  
> protocol은 HTTP  
> version은 1.1

### 요구사항 3 - Query String 파싱
- HTTP 요청(request)의 Query String으로 전달되는 데이터 파싱
- 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2

### 요구사항 4 - enum 적용
- HTTP method인 GET, POST를 enum으로 구현


## 2단계 - HTTP 웹 서버 구현


### 기능 요구사항 1

http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답

``` text
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: &ast;/&ast;
```


### 기능 요구사항 2

“회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입  
회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달

```text
/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```

HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장

```text
GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```


### 기능 요구사항 3

http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현

```text
POST /user/create HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 59
Content-Type: application/x-www-form-urlencoded
Accept: */*

userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```


### 기능 요구사항 4

- redirect 방식처럼 회원가입을 완료한 후 /index.html 페이지로 이동
  - HTTP 응답 헤더의 status code를 200이 아닌 302 code를 사용


### 기능 요구사항 5

- “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인
- 로그인이 성공하면 index.html로 이동
- 로그인이 실패하면 /user/login_failed.html로 이동
- 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지 
- 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달

```text
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
Cookie: logined=true
```

```text
HTTP/1.1 200 OK
Content-Type: text/html
Set-Cookie: logined=true; Path=/
```


### 기능 요구사항 6
- 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력
- 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동
- 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용

```text
{
  blogs: [
    title: {
      story: {
        name: "Handlebars.java rocks!"
      }
    }
  ]
}
```

```text
{{#blogs}}
  <h1>{{title}}</h1>
  {{#story}}
    <span>{{name}}</span>
  {{/story}}
{{/blogs}}
```


### 기능 요구사항 7

- Stylesheet 파일을 지원하도록 구현

```text
GET ./css/style.css HTTP/1.1
Host: localhost:8080
Accept: text/css,*/*;q=0.1
Connection: keep-alive
```



## 3단계 - HTTP 웹 서버 리팩토링

### HTTP 요청/응답 처리 기능

- HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리
  - 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리(HttpRequest)
  - 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리(HttpResponse)
  - 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거

