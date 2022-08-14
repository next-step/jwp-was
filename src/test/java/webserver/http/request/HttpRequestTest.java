package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        final InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        final BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        HttpRequest request = HttpRequest.parseFrom(br);

        assertEquals(HttpMethod.GET, request.getRequestLine().getMethod());
        assertEquals("/user/create", request.getRequestLine().getUrl().getPath());
        assertEquals("keep-alive", request.getRequestHeader().getConnection());
        assertEquals("javajigi", request.getRequestLine().getUrl().getQueryParameter().getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        final InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        final BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        HttpRequest request = HttpRequest.parseFrom(br);

        assertEquals(HttpMethod.POST, request.getRequestLine().getMethod());
        assertEquals("/user/create", request.getRequestLine().getUrl().getPath());
        assertEquals("keep-alive", request.getRequestHeader().getConnection());
        assertEquals("javajigi", request.getRequestBody().getContent("userId"));
    }
}
