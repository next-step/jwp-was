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
## 기능 정의
### Step1. HTTP Header를 파싱한다.
  ```text/plain
    GET /docs/index.html HTTP/1.1
    Host: www.nowhere123.com
    Accept: image/gif, image/jpeg, */*
    Accept-Language: en-us
    Accept-Encoding: gzip, deflate
    User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
    (blank line)
   ```


HttpHeaderParser
- response header 문자열을 파싱 한다.
- request header 문자열을 파싱 한다.


- HttpRequestParser
  - HttpRequestLineParser
    - request line을 파싱 한다.
    - uri는 쿼리 파라미터도 같이 파싱한다.
    - HttpRequestLine 객체를 생성한다.
 

- HttpResponseParser
  - HttpStatusLineParser
    - status line을 파싱 한다.
    - HttpStatusLine 객체를 생성 한다.

- HttpResponse를 생성 한다.
  - HttpHeader, (HttpContents) 객체를 포함한다.
  - 기본 Response가 있으며 사용자에 의해 교체 가능 해야 한다.

- HttpRequest를 생성 한다.
  - HttpHeader, (HttpContents) 객체를 포함한다. 
  - 기본 Request가 있으며 사용자에 의해 교체 가능 해야 한다.
    - 필수 입력 파라미터가 존재한다.(ex. uri, method)










- HTTP 요청을 받거나 응답을 보낸다.
- 요청 메시지는 request line, headers, contents로 나누어 진다.
- 응답 메시지는 status line, headers, contents로 나누어 진다.
- header는 여러 개의 키와 값을 포함한다.
- header는 키와 값을 구분하는 구분자는 :로 한다. : 사이에는 공백이 존재할 수도 있다.
- version은 protocol name, major version, minor version로 구분된다.
- status line은 version, status code, reason phrase로 구분된다.
- 프레임워크 제공의 입장에서 보았을 때 GET과 POST구분은 사용자의 필요에 의해서 생성되어야 할 수도 있다.
  - 즉 프레임워크 입장에서 method는 입력 되는 데이터 중 하나이다.
  - 단지 method에 따라 처리 해주어야 하는 방법이 달라지는 것이다.
- contents를 포함하는 경우와 포함하지 않는 경우가 존재한다.
- 다수의 요청을 처리할 때 모든 컨텐츠를 힙에 올리지 않아야 한다.
  - 올리길 원하는 경우 설정 값으로 컨트롤 할 수 있게 만들어야 한다.
- request 라인은 SP로 구분된다.
- 헤더와 컨텐츠 사이에는 CRLF로 구분된다.
- uri는 쿼리 파라미터도 파싱되어야 한다.
  - uri 형태는 전략에 따라 다른 형태를 가진다. 즉 전략에 따라 서버에서 처리할 수 있는 방법이 달라진다.
- 헤더의 key들은 직접 입력 할 경우 오기입의 여지가 있으므로 미리 정의하여 제공한다.



- HttpRequest
  - RequestLine
    - method: GET, POST, HEAD, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH
    - uri: /docs/index.html
    - version: HTTP/1.1
      - protocolName
      - majorVersion
      - minorVersion
    - headers:
      - key: Accept
        - value: image/gif, image/jpeg, */*
    - contents:
      - (blank line)
    - chunked: false

- HttpResponse
  - StatusLine
    - version: HTTP/1.1
    - status: 200, 404, 500, ...
    - reason: OK, Not Found, Internal Server Error, ...
    - headers:
      - Content-Type: text/html
      - Content-Length: 1234
    - contents:
      - (blank line)

- HttpContents
  - contents:
    - \<html\>
      - \<head\>
        - \<title\>Hello, World!\<\/title\>
      - \<\/head\>
      - \<body\>
        - \<h1\>Hello, World!\<\/h1\>
      - \<\/body\>
    - \<\/html\>

- HttpChunk
  - chunk:
    - chunk-size: 1234
    - chunk-data: ...
    - (blank line)
  - last-chunk:
    - chunk-size: 0
    - (blank line)
  

 ---
 - [참고] RFC 7230/7231 스펙
   * 모든 HTTP/1.1 메시지는 시작 라인과 그 뒤에 오는 시퀀스로 구성됩니다.
     * 시작 라인은 아래와 같이 구성되며 각 요소 사이는 single space(SP)로 구분됩니다.
         ```text/plain
         request-line   = method SP request-target SP HTTP-version CRLF
         ```
   * request-target은 적용 할 대상 리소스를 식별합니다 서버가 해석할 수 있는 것보다 길다면 414로 응답 합니다.
   * method 토큰은 대상 리소스에서 수행 할 요청 방법을 나타내며 대소문자를 구분합니다.
   * 유효하지 않은 요청 라인을 수신한 경우 400(잘못된 요청)으로 응답합니다.
   * RFC 7302는 content-length 없는 head를 허용한다.
   * GET 캐시
     * Cache-Control 헤더 필드에 의해 달리 표시되지 않는 한 캐시 사용 가능
   * POST 캐시
     * 기존과 동일한 처리를 요구하는 경우 303으로 응답하여 캐시된 해당 리소스로 리다이렉션 시킨다.
       * Location 필드에 해당 위치를 표시
       * user-agent에 아직 캐시되지 않은 경우 추가 요청 비용이 발생
  

