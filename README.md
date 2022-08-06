# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## step1 기능 요구사항
* HTTP 요청과 응답을 파싱해 원하는 값을 가져올 수 있는 API 제공
1. GET 요청
   1-1. RequestLine을 파싱하여 method, pathInformation, protocolInformation, version 을 추출한다. (protocolInformation, version은 하나의 클래스로)
   1-2. class 분리를 통해 인스턴스 변수 별 유효성 검사를 진행한다.
   1-3. Http method인 GET, POST를 enum으로 구현한다.
2. POST 요청 : GET 요청과 마찬가지로 RequestLine 파싱하여 데이터를 추출한다.
3. Query String 파싱
   3-1. RequestLine을 파싱하여 데이터를 추출한다.
   3-2. pathInformation 클래스에 Query String 인스턴스 변수를 추가해 데이터를 파싱한다.

## step2 기능 요구사항
* HTTP 웹 서버 구현
1. webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
   1-1. httpRequest 문자열을 받아 HttpRequest 클래스에 파싱하여 넣는다.
   1-2. 사용자 요청으로부터 path를 추출하여 index.html 파일을 읽어 응답한다.
2. GET 방식 요청으로 회원가입을 한다.
   2-1. Request Header 를 호출하여, 응답을 User 클래스에 담는다.
3. POST 방식 요청으로 회원가입을 한다.
   3-1. HTTP Body를 가져와 User 클래스에 담는다.
4. 회원 가입 이후에 index.html 페이지로 이동 시킨다.
5. 로그인 성공시 index.html, 실패시 login_failed.html로 이동한다. 로그인 성공시 cookie를 활용해 로그인 상태 유지
   5-1. 로그인 성공시 cookie header 값을 logined=true, 실패시 logined=false 로 전달
   5-2. cookie는 정상적으로 로그인 되었을 경우, 앞 단계에서 회원가입한 데이터를 유지해야 한다.
   5-3. 응답 header의 Set-Cookie 값을 설정한다.
   5-4. 응답 header에 set-Cookie 값을 설정한 경우 요청 header에 Cookie 값이 전달 되는지 확인한다.
6. 사용자가 로그인 상태라면 사용자 목록 페이지 이동, 로그인 하지 않은 상태라면 로그인 페이지로 이동
   6-1. classPath에 있는 template 파일을 읽어 동적으로 html 생성
   6-2. handlebars 라이브러리를 사용해서 User 객체를 동적으로 html 생성해서 전달한다.
7. StyleSheet 파일 지원
   7-1. style.css 파일을 응답으로 전달 할 수 있도록 구현