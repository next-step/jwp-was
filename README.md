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
- [ ] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
  - [ ] query string을 추출 하는 기능이 구현 되어 있으니, model.User 클래스에 저장한다.
#### 기능 요구사항 3.
- [ ] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
  - [ ] Post 메서드 일 때 requestBody를 읽도록 조건을 추가한다.
  - [ ] reuqestBody를 파싱한다.
  - [ ] 회원가입시 입력한 모든 데이터를 추출해 User 객체를 생성한다.
#### 기능 요구사항 4.
- [ ] redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다.
  - [ ] 응답 URL을 index.html로 지정
  - [ ] 회원가입시 입력한 모든 데이터를 추출해 User 객체를 생성한다.
#### 기능 요구사항 5.
- [ ] 로그인 성공 실패 분기 처리를 한다.
  - [ ] 앞 단계에서 회원가입할 때 생성한 User 객체를 DataBase.addUser() 메서드를 활용해 RAM 메모리에 저장한다.
  - [ ] login 실패 시 login_failed.html로 이동하도록 한다.
  - [ ] logined=false 값을 넎는다.
#### 기능 요구사항 6.
- [ ] 로그인 되었을 때 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력
  - [ ] handlebars.java template engine 사용하여 동적으로 html을 생성
#### 기능 요구사항 7.
- [ ] Stylesheet 파일을 지원하도록 구현하도록 한다.
  