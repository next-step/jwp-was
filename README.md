# 웹 애플리케이션 서버

## 진행 방법

* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

### Step1 요구사항

- GET 요청 파싱
    - HTTP GET 요청에 대한 RequestLine을 파싱한다
    - 예를 들어 `GET /users HTTP/1.1`을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
        - method는 `GET`
        - path는 `/users`
        - protocol은 `HTTP`
        - version은 `1.1`
- POST 요청 파싱
    - HTTP POST 요청에 대한 RequestLine을 파싱한다.
    - 예를 들어 `POST /users HTTP/1.1`을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다
        - method는 `POST`
        - path는 `/users`
        - protocol은 `HTTP`
        - version은 `1.1`
- Query String 파싱
    - HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
    - 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
    - 예를 들어 `GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1` 를 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
        - userId는 `javajigi`
        - password는 `password`
        - name은 `Jaesung`
- enum 적용(선택)
    - HTTP method인 GET, POST를 enum으로 구현한다.

### Step2 요구사항

- 서버에 있는 리소스 정적 파일(html) 응답하기
    - http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- 모든 Request Header 파싱하기
    - 예를 들어 다음과 같은 요청이 있을 때 첫 줄(RequestLine)을 제외한 나머지 Header 라인도 모두 파싱한다.
      ```
      GET /index.html HTTP/1.1
      Host: localhost:8080
      Connection: keep-alive
      Accept: */* 
      ```
- “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
    - 예를들어 다음과 같은 사용자 입력 값 요청이 있을 때 값을 파싱해 User 모델에 저장한다
  ```
  /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
  ```
- http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
    - POST method로 데이터를 전달할 경우 전달하는 데이터는 HTTP Body에 담겨 전달되기 때문에 이 HTTP Body에 있는 내용을 기반으로 User가 저장될 수 있도록 한다.
- HTTP 쿠키를 활용한 로그인 구현하기
    - “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면
      /user/login_failed.html로 이동해야 한다. 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이
      성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.
- 정적 웹 페이지를 동적으로(template engine) 응답 구현하기
    - 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약
      로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다. 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
- 응답한 웹 페이지 스타일시트(css) 적용 구현하기
    - 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.




