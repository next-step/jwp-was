package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.header.Cookie;
import webserver.http.request.header.HttpHeader;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    @Test
    @DisplayName("HttpRequest 를 생성한다.")
    void create_HttpRequest() throws Exception {
        // given
        RequestLine requestLine = RequestLine.parse("POST /user/create HTTP/1.1");
        HttpHeader header = new HttpHeader(Map.of(
                "Host", "localhost:8080",
                "Connection", "keep-alive",
                "Content-Length", "71",
                "Content-Type", "application/x-www-form-urlencoded",
                "Accept", "*/*"
        ), new Cookie());
        QueryString body = QueryString.parse("userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net");
        HttpRequest expectedHttpRequest = new HttpRequest(requestLine, header, body);

        // when
        InputStream in = new FileInputStream(new File("./src/test/resources/request.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        HttpRequest actualHttpRequest = HttpRequest.of(br);

        // then
        assertThat(expectedHttpRequest).isEqualTo(actualHttpRequest);
    }
}