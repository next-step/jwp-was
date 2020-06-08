package http;

import http.enums.HttpResponseCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpResponseTest {

    @Test
    void makeResponseHeaderTest() {
        Header httpHeaderInfo = new Header();
        httpHeaderInfo.addKeyAndValue("Location", "http://www.iana.org/domains/example/");
        String response = "HTTP/1.1 302 Found\n" +
                "Location: http://www.iana.org/domains/example/\n\n";
        HttpResponse httpResponse = new HttpResponse(HttpResponseCode.REDIRECT, new byte[0], httpHeaderInfo);
        assertTrue(response.equals(new String(httpResponse.makeResponseBody())));
    }
}
