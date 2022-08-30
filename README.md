# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## Step1
- GET요청 파싱을 위한 RequestParser 클래스 생성 및 테스트코드 작성
- POST요청 파싱을 위한 RequestParser 클래스 생성 및 테스트코드 작성
- ENUM 적용

## Step2
### 기능 요구사항 1
- RequestHeader 도메인 구현
- Request 도메인 구현
- path 값을 응답

### 기능 요구사항 2
- User 클래스 생성
- RequestLine 파라미터 분리
- 분리한 파라미터 메소드로 User 생성

### 기능 요구사항 3
- RequestBody 구현

### 기능 요구사항 4
- HttpResponse 클래스 구현
- redirect, forward 구현

### 기능 요구사항 5
- /user/login 분기 구현
- response 에 set-cookie 헤더 추가

### 기능 요구사항 6
- /user/list 분기에서 로그인 상태이면 동적으로 html 생성 후 이동
- 비로그인 상태라면 index.html 로 이동

### 리팩토링 목록
- Request 코드 리팩토링
  - HttpRequest는 RequestLine, Header, Body를 갖는다.(o)
  - 현재 Method, Path를 RequestLine 도메인으로 관리(o)
  - Method enum 클래스내의 상태값 및 메소드 추가(o)
  - QueryString과 RequestBody를 따로 관리할 필요가 있을까? 그냥 Body 하나로 관리하는건 어떨까?(o)

- Controller 생성 및 RequestHandler 리팩토링(o)

- Handlebars 클래스 분리(o)

- ContentType enum 클래스로 분리(o)

- HttpResponse 클래스 리팩토링
  - HttpResponse 클래스는 StatusLine, Header, RequestBody 를 포함한다.

- Cookie 클래스 분리

## Step4
- HttpSession 클래스 구현
- HttpSession을 관리하는 HttpSessionRepository 클래스 구현
- Session 로그인 로직
  1. 사용자가 로그인 시도를 하면 db에서 유저가 존재하는지 확인하고, 존재한다면 session 을 생성한다.
  2. Session에 로그인했다는 logined 라는 Attribute true로 설정한다.
  3. 1에서 만든 session의 ID를 Set-Cookie에 설정하고 브라우저에 응답 한다.
  4. 다음 요청에서 Cookie에 sessionId가 있다면 해당 id의 세션을 찾는다.
  5. 찾은 세션에서 logined 가 설정되어있다면 해당 요청한 유저는 로그인 된 상태다.
  
## Step5
- ThreadPoolExecutor를 활용해 ThreadPool 기능을 추가한다.
- restTemplate을 활용해 서버의 ThreadPool 수보다 많은 요청을 동시에 보내본다.


