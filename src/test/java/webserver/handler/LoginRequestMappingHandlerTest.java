package webserver.handler;

import db.DataBase;
import model.User;
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

public class LoginRequestMappingHandlerTest {

    private RequestMappingHandler handler;

    @BeforeEach
    void setUp() {
        handler = new LoginRequestMappingHandler(new HtmlViewResolver());
    }

    @Test
    @DisplayName("Move login page")
    void doGet() throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "GET /user/login.html HTTP/1.1",
                "Host: www.nowhere123.com",
                "Accept-Language: en-us",
                "Accept-Encoding: gzip, deflate",
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)"
        ));

        HttpResponse httpResponse = handler.doHandle(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getBody()).isNotEmpty();
    }

    @DisplayName("Login and redirect")
    @ParameterizedTest(name = "body : {0} | Location : {1} | logined : {2}")
    @CsvSource(value = {
            "userId=javajigi&password=password | /index.html             | true",
            "userId=javajigi&password=failed   | /user/login_failed.html | false"
    }, delimiter = '|')
    void doPost(String body, String expectedLocation, boolean expectedLogined) throws Exception {
        DataBase.addUser(new User("javajigi", "password", "Name", "abc@google.com"));

        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "POST /user/login HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 33",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                body
        ));

        HttpResponse httpResponse = handler.doHandle(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(httpResponse.getHeader("Location")).isEqualTo(expectedLocation);
        assertThat(httpResponse.getHeader("Set-Cookie")).contains("logined=" + expectedLogined);
        assertThat(httpResponse.getBody()).isNotEmpty();
    }

    @DisplayName("Check to handle url")
    @ParameterizedTest
    @CsvSource(value = {
            "/user/login      | true",
            "/user/login.html | true",
            "/user/login_1    | false"
    }, delimiter = '|')
    void canHandle(String path, boolean expected) throws Exception {
        HttpRequest httpRequest = HttpRequest.parse(HttpRequestTest.createInputStream(
                "GET " + path + " HTTP/1.1"
        ));

        assertThat(handler.canHandle(httpRequest)).isEqualTo(expected);
    }
}