# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

### 기능 요구사항 명세
#### Step 2
1. http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답.
2. “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입.
3. http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현.
4. “회원가입”을 완료하면 /index.html 페이지로 이동.
5. “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 
    로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동.
6. 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력.
   만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동.
7. Stylesheet 파일을 지원하도록 구현.
#### Step 3
1. 리팩토링
   1. 메소드와 클래스 분리를 통하여 메소드가 한 가지 일을 하도록 리팩토링.
   2. 요청 데이터를 처리하는 로직을 별도 클래스로 분리.
#### Step 4
1. 세션 구현하기
   1. 세션 아이디를 반환
   2. 세션에 value 인자로 전달되는 객체를 저장
   3. 세션 객체 값 반환
   4. 세션 객체 값 삭제
   5. 세션의 모든 값 삭제
#### Step 5
1. ThreadPoolExecutor를 활용하여 Thread Pool 기능 추가
   1. 쓰레드의 크기는 250
   2. 쓰레드 대기 상태의 최대 값은 100
2. 서버의 ThreadPool보다 많은 요청을 보내본다.
