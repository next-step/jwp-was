## 요구사항 정리

### 요구사항 1 - GET 요청
- HTTP GET 요청에 대한 RequestLine을 파싱
- 파싱하는 로직 구현을 TDD로 구현
- 예) "GET /users HTTP/1.1" 파싱
  - method: GET
  - path: /users
  - protocol: HTTP
  - version: 1.1
  
### 요구사항 2 - POST 요청
- HTTP POST 요청에 대한 RequestLine을 파싱
- 파싱하는 로직 구현을 TDD로 구현
- 예) "POST /users HTTP/1.1" 파싱
  - method: POST
  - path:  /users
  - protocol: HTTP
  - version: 1.1

### 요구사항 3 - Query String 요청
- HTTP 요청(request)의 Query String 으로 전달되는 데이터 파싱
- 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2 와 같은 구조로 전달됨
- 파싱하는 로직 구현을 TDD로 구현

### 요구사항 4 - enum 적용
- HTTP method인 GET, POST를 enum 으로 구현 