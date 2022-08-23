# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단계 - TDD 실습
### 요구사항 공통부
* 도메인 구조
  * HttpRequest
    * RequestLine
      * HttpMethod
      * Path
        * QueryString
      * Protocol
        * Version
    * HttpHeaders
    * HttpBody
* HTTP 요청의 첫번째 라인을 의미하는 RequestLine을 구현해야 한다. 
* 각 요청을 파싱하여 보여주기 위한 ResultView를 구현해야 한다.

### 요구사항1 - GET 요청
* HTTP GET 요청에 대한 RequestLine을 파싱한다.
* 파싱하는 로직 구현을 TDD로 구현한다.
* "GET /users HTTP/1.1" 결과
  * method는 GET
  * path는 /users
  * protocol은 HTTP
  * version은 1.1

### 요구사항2 - POST 요청
* HTTP POST 요청에 대한 RequestLine을 파싱한다.
* 파싱하는 로직 구현을 TDD로 구현한다.
* "GET /users HTTP/1.1" 결과
  * method는 POST
  * path는 /users
  * protocol은 HTTP
  * version은 1.1

### 요구사항3 - Query String 파싱
* HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
* 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
* 파싱하는 로직 구현을 TDD로 구현한다.

### 요구사항4 - enum 적용
* HTTP method인 GET, POST를 eunm으로 구현한다.

## 2단계 - HTTP 웹 서버 구현
### 기능 요구사항 1
> http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
* index.html 로 요청이 갔을 때, 실제 해당 파일을 읽어서 응답으로 보낼 수 있어야 한다.
  * 먼저, 브라우저에서 요청으로 보내는 그 요청이 index.html 에 대한 요청인지 알아야 한다.
  * Request Line 에 브라우저에서 서버로 보내는 모든 요청 데이터들은 InputStream 에 담겨있다.
  * InputStream 에서 데이터를 읽어들이기가 생각보다 쉽지않다.
    * 따라서, java 에서 제공하는 BufferedReader 를 통해서 inputSteam 을 변환해 한 라인 씩 읽을 수 있도록 구현해야 한다.
  * 서버에서 클라이언트 쪽으로 뭔가 응답을 보낼 때 데이터들을 outputStream 에 담아서 보낸다.

### 기능 요구사항 2
> “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
> 
> 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
> 
> HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
* QueryString 형태로 전달된 이름=값을 추출해서 User 클래스에 담는다.
  * RequestLine 에서 path를 확인하고 /user/create 인 경우, 뒤에 전달되는 QueryString 을 파싱한다.
  * 각각의 key, value 쌍을 Map으로 저장하고, User 클래스의 필드로 넣을 값들을 꺼내온다.

### 기능 요구사항 3
> http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
* 회원가입 method를 POST로 변경한다.
  * post 요청인 경우 http body 값을 확인하게 되고, http body 내 '이름=값'을 통해 User 클래스의 필드를 채워넣는다.
* http body 값을 확인하기 위해 header 값들 중 Content-Length 값을 확인해야한다.
  * header 값들을 한 줄씩 읽어서 저장하기 위한 객체 필요 → HttpHeaders

### 기능 요구사항 4
> “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
* HTTP 응답 헤더의 status code → 302
* response를 아래와 같이 구성한다.
````
HTTP/1.1 302 Found
Location: http://www.iana.org/domains/example/
````

### 기능 요구사항 5
>“로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.
> 
> 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.
* 정상적으로 로그인 되었는지 확인하기 위해 앞서 회원가입한 데이터와 로그인 정보 비교
  * DataBase.findUserById 함수를 통해 해당 회원정보를 조회하고 로그인 시 입력받은 비밀번호와 일치 여부 확인
* 로그인 결과에 따라 HTTP Response Header에 Set-Cookie 값을 설정
  * 로그인 성공 → Set-Cookie: logined=true;
  * 로그인 실패 → Set-Cookie: logined=false;

### 기능 요구사항 6
> 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다. 
> 
> 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
* HTTP Request Header에 Cookie 값을 확인해서 로그인 상태인지 아닌지 확인
  * 로그인 상태 → Cookie: logined=true
    * 로그인 상태인 경우, 예시 코드를 활용해서 /user/list 요청을 받았을 때, 사용자 목록을 출력하도록 한다. 
  * 로그인하지 않은 상태 → Cookie: logined=false
    * 로그인 상태가 아닌 경우, 로그인 페이지(login.html)로 이동

### 기능 요구사항 7
> 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
* css 파일에 관련한 요청인 경우 HTTP Response Header에서 Accept 값을 text/css로 전송한다.

## 3단계 - HTTP 웹 서버 리팩토링
### HttpRequest 클래스와 HttpResponse 클래스를 만들어 책임을 분리해보자
* HttpRequest 클래스가 아래의 멤버 변수들을 갖도록 구현
  * RequestLine
  * HttpHeaders
  * RequestParameters
* HttpRequest 클래스 내에서 위 멤버 변수들을 초기화 하기 위한 소스 코드가 길어지게 된다.
  * BufferedRequestToHttpRequest 클래스에서 HttpRequest 를 생성하도록 구현
* 실제 WAS를 실행시켜서 테스트하지 않고, test resource에 요청을 mocking 할 수 있도록 txt 파일을 만들어 테스트할 수 있도록 테스트 케이스 추가 작성

<br>

* HttpResponse 클래스가 아래의 멤버 변수를 갖도록 구현
  * DataOutputStream
* 실제 WAS를 실행시켜서 테스트하지 않고, test resource에 응답 결과를 txt 파일로 확인할 수 있도록 테스트 케이스 추가
