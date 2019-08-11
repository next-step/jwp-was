package webserver.http.request;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void get_request_test() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.parse(bufferedReader);

        assertTrue(request.isGetRequest());
        assertEquals("/user/create", request.getRequestUriPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getQueryStringParameter("userId"));
    }

    @Test
    void post_request_test() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.parse(bufferedReader);

        assertTrue(request.isPostRequest());
        assertEquals("/user/create", request.getRequestUriPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getRequestBodyParameter("userId"));
    }

    @Test
    public void post_request_with_querystring() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST2.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.parse(bufferedReader);

        assertTrue(request.isPostRequest());
        assertEquals("/user/create", request.getRequestUriPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("1", request.getQueryStringParameter("id"));
        assertEquals("javajigi", request.getRequestBodyParameter("userId"));
    }
}