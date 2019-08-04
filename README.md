# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

##1주차 작업 내역
feat ($step1): HTTP 파싱 및 IF문 중복 제거

새로 추가한 작업 $step1:
- FigureConstructor 클래스 추가
- FigureFactory 클래스 내 중복 IF문 FigureConstructor 클래스 사용하여 해결
- RequestLineTest 파라미터 파싱 테스트 추가
- RequestLine 파라미터 분리 메소드 추가

새로 추가한 작업 $step2:
- UserController 클래스 추가 (유저 관련 호출 담당)
- ControllerCreator 인터페이스 추가
- HttpStatus Enum 추가 (HTTP 상태값 관리)
- HttpControllerManage 클래스 추가 (컨트롤러 클래스 관리)
- HttpMethodParameter 클래스 추가 (GET, POST 파라미터 추출 클래스)
- HttpResponse 클래스 추가 (HTTP 응답 담당)