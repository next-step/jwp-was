package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.RequestControllerContainer;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.RequestLine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CreatUserControllerTest {

    @BeforeEach
    void setup() {
        DataBase.clear();
    }

    @Test
    void service_post() throws Exception {
        RequestLine requestLine = new RequestLine("POST /user/create HTTP/1.1");
        String body = "userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net";

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Content-Length", String.valueOf(body.getBytes().length));
        headers.put("Accept", "*/*");

        OutputStream outputStream = new ByteArrayOutputStream();

        HttpRequest httpRequest = new HttpRequest(requestLine, headers, body);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        RequestControllerContainer.match(httpRequest)
                .service(httpRequest, httpResponse);

        String response = outputStream.toString();

        assertThat(response).contains("HTTP/1.1 " + HttpStatus.FOUND + " \r\n");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8 \r\n");
        assertThat(response).contains("Location: /index.html \r\n");

        User user = DataBase.findUserById("javajigi");

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.checkPassword("password")).isTrue();
        assertThat(user.getName()).isEqualTo("JaeSung");
        assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void serving_get() throws Exception {
        RequestLine requestLine = new RequestLine("GET /user/create?" +
                "userId=javajigi&" +
                "password=password&" +
                "name=JaeSung&" +
                "email=javajigi@slipp.net" +
                " HTTP/1.1");

        OutputStream outputStream = new ByteArrayOutputStream();

        HttpRequest httpRequest = new HttpRequest(requestLine, null, null);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        RequestControllerContainer.match(httpRequest)
                .service(httpRequest, httpResponse);

        String response = outputStream.toString();

        assertThat(response).contains("HTTP/1.1 " + HttpStatus.NOT_FOUND + " \r\n");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8 \r\n");

        User user = DataBase.findUserById("javajigi");

        assertThat(user).isNull();
    }

}