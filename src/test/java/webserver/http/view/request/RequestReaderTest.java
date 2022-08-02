package webserver.http.view.request;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Headers;
import webserver.http.domain.Protocol;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Parameters;
import webserver.http.domain.request.Request;
import webserver.http.domain.request.RequestLine;
import webserver.http.domain.request.URI;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.domain.Headers.ACCEPT;
import static webserver.http.domain.Headers.CONTENT_LENGTH;
import static webserver.http.domain.Headers.CONTENT_TYPE;
import static webserver.http.domain.Headers.COOKIE;
import static webserver.http.domain.Headers.HOST;
import static webserver.http.domain.request.Method.POST;

class RequestReaderTest {
    private final RequestReader requestReader = new RequestReader();

    @DisplayName("Content-Length 헤더가 없거나, Content-Type이 application/x-www-form-urlencoded이 아닌 경우, request line과 header 만 포함된 request 객체 생성")
    @Test
    void read_empty_body() throws IOException {
        String message = "GET /path?name=jordy&age=20 HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: application/json\r\n" +
                "\r\n";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(message));

        Request request = requestReader.read(bufferedReader);

        assertThat(request).usingRecursiveComparison()
                .isEqualTo(getEmptyBodyRequest());
    }

    private Request getEmptyBodyRequest() {
        return new Request(
                new RequestLine(
                        Method.GET,
                        new URI("/path",
                                new Parameters(
                                        Map.of(
                                                "name", Lists.list("jordy"),
                                                "age", Lists.list("20")
                                        )
                                )
                        ),
                        Protocol.HTTP_1_1
                ),
                new Headers(
                        Map.of(
                                HOST, "localhost:8080",
                                ACCEPT, "application/json"
                        )
                )
        );
    }

    @DisplayName("Cookie 헤더를 포함한 경우, request line과 header와 Cookies를 포함하는 request 객체 생성")
    @Test
    void read_with_cookie() throws IOException {
        String message = "GET /path?name=jordy&age=20 HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: application/json\r\n" +
                "Cookie: logined=true; item=; type=intellij\r\n" +
                "\r\n";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(message));

        Request request = requestReader.read(bufferedReader);

        assertThat(request).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(getRequestIncludingCookies());
    }

    private Request getRequestIncludingCookies() {
        return new Request(
                new RequestLine(
                        Method.GET,
                        new URI("/path",
                                new Parameters(
                                        Map.of(
                                                "name", Lists.list("jordy"),
                                                "age", Lists.list("20")
                                        )
                                )
                        ),
                        Protocol.HTTP_1_1
                ),
                new Headers(
                        Map.of(
                                HOST, "localhost:8080",
                                ACCEPT, "application/json",
                                COOKIE, "logined=true; item=; type=intellij"
                        )
                )
        );
    }

    @DisplayName("양수 Content-Length 헤더와 application/x-www-form-urlencoded 타입의 Content-Type을 갖는 경우, request line과 header, request body까지 포함된 request 객체 생성")
    @Test
    void read_full_request_with_body() throws IOException {
        String message = "POST /path HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: application/json\r\n" +
                "Content-Length: 17\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "\r\n" +
                "name=jordy&age=20";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(message));

        Request request = requestReader.read(bufferedReader);

        assertThat(request).usingRecursiveComparison()
                .isEqualTo(getRequestWithBody());
    }

    private Request getRequestWithBody() {
        return new Request(
                new RequestLine(
                        POST,
                        new URI("/path",
                                new Parameters(
                                        Map.of(
                                                "name", Lists.list("jordy"),
                                                "age", Lists.list("20")
                                        )
                                )
                        ),
                        Protocol.HTTP_1_1
                ),
                new Headers(
                        Map.of(
                                HOST, "localhost:8080",
                                ACCEPT, "application/json",
                                CONTENT_LENGTH, "17",
                                CONTENT_TYPE, "application/x-www-form-urlencoded"
                        )
                )
        );
    }

    @Test
    public void request_POST() throws Exception {
        String testDirectory = "./src/test/resources/";
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        RequestReader requestReader = new RequestReader();
        Request request = requestReader.read(new BufferedReader(new InputStreamReader(in)));

        assertThat(request.getMethod()).isEqualTo(POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_POST2() throws Exception {
        String testDirectory = "./src/test/resources/";
        InputStream in = new FileInputStream(testDirectory + "Http_POST2.txt");
        RequestReader requestReader = new RequestReader();
        Request request = requestReader.read(new BufferedReader(new InputStreamReader(in)));

        assertThat(request.getMethod()).isEqualTo(POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getParameter("id")).isEqualTo("1");
    }
}