package webserver;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.parser.RequestReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("Http Request Message를 정상적으로 파싱할 수 있다")
    public void request_GET() throws Exception {
        final InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        final HttpRequest request = RequestReader.read(in);

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}