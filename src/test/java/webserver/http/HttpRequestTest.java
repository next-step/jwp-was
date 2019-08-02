package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    private final static String HTTP_PLAIN_GET = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";

    private final static String HTTP_PLAIN_POST = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 93\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    InputStream inputStreamGet;

    InputStream inputStreamPost;

    @BeforeEach
    public void beforeSet(){
        inputStreamGet = new ByteArrayInputStream(HTTP_PLAIN_GET.getBytes());
        inputStreamPost = new ByteArrayInputStream(HTTP_PLAIN_POST.getBytes());
    }

    @DisplayName("Get Http Request 테스트")
    @Test
    public void httpRequestParse(){
        try{
            HttpRequest httpRequest = HttpRequest.parse(inputStreamGet);
            assertThat(httpRequest).isNotNull();
            assertThat(httpRequest.getPath()).isEqualTo("/index.html");
        } catch (Exception e) {
            assertThat(e).hasNoCause();
        }

    }

    @DisplayName("Post Http Request 테스트")
    @Test
    public void httpRequestParsePost(){
        try{
            HttpRequest httpRequest = HttpRequest.parse(inputStreamPost);
            assertThat(httpRequest).isNotNull();
            assertThat(httpRequest.getPath()).isEqualTo("/user/create");
            assertThat(httpRequest.getBody()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        } catch (Exception e) {
            assertThat(e).hasNoCause();
        }

    }


}
