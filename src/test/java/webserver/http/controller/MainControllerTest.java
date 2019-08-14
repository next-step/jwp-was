package webserver.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.view.HtmlViewResolver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class MainControllerTest {

    private MainController<HtmlViewResolver> mainController;

    @BeforeEach
    void setUp() {
        mainController = new MainController<>(new HtmlViewResolver());
    }

    @DisplayName("GET 요청 테스트")
    @Test
    void mainControllerGetTest() throws IOException {
        String getRequest = "GET /index HTTP/1.1\nAccept: */*\n";

        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(getRequest.getBytes()));
        HttpResponse httpResponse = new HttpResponse(new ByteArrayOutputStream());
        mainController.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("지원하지 않는 POST 요청 테스트")
    @Test
    void mainControllerNotAllowMethodTest() throws IOException {
        String postRequest = "POST /index HTTP/1.1\nAccept: */*\n";

        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(postRequest.getBytes()));
        HttpResponse httpResponse = new HttpResponse(new ByteArrayOutputStream());
        mainController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }
}