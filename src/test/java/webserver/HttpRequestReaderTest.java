package webserver;

import http.HttpMethod;
import http.RequestLine;
import http.request.HttpRequest;
import http.request.HttpRequestHeader;
import mock.MockSocket;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class HttpRequestReaderTest {

    @Test
    void read_refactoring() throws Exception {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n";
        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();

        // 스트림의 내용을 읽는다
        BufferedReader br = HttpRequestReader.readBuffer(in);

        // 리퀘스트 부분을 얻는다
        RequestLine line = HttpRequestReader.parseReadLine(br);

        // 헤더를 얻는다.
        Map<String, String> headerMap = HttpRequestReader.readHeader(br);
        HttpRequestHeader header = new HttpRequestHeader(headerMap);

        // 바디를 확인한다.

        if (header.hasBody()) {
            int contentLength = header.getContentLength();
            Map<String, String> paramMap = HttpRequestReader.readBody(br, contentLength);
        }

        // HttpRequest 를 생성한다.

    }

    @Test
    void read() {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n";
        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();

        HttpRequest httpRequest = HttpRequestReader.read(in);
        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    void readAndResponse() throws Exception {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";

        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();

        HttpRequest httpRequest = HttpRequestReader.read(in);

        byte[] body = RequestMappingManager.fileLoadFromPath(httpRequest.getPath());

        assertThat(body).isEqualTo(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
    }


    @Test
    void readRequestParameterGet() {
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();

        HttpRequest httpRequest = HttpRequestReader.read(in);
        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");

        assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("박재성");
        assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void readRequestParameterPost() {
        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();

        HttpRequest httpRequest = HttpRequestReader.read(in);
        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");

        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*");

        assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("박재성");
        assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net");
    }


}
