##객체 설계

`RequestLine` 은 `RequestMethod` , `Protocol`  상태 값을 가진다.  
`RequestLine` 은 잘 못된 요청에 대해계 예외처리 한다.

`RequestMethod` 는 methodName, path 값을 가진다.  
`RequestMethod` 는 path에 해당되는 파일 위치를 알 수 있다.

`RequestGet` 은 `RequestMethod` 를 상속 한다.  
`RequestGet` 은  `QueryStrings` 상태 값을 가진다.

`RequestPost` 는 `RequestMethod` 를 상속 한다.  
`RequestPost` 는 `RequestBody` 상태 값을 가진다.

`QueryStrings` 는 `Map` 상태 값을 가진다.  
`QueryStrings` 는 key 값으로 value를 추출할 수 있다.
