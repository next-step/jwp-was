# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


# 기능 목록
- RequestLine 파싱
  - [x] 문자열이 비어 있으면 파싱할 수 없다.
  - [x] GET 요청
    - [x] method, path, protocol, version
  - [x] POST 요청
  - [x] Query String 파싱
  - [x] http method enum 적용
  - [x] Path 클래스로 분리
  - [x] Protocol 클래스로 분리

# 1단계 피드백
- [x] 테스트에서 getter 비교보다는 객체를 비교

# 🚀 2단계 - HTTP 웹 서버 구현

### 요구사항1
> http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

### 기능 목록
- [x] InputStream(사용자의 요청)을 읽어 RequestLine을 생성한다
  - [x] InputStream을 BufferedReader로 변환
  - [x] BufferedReader로 HTTP 요청 메시지를 RequestLine으로 파싱한다
- [x] Path(Location)에 해당하는 파일을 읽어 응답한다.

---

### 요구사항2
> “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.  
회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.  
HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.

### 기능 목록
- [x] QueryString을 User클래스에 바인딩한다.
  - [x] QueryString 문자열을 Map<String, String> 타입으로 변환한다.
  - [x] User 클래스의 생성자를 활용하여 객체를 생성한다
    - [x] User를 생성하는 UserBinder 클래스 생성
    - [x] HttpMethod가 `GET`, Path의 Location이 `/user/create` 인 경우

---

### 요구사항3
> http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

### 기능 목록
- [x] user/form.html의 form 태그 method를 get에서 post로 수정
- [x] HTTP Request Body 파싱
  - [x] HTTP Header 파싱 (Body를 파싱하기 위해 ContentLength를 사용)
  - [x] HTTP Body 파싱 
- [x] HTTP Body를 이용해 User 객체를 생성한다.
  - [x] User 객체를 생성 조건의 HttpMethod를 POST로 변경한다.
  - [x] IOUtils#readData() 메서드를 활용해 문자열로 변환한다.

---

### 요구사항4
> “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

### 기능 목록
- 회원가입을 하는 경우 
- [x] 응답 헤더의 status code를 302로 설정한다.
- [x] 응답 헤더의 Location을 /index.html로 설정한다.

---

### 요구사항5
> “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.  
앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

### 기능 목록
- [x] 회원가입 시 메모리 DB에 회원정보 저장
- [x] 로그인 시 메모리 DB와 ID/PW 비교
  - [x] HttpMethod가 `POST`, Path의 Location이 `/user/login`
- [x] 로그인 성공
  - [x] Cookie 생성 (logined=true)
  - [x] index.html 이동
- [x] 로그인 실패 
  - [x] Cookie 생성 (logined=false)
  - [x] /user/login_failed.html 이동 


---

### 요구사항6
> 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.  
동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

### 기능 목록
- [x] 요청 헤더에서 Cookie 값을 읽어서 로그인 상태인지 확인한다.
- [x] 로그인 상태
  - [x] 사용자 목록을 출력한다
  - [x] handlebars template engine을 활용한 html 생성
- [x] 로그인 하지 않은 상태
  - [x] 로그인 페이지로 이동

---

### 요구사항7
> Stylesheet 파일을 지원하도록 구현하도록 한다.

### 기능 목록
- [x] Location의 확장자를 활용해 응답 헤더에 Content-Type을 지정한다.
- [x] ContentType은 enum으로 정의한다.
