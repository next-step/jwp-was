package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    public void requestGET() throws Exception {
        HttpRequest request = HttpRequest.parseFrom(createBufferedReader("Http_GET.txt"));

        assertEquals(HttpMethod.GET, request.getRequestLine().getMethod());
        assertEquals("/user/create", request.getRequestLine().getUrl().getPath());
        assertEquals("keep-alive", request.getRequestHeader().getConnection());
        assertEquals("javajigi", request.getRequestLine().getUrl().getQueryParameter().getParameter("userId"));
    }

    @Test
    public void requestPOST() throws Exception {
        HttpRequest request = HttpRequest.parseFrom(createBufferedReader("Http_POST.txt"));

        assertEquals(HttpMethod.POST, request.getRequestLine().getMethod());
        assertEquals("/user/create", request.getRequestLine().getUrl().getPath());
        assertEquals("keep-alive", request.getRequestHeader().getConnection());
        assertEquals("javajigi", request.getRequestBody().getContent("userId"));
    }

    private BufferedReader createBufferedReader(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        final InputStream in = new FileInputStream(testDirectory + fileName);
        return new BufferedReader(new InputStreamReader(in, "UTF-8"));
    }
}
