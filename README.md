# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 요구사항 1 - GET 요청
### HTTP GET 요청에 대한 RequestLine을 파싱한다.
- 파싱하는 로직 구현을 TDD로 구현한다.
- 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
  - method는 GET
  - path는 /users
  - protocol은 HTTP
  - version은 1.1
## 요구사항 2 - POST 요청
### HTTP POST 요청에 대한 RequestLine을 파싱한다.
- 파싱하는 로직 구현을 TDD로 구현한다.
- 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
  - method는 POST
  - path는 /users
  - protocol은 HTTP
- version은 1.1
## 요구사항 3 - Query String 파싱

### HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.

- 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
- 파싱하는 로직 구현을 TDD로 구현한다.

### Query String 예 - GET 요청

- GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1

### 요구사항 4 - enum 적용(선택)

- HTTP method인 GET, POST를 enum으로 구현한다.

## STEP2

### 기능 요구사항 1

http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다. HTTP Request Header 예

```
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```

### 기능 요구사항 2

- “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.

- 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.

```
/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```

### 기능 요구사항 3

http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

```
POST /user/create HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 59
Content-Type: application/x-www-form-urlencoded
Accept: */*

userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```

### 기능 요구사항 4

- “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다.
- 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

### 기능 요구사항 5

- “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면
  /user/login_failed.html로 이동해야 한다.

- 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.
- 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

```
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
Cookie: logined=true
```

```
HTTP/1.1 200 OK
Content-Type: text/html
Set-Cookie: logined=true; Path=/
```

### 기능 요구사항 6

- 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다.
- 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.

- 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

### 기능 요구사항 7

- 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

- HTTP Request Header 예

```
GET ./css/style.css HTTP/1.1
Host: localhost:8080
Accept: text/css,*/*;q=0.1
Connection: keep-alive

```

## STEP4

### 요구사항

서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다. HttpSession API 중 구현할 메소드는 getId(), setAttribute(String name, Object value),
getAttribute(String name), removeAttribute(String name), invalidate() 5개이다. HttpSession의 가장 중요하고 핵심이 되는 메소드이다.

각 메소드의 역할은 다음과 같다.

- String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
- void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
- Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
- void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
- void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제

세션은 클라이언트와 서버 간에 상태 값을 공유하기 위해 고유한 아이디를 활용하고, 이 고유한 아이디는 쿠키를 활용해 공유한다. 여기서 힌트를 얻어 세션을 구현해 보자.

# step5

현재 구현되어 있는 웹 애플리케이션 서버(이하 WAS)는 사용자 요청이 있을 때마다 Thread를 생성해 사용자 요청을 처리한다. WAS가 이와 같이 처리할 경우 다음과 같은 문제가 발생할 가능성이 있다.

사용자 요청이 있을 때마다 Thread를 생성해야 하기 때문이다. Thread 생성 비용이 발생해 성능이 떨어진다. 동시 접속자가 많아질 경우 WAS가 메모리 자원의 한계, Context Switching 비용의
증가로 다운될 가능성이 높다. WAS는 많은 동시 접속자를 처리하는 것도 중요하지만 동시 접속자가 많더라도 다운되지 않으면서 안정적으로 서비스하는 것이 더 중요하다. WAS에 Thread Pool을 적용해 안정적인
서비스가 가능하도록 한다.

Java에서 기본으로 제공하는 ThreadPoolExecutor를 활용해 ThreadPool 기능을 추가한다.

최대 ThradPool의 크기는 250, 모든 Thread가 사용 중인(Busy) 상태이면 100명까지 대기 상태가 되도록 구현한다.
