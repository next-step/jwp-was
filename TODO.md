#TODO

- QueryString 클래스에서 파싱 부분을 split() 이용하도록 수정
- Test 코드에서 @DisplayName 을 도입하여 상세한 테스트 목적 제시
- RequestLineParser의 parser기능을 RequestLine 생성자로 이동
-

#DONE
- HttpMethod 생성시 예외처리할때에 input 값도 메시지로 표시하도록 수정
- QueryString 클래스의 생성메소드 of() 에서 of(null) 말고 가독성 좋게 ofNull() 같은 메서드로 치환
- HttpMethod Enum에서 toString() 오버라이드 대신 name() 사용하도록 수정
-