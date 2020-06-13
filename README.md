# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 구현 기능 목록
### STEP1
#### HTTP RequestLine 파싱
* GET, POST 요청에 대한 RequestLine 파싱
* Query String 파싱

### STEP2
#### 요구사항 1: HTTP Request를 받아 처리 후 리소스를 HTTP Response로 반환
> http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
* 수신한 inputstream을 requestHeader로 읽기
* RequestLine에서 path 추출
* path에 대응하는 파일 읽어 outputstream통해 응답
#### 요구사항 2: Request Parameter 읽어 회원가입 처리
* user/form.html에서 회원가입 요청 - GET 요청
* Request Parameter의 유저정보를 파싱
* 유저정보를 model.User 클래스에 저장
#### 요구사항 3: Request Body 읽어 회원가입 처리
* user/form.html에서 회원가입 요청 - POST 요청
* Request Body의 유저정보를 파싱
* 유저정보를 model.User 클래스에 저장
#### 요구사항 4: 회원가입 완료 시 index.html로 redirect
#### 요구사항 5
##### 로그인 기능 구현하고 성공/실패 여부에 따라 다른 페이지로 이동
* 성공 시 index.html로 이동
* 실패 시 /user/login_failed.html로 이동
##### Cookie를 구현하고 이를 로그인 여부 확인에 활용
* 성공 시 응답 header의 쿠키 헤더 값이 `logined=true`
* 싪패 시 header 값 `logined=false`로 전달
#### 요구사항 6: Cookie 값에 따라 특정 url 접속 분기 처리
* 로그인했을 경우, 사용자 목록 출력
* 로그인하지 않은 경우, 로그인 페이지로 이동
#### 요구사항 7: html이외의 정적 자원 요청에 응답하기