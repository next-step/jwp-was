# 1단계 - Header 파싱, if문 제거
## 요구사항 1 - Query String 파싱
* HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
* 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
* 파싱하는 로직 구현을 TDD로 구현한다.

### Query String 예1 - GET 요청
```
GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1
```
  
### 요구사항 2 - JUnit5 학습
* Test Case에서 인자에 대한 중복이 있는 경우 JUnit5의 @ParameterizedTest를 적용해 본다.
* Guide to JUnit 5 Parameterized Tests 문서를 참고해 적용할 부분이 있다면 적용해 본다.

### 요구사항 3 - if문 제거 연습
#### 프로그래밍 요구사항
* src/test/java 폴더의 coordinate 패키지 코드를 사용자가 입력하는 점의 수에 따라 Line, Triangle, Rectangle을 생성한다.
* FigureFactory 클래스의 getInstance() 메소드를 보면 라인/삼각형/사각형을 구분하기 위해 if문을 사용하고 있다. if문 없이 구현이 가능하도록 한다.

#### 힌트
* 다음 인터페이스를 활용해 구현한다.
```
public interface FigureCreator {
    Figure create(List<Point> points);
}
```
* Map Collection을 활용해 구현한다.


### 기능 목록 및 commit 로그 요구사항
* 기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
* git의 commit 단위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.
    * 참고문서: AngularJS Commit Message Conventions

### AngularJS Commit Message Conventions 중
* commit message 종류를 다음과 같이 구분
```
feat (feature)
fix (bug fix)
docs (documentation)
style (formatting, missing semi colons, …)
refactor
test (when adding missing tests)
chore (maintain)
```
  
## 1단계 실습 마무리
* 실습을 끝내면 코드 리뷰 1단계 문서의 7단계, 8단계를 참고해 자신의 저장소에 push한다.
* 코드 리뷰 2단계 문서를 참고해 코드 리뷰 요청(pull request)을 보내고, NextStep 우측 상단의 Github 아이콘을 클릭해 리뷰 요청을 보낸다.
* 피드백 또는 merge 될 때까지 기다린다.

> PR에 대한 수정 요청을 받아 코드를 수정하는 경우 새로운 PR을 보낼 필요가 없다.
> 코드를 수정한 후 add/commit/push만 하면 자동으로 해당 PR에 추가된다.
  
* Slack을 통해 merge가 되는지 확인한 후에 코드 리뷰 3단계 과정으로 다음 단계 미션을 진행한다.1단계 - Header 파싱, if문 제거
                                                            요구사항 1 - Query String 파싱
                                                            HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
                                                            클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
                                                            파싱하는 로직 구현을 TDD로 구현한다.
                                                            Query String 예1 - GET 요청
                                                            GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1
                                                            요구사항 2 - JUnit5 학습
                                                            Test Case에서 인자에 대한 중복이 있는 경우 JUnit5의 @ParameterizedTest를 적용해 본다.
                                                            Guide to JUnit 5 Parameterized Tests 문서를 참고해 적용할 부분이 있다면 적용해 본다.
                                                            요구사항 3 - if문 제거 연습
                                                            프로그래밍 요구사항
                                                            src/test/java 폴더의 coordinate 패키지 코드를 사용자가 입력하는 점의 수에 따라 Line, Triangle, Rectangle을 생성한다.
                                                            FigureFactory 클래스의 getInstance() 메소드를 보면 라인/삼각형/사각형을 구분하기 위해 if문을 사용하고 있다. if문 없이 구현이 가능하도록 한다.
                                                            힌트
                                                            다음 인터페이스를 활용해 구현한다.
                                                            public interface FigureCreator {
                                                                Figure create(List<Point> points);
                                                            }
                                                            Map Collection을 활용해 구현한다.
                                                            기능 목록 및 commit 로그 요구사항
                                                            기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
                                                            git의 commit 단위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.
                                                            참고문서: AngularJS Commit Message Conventions
                                                            AngularJS Commit Message Conventions 중
                                                            commit message 종류를 다음과 같이 구분
                                                            feat (feature)
                                                            fix (bug fix)
                                                            docs (documentation)
                                                            style (formatting, missing semi colons, …)
                                                            refactor
                                                            test (when adding missing tests)
                                                            chore (maintain)
                                                            1단계 실습 마무리
                                                            실습을 끝내면 코드 리뷰 1단계 문서의 7단계, 8단계를 참고해 자신의 저장소에 push한다.
                                                            코드 리뷰 2단계 문서를 참고해 코드 리뷰 요청(pull request)을 보내고, NextStep 우측 상단의 Github 아이콘을 클릭해 리뷰 요청을 보낸다.
                                                            피드백 또는 merge 될 때까지 기다린다.
                                                            PR에 대한 수정 요청을 받아 코드를 수정하는 경우 새로운 PR을 보낼 필요가 없다.
                                                            코드를 수정한 후 add/commit/push만 하면 자동으로 해당 PR에 추가된다.
                                                            
                                                            Slack을 통해 merge가 되는지 확인한 후에 코드 리뷰 3단계 과정으로 다음 단계 미션을 진행한다.