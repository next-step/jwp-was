# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

# 기능 요구사항 (TDD 실습)
## RequestLine 을 파싱한다.
- RequestLine 을 파싱해 원하는 값을 가져올 수 있는 API 를 제공해야 한다.
- RequestLine 은 HTTP 요청의 첫번째 라인을 의미한다.

## 요구사항 1 - GET 요청
- HTTP GET 요청에 대한 RequestLine 을 파싱한다.
- 파싱하는 로직 구현을 TDD 로 구현한다.
- 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method 는 GET
    - path 는 /users
    - protocol 은 HTTP
    - version 은 1.1

## 요구사항 2 - POST 요청
- HTTP POST 요청에 대한 RequestLine 을 파싱한다.
- 파싱하는 로직 구현을 TDD 로 구현한다.
- 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method 는 POST
    - path 는 /users
    - protocol 은 HTTP
    - version 은 1.1

## 요구사항 3 - Query String 파싱
- HTTP 요청(request)의 Query String 으로 전달되는 데이터를 파싱한다.
- 클라이언트에서 서버로 전달되는 데이터의 구조는 `name1=value1&name2=value2`와 같은 구조로 전달된다.
- 파싱하는 로직 구현을 TDD 로 구현한다.
- Query String 예 - GET 요청
```http request
GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1
```

## 요구사항 4 - enum 적용 (선택)
- HTTP method 인 GET, POST 를 enum 으로 구현한다.

# 기능 요구사항 (HTTP 웹 서버 구현)
## 요구사항 1
- http://localhost:8080/index.html 로 접속 했을 때, templates 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

## 요구사항 2
- "회원가입" 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원 가입을 진행한다. 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
```http request
/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```
- HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.

## 요구사항 3
- http://localhost:8080/user/form.html 파일의 form 태그 method 를 get 에서 post 로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

## 요구사항 4
- "회원가입"을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL 이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 "index.html"로 이동해야 한다. 즉, 브라우저의 URL 을 /index.html 로 변경해야 한다.

## 요구사항 5
- "로그인" 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html 로 이동하고, 로그인이 실패하면 /user/login_failed.html 로 이동해야 한다.
- 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie 를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이 성공할 경우 요청 header 의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false 로 전달되어야 한다.

## 요구사항 6
- 접근하고 있는 사용자가 "로그인" 상태일 경우 (Cookie 값이 logined=true) http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
- 동적으로 html 을 생성하기 위해 handlebars.java template engine 을 활용한다.

## 요구사항 7
- 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

# 기능 요구사항 (HTTP 웹 서버 리팩토링)
- WAS 기능, HTTP 요청/응답 처리, 개발자가 구현할 애플리케이션 기능이 혼재되어있는 것을 각각 역할을 분리해 재사용 가능하도록 개선한다.
- WAS 기능, HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다.

# 기능 요구사항 (세션 구현하기)
- 서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다.
- HttpSession API 중 구현할 메소드는 getId(), setAttribute(String name, Object value), getAttribute(String name), removeAttribute(String name), invalidate() 5개 이다.
- HttpSession 의 가장 중요하고 핵심이 되는 메소드이다.

각 메소드의 역할은 다음과 같다.
- String getId() : 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
- void setAttribute(String name, Object value) : 현재 세션에 value 인자로 저장되어 있는 객체 값을 찾아 반환
- Object getAttribute(String name) : 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
- void removeAttribute(String name) : 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
- void invalidate() : 현재 세션에 저장되어 있는 모든 값을 삭제

세션은 클라이언트와 서버 간에 상태 값을 공유하기 위해 고유한 아이디를 활용하고, 이 고유한 아이디는 쿠키를 활용해 공유한다.

# 기능 요구사항 (Thread Pool 적용)
## 요구사항 1
현재 구현되어 있는 웹 애플리케이션 서버(이하 WAS)는 사용자 요청이 있을 떄마다 Thread 를 생성해 사용자 요청을 처리한다. WAS 가 이와 같이 처리할 경우 다음과 같은 문제가 발생할 가능성이 있다.
- 사용자 요청이 있을 때마다 Thread 를 생성해야 하기 때문에 Thread 생성 비용이 발생해 성능이 떨어진다.
- 동시 접속자가 많아질 경우, WAS 가 메모리 자원의 한계, Context Switching 비용의 증가로 다운될 가능성이 높다.

