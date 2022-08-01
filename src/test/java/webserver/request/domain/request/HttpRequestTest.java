package webserver.request.domain.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.request.domain.request.HttpRequest;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpRequestTest {

//    @Test
//    @DisplayName("HttpRequest 안에 RequestLine이 null 이면 예외를 던진다.")
//    void notFoundRequestLineExceptionTest() {
//        HttpRequest httpRequest = new HttpRequest();
//
//        assertThrows(NotFoundRequestLineException.class,
//                () -> {
//            httpRequest.getPath();
//        });
//    }
//
//    @Test
//    @DisplayName("HttpRequest 객체에서 path 값 가져오기 테스트")
//    void requestPathTest() {
//        HttpRequest httpRequest = new HttpRequest();
//        httpRequest.addProperty("GET /index.html HTTP/1.1");
//        httpRequest.addProperty("Host: localhost:8080");
//        httpRequest.addProperty("Connection: keep-alive");
//        httpRequest.addProperty("Accept: */*");
//
//        Assertions.assertThat(httpRequest.getPath()).isEqualTo("/index.html");
//    }

    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "HTTP_GET.txt");
        HttpRequest request = new HttpRequest(in);

        Assertions.assertThat(request.getMethod()).isEqualTo("GET");
        Assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        Assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        Assertions.assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "HTTP_POST.txt");
        HttpRequest request = new HttpRequest(in);

        Assertions.assertThat(request.getMethod()).isEqualTo("POST");
        Assertions.assertThat(request.getPath()).isEqualTo("/user/create");
        Assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        Assertions.assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }
}
