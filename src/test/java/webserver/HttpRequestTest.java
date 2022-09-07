package webserver;

import model.http.HttpHeader;
import model.http.HttpMethod;
import model.http.HttpRequest;
import model.request.RequestBody;
import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {

        HttpRequest request = createRequest("Http_GET.http");

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    @Test
    public void request_POST() throws Exception {
        HttpRequest request = createRequest("Http_POST.http");

        assertEquals(HttpMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getParameter("userId"));
    }

    private HttpRequest createRequest(String fileName) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + fileName));

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        final RequestLine requestLine = new RequestLine(IOUtils.readRequestData(bufferedReader));
        final HttpHeader httpHeader = new HttpHeader(IOUtils.readHeaderData(bufferedReader));
        final RequestBody body = new RequestBody(IOUtils.readData(bufferedReader, httpHeader.getValueToInt("Content-Length")));

        return new HttpRequest(httpHeader, requestLine, body);
    }
}
