package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMessageTest {

    @DisplayName("GET 요청은 requestLine과 header로 파싱되어 생성")
    @Test
    void test_createRequestMessage_by_get_should_pass() throws IOException {
        // given
        String input = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        StringReader sr = new StringReader(input);
        BufferedReader br = new BufferedReader(sr);
        // when
        RequestMessage requestMessage = RequestMessage.from(br);
        // then
        assertThat(requestMessage.equals(
                RequestMessage.createWithDefaultBody(RequestLine.from("GET /index.html HTTP/1.1"),
                        new Header(Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*")))))
                .isTrue();
    }

    @DisplayName("POST 요청은 requestLine, header, body로 파싱되어 생성")
    @Test
    void test_createRequestMessage_by_post_should_pass() throws IOException {
        // given
        String input = "POST /user/create HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 59\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=crystal&password=password&name=%EC%9E%84%EC%88%98%EC%A0%95&email=crystal%40naver.com";StringReader sr = new StringReader(input);
        BufferedReader br = new BufferedReader(sr);
        // when
        RequestMessage requestMessage = RequestMessage.from(br);
        // then
        assertThat(requestMessage.equals(RequestMessage.create(
                        RequestLine.from("POST /user/create HTTP/1.1"),
                        new Header(Collections.emptyList()),
                        "userId=crystal&password=password&name=%EC%9E%84%EC%88%98%EC%A0%95&email=crystal%40naver.com"
        )));
        }

}