# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 기능 요구사항
* HTTP 요청과 응답을 파싱해 원하는 값을 가져올 수 있는 API 제공
1. GET 요청
    1-1. RequestLine을 파싱하여 method, pathInformation, protocolInformation, version 을 추출한다. (protocolInformation, version은 하나의 클래스로)
    1-2. class 분리를 통해 인스턴스 변수 별 유효성 검사를 진행한다.
    1-3. Http method인 GET, POST를 enum으로 구현한다. 
2. POST 요청 : GET 요청과 마찬가지로 RequestLine 파싱하여 데이터를 추출한다.
3. Query String 파싱
    3-1. RequestLine을 파싱하여 데이터를 추출한다.
    3-2. pathInformation 클래스에 Query String 인스턴스 변수를 추가해 데이터를 파싱한다.