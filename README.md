# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 기능 요구사항 - 1단계 (TDD 실습) 
- [x] HTTP GET 요청에 대한 RequestLine을 파싱한다.
- [x] HTTP POST 요청에 대한 RequestLine을 파싱한다.
- [x] HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
- [x] HTTP method인 GET, POST를 enum으로 구현한다.

## 기능 요구사항 - 2단계 (HTTP 웹 서버 구현)
- [x] `http://localhost:8080/index.html` 로 요청 시, `index.html` 파일을 읽어 클라이언트에 응답한다. 
- [x] '회원가입' 메뉴 클릭 시, 회원가입 페이지 HTML 파일을 읽어 클라이언트에 응답한다.
- [x] 회원가입 요청 시, 사용자가 입력한 값을 파싱하여 새로운 회원을 등록할 수 있다.
- [x] 회원가입이 완료되면, `index.html`로 리다이렉트한다.
- [x] '로그인' 메뉴 클릭 시, 로그인 페이지 HTML 파일을 읽어 클라이언트에 응답한다.
- [x] 로그인에 성공하면 로그인 성공 여부를 쿠키에 저장하고 `index.html`로 이동한다.
- [x] 로그인에 실패하면 `/user/login_failed.html` 로 이동한다.
- [x] 로그인 성공 시, Cookie를 활용하여 로그인 상태를 유지한다.
- [x] `http://localhost:8080/user/list` 요청 시, 사용자가 로그인 상태인 경우 사용자 목록을 출력한다.
- [x] `http://localhost:8080/user/list` 요청 시, 사용자가 비로그인 상태인 경우 로그인 페이지로 이동한다.
- [x] Stylesheet 파일을 지원한다.

## 기능 요구사항 - 3단계 (HTTP 웹 서버 리팩토링)
- [x] 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다. (HttpRequest)
- [x] 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다. (HttpResponse)
- [x] 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.

## 기능 요구사항 - 4단계 (세션 구현하기)
- [x] 세션은 다음 목록과 같은 메서드들을 지원해야 한다.
  - [x] `String getId()`: 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환한다.
  - [x] `void setAttribute(String name, Object value)`: 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장한다.
  - [x] `Object getAttribute(String name)`: 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환한다.
  - [x] `void removeAttribute(String name)`: 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제한다.
  - [x] `void invalidate()`: 현재 세션에 저장되어 있는 모든 값을 삭제한다.
- [x] 클라이언트가 최초 요청 시, 응답 시 Cookie에 세션 ID 값을 함께 전달한다.
- [x] 동일한 클라이언트가 요청 시, 요청 헤더에 세션 ID가 존재하는 경우 응답 시 세션 ID 값을 전달하지 않는다.
