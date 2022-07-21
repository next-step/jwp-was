## 프로그래밍 요구사항

* 모든 로직에 단위 테스트를 구현한다. 단, 테스트하기 어려운 UI 로직은 제외
    - 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분한다.
    - UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리한다.

* 자바 코드 컨벤션을 지키면서 프로그래밍한다.
    - 이 과정의 Code Style은 intellij idea Code Style. Java을 따른다.
    - intellij idea Code Style. Java을 따르려면 code formatting 단축키(Windows : Ctrl + Alt + L. Mac : ⌥ (Option) + ⌘ (Command)
        + L.)를 사용한다.

* 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기(indent)만 한다.
    - indent가 2이상인 메소드의 경우 메소드를 분리하면 indent를 줄일 수 있다.
    - else를 사용하지 않아 indent를 줄일 수 있다.

## 기능 목록 및 commit 로그 요구사항

* 기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
* git의 commit 단위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.

## git commit message

* feat (feature)
* fix (bug fix)
* docs (documentation)
* style (formatting, missing semi colons, …)
* refactor
* test (when adding missing tests)
* chore (maintain)

<hr>

# step1 미션 요구 사항

## RequestLine을 파싱한다.

* RequestLine을 파싱해 원하는 값을 가져올 수 있는 API를 제공해야 한다.
* RequestLine은 HTTP 요청의 첫번째 라인을 의미한다.

<br>

### 요구사항 1 - GET 요청

* HTTP GET 요청에 대한 RequestLine을 파싱한다.
* 파싱하는 로직 구현을 TDD로 구현한다.
* 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 GET
    - path는 /users
    - protocol은 HTTP
    - version은 1.1

<br>

### 요구사항 2 - POST 요청

* HTTP POST 요청에 대한 RequestLine을 파싱한다.
* 파싱하는 로직 구현을 TDD로 구현한다.
* 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 POST
    - path는 /users
    - protocol은 HTTP
    - version은 1.1

<br>

### 요구사항 3 - Query String 파싱

* HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
* 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
* 파싱하는 로직 구현을 TDD로 구현한다.

#### Query String 예 - GET 요청

```bash
GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1
```

<br>

### 요구사항 4 - enum 적용(선택)

* HTTP method인 GET, POST를 enum으로 구현한다.

<hr>

# step2 미션 요구 사항

<br>

### 요구사항 1

> http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

#### HTTP Request Header 예

```bash
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```

#### path에 해당하는 파일 읽어 응답하기 힌트

* 요청 URL에 해당하는 파일을 src/main/resources 디렉토리에서 읽어 전달하면 된다.
* utils.FileIoUtils의 loadFileFromClasspath() 메소드를 이용해 classpath에 있는 파일을 읽는다.

```java
public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }
}
```

<br>

### 요구사항 2

> “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
> 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.

```bash
/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```

> HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.

#### HTTP Request Header 예

```bash
GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```

<br>

### 요구사항 3

> http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

#### HTTP Request Header 예

```bash
POST /user/create HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 59
Content-Type: application/x-www-form-urlencoded
Accept: */*

userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```

#### Request Body의 값 추출하기 힌트

* POST method로 데이터를 전달할 경우 전달하는 데이터는 HTTP Body에 담긴다.
* HTTP Body는 HTTP header 이후 빈 공백을 가지는 한 줄(line) 다음부터 시작한다.
* HTTP Body에 전달되는 데이터는 GET method의 이름=값과 같다.
* BufferedReader에서 본문 데이터는 util.IOUtils 클래스의 readData() 메서드를 활용한다. 본문의 길이는 http header의 Content-Length의 값이다.
* 회원가입시 입력한 모든 데이터를 추출해 User 객체를 생성한다.

<br>

### 요구사항 4

> “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

#### redirect 힌트

* HTTP 응답 헤더의 status code를 200이 아니라 302 code를 사용한다.
    - HTTP_302 문서 참고

<br>

### 요구사항 5

> “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.

> 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

#### HTTP Request Header 예

```bash
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
Cookie: logined=true
```

#### HTTP Response Header 예

```bash
HTTP/1.1 200 OK
Content-Type: text/html
Set-Cookie: logined=true; Path=/
```

#### Cookie 구현 힌트

* 정상적으로 로그인 되었는지 확인하려면 앞 단계에서 회원가입한 데이터를 유지해야 한다.
    - 앞 단계에서 회원가입할 때 생성한 User 객체를 DataBase.addUser() 메서드를 활용해 RAM 메모리에 저장한다.
* 아이디와 비밀번호가 같은지를 확인해 로그인이 성공하면 응답 header의 Set-Cookie 값을 logined=true, 로그인이 실패할 경우 Set-Cookie 값을 logined=false로 설정한다.
  Set-Cookie 설정시 모든 요청에 대해 Cookie 처리가 가능하도록 Path 설정 값을 /(Path=/)로 설정한다.
* 응답 header에 Set-Cookie값을 설정한 후 요청 header에 Cookie이 전달되는지 확인한다.

<br>

### 요구사항 6

> 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.

> 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

#### classpath에 있는 template 파일을 읽어 동적으로 html을 생성하는 방법은 다음과 같다.

```bash
public class HandlebarsTest {
    private static final Logger log = LoggerFactory.getLogger(HandlebarsTest.class);

    @Test
    void name() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        String profilePage = template.apply(user);
        log.debug("ProfilePage : {}", profilePage);
    }
}
```

<br>

### 요구사항 7

> 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

#### HTTP Request Header 예

```bash
GET ./css/style.css HTTP/1.1
Host: localhost:8080
Accept: text/css,*/*;q=0.1
Connection: keep-alive
```

#### Content-Type 처리 힌트

* 응답 헤더의 Content-Type을 text/html로 보내면 브라우저는 html 파일로 인식하기 때문에 css가 정상적으로 동작하지 않는다.
* Stylesheet인 경우 응답 헤더의 Content-Type을 text/css로 전송한다. Content-Type은 확장자를 통해 구분할 수도 있으며, 요청 헤더의 Accept를 활용할 수도 있다.




































