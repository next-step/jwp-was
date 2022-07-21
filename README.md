# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 1단계 - TDD 실습
HTTP 요청/응답을 파싱해 원하는 값을 가져올 수 있는 API를 TDD로 구현한다.

- 요구사항
  - RequestLine 을 파싱해 원하는 값을 가져올 수 있는 API 를 제공한다.
  - RequestLine 은 HTTP 요청의 첫 번째 라인을 의미한다.
    - GET / POST 요청을 파싱한다.
    - Http 요청의 Query String 을 파싱한다.
    - Http Method enum 을 적용한다.

- 구현
  - RequestLine 구현
  - 관련 테스트 작성
  - HTTP Method enum 구현
