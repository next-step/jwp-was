##객체 설계

`RequestLine` 은 `RequestMethod` , `Protocol`  상태 값을 가진다.  
`RequestLine` 은 잘 못된 요청에 대해계 예외처리 한다.
  
`RequestMethod` 는 path에 해당되는 파일 위치를 알 수 있다.
`RequestMethod` 는 어떤 메소드인지 알 수 있다.

`RequestMethodGet` 은 `RequestMethod` 를 상속 한다.  
`RequestMethodGet` 은  methodName, path, `RequestParameters` 상태 값을 가진다.

`RequestMethodPost` 는 `RequestMethod` 를 상속 한다.  
`RequestMethodPost` 는 methodName, path, `RequestParameters` 상태 값을 가진다.

`RequestParameters` 는 `Map` 상태 값을 가진다.  
`RequestParameters` 는 key 값으로 value를 추출할 수 있다.

`RequestHandlerMapping` 는 요청 URL에 따른 분기처리를 해준다. (`Controller`로 보냄)

`Controller` 는 URL의 요청 사항을 처리
`Controller` 는 비지니스 로직을 `Service` 로 책임을 위임 후, 로직 결과에 대한 `Response` 를 처리

###URL Mapping
- url 요청 시 해당 컨트롤러 연결
- 다형성을 통해 `BaseController` 를 상태 값으로 가지며 객체의 생성은 상속받은 객체 (LSP..?) 
- static을 통해 매번 객체 생성하지 않게

###Session
HttpSession API 중 구현할 메소드는 getId(), setAttribute(String name, Object value), getAttribute(String name), removeAttribute(String name), invalidate() 5개이다
`HttpSession` 은 sessionId, `Attribute` 의 두 상태값을 가질 수 있다.

`Attribute` 는 `Map` 타입의 상태 값을 가지는 일급컬렉션
`Map` 의 타입은 key = String, value = Object 기반이다.
