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