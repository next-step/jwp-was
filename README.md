## 프로그래밍 요구사항

* 모든 로직에 단위 테스트를 구현한다. 단, 테스트하기 어려운 UI 로직은 제외
    - 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분한다.
    - UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리한다.

* 자바 코드 컨벤션을 지키면서 프로그래밍한다.
    - 이 과정의 Code Style은 intellij idea Code Style. Java을 따른다.
    - intellij idea Code Style. Java을 따르려면 code formatting 단축키(Windows : Ctrl + Alt + L. Mac : ⌥ (Option) + ⌘ (Command)
        + L.)를 사용한다.

* 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기(indent)만 한다.
    - indent가 2이상인 메소드의 경우 메소드를 분리하면 indent를 줄일 수 있다.
    - else를 사용하지 않아 indent를 줄일 수 있다.

## 기능 목록 및 commit 로그 요구사항

* 기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
* git의 commit 단위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.

## git commit message

* feat (feature)
* fix (bug fix)
* docs (documentation)
* style (formatting, missing semi colons, …)
* refactor
* test (when adding missing tests)
* chore (maintain)

<hr>

# 미션 요구 사항

## RequestLine을 파싱한다.

* RequestLine을 파싱해 원하는 값을 가져올 수 있는 API를 제공해야 한다.
* RequestLine은 HTTP 요청의 첫번째 라인을 의미한다.

### 요구사항 1 - GET 요청

* HTTP GET 요청에 대한 RequestLine을 파싱한다.
* 파싱하는 로직 구현을 TDD로 구현한다.
* 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 GET
    - path는 /users
    - protocol은 HTTP
    - version은 1.1

### 요구사항 2 - POST 요청

* HTTP POST 요청에 대한 RequestLine을 파싱한다.
* 파싱하는 로직 구현을 TDD로 구현한다.
* 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 POST
    - path는 /users
    - protocol은 HTTP
    - version은 1.1

### 요구사항 3 - Query String 파싱

* HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
* 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
* 파싱하는 로직 구현을 TDD로 구현한다.

#### Query String 예 - GET 요청

```bash
GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1
```

### 요구사항 4 - enum 적용(선택)

* HTTP method인 GET, POST를 enum으로 구현한다.
