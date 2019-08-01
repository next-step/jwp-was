# 0단계 - TDD로 RequestLine 파싱

## 실습 환경 구축 및 코드 리뷰
* 첫 번째 미션인 HTTP 웹 서버 구현 실습 저장소 저장소 브랜치에 자신의 github 아이디에 해당하는 브랜치가 있는지 확인한다. 없으면 미션 시작 버튼을 눌러 미션을 시작한다.
* 온라인 코드리뷰 요청 1단계 문서의 1단계부터 5단계까지 참고해 실습 환경을 구축한다.
* webserver.WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접근한다.
* 브라우저에 “Hello World”이 뜨면 정상적으로 세팅된 것이다.

## HTTP 웹 서버 코드 리뷰
* 서버 시작은 WebServer 클래스가 담당
* 클라이언트의 요청을 받고 응답을 보내는 모든 작업은 RequestHandler 클래스가 담당
    * 실습으로 진행할 모든 요구사항은 RequestHandler 클래스에 구현하면 된다.

## 실습 요구사항
### HTTP 요청 예
```
GET /docs/index.html HTTP/1.1
Host: www.nowhere123.com
Accept: image/gif, image/jpeg, */*
Accept-Language: en-us
Accept-Encoding: gzip, deflate
User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
(blank line)
```
  
### HTTP 응답 예
```
HTTP/1.1 200 OK
Date: Sun, 18 Oct 2009 08:56:53 GMT
Server: Apache/2.2.14 (Win32)
Last-Modified: Sat, 20 Nov 2004 07:16:26 GMT
ETag: "10000000565a5-2c-3e94b66c2e680"
Accept-Ranges: bytes
Content-Length: 44
Connection: close
Content-Type: text/html
X-Pad: avoid browser bug

<html><body><h1>It works!</h1></body></html>
```

* HTTP 요청(request) Header의 첫번째 라인(Request Line)을 파싱한다.
* 파싱하는 로직 구현을 TDD로 구현한다.
  
### Request Line 예1 - GET 요청
```
GET /users HTTP/1.1
```
  
 ### Request Line 예2 - POST 요청
```
POST /users HTTP/1.1
```

#### 힌트
* String 클래스의 split(" ")을 활용해 빈 공백 문자열(space)로 분리

## 실습 마무리
* 실습을 끝내면 코드 리뷰 1단계 문서의 7단계, 8단계를 참고해 자신의 저장소에 push한다.
* 코드 리뷰 2단계 문서를 참고해 코드 리뷰 요청(pull request)을 보내고, NextStep 우측 상단의 Github 아이콘을 클릭해 리뷰 요청을 보낸다.
* 피드백 또는 merge 될 때까지 기다린다.

> PR에 대한 수정 요청을 받아 코드를 수정하는 경우 새로운 PR을 보낼 필요가 없다.
> 코드를 수정한 후 add/commit/push만 하면 자동으로 해당 PR에 추가된다.
  
* Slack을 통해 merge가 되는지 확인한 후에 코드 리뷰 3단계 과정으로 다음 단계 미션을 진행한다.
