# 메모?

중간에 손 놓을 때 의식의 흐름 컨텍스트를 저장할 필요가 있어서 휘갈겨 씀  

## step3. 

AmazingController#dispatch를 보면 분기가 심한 상태임..  
다음과 같이 뺄 수 있을거라고 생각함.  

일단.. 핸들러는 in/out이 ResponseContext(rename예정)/HttpRequest임.  
따라서 핸들러는 하나로 추상화 시킬 수 있음.    

- RequestMapper 모듈
    - 분기 조건
        - 지원하는 HttpMethod 리스트(혹은 배열 뭐든간에)
        - 엔드포인트 (URI)

음 고민이긴 한데 애플리케이션 레벨에서 사용할 context와 HTTP의 request/response를 나누면 아름다워 질 것 같음.

  