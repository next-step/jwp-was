package http;

import http.request.RequestUtils;
import http.request.body.Body;
import http.request.headers.Headers2;
import http.request.requestline.requestLine2.RequestLine2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestUtilsTest {
    private static final String TEST_REQUEST = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @DisplayName("RequestLine 추출")
    @Test
    void getRequestLine() throws IOException {
        //given
        Reader reader = new StringReader(TEST_REQUEST);
        BufferedReader br = new BufferedReader(reader);

        //when
        RequestUtils requestUtils = new RequestUtils(br);
        RequestLine2 requestLine = requestUtils.getRequestLine();

        //then
        assertThat(requestLine).isEqualTo(new RequestLine2("POST /user/create HTTP/1.1"));
    }

    @DisplayName("Headers 추출")
    @Test
    void getHeaders() throws IOException {
        //given
        Reader reader = new StringReader(TEST_REQUEST);
        BufferedReader br = new BufferedReader(reader);
        RequestUtils requestUtils = new RequestUtils(br);
        requestUtils.getRequestLine();

        //when
        Headers2 headers = requestUtils.getHeaders();

        //then
        assertThat(headers.getSize()).isEqualTo(5);
        assertThat(headers.getParameter("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getParameter("Connection")).isEqualTo("keep-alive");
        assertThat(headers.getParameter("Content-Length")).isEqualTo("59");
        assertThat(headers.getParameter("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(headers.getParameter("Accept")).isEqualTo("*/*");
    }

    @DisplayName("Body 추출")
    @Test
    void getBody() throws IOException {
        //given
        Reader reader = new StringReader(TEST_REQUEST);
        BufferedReader br = new BufferedReader(reader);
        RequestUtils requestUtils = new RequestUtils(br);
        requestUtils.getRequestLine();
        Headers2 headers = requestUtils.getHeaders();

        //when
        Body body = requestUtils.getBody();

        //then
        assertThat(body.getBody())
                .isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}
