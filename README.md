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
- [x] 로그인이 성공 : redirect : index.html (header cookie : logined=true)
- [x] 로그인이 실패 : redirect : /user/login_fail.html (header cookie : logined=false)

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