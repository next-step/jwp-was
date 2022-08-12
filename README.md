### 웹 서버 리펙토링 하기 작업 목록

- HttpHeader 자료형 변경 Map<String, String> -> getHeader(“필드 이름”) 메소드를 통해 접근 기능
  
- HttpResponse 
    - requestLine 추가

- GET과 POST 메소드에 따라 전달되는 인자를 Map<String, String>에 저장해 관리하고 getParameter(“인자 이름”) 메소드를 통해 접근 가능하도록 구현한다.