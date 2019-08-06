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

public class UserCreateRequestMappingHandlerTest {

    private RequestMappingHandler handler;

    @BeforeEach
    void setUp() {
        handler = new UserCreateRequestMappingHandler(new HtmlViewResolver());
    }

    @Test
    @DisplayName("Move user create page")
    void doGet() throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "GET /user/create HTTP/1.1",
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

    @Test
    @DisplayName("Move user create page")
    void doPost() throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 93",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        ));

        HttpResponse httpResponse = handler.doHandle(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(httpResponse.getBody()).isNotEmpty();
    }

    @DisplayName("Check to handle url")
    @ParameterizedTest
    @CsvSource(value = {
            "/user/create | true",
            "/user        | false"
    }, delimiter = '|')
    void canHandle(String path, boolean expected) throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "GET " + path + " HTTP/1.1"
        ));

        assertThat(handler.canHandle(httpRequest)).isEqualTo(expected);
    }
}