WAS 는 많은 동시 접속자를 처리하는 것도 중요하지만 동시 접속자가 많더라도 다운되지 않으면서 안정적으로 서비스하는 것이 더 중요하다. WAS 에 Thread Pool 을 적용해 안정적인 서비스가 가능하도록 한다.

Java 에서 기본으로 제공하는 ThreadPoolExecutor 를 활용해 ThreadPool 기능을 추가한다.

최대 ThreadPool 의 크기는 250, 모든 Thread 가 사용 중인(Busy) 상태이면 100명까지 대기 상태가 되도록 구현한다.

## 요구사항 2
src/test/java 의 webserver.ExecutorsTest 는 100개의 Thread 가 동시에 실행하도록 구현한 테스트 코드이다.
Spring 에서 제공하는 RestTemplate 을 활용해 서버의 ThreadPool 수보다 많은 요청을 동시에 보내본다.

예를 들어 서버의 최대 Thread Pool 수를 5로 설정하고, ExecutorsTest 의 `Executors.newFixedThreadPool(10)`과 같이 설정해 동시에 10개의 요청이 발생하도록 구현해 본다.

## 기능 목록

### * 요청 객체
- HttpRequest 객체
  - 요청 라인, 헤더, 바디에 대한 정보를 처리한다.
  - RequestLine, Header, QueryString 을 필드로 가진다. 
  - RequestLine 객체
    - HttpMethod, Path, Protocol 객체를 필드로 가진다.
    - HttpMethod 객체
      - method 는 GET/POST/PUT/DELETE/PATCH 중 하나이다. 이외의 값은 예외가 발생한다.
    - Path 객체
      - path 와 QueryString 객체를 필드로 가진다. 
      - path는 항상 '/'로 시작한다. 그렇지 않으면 예외가 발생한다.
      - QueryString 이 없을 경우, 빈 Map 을 가진다.
        - QueryString 객체
          - Query String 을 파싱하기 위해 key 와 value 를 가진 queryStrings Map 필드를 가진다.
              - path 는 `?` 이전까지이고, 이후 `a=b&c=d` 형태라면 a와 c는 key, b와 d는 value 가 되고, 각 Query String 은 `&`로 구분된다.
              - queryStrings 필드는 비어있을 수 있다.
    - Protocol 객체
      - ProtocolType 객체와 Version 객체를 필드로 가진다. 
      - ProtocolType 은 항상 HTTP 이다. 그렇지 않으면 예외가 발생한다.
      - version 은 1.0, 1.1, 2.0 중 하나이다. 이외의 값은 예외가 발생한다.
  - Header 객체
    - header key, value Map 필드를 가진다.
    - Cookie 객체를 필드로 가진다.
      - key, value Map 을 필드로 가진다. (value => logined=false or logined=true 값을 가진다.)
  - QueryString 객체
    - HttpRequest 의 body 역할을 한다.
  
### * 응답 객체
- HttpResponse 객체 
  - 응답 프로토콜, 버전, 헤더, 바디에 대한 정보를 처리한다. 
  - StatusLine, Header, body를 필드로 가진다.
  - StatusLine 객체
    - Protocol 객체 (HttpRequest 의 RequestLine 객체 필드에 있는 Protocol 과 동일)
    - StatusCode 객체
      - 응답 코드(200, 302, 404)와 응답 메시지("OK", "Found", "Not Found")를 필드로 가진다.
  - Header 객체 (HttpRequest 의 Header 와 동일)
    - HeaderKey 인터페이스
      - GeneralHeader / RequestHeader / ResponseHeader / EntityHeader 를 구현체로 가진다.
    - HeaderValue 클래스
      - Header Key 에 대한 상수 값을 가진다.
  - body (byte[]) 필드
    - responseBody 에 대한 byte[] 를 가진다.

### * 세션 객체
- HttpSession 객체
  - 접속 브라우저 (유저) 에 대한 unique id, attribute 값을 관리한다.
  - unique id 는 UUID 를 이용하여 식별한다.
  - attribute 값은 해당 유저를 식별할 수 있는 데이터를 value 에 저장한다. (key 값을 통해 유저의 데이터를 가져올 수 있다.)
  - id 값을 가져올 수 있다.
  - attribute 값을 저장 & 조회 & 삭제 할 수 있다.
  - invalidate() 메서드를 통해 세션을 비활성화 시킬 수 있다.
    - attribute 값을 제거한 뒤, SessionDatabase 에서 해당 세션의 id 값을 제거한다.
