# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## Step1
- GET요청 파싱을 위한 RequestParser 클래스 생성 및 테스트코드 작성
- POST요청 파싱을 위한 RequestParser 클래스 생성 및 테스트코드 작성
- ENUM 적용

## Step2
### 기능 요구사항 1
- RequestHeader 도메인 구현
- Request 도메인 구현
- path 값을 응답

### 기능 요구사항 2
- User 클래스 생성
- RequestLine 파라미터 분리
- 분리한 파라미터 메소드로 User 생성