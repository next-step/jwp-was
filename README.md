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

- [ ] Http 요청이 들어오면 해당 RequesterLine의 path 에 해당하는 Resource를 반환할 수 있다.
    - [ ] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
        - [ ] 입력으로 들어온 classpath의 resource(index.html 파일)를 읽을 수 있다.
        - [x] HttpRequestParser는
            - [x] 입력으로 들어온 모든 RequestLine을 라인별로 분리할 수 있다.
            - [x] RequestLine에서 path를 분리 할 수 있다.
        - [x] HttpHeaderParser는
          - [x] 입력으로 들어온 Header 리스트들을 HttpHeader 객체로 변환 할 수 있다.


- [ ] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
    - [ ] Header의 첫 번째 라인에서 요청 URL을 추출한다.
    - [ ] 예약어(RFC3986 https://datatracker.ietf.org/doc/html/rfc3986#section-2.2 )는 url safe 하도록 PercentEncoding 된 형태로
      넘어오기 때문에 decoding 하여야 한다.
    - [ ] 요청 URL에서 접근 경로와 이름=값을 추출해 User 클래스를 생성 할 수 있다.