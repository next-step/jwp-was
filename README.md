# 웹 애플리케이션 서버

## 진행 방법

* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

---

### 1단계 - TDD 실습

- [x] 요구사항 1 - GET 요청
- [x] 요구사항 2 - POST 요청
- [x] 요구사항 3 - Query String 파싱
- [x] 요구사항 4 - enum 적용(선택)

### 2단계 - HTTP 웹 서버 구현

- [x] 요구사항 1 - http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- [x] 요구사항 2 - “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다. 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
- [x] 요구사항 3 - http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
- [x] 요구사항 4 - “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
- [x] 요구사항 5 - “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다. 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면
  Cookie header 값이 logined=false로 전달되어야 한다.
- [x] 요구사항 6 - 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다. 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
- [x] 요구사항 7 - 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

### 3단계 - HTTP 웹 서버 리팩토링

- [x] WAS 기능, HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다.
- [x] HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
- [x] 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
- [x] 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse)
- [x] 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
- [x] 추가 요구사항이나 변경이 발생하는 경우

### 4단계 - 세션 구현하기

#### 서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다. HttpSession API 중 구현할 메소드는 getId(), setAttribute(String name, Object value), getAttribute(String name), removeAttribute(String name), invalidate() 5개이다. HttpSession의 가장 중요하고 핵심이 되는 메소드이다.

- [x] String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
- [x] void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
- [x] Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
- [x] void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
- [x] void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
- [x] 세션은 클라이언트와 서버 간에 상태 값을 공유하기 위해 고유한 아이디를 활용하고, 이 고유한 아이디는 쿠키를 활용해 공유한다.