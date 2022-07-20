## HTTP 이해 - 웹 서버 구현


### 기능 요구 사항

- HTTP GET, POST 요청에 대한 RequestLine을 파싱한다.
- 파싱하는 로직 구현을 TDD로 구현한다.
- 예를 들어 `"GET /users HTTP/1.1"`을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
  - method는 GET
  - path는 /users
  - protocol은 HTTP
  - version은 1.1 
- HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다. 
- 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
- 파싱하는 로직 구현을 TDD로 구현한다.



### 프로그래밍 요구사항

- 모든 로직에 단위 테스트를 구현한다. 단, 테스트하기 어려운 UI 로직은 제외
  - 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분한다.
  - UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리한다.
- 자바 코드 컨벤션을 지키면서 프로그래밍한다.
   - 이 과정의 Code Style은 intellij idea Code Style. Java을 따른다.
   - intellij idea Code Style. Java을 따르려면 code formatting 단축키(Windows : Ctrl + Alt + L. Mac : ⌥ (Option) + ⌘ (Command) + L.)를 사용한다.
- 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기(indent)만 한다.
   - indent가 2이상인 메소드의 경우 메소드를 분리하면 indent를 줄일 수 있다.
   - else를 사용하지 않아 indent를 줄일 수 있다.

## 기능 구현 목록

### Domain

- RequestLine
  - RequestLine의 구성 요소를 모두 포함하고 있는 객체
    - HTTP Method
    - Path (+ Query String)
    - ProtocolType & Version
  * `" "`, `"?"`으로 RequestLine 파싱하여 method, path, protocol 분리
  * HttpMethod가 소문자를 포함하는 경우 예외 발생

* HttpMethod
  * GET, POST 두가지로 나뉨.

* Protocol
  * Protocol 정보를 가지고 있는 객체
  * `/`으로 protocol을 파싱하여 protocolType과 Version 분리
  * protocolType이 대문자가 아닌 경우 예외 발생

* ProtocolType
  * HTTP 포함

* Path
  * `?`로 path의 QueryString을 분리

* QueryParameters
  * `&`로 QueryString 분리
  * `=`로 key-value형태의 pairs에 데이터 저장
