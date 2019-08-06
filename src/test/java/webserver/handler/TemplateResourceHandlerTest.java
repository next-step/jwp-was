package webserver.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestTest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatusCode;
import webserver.resolver.HtmlViewResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateResourceHandlerTest {

    private TemplateResourceHandler handler;

    @BeforeEach
    void setUp() {
        handler = new TemplateResourceHandler(new HtmlViewResolver());
    }

    @Test
    void doHandle() throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "GET /index.html HTTP/1.1",
                        "Host: www.nowhere123.com",
                        "Accept: image/gif, image/jpeg, */*",
                        "Accept-Language: en-us",
                        "Accept-Encoding: gzip, deflate",
                        "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)"
        ));

        HttpResponse httpResponse = handler.doHandle(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getBody()).isNotEmpty();
    }

    @DisplayName("This handler can handle html file")
    @ParameterizedTest
    @CsvSource(value = {
            "/index.html | true",
            "/script.js  | false",
    }, delimiter = '|')
    void canHandle(String path, boolean expected) throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "GET " + path + " HTTP/1.1"
        ));

        assertThat(handler.canHandle(httpRequest)).isEqualTo(expected);
    }
}