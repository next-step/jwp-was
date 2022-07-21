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
- [ ] '로그인' 메뉴 클릭 시, 로그인 페이지 HTML 파일을 읽어 클라이언트에 응답한다.
- [ ] 로그인에 성공하면 `index.html`로 이동한다.
- [ ] 로그인에 실패하면 `/user/login_failed.html` 로 이동한다.
- [ ] 로그인 성공 시, Cookie를 활용하여 로그인 상태를 유지한다.
- [ ] `http://localhost:8080/user/list` 요청 시, 사용자가 로그인 상태인 경우 사용자 목록을 출력한다.
- [ ] `http://localhost:8080/user/list` 요청 시, 사용자가 비로그인 상태인 경우 로그인 페이지로 이동한다.
- [ ] Stylesheet 파일을 지원한다.
