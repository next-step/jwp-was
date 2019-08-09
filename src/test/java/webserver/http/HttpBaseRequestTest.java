package webserver.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpBaseRequestTest {


    private static final String HTTP_PLAIN_GET = "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Cookie: test=testValue\n" +
            "Accept: */*\n";


    private static final String HTTP_PLAIN_GET_QUERY_STRING = "GET http://localhost:8080/index.html?userId=circlee HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";


    private static final String HTTP_PLAIN_POST = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 93\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    InputStream inputStreamGet;

    InputStream inputStreamGetQueryString;

    InputStream inputStreamPost;

    @BeforeEach
    public void beforeSet(){
        inputStreamGet = new ByteArrayInputStream(HTTP_PLAIN_GET.getBytes());
        inputStreamGetQueryString = new ByteArrayInputStream(HTTP_PLAIN_GET_QUERY_STRING.getBytes());
        inputStreamPost = new ByteArrayInputStream(HTTP_PLAIN_POST.getBytes());
    }

    @DisplayName("Get Http HttpRequest 테스트")
    @Test
    public void httpRequestParse(){
        try{
            HttpBaseRequest httpRequest = HttpBaseRequest.parse(inputStreamGet);
            assertThat(httpRequest).isNotNull();
            assertThat(httpRequest.getPath()).isEqualTo("/index.html");
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
            assertThat(e).hasNoCause();
        }
    }
    
    @DisplayName("Get Http HttpRequest QueryString 테스트 : cookie 확인")
    @Test
    public void httpRequestParseWithQueryString(){ 
        try{
            HttpBaseRequest httpRequest = HttpBaseRequest.parse(inputStreamGetQueryString);
            assertThat(httpRequest).isNotNull();
            assertThat(httpRequest.getPath()).isEqualTo("http://localhost:8080/index.html?userId=circlee");
            assertThat(httpRequest.getRequestURI()).isEqualTo("/index.html");
            assertThat(httpRequest.getParameter("userId")).isEqualTo("circlee");
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
            assertThat(e).hasNoCause();
        }
    }

    @DisplayName("Get Http HttpRequest Cookies 테스트 : cookie 확인")
    @Test
    public void httpRequestCookiesTest(){
        try{
            HttpBaseRequest httpRequest = HttpBaseRequest.parse(inputStreamGet);
            assertThat(httpRequest.getCookie("test")).isNotNull();
            assertThat(httpRequest.getCookie("test").getValue()).isEqualTo("testValue");
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
            assertThat(e).hasNoCause();
        }

    }

    @DisplayName("Post Http HttpRequest 테스트")
    @Test
    public void httpRequestParsePost(){
        try{
            HttpBaseRequest httpRequest = HttpBaseRequest.parse(inputStreamPost);
            assertThat(httpRequest).isNotNull();
            assertThat(httpRequest.getPath()).isEqualTo("/user/create");
            assertThat(httpRequest.getBody()).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        } catch (Exception e) {
            assertThat(e).doesNotThrowAnyException();
            assertThat(e).hasNoCause();
        }

    }


}
