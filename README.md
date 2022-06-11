# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)



## step1. 요구사항
HTTP 요청과 RequestLine을 파싱해 원하는 값을 가져올 수 있는 API를 제공해야 한다.(RequestLine은 HTTP 요청의 첫번째 라인을 의미한다.)

- 요청 값을 파싱해서 결과 값을 반환하는 Parser 클래스와 parse 메소드를 구현한다.
- 파싱된 결과 값을 저장할 오브젝트(RequestLine)를 추가한다.
  - HttpMethod, Protocol(protocol, version), Uri(path, QueryString) 오브젝트로 구성된다.

