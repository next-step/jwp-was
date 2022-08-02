# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


# 단계별 요구사항
## Step 1 TDD 실습

- [x] RequestLine 파싱하기
   1. RequestLine을 파싱해 원하는 값을 가져올 수 있는 API를 제공해야 한다.
   2. RequestLine은 HTTP 요청의 첫번째 라인을 의미한다.

- [x] 요구사항 1 - GET 요청
   1. HTTP GET 요청에 대한 RequestLine을 파싱한다.
   2. 파싱하는 로직 구현을 TDD로 구현한다.
   3. 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
   4. method는 GET
   5. path는 /users
   6. protocol은 HTTP
   7. version은 1.1

- [x] 요구사항 2 - POST 요청 HTTP POST 요청에 대한 RequestLine을 파싱한다.
   1. 파싱하는 로직 구현을 TDD로 구현한다.
   2. 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
   3. method는 POST
   4. path는 /users
   5. protocol은 HTTP
   6. version은 1.1

- [x] 요구사항 3 - Query String 파싱
   1. HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
   2. 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
   3. 파싱하는 로직 구현을 TDD로 구현한다.
   4. Query String 예 - GET 요청   
      ```GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1```

- [x] 요구사항 4 - enum 적용(선택)
   1. HTTP method인 GET, POST를 enum으로 구현한다.

## Step 2 HTTP 웹 서버 구현

- [x] 기능 요구사항 1   
  - `http://localhost:8080/index.html` 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
    - HTTP Request Header 예
      ```http request
      GET /index.html HTTP/1.1
      Host: localhost:8080
      Connection: keep-alive
      Accept: */*
      ```
  
- [x] 기능 요구사항 2
  - `회원가입` 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.   
  회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
    ```http request
    /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
    ```
    HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
    - HTTP Request Header 예
      ```http request
      GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1
      Host: localhost:8080
      Connection: keep-alive
      Accept: */*
      ```
- [x] 기능 요구사항 3
  - `http://localhost:8080/user/form.html` 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
    - HTTP Request Header 예
      ```
      POST /user/create HTTP/1.1
      Host: localhost:8080
      Connection: keep-alive
      Content-Length: 59
      Content-Type: application/x-www-form-urlencoded
      Accept: */*
    
      userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
      ```
    
- [x] 기능 요구사항 4
  - “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다.    
  따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

- [x] 기능 요구사항 5
  - “로그인” 메뉴를 클릭하면 `http://localhost:8080/user/login.html` 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.   
앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.
  - HTTP Request Header 예
    ```http request
    GET /index.html HTTP/1.1
    Host: localhost:8080
    Connection: keep-alive
    Accept: */*
    Cookie: logined=true
    ```
  - HTTP Response Header 예
    ```
    HTTP/1.1 200 OK
    Content-Type: text/html
    Set-Cookie: logined=true; Path=/
    ```

- [x] 기능 요구사항 6
  - 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.   
동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

- [ ] 기능 요구사항 7
  - 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
    - HTTP Request Header 예
      ```
      GET ./css/style.css HTTP/1.1
      Host: localhost:8080
      Accept: text/css,*/*;q=0.1
      Connection: keep-alive
      ```
## Step 4 - 세션 구현하기

- 요구사항
  - [x] 서블릿에서 지원하는 HttpSession API의 일부 기능 구현
    - [x] String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
    - [x] void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
    - [x] Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
    - [x] void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
    - [x] void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
- 힌트
  - SessionID 생성
    - ```java UUID uuid = UUID.randomUUID();```
  - 세션 관리를 위한 자료구조
    - ex: Map<String, HttpSession> 
    - SpringContextHolder 개념도 차용해볼만하다.

## Step 5 - Thread Pool 적용
- 요구사항
  - [x] Java에서 기본으로 제공하는 ThreadPoolExecutor를 활용해 ThreadPool 기능을 추가한다.
  - [x] 최대 ThradPool의 크기는 250, 모든 Thread가 사용 중인(Busy) 상태이면 100명까지 대기 상태가 되도록 구현한다.
  - [x] Spring에서 제공하는 RestTemplate을 활용해 서버의 ThreadPool 수보다 많은 요청을 동시에 보내본다.
    - [x] 서버의 최대 Thread Pool 수를 5로 설정하고, ExecutorsTest의 Executors.newFixedThreadPool(10)과 같이 설정해 동시에 10개의 요청이 발생하도록 구현
