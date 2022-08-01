package webserver.servlet;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.enums.HttpStatus;
import webserver.request.HttpRequestBody;
import webserver.request.HttpRequestHeader;
import webserver.request.HttpRequest;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;

class UserCreateControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new UserCreateController();

        DataBase.deleteAll();
    }

    @Test
    void createTest() {
        RequestLine requestLine = RequestLine.of("POST /user/create HTTP/1.1");
        HttpRequestHeader httpHeader = HttpRequestHeader.createEmpty();
        httpHeader.putHeader("Host", "localhost:8080");
        httpHeader.putHeader("Connection", "keep-alive");
        httpHeader.putHeader("Content-Length", "59");
        httpHeader.putHeader("Content-Type", "application/x-www-form-urlencoded");
        httpHeader.putHeader("Accept", "*/*");
        HttpRequestBody httpBody = HttpRequestBody.of("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        HttpRequest httpRequest = new HttpRequest(requestLine, httpHeader, httpBody);
        HttpResponse httpResponse = new HttpResponse();

        controller.service(httpRequest, httpResponse);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(DataBase.findUserById("javajigi")).isNotNull();
    }

}
