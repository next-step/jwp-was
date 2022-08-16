package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.header.Cookie;
import webserver.http.header.type.EntityHeader;
import webserver.http.header.type.GeneralHeader;
import webserver.http.header.Header;
import webserver.http.header.HeaderValue;
import webserver.http.header.type.RequestHeader;
import webserver.http.request.requestline.Method;
import webserver.http.request.requestline.Path;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.QueryString;
import webserver.http.request.requestline.RequestLine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpRequestTest {
    @Test
    @DisplayName("HttpRequest 를 생성한다.")
    void create_HttpRequest() throws Exception {
        // given & when
        InputStream in = new FileInputStream("./src/test/resources/request.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        HttpRequest actualHttpRequest = new HttpRequest(br);
        String sessionId = actualHttpRequest.getSessionId();

        RequestLine requestLine = RequestLine.parse("POST /user/create HTTP/1.1");
        Header header = new Header(Map.of(
                RequestHeader.HOST, "localhost:8080",
                GeneralHeader.CONNECTION, HeaderValue.KEEP_ALIVE,
                EntityHeader.CONTENT_LENGTH, "71",
                EntityHeader.CONTENT_TYPE, HeaderValue.APPLICATION_HTML_FORM,
                RequestHeader.ACCEPT, HeaderValue.ALL_MIME_TYPE,
                RequestHeader.COOKIE, String.format(HeaderValue.JSESSION_ID, sessionId)
        ), new Cookie(Map.of("JSESSIONID", sessionId)));
        QueryString body = QueryString.parse("userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net");
        HttpRequest expectedHttpRequest = new HttpRequest(requestLine, header, body);

        // then
        assertThat(expectedHttpRequest).isEqualTo(actualHttpRequest);
    }

    @Test
    @DisplayName("HttpRequest 요청이 queryString 과 body 이 함께 들어올 경우에 대한 처리를 한다.")
    void create_HttpRequest_with_queryString_and_body() throws Exception {
        // given & when
        InputStream in = new FileInputStream("./src/test/resources/request_with_query_string_and_body.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        HttpRequest actualHttpRequest = new HttpRequest(br);
        String sessionId = actualHttpRequest.getSessionId();

        RequestLine requestLine = RequestLine.parse("POST /user/create?id=1 HTTP/1.1");
        Header header = new Header(Map.of(
                RequestHeader.HOST, "localhost:8080",
                GeneralHeader.CONNECTION, HeaderValue.KEEP_ALIVE,
                EntityHeader.CONTENT_LENGTH, "46",
                EntityHeader.CONTENT_TYPE, HeaderValue.APPLICATION_HTML_FORM,
                RequestHeader.ACCEPT, HeaderValue.ALL_MIME_TYPE,
                RequestHeader.COOKIE, String.format(HeaderValue.JSESSION_ID, sessionId)
        ), new Cookie(Map.of("JSESSIONID", sessionId)));
        QueryString body = QueryString.parse("userId=javajigi&password=password&name=JaeSung");
        HttpRequest expectedHttpRequest = new HttpRequest(requestLine, header, body);

        // then
        assertThat(expectedHttpRequest).isEqualTo(actualHttpRequest);
    }

    @Test
    @DisplayName("HttpRequest 요청라인, 헤더, 바디가 null 일 경우 예외가 발생한다.")
    void throw_exception_request_null() {
        assertAll(
                () -> assertThatThrownBy(() -> new HttpRequest(null, new Header(), new QueryString())).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new HttpRequest(new RequestLine(Method.GET, new Path("/index.html", new QueryString()), Protocol.ofHttpV11()), null, new QueryString())).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new HttpRequest(new RequestLine(Method.GET, new Path("/index.html", new QueryString()), Protocol.ofHttpV11()), new Header(), null)).isInstanceOf(IllegalArgumentException.class)
        );
    }
}