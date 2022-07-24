# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


# 기능 목록
- RequestLine 파싱 
  - [x] 문자열이 비어 있으면 파싱할 수 없다.
  - [x] GET 요청
    - [x] method, path, protocol, version
  - [x] POST 요청
  - [x] Query String 파싱 
  - [x] http method enum 적용
  - [x] Path 클래스로 분리
  - [x] Protocol 클래스로 분리

# 1단계 피드백
- [x] 테스트에서 getter 비교보다는 객체를 비교

# 🚀 2단계 - HTTP 웹 서버 구현

> 요구사항1 - http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

### 기능 목록
- [x] InputStream(사용자의 요청)을 읽어 RequestLine을 생성한다
  - [x] InputStream을 BufferedReader로 변환
  - [x] BufferedReader로 HTTP 요청 메시지를 RequestLine으로 파싱한다
- [x] Path(Location)에 해당하는 파일을 읽어 응답한다.

> 요구사항2 - “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.  
회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.  
HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.

### 기능 목록
- [ ] QueryString을 User클래스에 바인딩한다.
  - [x] QueryString 문자열을 Map<String, String> 타입으로 변환한다.
  - [x] User 클래스의 생성자를 활용하여 객체를 생성한다
    - [x] User를 생성하는 UserBinder 클래스 생성
    - [ ] HttpMethod가 `GET`, Path의 Location이 `/user/create` 인 경우 
