package webserver.controller;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import exception.HttpMethodNotSupportedException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

class UserCreateControllerTest {

    @AfterEach
    void teardown() {
        DataBase.deleteAll();
    }

    @DisplayName("사용자 등록 요청 시, HTTP 메서드가 GET인 경우 HttpMethodNotSupportedException 예외가 발생한다.")
    @Test
    void invalidHttpMethod() {
        String loginRequest = "GET /user/create HTTP/1.1";
        HttpRequest request = new HttpRequest(new ByteArrayInputStream(loginRequest.getBytes(UTF_8)));
        HttpResponse response = new HttpResponse(new ByteArrayOutputStream());

        UserCreateController controller = new UserCreateController();

        assertThatThrownBy(() -> controller.handle(request, response))
            .isInstanceOf(HttpMethodNotSupportedException.class);
    }

    @DisplayName("사용자 등록 요청 시, 사용자를 등록한 다음 302 Found 응답과 함께 index.html로 리다이렉트한다.")
    @Test
    void create() throws IOException, URISyntaxException {
        String userCreateRequest = userCreateRequest();

        HttpRequest request = new HttpRequest(new ByteArrayInputStream(userCreateRequest.getBytes(UTF_8)));
        HttpResponse response = new HttpResponse(new ByteArrayOutputStream());

        UserCreateController controller = new UserCreateController();
        controller.handle(request, response);

        assertThat(DataBase.findUserById("user")).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
    }

    private String userCreateRequest() {
        String lineSeparator = System.lineSeparator();
        String requestBody = "userId=user&password=1234&name=유저&email=user@example.com";

        StringBuilder builder = new StringBuilder("POST /user/create HTTP/1.1").append(lineSeparator);
        builder.append("Content-Type: application/x-www-form-urlencoded").append(lineSeparator);
        builder.append(String.format("Content-Length: %d", requestBody.length())).append(lineSeparator);
        builder.append(lineSeparator);
        builder.append(requestBody);
        return builder.toString();
    }
}
