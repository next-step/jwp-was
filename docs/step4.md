# 4단계 - 세션 구현하기

## 요구사항

서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다.

HttpSession API 중 구현할 메소드는 getId(), setAttribute(String name, Object value), getAttribute(String name), removeAttribute(String name), invalidate() 5개이다. HttpSession의 가장 중요하고 핵심이 되는 메소드이다.

각 메소드의 역할은 다음과 같다.

* String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
* void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
* Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
* void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
* void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제

세션은 클라이언트와 서버 간에 상태 값을 공유하기 위해 고유한 아이디를 활용하고, 이 고유한 아이디는 쿠키를 활용해 공유한다.

여기서 힌트를 얻어 세션을 구현해 보자.


## 힌트

### SessionId 생성
* JDK에서 제공하는 UUID 클래스ㄹ 사용해 고유한 아이디를 생성할 수 있다.

```java
UUID uuid = UUID.randomUUID();
```

### 세션 관리를 위한 자료구조
* Map을 활용할 수 있으며, Map<String, HttpSession>와 같은 구조가 될 것이다. 이 Map의 키(key)는 앞에서 UUID로 생성한 고유한 아이디이다.