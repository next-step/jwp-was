##객체 설계

`RequestLine` 은 `RequestMethod` , `Protocol`  상태 값을 가진다.  
`RequestLine` 은 잘 못된 요청에 대해계 예외처리 한다.
  
`RequestMethod` 는 path에 해당되는 파일 위치를 알 수 있다.
`RequestMethod` 는 어떤 메소드인지 알 수 있다.

`RequestMethodGet` 은 `RequestMethod` 를 상속 한다.  
`RequestMethodGet` 은  methodName, path, `QueryStrings` 상태 값을 가진다.

`RequestMethodPost` 는 `RequestMethod` 를 상속 한다.  
`RequestMethodPost` 는 methodName, path, `RequestBody` 상태 값을 가진다.

`QueryStrings` 는 `Map` 상태 값을 가진다.  
`QueryStrings` 는 key 값으로 value를 추출할 수 있다.

`RequestHandlerMapping` 는 요청 URL에 따른 분기처리를 해준다. (`Controller`로 보냄)

`Controller` 는 URL의 요청 사항을 처리
`Controller` 는 비지니스 로직을 `Service` 로 책임을 위임 후, 로직 결과에 대한 `Response` 를 처리
