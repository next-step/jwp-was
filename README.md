### 1단계 - Header 파싱, if문 제거
- 요구사항 1 : Query String 파싱
- 요구사항 2 : @ParameterizedTest 사용
- 요구사항 3 : if문 제거

### 2단계 - HTTP 웹 서버 구현 
- 요구사항 1 : html 파일 응답
    - Request Header 파싱
    - *.html 로 요청이 들어왔을때 html 파일 return
- 요구사항 2 : 회원가입 Get Request 방식으로 구현
    - model.User 클래스에 저장
- 요구사항 3 : 회원가입 Post Request 방식으로 수정
    - RequestBody 파싱 구현
- 요구사항 4 : redirect 구현
    - Redirect Response 구현
- 요구사항 5 : 로그인 구현
    - 로그인 시도 후 이동페이지 분기
    - 로그인 결과 Cookie 저장
- 요구사항 6 : 사용자 목록 출력
    - 인증된 사용자면 사용자 목록 출력
    - 인증되지 않은 사용자면 로그인 페이지로 이동
- 요구사항 7 : Stylesheet 파일 지원
    - Request Contents-Type에 맞는 Response 구현 
