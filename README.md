# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


## STEP 1 요구사항
* GET, POST 요청에 대한 RequestLine을 파싱한다.
* 요청의 Query String을 파싱한다.
* HTTP method를 enum으로 구현

## STEP 1 테스트 영역
* inputStream test (inputStream을 읽어서 입력한 값이랑 일치하는지 확인)
* GET 요청 파싱 test
* POST 요청 파싱 test
* Query String 파싱 test
* 기타 요청 예외처리 test

