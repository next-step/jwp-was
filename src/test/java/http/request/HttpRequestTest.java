package http.request;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        HttpRequest request = new HttpRequest(new BufferedReader(inputStreamReader));

        assertEquals("GET", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        HttpRequest request = new HttpRequest(new BufferedReader(inputStreamReader));

        assertEquals("POST", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }
}
