----
##1단계 TDD 실습 - 구현할 기능 목록

- [x] Request Line은 RFC7230 (https://www.rfc-editor.org/rfc/rfc7230) -> 3.1.1. Request Line 의 정의에 따라 String
  타입의 `method SP request-target SP HTTP-version CRLF` 의 구조를 가진다.
- [x] method는 Enum으로 구현하여야 하며 형태는 RFC7231 (https://www.rfc-editor.org/rfc/rfc7231#section-4) 의 정의에 따라  
  `GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE`의 형태를 가진다.
- [x] HttpParser는 RequestLine을 method, path(request-target), protocaol, version으로 분리 할 수 있다.
- [x] Request Line 객체를 만든다.
- Request Line 객체는 method, path(request-target), protocaol, version을 필드로 가진다.
- [x] Query String이 포함된 형태의 Request Line이 들어오면 쿼리 스트링을 파싱 할 수 있다. (Map 형태로 파싱할예정)

---
##2단계 HTTP 웹 서버 구현 - 구현할 기능 목록

- 기능 요구사항 1

- [x] Http 요청이 들어오면 해당 RequesterLine의 path 에 해당하는 Resource를 반환할 수 있다.
    - [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
        - [x] 입력으로 들어온 classpath의 resource(index.html 파일)를 읽을 수 있다.
        - [x] HttpRequestParser는
            - [x] 입력으로 들어온 모든 RequestLine을 라인별로 분리할 수 있다.
            - [x] RequestLine에서 path를 분리 할 수 있다.
        - [x] HttpHeaderParser는
            - [x] 입력으로 들어온 Header 리스트들을 HttpHeader 객체로 변환 할 수 있다.


- 기능 요구사항 2

- [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
    - [x] Header의 첫 번째 라인에서 요청 URL을 추출한다.
    - [x] PathHandler 인터페이스는 canHandling(), Handle() 메소드를 가진다.
        - [x] UserHandler의 canHandling()은 path의 Root Resource가 `user` 로 시작한다면 true를 반환한다.
        - [x] UserHandler의 Handle()은 입력한 값을 파싱해 접근 경로와 이름=값을 추출해 User 클래스를 생성 할 수 있다.
        - [x] IndexHandler의 canHandling()은 path의 Root Resource가 파일 확장자 `.` 를 가지면 true를 반환한다.
    - [x] HandlerSelector는 canHandling 가능한 Handler 객체를 반환한다.
    - [x] path의 resource에 File 구분자가 존재하면
    - [x] http://localhost:8080/index.html로 접속했을 때는 root경로의 index.html을 처리하는 IndexHandler가 동작한다.
    - [x] http://localhost:8080/user/form.html로 접속했을 떄는 root경로+User경로의 user/form.html을 처리하는 UserHandler가 동작한다.
    - [x] 예약어(RFC3986 https://datatracker.ietf.org/doc/html/rfc3986#section-2.2 )는 url safe 하도록 PercentEncoding 된 형태로
      넘어오기 때문에 decoding 하여야 한다.


- 기능 요구사항 3
- [x] http://localhost:8080/user/form.html 파일의 시한다.
- [x] POST method로 데이터를 전달할 경우 전달하는 데이터는 HTTP Body에 담긴다.
    - [x] HttpRequestHeader에 RequestBody 필드를 추가한다.


- 기능 요구사항 4
- [x] “회원가입”을 완료하면 /index.html 페이지로 이동해야함
    - [x] redirect 방식으로 브라우저의 URL이 /index.html로 변경되도록 만든다.
    - [x] HTTP 응답 헤더의 status code를 200이 아니라 302 code 사용
        - [x] Handler들의 Handle 메소드 호출시, HttpResponseHeader가 반환된다.
        - [x] HttpResponseHeader로 모든 response 종류를 처리할 수 있다.


- 기능 요구사항 5
- [x] “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 앞에서 회원가입한 사용자로 로그인
    - [x] 로그인이 성공하면 index.html로 이동
        - [x] 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.
        - [x] 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true로 전달
    - [x] 로그인이 실패하면 /user/login_failed.html로 이동
        - [x] 로그인이 실패하면 Cookie header 값이 logined=false로 전달
    - [x] 응답 header에 Set-Cookie값을 설정한 후 요청 header에 Cookie이 전달되는지 확인한다.


- 기능 요구사항 6
- [x] “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다.
    - [x] cookie를 체크하는 로직을 추가한다.
    - [x] 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
- [x] 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.


- 기능 요구사항 7
- [x] Stylesheet 파일을 지원하도록 구현
    - [x] 응답 헤더의 Content-Type을 text/css로 전송한다.
    - [x] resources Handler를 추가한다.

----
##3단계 리팩토링

- [x] QueryString 객체 생성
- [x] getHeader(“필드 이름”) 메소드를 통해 접근 가능하도록 구현한다.
- [x] getParameter(“인자 이름”) 메소드를 통해 접근 가능하도록 구현한다.
- [x] 응답을 보낼 때 HTML, CSS, 자바스크립트 파일을 직접 읽어 응답으로 보내는 메소드는 forward(), 다른 URL로 리다이렉트하는 메소드는 sendRedirect() 메소드를 나누어 구현한다.
    - [x] Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
    - [x] Http_Redirect.txt 결과는 응답 header에 Location 정보가 /index.html로 포함되어 있어야 한다.
- [x] Controller 인터페이스를 구현하는 AbstractController 추상클래스를 추가해 중복을 제거하고, service() 메소드에서 GET과 POST HTTP 메소드에 따라 doGet(),
  doPost() 메소드를 호출하도록 한다.

----

## 4단계 - 세션구현하기

- [x] HttpSession API 메소드 구현하기
- [x] 세션 객체 생성하기
  - [x] getId() = 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
  - [x] setAttribute(String name, Object value) = 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
  - [x] getAttribute(String name) = 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
  - [x] removeAttribute(String name) = 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
  - [x] invalidate() = 현재 세션에 저장되어 있는 모든 값을 삭제
- [x] 유저의 최초 Login 요청시 세션 할당하고 쿠키로 세션 관리하기