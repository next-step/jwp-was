package webserver;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        HttpRequest request = new HttpRequest(in);

        assertEquals("GET", request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getBodyParameter("userId"));
    }

    @Test
    public void request_GET2() {
        //given
        String sampleLine = "GET /user/create?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        List<String> sampleHeaders = List.of(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");

        RequestLine requestLine = RequestLine.parse(sampleLine);
        HttpHeader httpHeader = new HttpHeader();
        for (String sampleHeader : sampleHeaders) {
            httpHeader.addHeader(sampleHeader);
        }

        // when
        HttpRequest httpRequest = new HttpRequest(httpHeader, requestLine);

        // then
        assertEquals("GET", httpRequest.getMethod().name());
        assertEquals("/user/create", httpRequest.getPath());
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("javajigi", httpRequest.getQueryParameter("userId"));
    }
}
