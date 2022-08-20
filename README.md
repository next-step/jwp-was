# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

### 1단계 - 기능 요구사항
- [X] GET 요청
  - [X] HTTP GET 요청에 대한 RequestLine을 파싱한다
  - [X]  "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
      - method는 GET
      - path는 /users
      - protocol은 HTTP
      - version은 1.1
- [X] POST 요청
  - [X] HTTP POST 요청에 대한 RequestLine을 파싱한다.
  - [X] "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 POST
    - path는 /users
    - protocol은 HTTP
    - version은 1.1
- [X] Query String 파싱
  - [X] HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
  - [X] 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
- [X] enum 적용(선택)
  - [X] HTTP method인 GET, POST를 enum으로 구현한다.

### 2단계 HTTP 웹 서버 구현 - 구현할 기능 목록
- [ ] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
  - [ ] 입력으로 들어온 classpath의 resource(index.html 파일)를 읽을 수 있다.
  - [ ] HttpRequestParser는 입력으로 들어온 모든 RequestLine을 라인별로 분리할 수 있다.
  - [ ] HttpHeaderParser는 입력으로 들어온 Header 리스트들을 HttpHeader 객체로 변환 할 수 있다.
- [ ] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
    - [ ] Header의 첫 번째 라인에서 요청 URL을 추출한다.
    - [ ] URL은 encoding 된 형태로 넘어오기 때문에 decoding 한다.
- [ ] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
  - [ ] POST method로 데이터를 전달할 경우 전달하는 데이터는 HTTP Body에 담긴다.
  - [ ] 회원가입시 입력한 모든 데이터를 추출해 User 객체를 생성한다.
- [ ] “회원가입”을 완료하면 /index.html 페이지로 이동한다.
    - [ ] redirect 방식으로 회원가입 완료 후, 브라우저 URL이 /index.html로 변경되도록 한다.
      - [ ] HTTP 응답 헤더의 status code를 200이 아니라 302 code 사용한다.
- [ ] “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다.
    - [ ] 로그인이 성공하면 index.html로 이동한다.
    - [ ] 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.
    - [ ] 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true로 전달한다.
    - [ ] 로그인이 실패하면 Cookie header 값이 logined=false로 전달한다.
    - [ ] 응답 header에 Set-Cookie값을 설정한 후 요청 header에 Cookie이 전달되는지 확인한다.
- [ ] “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다.
    - [ ] 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
    - [ ] cookie 값 logined의 true/false 체크하는 로직을 추가한다.
    - [ ] 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
- [ ] Stylesheet 파일을 지원하도록 구현한다.