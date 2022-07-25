package webserver.servlet;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.enums.StatusCode;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

class UserCreateServletTest {

    private Servlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new UserCreateServlet();

        DataBase.deleteAll();
    }

    @Test
    void createTest() {
        HttpRequest httpRequest = new HttpRequest("POST /user/create HTTP/1.1");
        httpRequest.addHeader("Host: localhost:8080");
        httpRequest.addHeader("Connection: keep-alive");
        httpRequest.addHeader("Content-Length: 59");
        httpRequest.addHeader("Content-Type: application/x-www-form-urlencoded");
        httpRequest.addHeader("Accept: */*");
        httpRequest.setBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        HttpResponse httpResponse = new HttpResponse();

        servlet.serve(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(StatusCode.FOUND);
        assertThat(DataBase.findUserById("javajigi")).isNotNull();
    }

}
