# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

----

# HTTP 웹 서버 구현

### Step2 요구사항 
* `페이지 이동`
- [x] webapp directory file read and responseBody write 

* `회원가입` 
- [x] 회원가입 페이지로 이동 (page: /user/form.html, method: get)
- [x] 회원가입 (action: /user/create, method: post)
- [x] 회원가입 성공 시 메인 페이지로 이동
    * redirect
    - [x] httpResponse status 302
    - [x] header Location (index.html) 

* `로그인` /user/login.html        
- [x] 로그인이 성공 : redirect : index.html (header cookies : logined=true)
- [x] 로그인이 실패 : redirect : /user/login_fail.html (header cookies : logined=false)

*  `회원 리스트` /user/list
- [x] 로그인 X (logined = false): 로그인 페이지로 이동 (login.html)
- [x] 로그인 O (logined = true): 회원 목록 보기 (/user/list)

- [x] viewResolver : templateEngine 
- [x] Accept : html, css, js... 인 경우, 응답 header contentType 에 담아준다

---
### Step3 웹 서버 리팩토링
* WAS 기능 요구사항
- [x] Thread pool 구현
 
* HTTP 요청/응답 처리 기능
- [x] Http Request Header/Body 분리
- [x] Http Response Header/Body 분리
- [x] HttpHeaderProperty 분리
- [x] servlet -> controller
- [x] request parameter merge

---

### Step4 세션 구현하기
* 요구사항
- Servlet 에서 지원하는 HttpSession API
- [x] String getId() 
- [x] void setAttribute(String name, Object value)
- [x] Object getAttribute(String name) 
- [x] void removeAttribute(String name) 
- [x] void invalidate() 

```
String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
세션은 클라이언트와 서버 간에 상태 값을 공유하기 위해 고유한 아이디를 활용하고, 
이 고유한 아이디는 쿠키를 활용해 공유한다.
```

SessionId 생성
UUID uuid = UUID.randomUUID();

Map<String, HttpSession>와 같은 구조가 될 것이다. 
이 Map의 키(key)는 앞에서 UUID로 생성한 고유한 아이디이다.