- SessionDatabase 객체
  - 여러 접속 브라우저 (유저) 에 대한 세션 값을 저장하는 데이터베이스이다.
  - 특정 세션의 id 를 키로 가지고, 그 key 에 대한 세션을 value 로 저장한다.
  - session id 로 특정 세션을 조회할 수 있다.
  - session id 와 함께 특정 세션을 저장할 수 있다.
  - session id 로 특정 세션을 삭제할 수 있다.

### * 컨트롤러
- Controller 인터페이스
  - HttpRequest 에 대한 HttpResponse 를 반환하는 추상 메서드 / HttpRequest 와 매핑되는 지 판단하는 추상 메서드를 가진다.
  - MethodController 추상 클래스로 각 컨트롤러의 요청 path 가 같더라도 메서드가 다를 경우에 대한 처리를 담당한다.
  - StaticController 구현체
    - 정적 파일에 대한 요청 처리를 담당한다.
    - Content-Type을 text/css 값으로 응답을 준다.
    - body 는 정적 파일이 존재하는 위치의 파일을 읽은 값을 응답으로 준다. (응답 코드 : 200, 응답 메시지 : OK)
    - 해당 요청은 GET 메서드이고, 정적 파일 (".css", ".eot", ".svg", "ttf", "woff", "woff2", ".png", ".js") 로 끝나는지 판단한다.
  - TemplateController 구현체
    - html 파일에 대한 요청 처리를 담당한다.
    - Content-Type을 text/html 값으로 응답을 준다.
    - body 는 html 파일이 존재하는 위치의 파일을 읽은 값을 응답으로 준다. (응답 코드 : 200, 응답 메시지 : OK)
    - 해당 요청은 GET 메서드이고, html 파일(".html") 로 끝나는지 판단한다.
  - UserCreateController 구현체
    - 회원 가입 요청 처리를 담당한다.
    - 요청이 POST 이므로, body 에서 user 에 대한 값을 받아서 DataBase 메모리에 저장한다.
    - 이후, /index.html 로 redirect 시킨다. (응답 코드 : 302, 응답 메시지 : Found)
    - 해당 요청은 POST 메서드이고, path "/user/create" 와 같은지 판단한다.
  - UserListController 구현체
    - 현재 등록된 유저 리스트를 조회하는 처리를 담당한다.
    - /user/list 로 접근 시, Cookie 가 logined=true 라면 템플릿 엔진을 이용해서 DataBase 메모리에 저장된 유저를 모두 찾아서 html 파일을 동적으로 만들어서 응답으로 내려준다. (응답 코드 : 200, 응답 메시지 : OK)
    - /user/list 로 접근 시, Cookie 가 logined=false 라면 /user/login.html redirect 시킨다. (응답 코드 : 302, 응답 메시지 : Found)
    - 해당 요청은 GET 메서드이고, path "/user/list" 와 같은지 판단한다.
  - UserLoginController 구현체
    - 유저 로그인 처리를 담당한다.
    - 데이터 베이스에서 로그인 정보에 대한 유저를 찾지 못하거나, 찾아 왔지만 패스워드가 다를 경우 /user/login_failed.html 로 리다이렉트 시킨다.
      - Set-Cookie 값은 logined=false 로 지정한다. (모든 Path 에 적용을 위해 Path=/ 값을 추가한다.)
    - 데이터베이스에서 유저를 찾고, 패스워드까지 일치할 경우, /index.html 페이지로 리다이렉트 시킨다.
      - Set-Cookie 값은 logined=true 로 지정한다. (모든 Path 에 적용을 위해 Path=/ 값을 추가한다.)
    - 해당 요청은 POST 메서드이고, path "/user/login" 와 같은지 판단한다.

- RequestMapping 객체
  - 요청 Method, 요청 Path, path 의 file 유무로 매핑되는 컨트롤러를 찾는다.
  - 매핑 되는 컨트롤러를 찾지 못하거나 컨트롤러 처리중 path 로 인한 데이터를 읽지 못할 경우 Not Found 를 반환한다.

