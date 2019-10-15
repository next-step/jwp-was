package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.mapping.RequestMapping;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    @DisplayName("302 Found with Cookie")
    @Test
    void response() {
        String filePath = "/index.html";
        String[] cookies = {"logined=true; Path=/"};
        byte[] responseBody = RequestMapping.getFilePath(filePath).getBytes();

        HttpResponse httpResponse = new HttpResponse().found(filePath, responseBody, cookies);

        logger.debug(httpResponse.getResponseHeader());
    }

    @DisplayName("302 Found with Cookies")
    @Test
    void response_with_cookies() {
        String filePath = "/index.html";
        String[] cookies = {"session-id=12345; Max-Age=-1;", "session-id=67890;"};
        byte[] responseBody = RequestMapping.getFilePath(filePath).getBytes();

        HttpResponse httpResponse = new HttpResponse().found(filePath, responseBody, cookies);

        String responseHeader = httpResponse.getResponseHeader();
        String expectedResHeader = "HTTP/1.1 302 Found\r\n" +
                "Location : /index.html\r\n" +
                "Set-Cookie: session-id=12345; Max-Age=-1;\r\n" +
                "Set-Cookie: session-id=67890;\r\n\r\n";

        assertThat(responseHeader).isEqualTo(expectedResHeader);
    }
}
