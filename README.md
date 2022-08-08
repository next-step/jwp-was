# 웹 애플리케이션 서버
- HTTP 요청과 응답을 파싱해 원하는 값을 가져올 수 있는 API를 제공해야 한다.
- 이 API는 TDD로 구현 해야 한다.

## RequestLine 파싱
- RequestLine을 파싱해 원하는 값을 가져올 수 있는 API를 제공
### 요구사항 1 - GET 요청
- [X] HTTP GET 요청에 대한 RequestLine을 파싱한다.
- [X] 파싱하는 로직 구현을 TDD로 구현한다.
- [X] "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다. 
  - method는 GET
  - path는 /users
  - protocol은 HTTP
  - version은 1.1
### 요구사항 2 - POST 요청
- [X] HTTP POST 요청에 대한 RequestLine을 파싱한다.
- [X] 파싱하는 로직 구현을 TDD로 구현한다.
- [X] "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
  - method는 POST
  - path는 /users
  - protocol은 HTTP
  - version은 1.1
### 요구사항 3 - Query String 파싱
- [X] HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
- [X] 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
- [X] 파싱하는 로직 구현을 TDD로 구현한다.
### 요구사항 4 - enum 적용(선택)
- [X] HTTP method인 GET, POST를 enum으로 구현한다.

### 피드백 구현
- [X] 인스턴스 변수 줄이기
  - [X] 관련 값을 묶어 새로운 객체로 분리
- [X] 문자열 / 원시 값을 포장한다.
- [X] 일급 콜렉션을 적용한다.
- [X] System.out.println 대신 logging 사용하기
- [X] 테스트 코드에서 given, when, then을 추가하는 것보단 빈 공백 라인으로 가독성 향상시키기.
- [X] else 예약어 사용 금지

## 웹서버 구현하기
### 기능 요구사항 1
http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
```text
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```
- [X] HTTP Request Header 출력하기
  - BufferdReader 사용
- [X] Request Line에서 Path 분리하기
  - 별도의 Util 클래스를 만들고 단위 테스트 만들어 진행
- [X] Path에 해당하는 파일 읽어 응답하기
### 기능 요구사항 2
“회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
```text
GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```
- [X] Request Parameter 추출하기
  - Header의 첫 번째 라인에서 요청 URL을 추출한다.
  - 요청 URL에서 접근 경로와 이름=값을 추출해 User 클래스에 담는다.
### 기능 요구사항 3
http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
```text
POST /user/create HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 59
Content-Type: application/x-www-form-urlencoded
Accept: */*

userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```
- [X] Request Body의 값 추출하기
  - POST method로 데이터를 전달할 경우 전달하는 데이터는 HTTP Body에 담긴다.
  - HTTP Body는 HTTP header 이후 빈 공백을 가지는 한 줄(line) 다음부터 시작한다.
  - HTTP Body에 전달되는 데이터는 GET method의 이름=값과 같다.
  - BufferedReader에서 본문 데이터는 util.IOUtils 클래스의 readData() 메서드를 활용한다. 본문의 길이는 http header의 Content-Length의 값이다.
  - 회원가입시 입력한 모든 데이터를 추출해 User 객체를 생성한다.
### 기능 요구사항 4
“회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
- [X] redirect 시켜 브라우저의 URL이 /index.html으로 변경시킨다.
  - HTTP 응답 헤더의 status code를 200이 아니라 302 code를 사용한다.
### 기능 요구사항 5
- [X] “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.
- [X] 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다.
- [X] 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.
- [X] 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

- #### HTTP Request Header 예
```text
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
Cookie: logined=true
```
- #### HTTP Response Header 예
```text
HTTP/1.1 200 OK
Content-Type: text/html
Set-Cookie: logined=true; Path=/
```
### 기능 요구사항 6
- [X] 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
- [X] 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
### 기능 요구사항 7
- [X] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
### 실습 마무리
- [X] Content-Type 처리
### 피드백 구현 사항
- [X] 변수명 대문자 시작X, 축약어 사용X
- [X] HttpHeader 클래스 만들기
- [X] HttpMethod enum 클래스 생성
- [X] RequestLine 클래스 이름 변경
- [X] userList 화면 수정

## HTTP 웹 서버 리팩토링
### WAS 요구사항
- [X] 각각의 역할을 분리해 재사용 가능하도록 개선한다.
  - WAS 기능, HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다.
- [X] HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
### 코드 리팩토링 1단계
- 메소드 분리 및 클래스 분리 (private 메소드 vs 메소드 클래스)
  - [X] HttpRequest 클래스 만들기
    - getMethod / getPath / getHeader / getParameter

### 코드 리팩토링 2단계
- 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
  - [X] 클라이언트 요청 데이터를 담고 있는 InputStream을 생성자로 받아 HTTP 메소드, URL, 헤더, 본문을 분리하는 작업을 한다.
  - [X] 헤더는 Map<String, String>에 저장해 관리하고 getHeader(“필드 이름”) 메소드를 통해 접근 가능하도록 구현한다.
  - [X] GET과 POST 메소드에 따라 전달되는 인자를 Map<String, String>에 저장해 관리하고 getParameter(“인자 이름”) 메소드를 통해 접근 가능하도록 구현한다.
- 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse)
  - [X] 응답을 보낼 때 HTML, CSS, 자바스크립트 파일을 직접 읽어 응답으로 보내는 메소드는 forward(), 다른 URL로 리다이렉트하는 메소드는 sendRedirect() 메소드를 나누어 구현한다.
 
### 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
- [X] 각 분기문을 Controller 인터페이스를 구현하는(implements) 클래스를 만들어 분리한다.
- [X] 이렇게 생성한 Controller 구현체를 Map<String, Controller>에 저장한다. Map의 key에 해당하는 String은 요청 URL, value에 해당하는 Controller는 Controller 구현체이다.
- [X] 클라이언트 요청 URL에 해당하는 Controller를 찾아 service() 메소드를 호출한다.
- [X] Controller 인터페이스를 구현하는 AbstractController 추상클래스를 추가해 중복을 제거하고, service() 메소드에서 GET과 POST HTTP 메소드에 따라 doGet(), doPost() 메소드를 호출하도록 한다.

### 3단계 피드백
- [X] 일급 컬렉션 적용
- [X] HttpRequest POST방식 -> body 전달 + Query 전달
- [X] Test 수정
- [X] Response Cookie 설정 변경
- [X] Mapping Controller 클래스 생성
- [X] Controller의 service메소드 사용하여 연결

## 세션 구현하기
- [X] HttpSession 구현
  - String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
  - void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
  - Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
  - void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
  - void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
- [X] 쿠키를 활용하여 클라이언트와 서버의 세션을 공유한다.
- [X] Map<String, HttpSession>와 같은 구조로 세션을 관리한다.
  - 키(key)는 앞에서 UUID로 생성한 고유한 아이디