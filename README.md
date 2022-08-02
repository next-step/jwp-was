## 요구사항 정리

## STEP 1. TDD 실습
### 요구사항 1 - GET 요청
- HTTP GET 요청에 대한 RequestLine을 파싱
- 파싱하는 로직 구현을 TDD로 구현
- 예) "GET /users HTTP/1.1" 파싱
  - method: GET
  - path: /users
  - protocol: HTTP
  - version: 1.1
  
### 요구사항 2 - POST 요청
- HTTP POST 요청에 대한 RequestLine을 파싱
- 파싱하는 로직 구현을 TDD로 구현
- 예) "POST /users HTTP/1.1" 파싱
  - method: POST
  - path:  /users
  - protocol: HTTP
  - version: 1.1

### 요구사항 3 - Query String 요청
- HTTP 요청(request)의 Query String 으로 전달되는 데이터 파싱
- 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2 와 같은 구조로 전달됨
- 파싱하는 로직 구현을 TDD로 구현

### 요구사항 4 - enum 적용
- HTTP method인 GET, POST를 enum 으로 구현 


## STEP 2. HTTP 웹 서버 구현
### 요구사항 1
- http://localhost:8080/index.html 접속, webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답
- utils.FileIoUtils의 loadFileFromClasspath() 메소드를 이용

### 요구사항 2
- “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동 
- 회원가입

### 요구사항 3
- http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현

### 요구사항 4
- “회원가입”을 완료하면 /index.html 페이지로 이동
- 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없음
- redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동. 즉, 브라우저의 URL이 /index.html로 변경해야 함

### 요구사항 5
- “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인
- 로그인이 성공하면 index.html 로 이동
- 로그인이 실패하면 /user/login_failed.html 로 이동
- 앞에서 회원가입한 사용자로 로그인할 수 있어야 함
- 로그인이 성공하면 cookie 를 활용해 로그인 상태를 유지
- 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie 
  header 값이 logined=false로 전달

### 요구사항 6
- 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력
- 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동
- 동적으로 html을 생성하기 위해 handlebars.java template engine 을 활용

### 요구사항 7
- 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있음
- Stylesheet 파일을 지원하도록 구현


## STEP 3. HTTP 웹 서버 리펙토링
### 리펙토링 1 
- 메소드 분리 및 클래스 분리
- RequestHandler 클래스 책임분리
  - HttpRequest
  - HttpResponse
 
### 리펙토링 2
- 클라이언트 요청 데이터를 처리하는 별도 클래스로 분리
- 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리
- 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리 제거

### 추가 요구사항 변경이 발생하는 경우
- HTTP에서 POST 방식으로 데이터를 전달할 때 body를 통한 데이터 전달뿐만 아니라 Query String을 활용한 데이터 전달도 지원


## STEP 4. 세션 구현하기
- 서블릿에서 지원하는 HttpSession API의 일부를 지원해야 함
  - String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환 
  - void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장 
  - Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환 
  - void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제 
  - void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제


## STEP 5. Thread Pool 적용
### 요구사항 1
- 현재 구현되어 있는 웹 애플리케이션 서버(이하 WAS)는 사용자 요청이 있을 때마다 Thread를 생성해 사용자 요청을 처리
- WAS가 이와 같이 처리할 경우 다음과 같은 문제가 발생할 가능성이 존재
  - 사용자 요청이 있을 때마다 Thread를 생성해야 하기 때문 
  - Thread 생성 비용이 발생해 성능이 떨어짐
  - 동시 접속자가 많아질 경우 WAS가 메모리 자원의 한계, Context Switching 비용의 증가로 다운될 가능성이 높음
- WAS는 많은 동시 접속자를 처리하는 것도 중요하지만 동시 접속자가 많더라도 다운되지 않으면서 안정적으로 서비스하는 것이 더 중요
- WAS에 Thread Pool을 적용해 안정적인 서비스가 가능하도록 해야함
- Java에서 기본으로 제공하는 ThreadPoolExecutor를 활용해 ThreadPool 기능을 추가
- 최대 ThradPool의 크기는 250, 모든 Thread가 사용 중인(Busy) 상태이면 100명까지 대기 상태가 되도록 구현

### 요구사항 2
- src/test/java의 webserver.ExecutorsTest는 100개의 Thread가 동시에 실행하도록 구현한 테스트 코드
- Spring에서 제공하는 RestTemplate을 활용해 서버의 ThreadPool 수보다 많은 요청을 동시에 보내도록 함
- 예를 들어 서버의 최대 Thread Pool 수를 5로 설정하고, ExecutorsTest의 Executors.newFixedThreadPool(10)과 같이 설정해 동시에 10개의 요청이 발생하도록 구현








