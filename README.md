# HTTP 이해 - 웹 서버 구현

## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 프로그래밍 요구사항

- 모든 로직에 단위 테스트를 구현한다. 단, 테스트하기 어려운 UI 로직은 제외
    - 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분한다.
    - UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리한다.
- 자바 코드 컨벤션을 지키면서 프로그래밍한다.
    - 이 과정의 Code Style은 intellij idea Code Style. Java을 따른다.
    - intellij idea Code Style. Java을 따르려면 code formatting 단축키(Windows : Ctrl + Alt + L. Mac : ⌥ (Option) + ⌘ (Command) + L.)를 사용한다.
- 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기(indent)만 한다.
    - indent가 2이상인 메소드의 경우 메소드를 분리하면 indent를 줄일 수 있다.
    - else를 사용하지 않아 indent를 줄일 수 있다.

## 1단계 - TDD 실습

### 기능 요구사항

- HTTP GET, POST 요청에 대한 RequestLine을 파싱한다.
- 파싱하는 로직 구현을 TDD로 구현한다.
- 예를 들어 `"GET /users HTTP/1.1"`을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 GET
    - path는 /users
    - protocol은 HTTP
    - version은 1.1
- HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
- 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
- 파싱하는 로직 구현을 TDD로 구현한다.

## 기능 구현 목록

### Domain

- RequestLine
    - RequestLine의 구성 요소를 모두 포함하고 있는 객체
        - HTTP Method
        - Path (+ Query String)
        - ProtocolType & Version
    * `" "`, `"?"`으로 RequestLine 파싱하여 method, path, protocol 분리
    * HttpMethod가 소문자를 포함하는 경우 예외 발생

* HttpMethod
    * GET, POST 두가지로 나뉨.

* Protocol
    * Protocol 정보를 가지고 있는 객체
    * `/`으로 protocol을 파싱하여 protocolType과 Version 분리
    * protocolType이 대문자가 아닌 경우 예외 발생

* ProtocolType
    * HTTP 포함

* Path
    * `?`로 path의 QueryString을 분리
    * QueryString이 있는 경우와 없는 경우를 고려

* QueryParameters
    * `&`로 QueryString 분리
    * `=`로 key-value형태의 pairs에 데이터 저장

## 2단계 - HTTP 웹 서버 구현

### 기능 요구사항

* 정적 리소스를 읽어서 응답한다.
  * `HttpRequest`의 RequestLine에서 URL을 추출한다.
  * URL에 해당하는 파일을 `src/main/resources`에서 읽어 전달한다.

* 회원가입시 사용자가 입력한 값을 서버에 전달하고 `User`클래스에 입력값을 파싱하여 저장한다.
```shell
  GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1
  Host: localhost:8080
  Connection: keep-alive
  Accept: */*
```

* http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
    * POST 메서드로 전달된 데이터를 `requestBody`에서 읽어들인다.
    * 읽어들인 데이터를 추출해 `User`객체를 생성한다.

* 회원가입 완료시 `/index.html` 페이지로 리다이렉트한다.
  * StatusCode 302를 사용하여 페이지 redirect

* "로그인" 클릭하여 http://localhost:8080/user/login.html 으로 이동.
  * 로그인 성공시 -> `index.html`로 이동
    * Cookie를 활용해 로그인 상태를 유지한다.
    * 요청 헤더의 `Cookie header`값을 `logined=true`로 전달
    ```shell
    # Http Response Header
    HTTP/1.1 200 OK
    Content-Type: text/html
    Set-Cookie: logined=true; Path=/
    ```
  * 로그인 실패시 -> `user/login_failed.html`로 이동
    * 요청 헤더의 `Cookie header`값을 `logined=false`로 전달

* 사용자가 로그인 상태로 `http://localhost:8080/user/list` 에 접근했을 때 사용자 목록을 출력한다.
  * 로그인하지 않은 상태면 로그인 페이지 `login.html`로 이동
  * 동적 html 생성위해 `handlebars.java.template engine` 활용
  * stylesheet 파일을 지원하도록 구현
    ```shell
    GET ./css/style.css HTTP/1.1
    Host: localhost:8080
    Accept: text/css,*/*;q=0.1
    Connection: keep-alive
    ```
