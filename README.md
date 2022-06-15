# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


### Step 1
- [x] 요구사항 1 - GET 요청
  - HTTP GET 요청에 대한 RequestLine을 파싱한다.
  - 파싱하는 로직 구현을 TDD로 구현한다.
  - 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 GET
    - path는 /users
    - protocol은 HTTP
    - version은 1.1

- [x] 요구사항 2 - POST 요청
  - HTTP POST 요청에 대한 RequestLine을 파싱한다.
  - 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
  - method는 POST
  - path는 /users
  - protocol은 HTTP
  - version은 1.1
  
- [x] 요구사항 3 - Query String 파싱
  - HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
  - 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
  - ex) GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1

- [x] 요구사항 4 - enum 적용(선택)
  - HTTP method인 GET, POST를 enum으로 구현한다.

### Step 2
- [x] 요구사항 1 
  - http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

- [x] 요구사항 2
  - “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입 할 수 있다.
  - Restful 예제: /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net

- [ ] 요구사항 3
  - http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

- [ ] 요구사항 4
  - “회원가입”을 완료 후 /index.html 페이지로 이동한다.

- [ ] 요구사항 5
  - “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 
  - 로그인이 성공하면 
    - index.html로 이동한다.
    - Cookie header 값이 logined=true
  - 로그인이 실패하면 
    - /user/login_failed.html로 이동한다.
    - Cookie header 값이 logined=false
  - 회원가입한 사용자로 로그인할 수 있어야 한다. 
  - cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.

- [ ] 요구사항 6
  - http://localhost:8080/user/list 로 접근했을 때 
    - 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 사용자 목록을 출력한다. 
    - 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
  - 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

- [ ] 요구사항 7
  - Stylesheet 파일을 지원하도록 구현하도록 한다.