# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## step 1 - TDD 실습

### 요구사항 1 - GET 요청
- [o] HTTP GET 요청에 대한 RequestLine을 파싱

### 요구사항 2 - POST 요청
- [o] HTTP POST 요청에 대한 RequestLine을 파싱

### 요구사항 3 - Query String 요청
- [o] HTTP 요청의 Query String 으로 전달되는 데이터 파싱

### 요구사항 4 - enum 적용
- [o] HTTP metho를 enum 으로 구현 


## step 2 - HTTP 웹 서버 구현

### 요구사항 1 
- [o] BufferedReader api를 이용해 모든 Request Header 출력하기
- [o] 첫 번째 Request Line 에서 요청 URL 추출하기
- [o] path에 해당하는 파일을 읽어서 응답하기

### 요구사항 2
- [o] 회원가입 메뉴를 클릭하면 http://localhost:8080/user/form.html 로 이동하고 회원가입
- [o] 쿼리스트링으로 들어오는 사용자의 입력값을 파싱해서 model.User 클래스에 저장하기  

### 요구사항 3
- [o] 회원가입 기능을 get 에서 post 로 수정하기
- [o] 수정 후에도 정상적으로 User 객체 생성하기

### 요구사항 4
- [o] 회원가입이 완료되면 302 리다이렉트를 이용해서 /index.html 로 이동

### 요구사항 5
- [o] 회원가입때 생성한 User 객체를 DataBase.addUser() 메서드를 활용해 RAM 메모리에 저장
- [p] 클라이언트에서 로그인을 성공하면 index.html, 실패하면 /user/login_failed.html 로 이동
- [o] 로그인 시, 아이디와 비밀번호가 같은지 확인하고 성공하면 header의 set-Cookie 값을 logined=true, 실패하면 logined=false로 설정
- [o] Set-Cookie 설정시 모든 요청에 대해 Cookie 처리가 가능하도록 Path 설정 값을 /(Path=/)로 설정
- [o] 응답 header에 Set-Cookie값을 설정한 후 요청 header에 Cookie이 전달되는지 확인

### 요구사항 6
- [o] 접근하는 사용자가 "로그인" 상태일 경우, "/user/list" 로 접근했을 때, 사용자 목록 출력
- [o] 로그인 하지 않을 시, "login.html" 페이지로 이동

### 요구사항 7
- [o] stylesheet 파일을 지원하도록 구현하기

