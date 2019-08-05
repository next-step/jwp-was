# 3단계 - HTTP 웹 서버 리팩토링

## 웹 애플리케이션 서버(이하 WAS) 요구사항
> 앞 단계에서 구현한 코드는 WAS 기능, HTTP 요청/응답 처리, 개발자가 구현할 애플리케이션 기능이 혼재되어 있다.
> 이와 같이 여러 가지 역할을 가지는 코드가 혼재되어 있으면 재사용하기 힘들다.
> 
> 각각의 역할을 분리해 재사용 가능하도록 개선한다.
> 즉, WAS 기능, HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다.

## WAS 기능 요구사항
* 다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
* 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.

### 힌트
* JAVA 쓰레드풀 분석 - newFixedThreadPool 는 어떻게 동작하는가? 또는 Java ThreadPoolExecutor 문서 참고하면 힌트를 얻을 수 있음.
* java.util.concurrent.Executors에서 제공하는 api 활용

```java
public class ExecutorsTest {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorsTest.class);

    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(100);

        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                int idx = counter.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Thread {}", idx);
            });
        }
        sw.stop();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        logger.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }
}
```

## HTTP 요청/응답 처리 기능
* HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.

## 코드 리팩토링 요구사항
> HTTP 웹 서버를 구현하고 보니 소스 코드의 복잡도가 많이 증가했다. 소스 코드 리팩토링을 통해 복잡도를 낮춰보자.
>
> Bad Smell을 찾는 것은 쉽지 않은 작업이다.
> 리팩토링할 부분을 찾기 힘든 사람은 다음으로 제시하는 1단계 힌트를 참고해 리팩토링을 진행해 볼 것을 추천한다.
> 만약 혼자 힘으로 리팩토링할 부분을 찾은 사람은 먼저 도움 없이 리팩토링을 진행한다.

## 리팩토링 힌트 - 1단계

### 리팩토링 접근 방법 - 메소드 분리 및 클래스 분리
* 객체지향 생활 체조에서 제안하는 방법을 찾고해 리팩토링 진행
* 메소드가 한 가지 일을 하도록 메소드를 분리(extract method 리팩토링)하는 리팩토링을 한다.
* 클래스 내에 private 메소드가 많이 생성되었다. 메소드를 클래스로 분리하는 것이 좋을지 검토한다.
* private 메소드에 대해 테스트 코드를 생성하는 것이 좋겠다는 생각이 드는가? 메소드를 클래스로 분리하는 것이 좋을지에 대해 고려해 본다.
* 단위 테스트를 하기 어려운가? 단위 테스트를 하기 쉽도록 설계를 개선할 방법은 없는가? 로직을 구현하는 부분과 테스트를 하기 어렵게 만드는 부분을 분리해서 구현할 방법은 없는가?

### 직접적인 힌트
* RequestHandler 클래스의 책임을 분리한다.
* RequestHandler 클래스는 많은 책임을 가지고 있다. 객체 지향 설계 원칙 중 “단일 책임의 원칙”에 따라 RequestHandler 클래스가 가지고 있는 책임을 찾아 각 책임을 새로운 클래스를 만들어 분리한다.
* 다음과 같이 3개의 역할로 분리해 리팩토링을 시도하고, 테스트 가능한 부분은 단위 테스트를 진행한다.
* 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
* 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse)
* 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.

![image](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KfeN7ZIfxAdm54Vf9yP%2Fwas-refactoring1.jpg?alt=media&token=9164de9f-8124-4efa-a985-b624ab14b980)

## 리팩토링 힌트 - 2단계

### 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
* 클라이언트 요청 데이터를 담고 있는 InputStream을 생성자로 받아 HTTP 메소드, URL, 헤더, 본문을 분리하는 작업을 한다.
* 헤더는 Map<String, String>에 저장해 관리하고 getHeader(“필드 이름”) 메소드를 통해 접근 가능하도록 구현한다.
* GET과 POST 메소드에 따라 전달되는 인자를 Map<String, String>에 저장해 관리하고 getParameter(“인자 이름”) 메소드를 통해 접근 가능하도록 구현한다.
* RequestHandler가 새로 추가한 HttpRequest를 사용하도록 리팩토링한다.

