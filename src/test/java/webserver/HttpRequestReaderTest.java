package webserver;

import http.HttpMethod;
import http.RequestLine;
import http.request.HttpRequest;
import http.request.HttpRequestHeader;
import mock.MockSocket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpRequestReaderTest {

    InputStream get;
    InputStream getWithQueryString;
    InputStream post;

    @BeforeAll
    public void initGet() throws Exception {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Accept: */*\r\n" +
                "\r\n";
        MockSocket socket = new MockSocket(request);

        this.get = socket.getInputStream();
    }

    @BeforeAll
    public void initGetWithQueryString() throws Exception {
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "\n";
        MockSocket socket = new MockSocket(request);

        this.getWithQueryString = socket.getInputStream();
    }

    @BeforeAll
    public void initPost() throws Exception {
        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";
        MockSocket socket = new MockSocket(request);

        this.post = socket.getInputStream();
    }


    @Test
    void readBufferGet() throws Exception {
        get.reset();
        BufferedReader br = HttpRequestReader.readBuffer(get);

        assertThat(br).isNotEqualTo(null);
        assertThat(br.readLine()).startsWith("GET");
    }

    @Test
    void readBufferGetQuery() throws Exception {
        getWithQueryString.reset();
        BufferedReader br = HttpRequestReader.readBuffer(getWithQueryString);

        assertThat(br).isNotEqualTo(null);
        assertThat(br.readLine()).startsWith("GET");
    }

    @Test
    void readBufferPost() throws Exception {
        post.reset();
        BufferedReader br = HttpRequestReader.readBuffer(post);

        assertThat(br).isNotEqualTo(null);
        assertThat(br.readLine()).startsWith("POST");
    }

    @Test
    void readLineGet() throws Exception {
        get.reset();
        BufferedReader br = HttpRequestReader.readBuffer(get);
        RequestLine line = HttpRequestReader.parseReadLine(br);

        assertThat(line).isNotEqualTo(null);
        assertThat(line.isGet()).isEqualTo(true);
    }

    @Test
    void readLineGetQuery() throws Exception {
        getWithQueryString.reset();
        BufferedReader br = HttpRequestReader.readBuffer(getWithQueryString);
        RequestLine line = HttpRequestReader.parseReadLine(br);

        assertThat(line).isNotEqualTo(null);
        assertThat(line.isGet()).isEqualTo(true);
    }

    @Test
    void readLinePost() throws Exception {
        post.reset();
        BufferedReader br = HttpRequestReader.readBuffer(post);
        RequestLine line = HttpRequestReader.parseReadLine(br);

        assertThat(line).isNotEqualTo(null);
        assertThat(line.isGet()).isEqualTo(false);
    }

    @Test
    void readHeaderGet() throws Exception {
        get.reset();
        BufferedReader br = HttpRequestReader.readBuffer(get);
        Map<String, String> headerMap = HttpRequestReader.readHeader(br);
        HttpRequestHeader header = new HttpRequestHeader(headerMap);

        assertThat(header.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(header.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(header.getHeader("Accept")).isEqualTo("*/*");
    }

    @Test
    void readHeaderGetQuery() throws Exception {
        getWithQueryString.reset();
        BufferedReader br = HttpRequestReader.readBuffer(getWithQueryString);
        Map<String, String> headerMap = HttpRequestReader.readHeader(br);
        HttpRequestHeader header = new HttpRequestHeader(headerMap);

        assertThat(header.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(header.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(header.getHeader("Accept")).isEqualTo("*/*");
        assertThat(header.getContentLength()).isEqualTo(0);
        assertThat(header.hasBody()).isEqualTo(false);
    }

    @Test
    void readHeaderPost() throws Exception {
        post.reset();
        BufferedReader br = HttpRequestReader.readBuffer(post);
        Map<String, String> headerMap = HttpRequestReader.readHeader(br);
        HttpRequestHeader header = new HttpRequestHeader(headerMap);

        assertThat(header.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(header.getHeader("Accept")).isEqualTo("*/*");
        assertThat(header.getHeader("Content-Length")).isEqualTo("93");
        assertThat(header.getContentLength()).isEqualTo(93);
        assertThat(header.hasBody()).isEqualTo(true);
    }

    @Test
    void readBodyGetQuery() throws Exception {
        post.reset();
        BufferedReader br = HttpRequestReader.readBuffer(getWithQueryString);
        Map<String, String> headerMap = HttpRequestReader.readHeader(br);
        HttpRequestHeader header = new HttpRequestHeader(headerMap);
        Map<String, String> parameter = HttpRequestReader.readBody(br, header.getContentLength());

        assertThat(parameter.get("userId")).isEqualTo(null);
        assertThat(parameter.get("password")).isEqualTo(null);
        assertThat(parameter.get("name")).isEqualTo(null);
        assertThat(parameter.get("email")).isEqualTo(null);
    }

    @Test
    void readBodyPost() throws Exception {
        post.reset();
        BufferedReader br = HttpRequestReader.readBuffer(post);
        Map<String, String> headerMap = HttpRequestReader.readHeader(br);
        HttpRequestHeader header = new HttpRequestHeader(headerMap);
        Map<String, String> parameter = HttpRequestReader.readBody(br, header.getContentLength());

        assertThat(parameter.get("userId")).isEqualTo("javajigi");
        assertThat(parameter.get("password")).isEqualTo("password");
        assertThat(parameter.get("name")).isEqualTo("박재성");
        assertThat(parameter.get("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void readGet() throws Exception {
        get.reset();
        HttpRequest request = HttpRequestReader.read(get);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(request.getParameter("userId")).isEqualTo(null);
    }

    @Test
    void readGetQuery() throws Exception {
        getWithQueryString.reset();
        HttpRequest request = HttpRequestReader.read(getWithQueryString);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    void readPost() throws Exception {
        post.reset();
        HttpRequest request = HttpRequestReader.read(post);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }
}
