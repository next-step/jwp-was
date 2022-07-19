## 웹 애플리케이션 서버
#### 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

#### 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

----
## 구현할 기능 목록
- [x] Request Line은 RFC7230 (https://www.rfc-editor.org/rfc/rfc7230) -> 3.1.1. Request Line 의 정의에 따라 String 타입의 `method SP request-target SP HTTP-version CRLF` 의 구조를 가진다.
- [x] method는 Enum으로 구현하여야 하며 형태는 RFC7231 (https://www.rfc-editor.org/rfc/rfc7231#section-4) 의 정의에 따라  
  `GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE`의 형태를 가진다.
- [x] HttpParser는 RequestLine을 method, path(request-target), protocaol, version으로 분리 할 수 있다.
- [ ] Request Line 객체를 만든다.
- Request Line 객체는 method, path(request-target), protocaol, version을 필드로 가진다.
- [ ] Query String이 포함된 형태의 Request Line이 들어오면 쿼리 스트링을 파싱 할 수 있다. (Map 형태로 파싱할예정)
- Request Line 객체에 Map타입의 Query String 필드를 추가한다.
        
