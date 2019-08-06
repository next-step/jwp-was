package webserver.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.*;
import webserver.resolver.HandlebarsViewResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListRequestMappingHandlerTest {

    private RequestMappingHandler handler;

    @BeforeEach
    void setUp() {
        handler = new UserListRequestMappingHandler(new HandlebarsViewResolver());
    }

    @DisplayName("If logined, access user list ")
    @ParameterizedTest(name = "logined : {0} -> statusCode : {1}")
    @CsvSource(value = {
            "true  | OK",
            "false | FOUND"
    }, delimiter = '|')
    void doGet(boolean logined, HttpStatusCode expectedStatusCode) throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "GET /user/list HTTP/1.1",
                "Cookie: " + CustomCookie.LOGINED + "=" + logined
        ));

        HttpResponse httpResponse = handler.doHandle(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getStatusCode()).isEqualTo(expectedStatusCode);
        assertThat(httpResponse.getBody()).isNotEmpty();
    }

    @DisplayName("Check to handle url")
    @ParameterizedTest
    @CsvSource(value = {
            "/user/list  | true",
            "/user/lists | false"
    }, delimiter = '|')
    void canHandle(String path, boolean expected) throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "GET " + path + " HTTP/1.1"
        ));

        assertThat(handler.canHandle(httpRequest)).isEqualTo(expected);
    }
}