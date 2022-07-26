# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 요구사항
### STEP 1
- [X] GET 요청 파싱하기
- [X] POST 요청 파싱하기
- [X] QueryString 파싱하기
- [X] HttpMethod 에 enum 적용하기
### STEP 2
- 요구사항 1
  - [X] InputStream을 RequestLine으로 변환하기
  - [X] Request 헤더를 출력하기
  - [X] path에 해당하는 파일 읽어 응답하기
- 요구사항 2
  - [X] GET `/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net` 요청 처리하기
    - [X] Request Parameter 추출하기
    - [X] Request Parameter 디코딩하기
    - [X] 빌더 패턴 이용하여 User 객체 생성하기
