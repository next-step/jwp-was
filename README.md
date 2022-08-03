# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 요구사항
### 1 단계
- [x] GET 요청, POST 요청 RequestLine 생성
- [x] Query String 파싱
- [x] enum 적용

### 2단계
#### 기능 요구사항 1. 
- [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
  - [x] http header 전체를 출력한다.
  - [x] 1단계에서 얻은 Path로 src/main/resources 디렉토리에서 읽어 전달
#### 기능 요구사항 2. 
- [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
  - [x] query string을 추출 하는 기능이 구현 되어 있으니, model.User 클래스에 저장한다.
#### 리팩토링
- [x] 패키지 구조를 변경하여 한 눈에 구조가 들어오도록 한다.
  - [x] http 패키지를 만들어 그 안에 필요한 패키지를 생성
  - [x] header 패키지를 만들고 Header 클래스에 필요한 객체들을 생성한다.
  - [x] header 패키지가 사용 되도록 리팩토링 한다.
#### 기능 요구사항 3.
- [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
  - [x] Post 메서드 일 때 requestBody를 읽도록 조건을 추가한다.
  - [x] reuqestBody를 파싱한다.
  - [x] 회원가입시 입력한 모든 데이터를 추출해 User 객체를 생성한다.
#### 기능 요구사항 4.
- [x] redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다.
  - [x] 응답 URL을 index.html로 지정
  - [x] 회원가입시 입력한 모든 데이터를 추출해 User 객체를 생성한다.
### 리팩토링
- [x] 응답 반복 코드를 제거한다.
  - [x] 응답을 관리하는 클래스를 만든다.
  - [x] 응답 Http status를 enum으로 관리한다.
#### 기능 요구사항 5.
- [x] 로그인 성공 실패 분기 처리를 한다.
  - [x] 앞 단계에서 회원가입할 때 생성한 User 객체를 DataBase.addUser() 메서드를 활용해 RAM 메모리에 저장한다.
  - [x] login 실패 시 login_failed.html로 이동하도록 한다.
  - [x] logined=false 값을 넎는다.
#### 기능 요구사항 6.
- [x] 로그인 되었을 때 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력
  - [x] handlebars.java template engine 사용하여 동적으로 html을 생성
#### 기능 요구사항 7.
- [x] Stylesheet 파일을 지원하도록 구현하도록 한다.

### 3단계
#### 기능 요구사항 1.
- [x] HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
  - [x] 현재 Header와 Body는 분리가 되어 있다. HttpMethod를 request 패키지로 이동한다.
  - [x] 현재 가지는 구조는 한 눈에 보기 힘들다. webserver 아래로 httprequest와 httpresponse를 나누고 그 아래에 service를 두도록 한다.
  - [x] 다형성을 활용해 요청에 관한 URL handler를 분기 하도록 한다. -> 인터페이스를 활용해 보도록 한다. -> Map을 이용하자.
  - [x] RequestHandler의 다중 if가 제거 되었는지 체크한다. 만약 되지 않았다면 인터페이스를 활용한 handler분기가 제대로 되지 않은 것이니 다시 리팩토링 한다.
  - [x] endPoint가 늘어나는 구조가 변경 되었는지 확인한다. 아닐 시 이를 리팩토링 한다.
  - [x] 외부 공개가 필요 없는 상수의 접근제어자를 확인, 상수와 필드 사이의 공백이 있는지 체크.
  - [x] ContentType 다중 if로 처리되지 않도록 한다. (스트림을 사용)
  - [x] UesrParser의 책임을 분리한다. 요청에 관한 handler가 분리 될 테니 그에 맞게 요청의 내용을 각각의 역할로 분리 시킨다. (즉 역할이 모두 클래스로 나뉘도록 한다.)
  - [x] CreateHeader의 역할을 체크한다. 분리되지 않았다면 다중 if문을 제거 하도록 한다.
  - [x] "/user/create" 라는 상수가 여러 곳에서 사용이 되고 있는지 체크한다. 분리가 잘 되었다면 여러곳에서 사용되지 않을 것이다.
  - [x] 테스트 클래스 마지막 줄이 공백이 되도록 추가한다.
#### 기능 요구사항 2.
- [x] 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
  - [x] 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse) -> 1번에서 활용한 다형성으로 응답을 각각의 역할로 분리시키자.

### 4단계
#### 기능 요구사항 1.
- [ ] 서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다.
  - [ ] String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
  - [ ] void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
  - [ ] Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
  - [ ] void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
  - [ ] void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
- [ ] 쿠키를 통해 고유 아이디로 session을 불러 오도록 한다.
- [ ] 세션 스토리지를 구현한다.


