# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


## 🚀 1단계 - TDD 실습

### 요구사항 1 - GET 요청

- [x] HTTP GET 요청에 대한 RequestLine을 파싱한다.
- [x] 파싱하는 로직 구현을 TDD로 구현한다.
  - 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
  - method는 `GET`
  - path는 `/users`
  - protocol은 `HTTP`
  - version은 `1.1`

### 요구사항 2 - POST 요청

- [x] HTTP POST 요청에 대한 RequestLine을 파싱한다.
- [x] 파싱하는 로직 구현을 TDD로 구현한다.

- 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
  - method는 `POST`
  - path는 `/users`
  - protocol은 `HTTP`
  - version은 `1.1`
    
### 요구사항 3 - Query String 파싱
- [x] HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
- [x] 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
- [x] 파싱하는 로직 구현을 TDD로 구현한다.

  - Query String 예 - GET 요청
  - `GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1`
  
## 요구사항 4 - enum 적용(선택)
- [x] HTTP method인 GET, POST를 enum으로 구현한다.


## 🚀 2단계 - HTTP 웹 서버 구현

### 기능 요구사항 1
- [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

### 기능 요구사항 2
- [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
- [x] HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.

### 기능 요구사항 3
- [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

### 기능 요구사항 4
- [x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
- [x] HTTP 응답 헤더의 status code를 200이 아니라 302 code를 사용한다.

### 기능 요구사항 5
- [x] “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.
- [x] 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

### 기능 요구사항 6
- [x] 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
- [x] 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

### 기능 요구사항 7
- [x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
- [x] 확장자마다 contentType를 반환합니다.


## 🚀 3단계 - HTTP 웹 서버 리팩토링

### 리팩토링 1
- [x] RequestHandler 기능 분리하기
  - [x] 리뷰에서 받은 HttpResponse에 response200Header 넣기

### 리팩토링 2
- [x] 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
- [x] 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse)
- [x] 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.

### 리팩토링 3
- [x] Body및 query string 값을 가져올수 있게 getParameter 를 수정합니다.

### 테스트 코드 작성
- [x] GET, POST file을 읽고 Requst와 response를 확인합니다.
