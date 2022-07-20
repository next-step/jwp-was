# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 요구사항
### 프로그래밍 요구사항
* 모든 로직에 단위 테스트를 구현한다. 단, 테스트하기 어려운 UI 로직은 제외
* 자바 코드 컨벤션을 지키면서 프로그래밍한다.
* 한 메서드에 오직 한 단계의 들여쓰기(indent)만 한다.

### 기능 목록 및 Commit 로그 요구사항
* 기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
* git의 commit 단위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.

```text
feat (feature)
fix (bug fix)
docs (documentation)
style (formatting, missing semi colons, …)
refactor
test (when adding missing tests)
chore (maintain)
```

### RequestLine Parsing 요구사항
**1. GET 요청**
  * HTTP GET 요청에 대한 RequestLine을 파싱한다.
  * 파싱하는 로직 구현을 TDD로 구현한다.

**2. POST 요청**
   * HTTP POST 요청에 대한 RequestLine을 파싱한다.
   * 파싱하는 로직 구현을 TDD로 구현한다.

**3. Query String 파싱**
   * HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
   * 클라이언트에서 서버로 전달되는 데이터의 구조는 `name1=value1&name2=value2`와 같은 구조로 전달된다.
   * 파싱하는 로직 구현을 TDD로 구현한다.

**4. Enum 적용**
   * HTTP method인 GET, POST를 enum으로 구현한다.
