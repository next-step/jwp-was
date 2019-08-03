# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 참고 문서
* https://tools.ietf.org/html/rfc2616

## TODO
* 0단계 - TDD로 RequestLine 파싱
    * [o] HTTP Request Header의 첫번째 라인(Request Line) 파싱
        * [o] RequestLine 클래스 추가
* 1단계 - Header 파싱, if문 제거
    * [o] Query String 파싱
        * [o] HTTP 요청(request)의 Query String으로 전달되는 데이터 파싱
            * [o] RequestURI, Query 클래스 추가하여 파싱하도록 처리
    * [o] JUnit5 학습
        * [o] QueryTest에 @ParameterizedTest를 적용
    * [o] if문 제거 연습
        * [o] FigureFactory 클래스의 getInstance() 메소드에서 if절 없이 객체 생성하도록 구현
        * [o] Map Collection을 활용하여 구현