### 테스트 코드를 기반으로 구현한다. - GET
* GET에 대한 테스트 데이터(src/test/resources 에 생성)

```
GET /user/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1 
Host: localhost:8080 
Connection: keep-alive 
Accept: */*
// 빈 공백 문자열
```

* 테스트 코드
```java
public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";
    
    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);
        
        assertEquals("GET", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}
```

### 테스트 코드를 기반으로 구현한다. - POST
* POST에 대한 테스트 데이터(src/test/resources 에 생성)

```
POST /user/create HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 46
Content-Type: application/x-www-form-urlencoded
Accept: */*

userId=javajigi&password=password&name=JaeSung
```

* 테스트 코드
```java

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";
    
    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);
        
        assertEquals("POST", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}
```

## 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse)

* RequestHandler 클래스를 보면 응답 데이터 처리를 위한 많은 중복이 있다. 이 중복을 제거해 본다.
* 응답 헤더 정보를 Map<String, String>으로 관리한다.
* 응답을 보낼 때 HTML, CSS, 자바스크립트 파일을 직접 읽어 응답으로 보내는 메소드는 forward(), 다른 URL로 리다이렉트하는 메소드는 sendRedirect() 메소드를 나누어 구현한다.
* RequestHandler가 새로 추가한 HttpResponse를 사용하도록 리팩토링한다.
* 테스트 코드

```java
public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";
    
    @Test
    public void responseForward() throws Exception {
        // Http_Forward.txt 결과는 응답 body에 index.html이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        response.forward("/index.html");        
    }
    
    @Test
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }
    
    @Test
    public void responseCookies() throws Exception {
        // Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
        HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
```

## 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
* 각 요청과 응답에 대한 처리를 담당하는 부분을 추상화해 인터페이스로 만든다. 인터페이스는 다음과 같이 구현할 수 있다.

```java
public interface Controller {
    
    void service(HttpRequest request, HttpResponse response);
}
```

* 각 분기문을 Controller 인터페이스를 구현하는(implements) 클래스를 만들어 분리한다.
* 이렇게 생성한 Controller 구현체를 Map<String, Controller>에 저장한다. Map의 key에 해당하는 String은 요청 URL, value에 해당하는 Controller는 Controller 구현체이다.
* 클라이언트 요청 URL에 해당하는 Controller를 찾아 service() 메소드를 호출한다.
* Controller 인터페이스를 구현하는 AbstractController 추상클래스를 추가해 중복을 제거하고, service() 메소드에서 GET과 POST HTTP 메소드에 따라 doGet(), doPost() 메소드를 호출하도록 한다.

![image](https://firebasestorage.googleapis.com/v0/b/nextstep-real.appspot.com/o/lesson-attachments%2F-KfeN7ZIfxAdm54Vf9yP%2Fwas-refactoring2.jpg?alt=media&token=715ea3db-5172-488f-9201-ab837927578f)

## 추가 요구사항이나 변경이 발생하는 경우

> 기능 구현을 완료한 후 서비스 중에 추가 요구사항이 발생하거나 요구사항이 변경되는 경우가 많다. 이와 같은 경우 어떻게 처리하는 것이 좋은지 연습해 본다.
> 예를 들어 HTTP에서 POST 방식으로 데이터를 전달할 때 body를 통한 데이터 전달뿐만 아니라 Query String을 활용한 데이터 전달도 지원해야 한다. 이에 따른 리팩토링 방법도 연습해 본다.
  
```
POST /user/create?id=1 HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Content-Length: 46
Content-Type: application/x-www-form-urlencoded
Accept: */*

userId=javajigi&password=password&name=JaeSung
```

위와 같은 HTTP 요청이 발생하는 경우 다음 테스트 코드를 통과해야 한다.

```
@Test
public void request_POST2() throws Exception {
    InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
    HttpRequest request = new HttpRequest(in);

    assertEquals("POST", request.getMethod());
    assertEquals("/user/create", request.getPath());
    assertEquals("keep-alive", request.getHeader("Connection"));
    assertEquals("1", request.getParameter("id"));
    assertEquals("javajigi", request.getParameter("userId"));
}
```

### 힌트
* 기존의 클래스 구조로 충분히 소화할 수 있는지를 검토한다.
* 새롭게 변경된 요구사항에 따라 클래스 구조를 재설계한다.