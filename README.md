# 웹 애플리케이션 서버

## 진행 방법

* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정

* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

---

## 구현할 기능 목록

### 1단계

- [X] GET 요청 파싱
- [X] POST 요청 파싱
- [X] Query String 파싱
- [X] HttpMethod를 enum 타입으로 변경

### 2단계

- [X] 모든 header 파싱 및 index.html 요청 응답
- [X] GET 요청 시 query string 파싱
- [X] POST로 유저 생성 요청 시 body 파싱
- [X] http response에 header 설정 추가
- [X] header에서 쿠키 파싱 및 쿠키 사용
- [X] handlebars 사용하여 동적 페이지 추가
- [X] stylesheet 지원
