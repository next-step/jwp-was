# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)
## 깃 커밋 메시지 컨벤션
```text/plain
feat (feature)
fix (bug fix)
docs (documentation)
style (formatting, missing semi colons, …)
refactor
test (when adding missing tests)
chore (maintain)
```
## 요구 사항
-[x] request를 분석할 수 있다. 
  - Request Line을 파싱한다. 
  - Method, Path, Protocol, Version을 파싱한다. 
  - Path에서 Query를 분리하여 파싱한다.

-[x] 사용자가 접속하면 /index.html view를 볼 수 있다.
  - Headers를 파싱한다.
  - path에 해당하는 위치를 찾는다
  - 해당 위치에 /index.html을 읽어서 응답에 포함시킨다.
  - 해당위치에 리소스가 존재하지 않는 경우 404를 반환한다.
-[x] 회원가입을 요청하면 사용자는 회원가입을 할 수 있다.
-[x] 회원가입을 완료하면 /index.html로 리다이렉션 되어야 한다.
-[x] 사용자는 /user/login.html을 통하여 로그인이 가능하다.
-[x] /user/list에서 사용자 목록을 볼 수 있다. 로그인 상태가 아니라면 login.html로 리다이렉션 된다.
-[x] stylesheet 파일을 지원한다.

-[x] 세션지원으로 로그인 상태를 유지할 수 있다.
-[x] 클라이언트는 쿠키를 통해 세션아이디를 서버에 전달한다.
-[x] 서버는 세션아이디를 통해 세션을 찾는다.
-[x] 세션이 존재하지 않는 경우 새로운 세션을 생성한다.

-[x] 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.

