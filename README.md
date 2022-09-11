# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 요구사항
### STEP 1
- [X] GET 요청 파싱하기
- [X] POST 요청 파싱하기
- [X] QueryString 파싱하기
- [X] HttpMethod 에 enum 적용하기
### STEP 2
- 요구사항 1
  - [X] InputStream을 RequestLine으로 변환하기
  - [X] Request 헤더를 출력하기
  - [X] path에 해당하는 파일 읽어 응답하기
- 요구사항 2
  - [X] GET `/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net` 요청 처리하기
    - [X] Request Parameter 추출하기
    - [X] Request Parameter 디코딩하기
    - [X] 빌더 패턴 이용하여 User 객체 생성하기
- 요구사항 3
  - [X] POST `/user/create` 요청 처리하기
    - [X] RequestBody 생성하기
    - [X] HttpRequest 정의하여 추상화하기
    - [X] SignUpController 구현하기
- 요구사항 4
  - [X] 회원가입 후 `/index.html`로 리다이렉트하기
    - [X] route 기능 만들기
    - [X] HttpResonse 요소마다 분리하여 write하기
    - [X] HttpResponse에 redirect, getView 정의하기
    - [X] HttpStatus 에 알맞게 HttpHeader 생성하기
    - [X] HomeController 구현하기
- 요구사항 5
  - [X] 로그인 시 쿠키 전달하기
    - [X] 쿠키 헤더 구현하기
    - [X] LogInController 구현하기
- 요구사항 6
  - [X] GET `/user/list` 구현하기
    - [X] 쿠키로 로그인 상태 판별하여 비로그인 상태면 로그인 페이지로 리다이렉트하기
    - [X] Handlebars로 동적뷰 만들어서 리턴하기
- 요구사항 7
  - [X] GET `./css/style.css` 구현하기
    - [X] 정적파일 요청에 대해 ContentType 응답 헤더 추가하기
### STEP 3
- [X] RequestHandler의 역할 분리
- [X] 테스트 코드 보충
### STEP 4
- [X] HttpSession과 API 구현
  - [X] ConcurrentHashMap을 이용하여 SessionStorage 구현
  - [X] HttpSession, SessionId, SessionAttribute 구현
  - [X] 로그인 시 세션과 쿠키 세팅하도록 수정
  - [X] UserListController 에서 쿠키를 통해 세션 정보 확인하도록 수정
### STEP 5
- [X] 최대 250 크기, 최대 대기 100의 스레드풀 구현
- [X] RestTemplate 이용하여 스레드풀보다 적은 요청과 많은 요청을 발생시키는 t/c 작성
- [X] FuntionalInterface 활용하여 제너레이터에 SessionId 구현할 수 있도록 리팩토링
