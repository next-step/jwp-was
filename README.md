# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## step1 기능 요구사항
* HTTP 요청과 응답을 파싱해 원하는 값을 가져올 수 있는 API 제공
1. GET 요청
   1-1. RequestLine을 파싱하여 httpMethod, pathInformation, protocolInformation, version 을 추출한다. (protocolInformation, version은 하나의 클래스로)
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
   2-1. HttpRequest가 정적 리소스를 전달하는지, 동적 응답을 요청하는지 확인하고, 사용자 등록 호출시 queryString으로 넘어온 값으로 User 인스턴스를 생성한다.
3. POST 방식 요청으로 회원가입을 한다. 
   1. HttpRequest를 파싱하여 HttpRequestBody 데이터를 넣는다. 
   2. POST 방식으로 user를 등록한다.
4. 회원 가입 이후에 index.html 페이지로 이동 시킨다.
   1. controllerEnum enum 클래스를 통해 controller를 구현하고, user 등록시 index.html 페이지로 이동한다.
5. 로그인 성공시 index.html, 실패시 login_failed.html로 이동한다. 로그인 성공시 cookie를 활용해 로그인 상태 유지
   1. 로그인 성공 시 성공시 index.html, 실패시 login_failed.html로 이동한다.
   2. 로그인 성공시 cookie header 값을 logined=true, 실패시 logined=false 로 전달
   3. 응답 header에 set-Cookie 값을 설정한 경우 요청 header에 Cookie 값이 전달 되는지 확인한다.
6. 사용자가 로그인 상태라면 사용자 목록 페이지 이동, 로그인 하지 않은 상태라면 로그인 페이지로 이동
   1. 로그인 상태라면 사용자 목록 페이지 이동, 로그인 하지 않은 상태라면 로그인 페이지로 이동한다.
   2. handlebars 라이브러리를 사용해서 User 객체를 동적으로 html 생성해서 전달한다.
7. StyleSheet 파일 지원
   7-1. style.css 파일을 응답으로 전달 할 수 있도록 구현

## step3 기능 요구사항
* HTTP 웹 서버 리팩토링
1. HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
   1. RequestHandler 클래스에서 HttpRequest, HttpResponse 클래스를 분리한다.
      1. HttpRequest 클래스를 분리한다.
      2. HttpResponse 클래스를 분리한다.
      3. 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
   2. HttpRequest에 들어오는 클라이언트 요청 데이터를 별도의 클래스로 분리한다.
      1. InputStream을 생성자로 받아 HTTP 메소드, 헤더, 본문을 분리한다.
      2. 헤더는 Map<String, String>에 저장해 관리하고, getHeader("필드 이름")으로 접근한다.
      3. GET, POST 메소드에 따라 전달되는 인자를 Map<String, String> 에 저장해 관리하고 getParameter("인자 이름")으로 접근한다.
      4. RequestHandler가 새로 추가한 HttpRequest를 사용하도록 리팩토링한다.
   3. HttpResponse로 전달되는 응답 데이터를 별도의 클래스로 분리한다.
      1. 응답 데이터 처리를 위한 많은 중복이 있는데, 중복을 제거한다.
      2. 응답 헤더 정보를 Map<String, String>으로 관리한다.
      3. 응답을 보낼때 파일을 직접 읽어 보내는 메소드는 forward(), url으로 리다이렉트 하는 메소드는 sendRedirect로 나누어 구현한다.
      4. RequestHandler가 새로 추가한 HttpResponse를 사용하도록 리팩토링한다.
   4. 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
      1. 각 분기문을 Controller 인터페이스를 구현하는 클래스를 만들어 분리한다.
      2. controller 구현체를 Map<String, Controller>에 저장한다.
      3. 클라이언트 요청 URL에 해당하는 controller를 찾아 service() 메소드를 호출한다.
      4. AbstractController 추상클래스를 추가해 중복을 제거하고, service() 메소드에서 HTTP 메소드에 따라 doGet(), doPost()를 호출한다.
   5. 추가 요구사항이나 변경이 발생하는 경우
      1. POST 방식으로 데이터를 전달할 때 body 뿐만아니라 Query String을 활용한 데이터 전달도 지원한다.
         1. 기존의 클래스 구조로 충분히 소화 가능한지 검토
         2. 변경된 요구사항에 따라 클래스 구조 재설계

## step4 기능 요구사항
* 세션 구현하기
1. HttpSession 클래스를 생성하고, 세션 관리를 위해 Map<String, HttpSession> 구조를 만든다. 
2. 쿠키와 세션을 사용하여 로그인을 처리한다.