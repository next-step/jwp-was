# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단계 - TDD 실습
### 요구사항 공통부
* HTTP 요청의 첫번째 라인을 의미하는 RequestLine을 구현해야 한다. 
* 각 요청을 파싱하여 보여주기 위한 ResultView를 구현해야 한다.

### 요구사항1 - GET 요청
* HTTP GET 요청에 대한 RequestLine을 파싱한다.
* 파싱하는 로직 구현을 TDD로 구현한다.
* "GET /users HTTP/1.1" 결과
  * method는 GET
  * path는 /users
  * protocol은 HTTP
  * version은 1.1
  